package com.formation.computerdb.domain;

import java.util.Date;

import com.formation.computerdb.common.DBLogActionType;

/**
 * Class used to log in DB all actions on DB (Create - Delete - Update Computer)
 * @author F. Miglianico
 *
 */
public class DBLog {
	private DBLogActionType actionType;
	private Date date;
	private String description;
	
	public DBLog() {
		this.actionType = null;
		this.date = null;
		this.description = null;
	}
	
	public DBLog(DBLogActionType actionType, Date date, String description) {
		this.setActionType(actionType);
		this.setDate(date);
		this.setDescription(description);
	}
	
	/**
	 * @return the actionType
	 */
	public DBLogActionType getActionType() {
		return actionType;
	}
	/**
	 * @param actionType the actionType to set
	 */
	public void setActionType(DBLogActionType actionType) {
		this.actionType = actionType;
	}
	/**
	 * @return the date
	 */
	public Date getDate() {
		return date;
	}
	/**
	 * @param date the date to set
	 */
	public void setDate(Date date) {
		this.date = date;
	}
	/**
	 * @return the description
	 */
	public String getDescription() {
		return description;
	}
	/**
	 * @param description the description to set
	 */
	public void setDescription(String description) {
		this.description = description;
	}
}
