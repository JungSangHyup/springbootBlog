package com.cos.blog.service;

import com.cos.blog.model.Board;
import com.cos.blog.model.User;
import com.cos.blog.repository.BoardRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class BoardService {
    @Autowired
    private BoardRepository boardRepository;



    @Transactional
    public void 글쓰기(Board board, User user){
        board.setCount(0L);
        board.setUser(user);
        boardRepository.save(board);
    }

    public List<Board> 글목록(){
        return boardRepository.findAll();
    }
}
