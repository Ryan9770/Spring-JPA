package com.spring.practice.domain;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.PrePersist;
import javax.persistence.Table;

import org.hibernate.annotations.ColumnDefault;

@Entity
@Table(name = "bbs")
public class Board implements Serializable {
	private static final long serialVersionUID = 1L;
	
	@Id
	@Column(name = "num")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long num;
	
	@Column(name = "name", nullable = false, length = 30)
	private String name;
	
	@Column(name = "pwd", nullable = false, length = 50)
	private String pwd;
	
	@Column(name = "subject", nullable = false, length = 500)
	private String subject;
	
	@Column(name = "content", nullable = false, length = 4000)
	private String content;
	
	@Column(name = "ipAddr", nullable = false, length = 50, updatable = false)
	private String ipAddr;
	
	@Column(nullable = false, columnDefinition = "DATETIME DEFAULT SYSDATE()",
			updatable = false)
	private String reg_date;
	
	@Column(nullable = false, columnDefinition = "INT",
			insertable = false, updatable = false)
	@ColumnDefault("0")
	private int hitCount;
	
	@PrePersist // INSERT 전 호출
	public void prePersist() {
		this.reg_date = this.reg_date == null? new java.sql.Date(new java.util.Date().getTime()).toString() : this.reg_date;
	}
	
	public long getNum() {
		return num;
	}
	public void setNum(long num) {
		this.num = num;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getPwd() {
		return pwd;
	}
	public void setPwd(String pwd) {
		this.pwd = pwd;
	}
	public String getSubject() {
		return subject;
	}
	public void setSubject(String subject) {
		this.subject = subject;
	}
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}
	public String getIpAddr() {
		return ipAddr;
	}
	public void setIpAddr(String ipAddr) {
		this.ipAddr = ipAddr;
	}
	public String getReg_date() {
		return reg_date;
	}
	public void setReg_date(String reg_date) {
		this.reg_date = reg_date;
	}
	public int getHitCount() {
		return hitCount;
	}
	public void setHitCount(int hitCount) {
		this.hitCount = hitCount;
	}
	
}
