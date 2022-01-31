# springbootBlog
스프링부트 + JPA + Mysql  + 스프링 시큐리티를 활용한 간단한 블로그 제작하기

[TOC]

## 시작하기

### 구현목표

- 스프링 시큐리티를 이용한 로그인
- Oaut2를 활용한 카카오 로그인 기능
- 게시글 업로드, 보기, 수정, 삭제 기능



### 추가 라이브러리

```xml
<!-- SpringBoot -->
spring-boot-starter-data-jpa
spring-boot-starter-security
spring-boot-devtools
spring-boot-starter-test
    
<!-- DB -->
mysql-connector-java 
    
<!-- SpringSecurity -->
spring-security-test
spring-security-taglibs
    
<!-- Util -->
lombok
    
<!-- JSP 템플릿 엔진 -->
tomcat-embed-jasper
    
<!-- JSTL -->
jstl
```



## JPA 사용하기

### Model

#### Board

Reply(댓글) 과 강한 상관관계로 서로 연결되어 있다. User와 다대일로 매핑되어 있다.

```java
@Builder // ORM -> Java Object 테이블로 매핑해주는 기술
@NoArgsConstructor
@AllArgsConstructor
@Data
@Entity //User 클래스가 MySQL에 테이블이 생성이 된다.
public class Board {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(nullable = false, length = 100)
    private String title;

    @Lob // 대용량 데이터
    private String content; // 섬머노트 라이브러리

    //@ColumnDefault("0")
    private long count; // 조회수

    @ManyToOne(fetch = FetchType.EAGER) //Many = Board, User = One
    @JoinColumn(name = "userId")
    private User user; // DB는 오브젝트를 저장할 수 없지만 FK, 자바는 오브젝트를 저장가능하다

    @OneToMany(mappedBy = "board", fetch = FetchType.EAGER) 
    // mappedBy 연관관계의 주인이 아니다(난 FK가 아니다) DB에 컬럼을 만들지 말아야함
    private List<Reply> reply;

    @CreationTimestamp
    private Timestamp createDate;
}
```

#### Reply

Board 테이블과 다대일로 매핑, User 테이블과 다대일로 매핑

```java
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Data
@Entity
public class Reply {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(nullable = false, length = 200)
    private String content; // 섬머노트 라이브러리

    @ManyToOne
    @JoinColumn(name = "boardId")
    private Board board;

    @ManyToOne
    @JoinColumn(name = "userId")
    private User user;

    @CreationTimestamp
    private Timestamp createDate;
}
```

#### User

```java
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Data
@Entity
//@DynamicInsert    //insert시에 null값을 제거해준다.
public class User {
    @Id // Primary key
    @GeneratedValue(strategy = GenerationType.IDENTITY) // 연결된 DB의 넘버링 전략을 따라간다.
    private long id;

    @Column(nullable = false, length = 30)
    private String username;

    @Column(nullable = false, length = 100)
    private String password;

    @Column(nullable = false, length = 50)
    private String email;

//    @ColumnDefault(RoleType.USER)
    @Enumerated(EnumType.STRING)
    private RoleType role; // admin, user, manager

    @CreationTimestamp // 시간 자동 입력
    private Timestamp createDate;
}
```

### UserRepository

#### JPA 네이밍 쿼리 전략

```java
public interface UserRepository extends JpaRepository<User, Long> {
    @Query(value = "SELECT * FROM user WHERE username = ?1", nativeQuery = true)
    Optional<User> findByUsername(String username);
}

// JPA 네이밍 쿼리 전략
// SELECT * FROM user WHERE username = ? AND password = ?
//    User findByUsernameAndPassword(String username, String password);

// 네이티브 쿼리
//    @Query(value = "SELECT * FROM user WHERE username = ?1 AND password = ?2", nativeQuery = true)
//    User login(String username, String password);
```



## Spring Security 사용하기

자세한 시큐리티는 다른 예제를 통해 설명을 할거라서 기본적인 것만 설명을 한다.

#### SecurityConfig

WebSecurityConfigurer Adapter를 상속 받아 만들어지고 필요한 것들을 오버라이딩 해서 사용한다.

권한에 따른 페이지 분류를 하지 않고 기본적인 예제만 적용해보았다.

```java
@Configuration
@EnableWebSecurity // 필터를 거는것
@EnableGlobalMethodSecurity(prePostEnabled = true) 
// 특정 주소로 접근을 하면 권한 및 인증을 미리 체크하겠다는 뜻
//셋트임
public class SecurityConfig extends WebSecurityConfigurerAdapter {
    @Bean
    public BCryptPasswordEncoder encodePWD(){
        return new BCryptPasswordEncoder();
    } // 비밀번호 암호화 DI 사용

    @Bean
    @Override
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean();
    } // Authentication 객체를 사용하여 인증가능

    @Autowired
    private PrincipalDetailService principalDetailService;

    // 시큐리티가 대신 로그인 해주는 password 가로채기를 하는데
    // 해당 password가 뭘로 해쉬가 되어 회원가입이 되었는지 알아야
    // 같은 해쉬로 암호화해서 db에 있는 해쉬랑 비교할 수 있다.
    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(principalDetailService).passwordEncoder(encodePWD());
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                .csrf().disable() // csrf 토큰 비활성화 테스트시 걸어두는게 좋음
                .authorizeRequests()
                .antMatchers("/","/auth/**", "/js/**", "/css/**", "/image/**")
                .permitAll()
                .anyRequest()
                .authenticated()
                .and()
                .formLogin()
                .loginPage("/auth/loginform")
                .loginProcessingUrl("/auth/loginProc") // 스프링 시큐리티가 로그인을 가로챈다
                .defaultSuccessUrl("/");
    }
}
```

#### PrincipalDetail과 Service

UserDetail와 UserDetailService를 추상화 받은 것을 구체화하여 적용한다.

해당 메써드에 맞게 데이터를 반환하게 연결시키면 된다.
