package com.ammgroup.sep.controller.config.crud;

import java.util.List;

import org.springframework.stereotype.Component;

import com.ammgroup.sep.controller.config.crud.enums.CrudAction;

@Component
public class CrudDAOAgen<T> {

	private int index;
	private T dao;
	private CrudAction action;
	private List<T> itemsList;

	public CrudDAOAgen() {
		super();
	}

	public int getIndex() {
		return index;
	}

	public void setIndex(int index) {
		this.index = index;
	}

	public T getDao() {
		return dao;
	}

	public void setDao(T dao) {
		this.dao = dao;
	}

	public CrudAction getAction() {
		return action;
	}

	public void setAction(CrudAction action) {
		this.action = action;
	}

	public List<T> getItemsList() {
		return itemsList;
	}

	public void setItemsList(List<T> itemsList) {
		this.itemsList = itemsList;
	}
}
