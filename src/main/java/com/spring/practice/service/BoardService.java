package com.spring.practice.service;

import org.springframework.data.domain.Page;

import com.spring.practice.domain.Board;

public interface BoardService {
	public void insertBoard(Board dto) throws Exception;
	public void updateBoard(Board dto) throws Exception;
	public void deleteBoard(long num) throws Exception;
	
	public Page<Board> pageBoard(String condition, String keyword, int page, int size);
	
	public Board readBoard(long num);
	public Board preBoard(String condition, String keyword, long num);
	public Board nextBoard(String condition, String keyword, long num);
	
	public void updateHitCount(long num) throws Exception;
}
