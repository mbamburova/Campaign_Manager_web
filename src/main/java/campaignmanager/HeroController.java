package campaignmanager;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Created by Michaela Bamburová on 17.04.2016.
 */

@WebServlet(name = "HeroController", urlPatterns = {"/hero/*"})
public class HeroController extends HttpServlet {

    public static final String URL_MAPPING = "/hero";

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        showHeroList(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
      //  showHeroList(request, response);

        String action = request.getPathInfo();
        switch(action) {
            case "/add":
                //getting POST parameters from form
                String name = request.getParameter("name");
                String levelInput = request.getParameter("level");
                if (!levelInput.isEmpty() || name == null || name.length() == 0){
                    request.setAttribute("error", "Je nutné vyplnit všechny hodnoty !");
                    showHeroList(request, response);
                    return;
                }
                int level = Integer.parseInt(levelInput);
                showHeroList(request, response);

                try {
                    Hero hero = new Hero();
                    hero.setName(name);
                    hero.setLevel(level);
                    getHeroManager().createHero(hero);
                    //redirect-after-POST protects from multiple submission
                    response.sendRedirect(request.getContextPath()+URL_MAPPING);
                    return;
                } catch (Exception e) {
                    response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, e.getMessage());
                    return;
                }

            case "/delete":
                try {
                    Long id = Long.valueOf(request.getParameter("id"));
                    getHeroManager().deleteHero(getHeroManager().findHeroById(id));
                    response.sendRedirect(request.getContextPath()+URL_MAPPING);
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

    private HeroManager getHeroManager() {
        return (HeroManager) getServletContext().getAttribute("heroManager");
    }

    private void showHeroList(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        try {
            request.setAttribute("hero", getHeroManager().findAllHeroes());
            request.getRequestDispatcher("/list.jsp").forward(request, response);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
