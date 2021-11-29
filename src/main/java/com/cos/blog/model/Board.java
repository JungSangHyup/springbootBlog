package com.cos.blog.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Timestamp;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.Lob;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;

import org.hibernate.annotations.ColumnDefault;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.Fetch;


@Builder // ORM -> Java Object 테이블로 매핑해주는 기술
@NoArgsConstructor
@AllArgsConstructor
@Data
@Entity //User 클래스가 MySQL에 테이블이 생성이 된다.
public class Board {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, length = 100)
    private String title;

    @Lob // 대용량 데이터
    private String content; // 섬머노트 라이브러리

//    @ColumnDefault("0")
    private Long count; // 조회수

    @ManyToOne(fetch = FetchType.EAGER) //Many = Board, User = One
    @JoinColumn(name = "userId")
    private User user; // DB는 오브젝트를 저장할 수 없지만 FK, 자바는 오브젝트를 저장가능하다

    @OneToMany(mappedBy = "board", fetch = FetchType.EAGER) // mappedBy 연관관계의 주인이 아니다(난 FK가 아니다) DB에 컬럼을 만들지 말아야함
    private List<Reply> reply;

    @CreationTimestamp
    private Timestamp createDate;
}
