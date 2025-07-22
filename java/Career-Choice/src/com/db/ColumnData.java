package com.db;


public class ColumnData {
	public static String[] commonColumns = {"dataId", "dataYear", "industryType"};
	private static String[] countColumns = {"companyCount", "workerCount"};
	private static String[] CompanyDetailColumns = {
            "ownerMaleRate","ownerFemaleRate", "singlePropCompanyRate", "multiBusinessCompanyRate", 
            "U1D5CompanyRate", "U5D10CompanyRate", "U10D20CompanyRate", "U20D50CompanyRate", 
            "U50D100CompanyRate", "U100D300CompanyRate", "U300CompanyRate"
        	};
	private static String[] WorkerDetailColumns = {
			"workerMaleRate", "workerFemaleRate", "singlePropWorkerRate", "multiBusinessWorkerRate", 
            "selfEmpFamilyWorkerRate", "fulltimeWorkerRate", "dayWorkerRate", "etcWorkerRate",
            "U1D5WorkerRate", "U5D10WorkerRate", "U10D20WorkerRate", "U20D50WorkerRate", 
            "U50D100WorkerRate", "U100D300WorkerRate", "U300WorkerRate"
            };
	private static String[] WorkAvgColumns = {
			"avgAge","avgServYear",
            "avgWorkDay","avgTotalWorkTime","avgRegularWorkDay",
            "avgOverWorkDay","avgSalary","avgFixedSalary","avgOvertimeSalary","avgBonusSalary"
            };

	public static String[] getColumn(String tableName) {
		switch(tableName) {
		case "PredictIndustryCountData":
		case "RealIndustryCountData":
			return countColumns;
		case "PredictCompanyData":
		case "RealCompanyData":
			return CompanyDetailColumns;
		case "PredictWorkerData":
		case "RealWorkerData":
			return WorkerDetailColumns;
		case "PredictAvgData":
		case "RealAvgData":
			return WorkAvgColumns;
		}
		return null;
	}
}
