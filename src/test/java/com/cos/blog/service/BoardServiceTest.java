package com.cos.blog.service;

import com.cos.blog.model.Board;
import com.cos.blog.model.User;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import static org.junit.jupiter.api.Assertions.*;

class BoardServiceTest {
    private BoardService boardService;

    public BoardServiceTest(BoardService boardService) {
        this.boardService = boardService;
    }

    @Test
    void 글쓰기() {
        for(int i=1; i<=100; i++){
            Board board = new Board();
            board.setTitle(String.valueOf(i));
            board.setContent(String.valueOf(i));

            User user = new User();
            user.setUsername("test");
            user.setId((long) i);

            boardService.글쓰기(board, user);
        }
    }
}