package org.zerock.persistence;

import org.springframework.data.querydsl.QuerydslPredicateExecutor;
import org.springframework.data.repository.CrudRepository;
import org.zerock.domain.QWebBoard;
import org.zerock.domain.WebBoard;

import com.querydsl.core.BooleanBuilder;
import com.querydsl.core.types.Predicate;

public interface WebBoardRepository extends CrudRepository<WebBoard, Long>, 
								QuerydslPredicateExecutor<WebBoard>{
	
	public default Predicate makePredicate(String type, String keyword) {
		
		//BooleanBuilder: 매개변수 조건에 따라서 동적으로 WHERE 절을 추가
		BooleanBuilder builder = new BooleanBuilder();
		
		QWebBoard board = QWebBoard.webBoard;
		
		//type if~else
		
		//bno > 0
		builder.and(board.bno.gt(0));
		
		//검색 조건이 없을 때
		if(type == null) {
		return builder;
		}
		
		//검색 조건이 있을 때
		switch(type) {
		case "t":
			builder.and(board.title.like("%" + keyword + "%"));
			break;
		case "c":
			builder.and(board.content.like("%" + keyword + "%"));
			break;
		case "w":
			builder.and(board.writer.like("%" + keyword + "%"));
			break;
		}
		
		return builder;
	
	}

}
