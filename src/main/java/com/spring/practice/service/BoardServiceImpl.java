package com.spring.practice.service;

import java.util.NoSuchElementException;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import com.spring.practice.domain.Board;
import com.spring.practice.repository.BoardRepository;

// @Transactional(readOnly = true)
@Service
public class BoardServiceImpl implements BoardService {
	@Autowired
	private BoardRepository boardRepository;
	
	@Override
	public void insertBoard(Board entity) throws Exception {
		try {
			boardRepository.save(entity);
		} catch (Exception e) {
			e.printStackTrace();
			throw e;
		}
	}

	
	@Override
	public Board readBoard(long num) {
		Board dto = null;
		
		try {
			Optional<Board> board = boardRepository.findById(num);
			dto = board.get();
			              			
		}catch (NoSuchElementException e) {
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return dto;
	}

	@Override
	public Board preBoard(String condition, String keyword, long num) {
		Board dto = null;
		
		try {
			if(keyword.length()==0) {
				dto = boardRepository.findByPreBoard(num);
			} else if(condition.equals("name")) {
				dto = boardRepository.findByPreBoardName(num, keyword);
			} else if(condition.equals("all")) {
				dto = boardRepository.findByPreBoardAll(num, keyword);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return dto;
	}

	@Override
	public Board nextBoard(String condition, String keyword, long num) {
		Board dto = null;
		
		try {
			if(keyword.length()==0) {
				dto = boardRepository.findByNextBoard(num);
			} else if(condition.equals("name")) {
				dto = boardRepository.findByNextBoardName(num, keyword);
			} else if(condition.equals("all")) {
				dto = boardRepository.findByNextBoardAll(num, keyword);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return dto;
	}
	
	@Override
	public void updateBoard(Board entity) throws Exception {
		try {
			// save : 존재하면 수정, 없으면 추가
			boardRepository.save(entity);
		} catch (Exception e) {
			e.printStackTrace();
			throw e;
		}
	}
	
	@Override
	public void deleteBoard(long num) throws Exception{
		try {
			boardRepository.deleteById(num);
                			
		} catch (Exception e) {
			e.printStackTrace();
			throw e;
		}
	}
	
	@Override
	public void updateHitCount(long num) throws Exception {
		try {
			boardRepository.updateHitCount(num);
		} catch (Exception e) {
			e.printStackTrace();
			throw e;
		}
	}

	@Override
	public Page<Board> pageBoard(String condition, String keyword, int current_page, int size){
		Page<Board> page = null;
		// PageRequest : Pageable 구현 클래스
		try {
			Pageable pageable = PageRequest.of(current_page-1, size, Sort.by(Sort.Direction.DESC, "num"));
			if(keyword.length()==0) {
				page = boardRepository.findAll(pageable);
			} else if(condition.equals("name")){
				page = boardRepository.findByName(keyword, pageable);
			} else {
				page = boardRepository.findByAll(keyword, pageable);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return page;

	}


	
}
