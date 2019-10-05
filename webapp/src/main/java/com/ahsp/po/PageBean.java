package com.ahsp.po;

import java.io.Serializable;
import java.util.List;

public class PageBean<T> implements Serializable{

	private static final long serialVersionUID = -1515791330539207219L;
	private int pc; //当前页码 pageCode
	//private int tp; //总页数total pages
	private int tr; //总记录数total record
	private int ps; //每页记录数 page size
	private List<T> beanList; //每页的查询结果
	
	public int getPc() {
		return pc;
	}
	public void setPc(int pc) {
		this.pc = pc;
	}
	
	public int getTp() {
		int tp = tr / ps;
		return tr%ps==0 ? tp : tp+1 ; 
	}

	
	public int getTr() {
		return tr;
	}
	public void setTr(int tr) {
		this.tr = tr;
	}
	public int getPs() {
		return ps;
	}
	public void setPs(int ps) {
		this.ps = ps;
	}
	public List<T> getBeanList() {
		return beanList;
	}
	public void setBeanList(List<T> beanList) {
		this.beanList = beanList;
	}
}
