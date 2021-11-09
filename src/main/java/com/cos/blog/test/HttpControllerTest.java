package com.cos.blog.test;

import com.cos.blog.model.RoleType;
import com.cos.blog.model.User;
import com.cos.blog.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
public class HttpControllerTest {
    @Autowired // 외존성 주입
    private UserRepository userRepository;

    

    @GetMapping("/http/get")
    public String getTest(){
        return "get요청";
    }


    //인터넷넷 브라우저는 조건 get 요청만 가능하다
    @PostMapping("/http/post")
    public String postTest(){
        System.out.println("post요청");
        return "post요청";
    }

    //인터넷 브라우저는 조건 get 요청만 가능하다
    @PostMapping("/http/join")
    public String joinTest(User user){
        System.out.println("username: " + user.getUsername());
        System.out.println("password: " + user.getPassword());
        System.out.println("email: " + user.getEmail());

        user.setRole(RoleType.USER);
        userRepository.save(user);
        return "회원가입이 성공했습니다.";
    }

    @PutMapping("/http/put")
    public String putTest(){
        return "put요청";
    }

    @DeleteMapping("/http/delete")
    public String deleteTest(){
        return "delete요청";
    }
}
