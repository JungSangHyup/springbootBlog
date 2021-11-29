package com.cos.blog.service;

import com.cos.blog.model.User;
import com.cos.blog.repository.UserRepository;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

class UserServiceTest {
    @Autowired
    private UserService userService;

    @Autowired
    private UserRepository userRepository;

    @Test
    public void 회원가입(){
        User user = new User();
        User user2 = null;

        user.setUsername("jsh170303");
        user.setPassword("123456");
        user.setEmail("jsh170303@naver.com");

        try{
            user2 = userRepository.findByUsername(user.getUsername())
                    .orElseThrow(() -> new NullPointerException()
                    );
        }catch (NullPointerException e){
            Assertions.fail("Not found User!");
        }
        Assertions.assertThat(user2.getUsername()).isEqualTo(user.getUsername());
    }
}