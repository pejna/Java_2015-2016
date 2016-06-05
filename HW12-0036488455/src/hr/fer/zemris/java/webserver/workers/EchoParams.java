package hr.fer.zemris.java.webserver.workers;

import java.io.IOException;
import java.nio.charset.StandardCharsets;

import hr.fer.zemris.java.webserver.IWebWorker;
import hr.fer.zemris.java.webserver.RequestContext;

/**
 * {@link IWebWorker} implementation that echoes the parameters found in the
 * context given in the constructor to the output of the context in a table.
 * 
 * @author Juraj Pejnovic
 * @version 1.0
 * @see RequestContext
 */
public class EchoParams implements IWebWorker {

	@Override
	public void processRequest(RequestContext context) {
		context.setMimeType("text/html");

		StringBuilder sb = new StringBuilder("<html>\r\n" + "  <head>\r\n"
				+ "    <title>Params given</title>\r\n" + "  </head>\r\n"
				+ "  <body>\r\n" + "    <table border='1'>\r\n");

		for (String name : context.getParameterNames()) {
			System.out.println(name);
			sb.append("      <tr><td>").append(name).append("</td><td>")
					.append(context.getParameter(name))
					.append("</td></tr>\r\n");
		}

		sb.append("    </table>\r\n" + "  </body>\r\n" + "</html>\r\n");

		byte[] array = sb.toString().getBytes(StandardCharsets.UTF_8);

		try {
			context.write(array);
		} catch (IOException ignorable) {
		}
	}

}
