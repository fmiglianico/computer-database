package com.formation.computerdb.core.domain;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.Type;
import org.joda.time.DateTime;

import com.formation.computerdb.core.common.DBLogActionType;


/**
 * Class used to log in DB all actions on DB (Create - Delete - Update Computer)
 * @author F. Miglianico
 *
 */
@Entity
@Table(name="db_log")
public class DBLog implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue
	private Long id;
	
	@Column(name="action")
	@Enumerated(EnumType.STRING)
	private DBLogActionType actionType;
	
	@Type(type="org.jadira.usertype.dateandtime.joda.PersistentDateTime")
	@Column(name="date")
	private DateTime date;
	
	@Column(name="description")
	private String description;
	
	public DBLog() {
		this.actionType = null;
		this.date = null;
		this.description = null;
	}
	
	public DBLog(DBLogActionType actionType, DateTime date, String description) {
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
	
	public String getAction() {
		return actionType.toString();
	}
	
	public void setAction(String action) {
		this.actionType = DBLogActionType.valueOf(action);
	}
	
	/**
	 * @return the date
	 */
	public DateTime getDate() {
		return date;
	}
	/**
	 * @param date the date to set
	 */
	public void setDate(DateTime date) {
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
