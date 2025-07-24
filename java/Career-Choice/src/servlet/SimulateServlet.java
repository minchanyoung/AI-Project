package servlet;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;
import java.io.IOException;
import org.json.JSONArray;

import servlet.dto.UserInput;
import servlet.service.KlipsDataService;
import servlet.service.PredictionService;

@WebServlet(urlPatterns = "/SimulateServlet", loadOnStartup = 1)
public class SimulateServlet extends HttpServlet {

    private PredictionService predictionService;

    @Override
    public void init() throws ServletException {
        super.init();
        String klipsDataPath = getServletContext().getRealPath("/") + "/WEB-INF/data/klips_data_for_stats.csv";
        KlipsDataService.loadKlipsData(klipsDataPath);
        this.predictionService = new PredictionService(getServletContext());
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setCharacterEncoding("UTF-8");
        response.setCharacterEncoding("UTF-8");

        try {
            UserInput userInput = new UserInput(request);
            
            String jobACategoryParam = request.getParameter("job_A_category");
            String jobBCategoryParam = request.getParameter("job_B_category");
            
            double jobACategory = (jobACategoryParam != null) ? Double.parseDouble(jobACategoryParam) : 2; // Default to "전문가"
            double jobBCategory = (jobBCategoryParam != null) ? Double.parseDouble(jobBCategoryParam) : 3; // Default to "사무"

            if (jobACategoryParam == null) { // Set a different default for B if A is not specified
                 jobBCategory = 3;
            }

            JSONArray scenarios = predictionService.buildScenarios(userInput, jobACategory, jobBCategory);
            JSONArray predictionResults = predictionService.predict(scenarios);
            
            System.out.println("Prediction Results from Python: " + predictionResults.toString(4));
            
            setAttributesForJSP(request, userInput, jobACategory, jobBCategory);
            processRequest(request, response, predictionResults);

        } catch (NumberFormatException e) {
            handleError(request, response, "입력값이 올바르지 않습니다. 숫자 형식에 맞춰 입력해주세요.", e);
        } catch (Exception e) {
            handleError(request, response, "예측 시스템 처리 중 오류가 발생했습니다.", e);
        }
    }

    private void processRequest(HttpServletRequest request, HttpServletResponse response, JSONArray predictionResults) throws ServletException, IOException {
        request.setAttribute("predictionResults", predictionResults.toString());
        request.getRequestDispatcher("simulateResult.jsp").forward(request, response);
    }

    private void handleError(HttpServletRequest request, HttpServletResponse response, String userMessage, Exception e) throws ServletException, IOException {
        e.printStackTrace(); // Log the full stack trace for debugging
        request.setAttribute("errorMessage", userMessage);
        request.getRequestDispatcher("simulate.jsp").forward(request, response);
    }
    
    private void setAttributesForJSP(HttpServletRequest request, UserInput input, double jobACategory, double jobBCategory) {
        request.setAttribute("userAge", (int) input.getAge());
        request.setAttribute("userGender", (int) input.getGender());
        request.setAttribute("userEducation", (int) input.getEducation());
        request.setAttribute("userMonthlyIncome", (int) input.getMonthlyIncome());
        request.setAttribute("userJobSatisfaction", (int) input.getJobSatisfaction());
        request.setAttribute("satisFocusKey", input.getSatisFocusKey());
        request.setAttribute("currentJobCategory", (int) input.getCurrentJobCategory());
        request.setAttribute("selectedJobACategory", (int) jobACategory);
        request.setAttribute("selectedJobBCategory", (int) jobBCategory);
    }
}
