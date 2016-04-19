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

    private static final String URL_MAPPING = "/hero";
    //zle, nemoze byt globalny OPRAVIT!
    private Long id;

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        showHeroList(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        String action = request.getPathInfo();
        switch(action) {
            case "/add":
                add(request, response);
                break;
            case "/delete":
                delete(request, response);
                break;
            case "/initUpdate" :
                initUpdate(request, response);
                break;
            case "/update":
                update(request, response);
                break;
            default:
                response.sendError(HttpServletResponse.SC_NOT_FOUND,
                        "Unknown action: " + action);
        }
    }

    private void add(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String name = request.getParameter("name");
        String levelInput = request.getParameter("level");
        if (levelInput.isEmpty() || name.isEmpty()){
            request.setAttribute("error", "Je nutné vyplnit všechny hodnoty !");
            showHeroList(request, response);
            return;
        }
        int level = Integer.parseInt(levelInput);

        try {
            Hero hero = new Hero();
            hero.setName(name);
            hero.setLevel(level);
            getHeroManager().createHero(hero);
            response.sendRedirect(request.getContextPath()+URL_MAPPING);
        } catch (Exception e) {
            response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, e.getMessage());
        }
    }

    private void initUpdate(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        id = Long.valueOf(request.getParameter("id"));
        request.setAttribute("update", "hero is being updated");
        showHeroList(request, response);
        response.sendRedirect(request.getContextPath()+URL_MAPPING);
    }

    private void delete(HttpServletRequest request, HttpServletResponse response) throws IOException {
        try {
            long id = Long.valueOf(request.getParameter("id"));
            getHeroManager().deleteHero(getHeroManager().findHeroById(id));
            response.sendRedirect(request.getContextPath()+URL_MAPPING);

        } catch (Exception e) {
            response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, e.getMessage());
        }
    }

    private void update(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String nameForUpdate = request.getParameter("name");
        String levelInputForUpdate = request.getParameter("level");
        if (levelInputForUpdate.isEmpty() || nameForUpdate.isEmpty()){
            request.setAttribute("error", "Je nutné vyplnit všechny hodnoty !");
            showHeroList(request, response);
            return;
        }
        int levelForUpdate = Integer.parseInt(levelInputForUpdate);

        try {
            Hero found = getHeroManager().findHeroById(id);

            found.setName(nameForUpdate);
            found.setLevel(levelForUpdate);
            getHeroManager().updateHero(found);
            showHeroList(request, response);
        } catch (Exception e) {
            response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, e.getMessage());
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
            response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, e.getMessage());
        }
    }
}
