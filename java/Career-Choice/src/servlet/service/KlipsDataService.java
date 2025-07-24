package servlet.service;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

public class KlipsDataService {
    private static final Map<Integer, Map<String, Double>> jobCategoryStats = new HashMap<>();
    private static boolean isLoaded = false;

    public static synchronized void loadKlipsData(String csvFilePath) {
        if (isLoaded) {
            return;
        }
        System.out.println("Attempting to load KLIPS data from: " + csvFilePath);
        try (BufferedReader br = new BufferedReader(new InputStreamReader(new FileInputStream(csvFilePath), StandardCharsets.UTF_8))) {
            br.readLine(); // Skip header line
            String line;
            while ((line = br.readLine()) != null) {
                String[] parts = line.split(",");
                if (parts.length < 4) continue;
                try {
                    int jobCategory = Integer.parseInt(parts[0].trim());
                    Map<String, Double> stats = new HashMap<>();
                    stats.put("job_category_income_avg", Double.parseDouble(parts[1].trim()));
                    stats.put("job_category_education_avg", Double.parseDouble(parts[2].trim()));
                    stats.put("job_category_satisfaction_avg", Double.parseDouble(parts[3].trim()));
                    jobCategoryStats.put(jobCategory, stats);
                } catch (NumberFormatException e) {
                    System.err.println("Skipping malformed data line in CSV: " + line);
                }
            }
            isLoaded = true;
            System.out.println("KLIPS data loaded successfully. Total categories: " + jobCategoryStats.size());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static Map<String, Double> getJobCategoryStatistics(int jobCategory) {
        return jobCategoryStats.getOrDefault(jobCategory, Collections.emptyMap());
    }

    public static Map<Integer, Map<String, Double>> getAllJobCategoryStatistics() {
        return Collections.unmodifiableMap(jobCategoryStats);
    }
}
