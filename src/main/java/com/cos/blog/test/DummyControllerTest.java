package com.cos.blog.test;

import com.cos.blog.model.RoleType;
import com.cos.blog.model.User;
import com.cos.blog.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.function.Supplier;

@RestController
public class DummyControllerTest {
    @Autowired // 외존성 주입
    private UserRepository userRepository;

    @PostMapping("/dummy/join")
    public String joinTest(User user){
        System.out.println("username: " + user.getUsername());
        System.out.println("password: " + user.getPassword());
        System.out.println("email: " + user.getEmail());

        user.setRole(RoleType.USER);
        userRepository.save(user);
        return "회원가입이 성공했습니다.";
    }
    
    //Optional 로 한번 감싸서 반환함
    @GetMapping("/dummy/user/{id}")
    public User detail(@PathVariable Long id) {
        User user = userRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("해당 유저는 없습니다. id : " + id));
        return user;
    }
}
