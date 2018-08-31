package org.zerock.vo;

import java.util.ArrayList;
import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import lombok.Getter;
import lombok.ToString;
import lombok.extern.java.Log;

@Getter
@ToString(exclude="pageList")
@Log
public class PageMaker<T> {
	
	private Page<T> result;
	
	private Pageable prevPage; //이전페이지
	private Pageable nextPage; //다음페이지
	
	private int currentPageNum;
	private int totalPageNum;
	
	private Pageable currentPage;
	
	private List<Pageable> pageList;
	
	public PageMaker(Page<T> result) {
		
		this.result = result;
		this.currentPage = result.getPageable();
		this.currentPageNum = currentPage.getPageNumber()+1;
		this.totalPageNum = result.getTotalPages();
		this.pageList = new ArrayList<>();
		
		calcPages();
	}
	
	private void calcPages() {
		
		//Math.ceil : 소수점 이하를 올림
		int tempEndNum = (int)(Math.ceil(this.currentPageNum/10.0)*10);
		int startNum = tempEndNum -9;
		Pageable startPage = this.currentPage;
		
		//move to start Pageable
		for(int i = startNum; i < this.currentPageNum; i++) {
			startPage = startPage.previousOrFirst();
		}
		this.prevPage = startPage.getPageNumber() <= 0? null :startPage.previousOrFirst();
		
		log.info("tempEndNum: " + tempEndNum);
		log.info("total:" + totalPageNum);
		log.info("prevPage: " + prevPage);
		log.info("startPage: " + startPage.getPageNumber());
		
		if(this.totalPageNum < tempEndNum) {
			tempEndNum = this.totalPageNum;
			this.nextPage = null;
		}
		
		for(int i = startNum; i <= tempEndNum; i++) {
			pageList.add(startPage);
			startPage = startPage.next();
		}
		this.nextPage = startPage.getPageNumber() +1 < totalPageNum ? startPage:null;
	}
	
	/*PageMaker::
	 * 화면에 출력할 결과 Page<T>를 생성자로 전달받고, 내부적으로 페이지 계산을 처리한다.
	 * prevPage: 페이지 목록의 맨 앞인 '이전' 으로 이동하는데 필요한 정보를 가진 Pageable
	 * nextPage: 페이지 목록의 맨 뒤인 '다음' 으로 이동하는데 필요한 정보를 가진 Pageable
	 * currentPage: 현재 페이지의 정보를 가진 Pageable
	 * pageList: 페이지 번호의 시작부터 끝까지의 Pageable들을 저장한 리스트
	 * currentPageNum: 화면에 보이는 1부터 시작하는 페이지 번호
	 * */
	

}
