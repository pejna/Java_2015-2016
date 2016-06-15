package hr.fer.zemris.java.hw13.listeners;

import javax.servlet.ServletContext;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

/**
 * Mats the time of the server inception in the context attribute "startupTime".
 * 
 * @author Juraj Pejnovic
 * @version 1.0
 */
public class StartupTimeContextListener implements ServletContextListener {

	/**
	 * Keyword of the inception data.
	 */
	public static final String TIME_KEYWORD = "startupTime";


	/**
	 * Not used
	 */
	@Override
	public void contextDestroyed(ServletContextEvent e) {
	}


	/**
	 * Marks the time of the inception.
	 */
	@Override
	public void contextInitialized(ServletContextEvent e) {
		ServletContext context = e.getServletContext();
		context.setAttribute("startupTime", System.currentTimeMillis());
	}

}
