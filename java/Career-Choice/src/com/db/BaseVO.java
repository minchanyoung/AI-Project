package com.db;

import java.util.ArrayList;

public class BaseVO<E> implements InterfaceVO<E>{
	private int id;
	private int year;
	private String industryType;
	
	private ArrayList<E> dataList;

	public BaseVO() {
		dataList = new ArrayList<E>();
	}

	@Override
	public int getId() {
		return id;
	}
	@Override
	public void setId(int id) {
		this.id = id;
	}

	@Override
	public int getYear() {
		return year;
	}
	@Override
	public void setYear(int year) {
		this.year = year;
	}
	@Override
	public String getIndustryType(){
		return industryType;
	}
	@Override
	public void setIndustryType(String industryType) {
		this.industryType = industryType;
	}
	

	@Override
	public ArrayList<E> getData(){
		return dataList;
	}
	@Override
	public void setData(ArrayList<E> dataList){
		this.dataList = dataList;
	}
	public int size() {
		return dataList.size();
	}
	
}


//private int id;
//private int year;
//private String industryType;
//private int companyCount, workerCount;
//private float ownerMaleRate,singlePropCompanyRate
//,U1D5CompanyRate,U5D10CompanyRate,U10D20CompanyRate,U20D50CompanyRate,U50D100CompanyRate,U100D300CompanyRate,U300CompanyRate,
//workerMaleRate,singlePropWorkerRate,
//selfEmpFamilyWorkerRate,fulltimeWorkerRate,dayWorkerRate,etcWorkerRate,
//U1D5WorkerRate,U5D10WorkerRate,U10D20WorkerRate,U20D50WorkerRate,U50D100WorkerRate,U100D300WorkerRate,U300WorkerRate,
//avgAge,avgServYear,avgWorkDay,avgTotalWorkTime,avgRegularWorkDay,avgOverWorkDay,avgSalary,avgFixedSalary,avgOvertimeSalary,avgBonusSalary;

