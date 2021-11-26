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

        user.setUsername("jsh170303");
        user.setPassword("123456");
        user.setEmail("jsh170303@naver.com");

        userService.회원가입(user);

        User user2 = userRepository.findByUsername(user.getUsername());


        Assertions.assertThat(user2.getUsername()).isEqualTo(user.getUsername());
    }
}