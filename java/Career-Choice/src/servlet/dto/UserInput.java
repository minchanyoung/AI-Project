package servlet.dto;

import javax.servlet.http.HttpServletRequest;

public class UserInput {
    private final double age;
    private final double gender;
    private final double education;
    private final double monthlyIncome;
    private final double jobSatisfaction;
    private final double currentJobCategory;
    private final String satisFocusKey;

    public UserInput(HttpServletRequest request) {
        this.age = Double.parseDouble(request.getParameter("age"));
        this.gender = Double.parseDouble(request.getParameter("gender"));
        this.education = Double.parseDouble(request.getParameter("education"));
        this.monthlyIncome = Double.parseDouble(request.getParameter("monthly_income"));
        this.jobSatisfaction = Double.parseDouble(request.getParameter("job_satisfaction"));
        this.currentJobCategory = Double.parseDouble(request.getParameter("current_job_category"));
        this.satisFocusKey = request.getParameter("satis_focus_key");
    }

    // Getters for all fields
    public double getAge() {
        return age;
    }

    public double getGender() {
        return gender;
    }

    public double getEducation() {
        return education;
    }

    public double getMonthlyIncome() {
        return monthlyIncome;
    }

    public double getJobSatisfaction() {
        return jobSatisfaction;
    }

    public double getCurrentJobCategory() {
        return currentJobCategory;
    }

    public String getSatisFocusKey() {
        return satisFocusKey;
    }
}
