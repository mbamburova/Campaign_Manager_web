package campaignmanager;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Created by Anonym on 19. 4. 2016.
 */
@WebServlet(name = "MissionController", urlPatterns = {"/mission/*"})
public class MissionController extends HttpServlet {

    public static final String URL_MAPPING = "/mission";

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        showMissionList(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String action = request.getPathInfo();
        switch (action) {
            case "/add":

                String mission_name = request.getParameter("mission_name");
                String levelInput = request.getParameter("level_required");
                String capacityInput = request.getParameter("capacity");
                String availableInput = request.getParameter("available");
                if(mission_name.isEmpty() || availableInput.isEmpty() || levelInput.isEmpty() || capacityInput.isEmpty()){
                    request.setAttribute("error", "All values are required!");
                    showMissionList(request, response);
                    return;
                }

                if(!availableInput.toLowerCase().equals("true") && !availableInput.toLowerCase().equals("false")){
                    request.setAttribute("error", "Unexpected value of an 'available' attribute!");
                    showMissionList(request, response);
                    return;
                }

                int level_required = Integer.parseInt(levelInput);
                int capacity = Integer.parseInt(capacityInput);
                boolean available = Boolean.parseBoolean(availableInput);

                try {
                    Mission mission = new Mission();
                    mission.setMission_name(mission_name);
                    mission.setLevelRequired(level_required);
                    mission.setCapacity(capacity);
                    mission.setAvailable(available);
                    getMissionManager().createMission(mission);
                    //redirect-after-POST protects from multiple submission
                    response.sendRedirect(request.getContextPath() + URL_MAPPING);
                    return;
                } catch (Exception e) {
                    response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, e.getMessage());
                    return;
                }

            default:
                response.sendError(HttpServletResponse.SC_NOT_FOUND,
                        "Unknown action: " + action);

        }
    }

    private void showMissionList(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        try {
            request.setAttribute("mission", getMissionManager().findAllMission());
            request.getRequestDispatcher("/listMission.jsp").forward(request, response);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private MissionManager getMissionManager() {
        return (MissionManager) getServletContext().getAttribute("missionManager");
    }
}
