package com.cos.blog.test;

import com.cos.blog.model.RoleType;
import com.cos.blog.model.User;
import com.cos.blog.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.web.bind.annotation.*;

import javax.transaction.Transactional;
import java.util.List;

@RestController
@RequestMapping("/dummy")
public class DummyControllerTest {
    @Autowired // 외존성 주입
    private UserRepository userRepository;

    @GetMapping("/users")
    public List<User> list(){
        return userRepository.findAll();
    }

    //한 페이지당 2건의 데이터를 리턴 받아 사용
    @GetMapping("/user")
    public List<User> pageList(@PageableDefault(size = 2, sort = "id", direction = Sort.Direction.DESC)Pageable pageable){
        Page<User> pagingUser = userRepository.findAll(pageable);
        List<User> users = pagingUser.getContent();
        return users;
    }

    @Transactional // 함수 종료시에 자동 commit이 된다. 더티체킹 방식
    @PutMapping("/user/{id}")
    public User updateUser(@PathVariable Long id, @RequestBody User requestUser){
        System.out.println("id : " + id);
        System.out.println("password : " + requestUser.getPassword());
        System.out.println("email : " + requestUser.getEmail());

        User user = userRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("수정에 실패하였습니다."));

        user.setPassword(requestUser.getPassword());
        user.setEmail(requestUser.getEmail());

//        userRepository.save(user);

        //더티 체킹
        //영속화된 user랑 변경된 user를 비교한 후 다르면 업데이트를 수행한다.

        return user;
    }

    @DeleteMapping("/user/{id}")
    public String delete(@PathVariable Long id){
        try {
            userRepository.deleteById(id);
        }catch (EmptyResultDataAccessException e){
            return "삭제에 실패하였습니다. 해당 id는 없습니다.";
        }

        return "삭제되었습니다. id : " + id;

    }



    @PostMapping("/join")
    public String joinTest(User user){
        System.out.println("username: " + user.getUsername());
        System.out.println("password: " + user.getPassword());
        System.out.println("email: " + user.getEmail());

        user.setRole(RoleType.USER);
        userRepository.save(user);

        return "회원가입이 성공했습니다.";
    }
    
    //Optional 로 한번 감싸서 반환함
    @GetMapping("/user/{id}")
    public User detail(@PathVariable Long id) {
        User user = userRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("해당 유저는 없습니다. id : " + id));
        return user;
    }
}
