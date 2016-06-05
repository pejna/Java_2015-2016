package hr.fer.zemris.java.webserver;

/**
 * Interface requires the implementing classes to support working with
 * {@link RequestContext}. Intended for use with server commands.
 * 
 * @author Juraj Pejnovic
 * @version 1.0
 * @see SmartHttpServer
 */
public interface IWebWorker {

	/**
	 * Processes the request oves the context.
	 * 
	 * @param context
	 *            context of the request
	 */
	void processRequest(RequestContext context);
}
