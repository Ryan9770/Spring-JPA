package com.spring.practice.repository;

import javax.transaction.Transactional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.spring.practice.domain.Board;

public interface BoardRepository extends JpaRepository<Board, Long> {
	
	@Query(
			value = "SELECT b FROM Board b WHERE b.name LIKE %:keyword%",
			countQuery = "SELECT COUNT(b.num) FROM Board b WHERE b.name LIKE %:keyword%"
			)
	public Page<Board> findByName(@Param("keyword") String keyword, Pageable pageable);
	
	@Query(
			value = "SELECT b FROM Board b WHERE b.subject LIKE %:keyword% OR b.content LIKE %:keyword%",
			countQuery = "SELECT COUNT(b.num) FROM Board b WHERE b.subject LIKE %:keyword% OR b.content LIKE %:keyword%"
			)
	public Page<Board> findByAll(@Param("keyword") String keyword, Pageable pageable);
	
	@Transactional 
	@Modifying
	@Query(value = "UPDATE bbs SET hitCount=hitCount+1 WHERE num=:num", nativeQuery = true)
	public int updateHitCount(@Param("num") long num);
	
	@Query(value = "SELECT * FROM bbs WHERE num>:num ORDER BY num ASC FETCH FIRST 1 ROWS ONLY", nativeQuery = true)
	public Board findByPreBoard(@Param("num") long num);
	@Query(value = "SELECT * FROM bbs WHERE num>:num AND name LIKE %:keyword% ORDER BY num ASC FETCH FIRST 1 ROWS ONLY", nativeQuery = true)
	public Board findByPreBoardName(@Param("num") long num, @Param("keyword") String keyword);
	@Query(value = "SELECT * FROM bbs WHERE num>:num AND (subject LIKE %:keyword% OR content LIKE %:keyword%) ORDER BY num ASC FETCH FIRST 1 ROWS ONLY", nativeQuery = true)
	public Board findByPreBoardAll(@Param("num") long num, @Param("keyword") String keyword);
	
	@Query(value = "SELECT * FROM bbs WHERE num<:num ORDER BY num DESC FETCH FIRST 1 ROWS ONLY", nativeQuery = true)
	public Board findByNextBoard(@Param("num") long num);
	@Query(value = "SELECT * FROM bbs WHERE num<:num AND name LIKE %:keyword% ORDER BY num DESC FETCH FIRST 1 ROWS ONLY", nativeQuery = true)
	public Board findByNextBoardName(@Param("num") long num, @Param("keyword") String keyword);
	@Query(value = "SELECT * FROM bbs WHERE num<:num AND (subject LIKE %:keyword% OR content LIKE %:keyword%) ORDER BY num DESC FETCH FIRST 1 ROWS ONLY", nativeQuery = true)
	public Board findByNextBoardAll(@Param("num") long num, @Param("keyword") String keyword);
}
