package com.db;


public class ColumnData {
	public static String[] commonColumns = {"dataId", "dataYear", "industryType"};
	public static String[] countColumns = {"companyCount", "workerCount"};
	public static String[] CompanyDetailColumns = {
            "ownerMaleRate","ownerFemaleRate", "singlePropCompanyRate", "multiBusinessCompanyRate", 
            "U1D5CompanyRate", "U5D10CompanyRate", "U10D20CompanyRate", "U20D50CompanyRate", 
            "U50D100CompanyRate", "U100D300CompanyRate", "U300CompanyRate"
        	};
	public static String[] WorkerDetailColumns = {
			"workerMaleRate", "workerFemaleRate", "singlePropWorkerRate", "multiBusinessWorkerRate", 
            "selfEmpFamilyWorkerRate", "fulltimeWorkerRate", "dayWorkerRate", "etcWorkerRate",
            "U1D5WorkerRate", "U5D10WorkerRate", "U10D20WorkerRate", "U20D50WorkerRate", 
            "U50D100WorkerRate", "U100D300WorkerRate", "U300WorkerRate"
            };
	public static String[] WorkAvgColumns = {
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
	public static String[] getTablebyName(String tableName) {
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
	public static String getPredictTableName(String[] columns) {
		
		for(int i=0;i<columns.length;++i) {
			switch(columns[i]) {
			case "companyCount": 
			case "workerCount":
				return "PredictIndustryCountData";
			case "ownerMaleRate":
			case "ownerFemaleRate": 
			case "singlePropCompanyRate": 
			case "multiBusinessCompanyRate": 
	        case "U1D5CompanyRate":
			case "U5D10CompanyRate": 
			case "U10D20CompanyRate": 
			case "U20D50CompanyRate": 
	        case "U50D100CompanyRate": 
			case "U100D300CompanyRate": 
			case "U300CompanyRate":
				return "PredictCompanyData";
			case "workerMaleRate":
			case "workerFemaleRate": 
			case "singlePropWorkerRate": 
			case "multiBusinessWorkerRate": 
	        case "selfEmpFamilyWorkerRate":
			case "fulltimeWorkerRate": 
			case "dayWorkerRate": 
			case "etcWorkerRate": 
	        case "U1D5WorkerRate":
			case "U5D10WorkerRate": 
			case "U10D20WorkerRate": 
			case "U20D50WorkerRate": 
	        case "U50D100WorkerRate": 
			case "U100D300WorkerRate": 
			case "U300WorkerRate":
				return "PredictWorkerData";
			case "avgAge":
			case "avgServYear":
	        case "avgWorkDay":
			case "avgTotalWorkTime":
			case "avgRegularWorkDay":
	        case "avgOverWorkDay":
			case "avgSalary":
			case "avgFixedSalary":
			case "avgOvertimeSalary":
			case "avgBonusSalary":
				return "PredictAvgData";
			}
		}
		return null;
	}
	public static String[] getTable(String column) {
		switch(column) {
		case "companyCount": 
		case "workerCount":
			return commonColumns;
		case "ownerMaleRate":
		case "ownerFemaleRate": 
		case "singlePropCompanyRate": 
		case "multiBusinessCompanyRate": 
        case "U1D5CompanyRate":
		case "U5D10CompanyRate": 
		case "U10D20CompanyRate": 
		case "U20D50CompanyRate": 
        case "U50D100CompanyRate": 
		case "U100D300CompanyRate": 
		case "U300CompanyRate":
			return CompanyDetailColumns;
		case "workerMaleRate":
		case "workerFemaleRate": 
		case "singlePropWorkerRate": 
		case "multiBusinessWorkerRate": 
        case "selfEmpFamilyWorkerRate":
		case "fulltimeWorkerRate": 
		case "dayWorkerRate": 
		case "etcWorkerRate": 
        case "U1D5WorkerRate":
		case "U5D10WorkerRate": 
		case "U10D20WorkerRate": 
		case "U20D50WorkerRate": 
        case "U50D100WorkerRate": 
		case "U100D300WorkerRate": 
		case "U300WorkerRate":
			return WorkerDetailColumns;
		case "avgAge":
		case "avgServYear":
        case "avgWorkDay":
		case "avgTotalWorkTime":
		case "avgRegularWorkDay":
        case "avgOverWorkDay":
		case "avgSalary":
		case "avgFixedSalary":
		case "avgOvertimeSalary":
		case "avgBonusSalary":
			return WorkAvgColumns;
		}
		return null;
	}
	public static String getServiceType(String typeNum) {
		switch(typeNum) {
		case "0":
			return commonColumns[1] + ", " + commonColumns[2] + ", " + WorkerDetailColumns[5];
		case "1":
			return commonColumns[1] + ", " + commonColumns[2] + ", " + WorkAvgColumns[7];//+ ", " + WorkAvgColumns[3] + ", " + WorkAvgColumns[7];
		case "2":
			return commonColumns[1] + ", " + commonColumns[2] + ", " + WorkAvgColumns[5];//+ ", " + WorkAvgColumns[3] + ", " + WorkAvgColumns[5];
		case "3":
			return commonColumns[1] + ", " + commonColumns[2] + ", " + WorkAvgColumns[1];
		case "4":
			return commonColumns[1] + ", " + commonColumns[2] + ", " + CompanyDetailColumns[2];
		case "5":
			return commonColumns[1] + ", " + commonColumns[2] + ", " + countColumns[1];//", " + CompanyDetailColumns[4] + ", " + CompanyDetailColumns[5] + ", " + CompanyDetailColumns[6] + ", " + CompanyDetailColumns[7] + ", " + CompanyDetailColumns[8] + ", " + CompanyDetailColumns[9] + ", " + CompanyDetailColumns[10]
		}
		return null;
	}
	public static String getServiceTypeToTable(String typeNum) {
		switch(typeNum) {
		case "0":
			return "PredictWorkerData";
		case "1":
			return "PredictAvgData";
		case "2":
			return "PredictAvgData";
		case "3":
			return "PredictAvgData";
		case "4":
			return "PredictCompanyData";
		case "5":
			return "PredictIndustryCountData";
		}
		return null;
	}

	public static String getIndustryType(String industryType) {
		switch(industryType) {
			case "전체":
			case "농업임업및어업":
			case "광업":
			case "제조업":
			case "전기가스수도하수":
			case "건설업":
			case "도매및소매업":
			case "운수및창고업":
			case "숙박및음식점업":
			case "정보통신업":
			case "금융및보험업":
			case "부동산업시설관리지원임대":
			case "전문과학및기술서비스업":
			case "교육서비스업":
			case "보건업및사회복지서비스업":
			case "오락문화및운동관련서비스업":
			case "기타공공수리및개인서비스업":
				break;
		}
		return null;
	}
}
