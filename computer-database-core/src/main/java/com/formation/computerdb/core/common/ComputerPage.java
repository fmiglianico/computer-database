package com.formation.computerdb.core.common;

import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;

public class ComputerPage extends PageRequest {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private OrderBy orderBy;

	public ComputerPage(int page, int size) {
		super(page, size);
	}
	
	public ComputerPage(int page, int size, OrderBy orderBy) {
		super(page, size);
		this.setOrderBy(orderBy);
		
	}

	/**
	 * @return the orderBy
	 */
	public OrderBy getOrderBy() {
		return orderBy;
	}

	/**
	 * @param orderBy the orderBy to set
	 */
	public void setOrderBy(OrderBy orderBy) {
		this.orderBy = orderBy;
	}
	
	@Override
	public Sort getSort() {
		if(orderBy != null)
			return orderBy.getValue();
		return null;
	}
	
	
}
