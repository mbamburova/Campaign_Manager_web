package campaignmanager;

import org.apache.ibatis.jdbc.ScriptRunner;

import javax.annotation.Resource;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.annotation.WebListener;
import javax.sql.DataSource;
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.sql.Connection;
import java.sql.SQLException;


/**
 * Created by Michaela Bamburová on 17.04.2016.
 */
@WebListener
public class InitListener implements ServletContextListener {

    @Resource(name = "jdbc/heroDB")
    private DataSource dataSource;

    @Override
    public void contextInitialized(ServletContextEvent servletContextEvent) {
        try {
            dataSource = CampaignDatabase.prepareDataSource();
            Connection connection = dataSource.getConnection();
            ScriptRunner scriptRunner = new ScriptRunner(connection);
            scriptRunner.runScript(new BufferedReader(new FileReader("C:\\Users\\Anonym\\Documents\\GitHub\\Campaign_Manager\\src\\main\\resources\\create_table.sql")));
            scriptRunner.runScript(new BufferedReader(new FileReader("C:\\Users\\Anonym\\Documents\\GitHub\\Campaign_Manager\\src\\main\\resources\\test_data.sql")));
        } catch (FileNotFoundException | SQLException e) {
            e.printStackTrace();
        }
        HeroManagerImpl heroManager = new HeroManagerImpl();
        heroManager.setDataSource(dataSource);
        servletContextEvent.getServletContext().setAttribute("heroManager", heroManager);

        MissionManagerImpl missionManager = new MissionManagerImpl();
        missionManager.setDataSource(dataSource);
        servletContextEvent.getServletContext().setAttribute("missionManager", missionManager);

    }

    @Override
    public void contextDestroyed(ServletContextEvent sce) {
    }
}
