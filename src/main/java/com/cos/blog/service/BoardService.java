package com.cos.blog.service;

import com.cos.blog.dto.ResponseDto;
import com.cos.blog.model.Board;
import com.cos.blog.model.User;
import com.cos.blog.repository.BoardRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class BoardService {
    @Autowired
    private BoardRepository boardRepository;

    public BoardService(BoardRepository boardRepository) {
        this.boardRepository = boardRepository;
    }

    @Transactional(readOnly = true)
    public Board 글상세보기(long id){
        return boardRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("글 상세보기 실패"));
    }


    @Transactional
    public void 글쓰기(Board board, User user){
        board.setCount(0L);
        board.setUser(user);
        boardRepository.save(board);
    }

    @Transactional(readOnly = true)
    public Page<Board> 글목록(Pageable pageable){
        return boardRepository.findAll(pageable);
    }

    @Transactional
    public void 글삭제(long id){
        boardRepository.deleteById(id);
    }
    
    @Transactional
    public void 글수정하기(long id, Board requestBoard) {
        Board board = boardRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("글 찾기 실패"));
        board.setTitle(requestBoard.getTitle());
        board.setContent(requestBoard.getContent());
        // 해당 함수로 종료시에 트랜잭션이 Service가 종료될 때 트랜잭션이 종료된다.
        // 이 때 더티 체킹이 일어나며 자동 업데이트가 된다
    }
}
