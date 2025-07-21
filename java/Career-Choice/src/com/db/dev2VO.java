package com.db;

public class dev2VO {
	private int id;
	private int year;
	private int companyCount, workerCount;
	private float ownerMaleRate,singlePropCompanyRate
	,U1D5CompanyRate,U5D10CompanyRate,U10D20CompanyRate,U20D50CompanyRate,U50D100CompanyRate,U100D300CompanyRate,U300CompanyRate,
	workerMaleRate,singlePropWorkerRate,
	selfEmpFamilyWorkerRate,fulltimeWorkerRate,dayWorkerRate,etcWorkerRate,
	U1D5WorkerRate,U5D10WorkerRate,U10D20WorkerRate,U20D50WorkerRate,U50D100WorkerRate,U100D300WorkerRate,U300WorkerRate,
	avgAge,avgServYear,avgWorkDay,avgTotalWorkTime,avgRegularWorkDay,avgOverWorkDay,avgSalary,avgFixedSalary,avgOvertimeSalary,avgBonusSalary;
	
	public dev2VO() {
	}
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	
	public int getYear() {
		return year;
	}
	public void setYear(int year) {
		this.year = year;
	}

	public int getCompanyCount() {
	    return companyCount;
	}

	public void setCompanyCount(int companyCount) {
	    this.companyCount = companyCount;
	}

	public int getWorkerCount() {
	    return workerCount;
	}

	public void setWorkerCount(int workerCount) {
	    this.workerCount = workerCount;
	}

	public float getOwnerMaleRate() {
	    return ownerMaleRate;
	}

	public void setOwnerMaleRate(float ownerMaleRate) {
	    this.ownerMaleRate = ownerMaleRate;
	}

	public float getOwnerFemaleRate() {
	    return 1 - this.ownerMaleRate;
	}


	public float getSinglePropCompanyRate() {
	    return singlePropCompanyRate;
	}

	public void setSinglePropCompanyRate(float singlePropCompanyRate) {
	    this.singlePropCompanyRate = singlePropCompanyRate;
	}

	public float getMultiBusinessCompanyRate() {
	    return 1 - this.singlePropCompanyRate;
	}

	public float getU1D5CompanyRate() {
	    return U1D5CompanyRate;
	}

	public void setU1D5CompanyRate(float U1D5CompanyRate) {
	    this.U1D5CompanyRate = U1D5CompanyRate;
	}

	public float getU5D10CompanyRate() {
	    return U5D10CompanyRate;
	}

	public void setU5D10CompanyRate(float U5D10CompanyRate) {
	    this.U5D10CompanyRate = U5D10CompanyRate;
	}

	public float getU10D20CompanyRate() {
	    return U10D20CompanyRate;
	}

	public void setU10D20CompanyRate(float U10D20CompanyRate) {
	    this.U10D20CompanyRate = U10D20CompanyRate;
	}

	public float getU20D50CompanyRate() {
	    return U20D50CompanyRate;
	}

	public void setU20D50CompanyRate(float U20D50CompanyRate) {
	    this.U20D50CompanyRate = U20D50CompanyRate;
	}

	public float getU50D100CompanyRate() {
	    return U50D100CompanyRate;
	}

	public void setU50D100CompanyRate(float U50D100CompanyRate) {
	    this.U50D100CompanyRate = U50D100CompanyRate;
	}

	public float getU100D300CompanyRate() {
	    return U100D300CompanyRate;
	}

	public void setU100D300CompanyRate(float U100D300CompanyRate) {
	    this.U100D300CompanyRate = U100D300CompanyRate;
	}

	public float getU300CompanyRate() {
	    return U300CompanyRate;
	}

	public void setU300CompanyRate(float U300CompanyRate) {
	    this.U300CompanyRate = U300CompanyRate;
	}

	public float getWorkerMaleRate() {
	    return workerMaleRate;
	}

	public void setWorkerMaleRate(float workerMaleRate) {
	    this.workerMaleRate = workerMaleRate;
	}

	public float getWorkerFemaleRate() {
	    return 1 - this.workerMaleRate;
	}

	public float getSinglePropWorkerRate() {
	    return singlePropWorkerRate;
	}

	public void setSinglePropWorkerRate(float singlePropWorkerRate) {
	    this.singlePropWorkerRate = singlePropWorkerRate;
	}

	public float getMultiBusinessWorkerRate() {
	    return 1 - this.singlePropWorkerRate;
	}

	public float getSelfEmpFamilyWorkerRate() {
	    return selfEmpFamilyWorkerRate;
	}

	public void setSelfEmpFamilyWorkerRate(float selfEmpFamilyWorkerRate) {
	    this.selfEmpFamilyWorkerRate = selfEmpFamilyWorkerRate;
	}

	public float getFulltimeWorkerRate() {
	    return fulltimeWorkerRate;
	}

	public void setFulltimeWorkerRate(float fulltimeWorkerRate) {
	    this.fulltimeWorkerRate = fulltimeWorkerRate;
	}

	public float getDayWorkerRate() {
	    return dayWorkerRate;
	}

	public void setDayWorkerRate(float dayWorkerRate) {
	    this.dayWorkerRate = dayWorkerRate;
	}

	public float getEtcWorkerRate() {
	    return etcWorkerRate;
	}

	public void setEtcWorkerRate(float etcWorkerRate) {
	    this.etcWorkerRate = etcWorkerRate;
	}

	public float getU1D5WorkerRate() {
	    return U1D5WorkerRate;
	}

	public void setU1D5WorkerRate(float U1D5WorkerRate) {
	    this.U1D5WorkerRate = U1D5WorkerRate;
	}

	public float getU5D10WorkerRate() {
	    return U5D10WorkerRate;
	}

	public void setU5D10WorkerRate(float U5D10WorkerRate) {
	    this.U5D10WorkerRate = U5D10WorkerRate;
	}

	public float getU10D20WorkerRate() {
	    return U10D20WorkerRate;
	}

	public void setU10D20WorkerRate(float U10D20WorkerRate) {
	    this.U10D20WorkerRate = U10D20WorkerRate;
	}

	public float getU20D50WorkerRate() {
	    return U20D50WorkerRate;
	}

	public void setU20D50WorkerRate(float U20D50WorkerRate) {
	    this.U20D50WorkerRate = U20D50WorkerRate;
	}

	public float getU50D100WorkerRate() {
	    return U50D100WorkerRate;
	}

	public void setU50D100WorkerRate(float U50D100WorkerRate) {
	    this.U50D100WorkerRate = U50D100WorkerRate;
	}

	public float getU100D300WorkerRate() {
	    return U100D300WorkerRate;
	}

	public void setU100D300WorkerRate(float U100D300WorkerRate) {
	    this.U100D300WorkerRate = U100D300WorkerRate;
	}

	public float getU300WorkerRate() {
	    return U300WorkerRate;
	}

	public void setU300WorkerRate(float U300WorkerRate) {
	    this.U300WorkerRate = U300WorkerRate;
	}

	public float getAvgAge() {
	    return avgAge;
	}

	public void setAvgAge(float avgAge) {
	    this.avgAge = avgAge;
	}

	public float getAvgServYear() {
	    return avgServYear;
	}

	public void setAvgServYear(float avgServYear) {
	    this.avgServYear = avgServYear;
	}

	public float getAvgWorkDay() {
	    return avgWorkDay;
	}

	public void setAvgWorkDay(float avgWorkDay) {
	    this.avgWorkDay = avgWorkDay;
	}

	public float getAvgTotalWorkTime() {
	    return avgTotalWorkTime;
	}

	public void setAvgTotalWorkTime(float avgTotalWorkTime) {
	    this.avgTotalWorkTime = avgTotalWorkTime;
	}

	public float getAvgRegularWorkDay() {
	    return avgRegularWorkDay;
	}

	public void setAvgRegularWorkDay(float avgRegularWorkDay) {
	    this.avgRegularWorkDay = avgRegularWorkDay;
	}

	public float getAvgOverWorkDay() {
	    return avgOverWorkDay;
	}

	public void setAvgOverWorkDay(float avgOverWorkDay) {
	    this.avgOverWorkDay = avgOverWorkDay;
	}

	public float getAvgSalary() {
	    return avgSalary;
	}

	public void setAvgSalary(float avgSalary) {
	    this.avgSalary = avgSalary;
	}

	public float getAvgFixedSalary() {
	    return avgFixedSalary;
	}

	public void setAvgFixedSalary(float avgFixedSalary) {
	    this.avgFixedSalary = avgFixedSalary;
	}

	public float getAvgOvertimeSalary() {
	    return avgOvertimeSalary;
	}

	public void setAvgOvertimeSalary(float avgOvertimeSalary) {
	    this.avgOvertimeSalary = avgOvertimeSalary;
	}

	public float getAvgBonusSalary() {
	    return avgBonusSalary;
	}

	public void setAvgBonusSalary(float avgBonusSalary) {
	    this.avgBonusSalary = avgBonusSalary;
	}

}
