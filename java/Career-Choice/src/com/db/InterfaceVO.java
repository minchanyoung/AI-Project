package com.db;

import java.util.ArrayList;

public abstract interface InterfaceVO<E> {
	public abstract int getId();
	public abstract int getYear();
	public abstract String getIndustryType();
	public abstract void setId(int id);
	public abstract void setYear(int year);
	public abstract void setIndustryType(String industryType);
	public abstract ArrayList<E> getData();
	public abstract void setData(ArrayList<E> dataList);
}
