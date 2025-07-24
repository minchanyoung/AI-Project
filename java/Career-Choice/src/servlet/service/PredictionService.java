package servlet.service;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.nio.charset.StandardCharsets;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletContext;

import org.json.JSONArray;
import org.json.JSONObject;

import servlet.dto.UserInput;

public class PredictionService {

    private final ServletContext servletContext;

    public PredictionService(ServletContext servletContext) {
        this.servletContext = servletContext;
    }

    public JSONArray predict(JSONArray scenarios) throws IOException, InterruptedException {
        String pythonScriptPath = servletContext.getRealPath("/") + "/WEB-INF/python_scripts/predict.py";
        ProcessBuilder pb = new ProcessBuilder("python", pythonScriptPath);
        Process process = pb.start();

        try (BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(process.getOutputStream(), StandardCharsets.UTF_8))) {
            writer.write(scenarios.toString());
        }

        StringBuilder output = new StringBuilder();
        try (BufferedReader reader = new BufferedReader(new InputStreamReader(process.getInputStream(), StandardCharsets.UTF_8))) {
            String line;
            while ((line = reader.readLine()) != null) {
                output.append(line);
            }
        }

        int exitCode = process.waitFor();
        if (exitCode != 0) {
            StringBuilder errorOutput = new StringBuilder();
            try (BufferedReader errorReader = new BufferedReader(new InputStreamReader(process.getErrorStream(), StandardCharsets.UTF_8))) {
                String line;
                while ((line = errorReader.readLine()) != null) {
                    errorOutput.append(line);
                }
            }
            throw new IOException("Python script execution failed with exit code " + exitCode + ": " + errorOutput.toString());
        }
        return new JSONArray(output.toString());
    }

    public JSONArray buildScenarios(UserInput input, double jobACategory, double jobBCategory) {
        Map<String, Object> baseFeatures = createBaseFeatures(input);
        
        JSONArray scenarios = new JSONArray();
        scenarios.put(createScenario(baseFeatures, input.getCurrentJobCategory()));
        scenarios.put(createScenario(baseFeatures, jobACategory));
        scenarios.put(createScenario(baseFeatures, jobBCategory));
        
        return scenarios;
    }

    private Map<String, Object> createBaseFeatures(UserInput input) {
        Map<String, Object> features = new HashMap<>();
        features.put("age", input.getAge());
        features.put("gender", input.getGender());
        features.put("education", input.getEducation());
        features.put("monthly_income", input.getMonthlyIncome());
        features.put("job_satisfaction", input.getJobSatisfaction());
        features.put("prev_job_satisfaction", input.getJobSatisfaction());
        features.put("prev_monthly_income", input.getMonthlyIncome());

        final double WEIGHT_FACTOR = 2.5;
        List<String> satisfactionKeys = Arrays.asList("satis_wage", "satis_stability", "satis_task_content", "satis_work_env", "satis_work_time", "satis_growth", "satis_communication", "satis_fair_eval", "satis_welfare");
        
        for (String key : satisfactionKeys) {
            features.put(key, input.getSatisFocusKey().equals(key) ? input.getJobSatisfaction() * WEIGHT_FACTOR : input.getJobSatisfaction());
        }
        return features;
    }

    private JSONObject createScenario(Map<String, Object> baseFeatures, double jobCategory) {
        JSONObject scenario = new JSONObject(baseFeatures);
        scenario.put("job_category", jobCategory);
        
        addJobCategoryDerivedFeatures(scenario, (int) jobCategory, (double) baseFeatures.get("monthly_income"), (double) baseFeatures.get("education"));
        addInteractionFeatures(scenario, (double) baseFeatures.get("age"), (double) baseFeatures.get("monthly_income"), (double) baseFeatures.get("education"), jobCategory);
        
        return scenario;
    }

    private void addJobCategoryDerivedFeatures(JSONObject scenario, int jobCategory, double monthlyIncome, double education) {
        Map<String, Double> stats = KlipsDataService.getJobCategoryStatistics(jobCategory);
        double incomeAvg = stats.getOrDefault("job_category_income_avg", 0.0);
        double eduAvg = stats.getOrDefault("job_category_education_avg", 0.0);

        scenario.put("job_category_income_avg", incomeAvg);
        scenario.put("income_relative_to_job", (incomeAvg != 0) ? monthlyIncome / incomeAvg : 1.0);
        scenario.put("job_category_education_avg", eduAvg);
        scenario.put("education_relative_to_job", (eduAvg != 0) ? education / eduAvg : 1.0);
        scenario.put("job_category_satisfaction_avg", stats.getOrDefault("job_category_satisfaction_avg", 0.0));
        scenario.put("income_diff", monthlyIncome - incomeAvg);
    }

    private void addInteractionFeatures(JSONObject scenario, double age, double monthlyIncome, double education, double jobCategory) {
        scenario.put("age_x_job_category", age * jobCategory);
        scenario.put("monthly_income_x_job_category", monthlyIncome * jobCategory);
        scenario.put("education_x_job_category", education * jobCategory);
        scenario.put("income_relative_to_job_x_job_category", scenario.optDouble("income_relative_to_job", 1.0) * jobCategory);
    }
}
