package com.formation.computerdb.domain;

import java.util.List;

import com.formation.computerdb.common.OrderByColumn;

/**
 * Class representing a page context
 * @author F. Miglianico
 *
 */
public class Page {
	/**
	 * List of computers
	 */
	private List<Computer> list;
	/**
	 * Search parameter
	 */
	private String search;
	/**
	 * Current page the user is on
	 */
	private Integer currPage;
	/**
	 * Number of computers found
	 */
	private Integer nbComputers;
	/**
	 * Number of rows to display
	 */
	private Integer nbRows;
	/**
	 * Sort parameters
	 */
	private OrderByColumn orderBy;
	
	public Page() {
		this.list = null;
		this.search = null;
		this.currPage = null;
		this.nbComputers = null;
		this.nbRows = null;
		this.orderBy = null;
	}
	
	/**
	 * @return the list
	 */
	public List<Computer> getList() {
		return list;
	}

	/**
	 * @param list the list to set
	 */
	public void setList(List<Computer> list) {
		this.list = list;
	}

	/**
	 * @return the search
	 */
	public String getSearch() {
		return search;
	}
	/**
	 * @param search the search to set
	 */
	public void setSearch(String search) {
		this.search = search;
	}
	/**
	 * @return the page
	 */
	public Integer getCurrPage() {
		return currPage;
	}
	/**
	 * @param page the page to set
	 */
	public void setCurrPage(Integer page) {
		this.currPage = page;
	}
	
	/**
	 * @return the nbComputers
	 */
	public Integer getNbComputers() {
		return nbComputers;
	}

	/**
	 * @param nbComputers the nbComputers to set
	 */
	public void setNbComputers(Integer nbComputers) {
		this.nbComputers = nbComputers;
	}

	/**
	 * @return the nbRows
	 */
	public Integer getNbRows() {
		return nbRows;
	}

	/**
	 * @param nbRows the nbRows to set
	 */
	public void setNbRows(Integer nbRows) {
		this.nbRows = nbRows;
	}

	/**
	 * @return the orderBy
	 */
	public OrderByColumn getOrderBy() {
		return orderBy;
	}
	/**
	 * @param orderBy the orderBy to set
	 */
	public void setOrderBy(OrderByColumn orderBy) {
		this.orderBy = orderBy;
	}
	
	/**
	 * @return the nbPages
	 */
	public int getNbPages() {
		int nbPages = (int)(this.nbComputers / this.nbRows) + ((this.nbComputers % this.nbRows) == 0 ? 0 : 1);
		return nbPages;
	}
}
