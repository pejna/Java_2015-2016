package hr.fer.zemris.java.webserver;

import java.io.IOException;
import java.io.OutputStream;
import java.nio.charset.Charset;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Set;

public class RequestContext {

	public static class RCCookie {

		String name;

		String value;

		String domain;

		String path;


		/**
		 * @return the name
		 */
		public String getName() {
			return name;
		}


		/**
		 * @return the value
		 */
		public String getValue() {
			return value;
		}


		/**
		 * @return the domain
		 */
		public String getDomain() {
			return domain;
		}


		/**
		 * @return the path
		 */
		public String getPath() {
			return path;
		}
	}

	private OutputStream outputStream;

	private Charset charset;

	private String encoding = "UTF-8";

	private int statusCode = 200;

	private String statusText = "OK";

	private String mimeType = "text/html";

	private Map<String, String> parameters;

	private Map<String, String> temporaryParameters;

	private Map<String, String> persistentParameters;

	private List<RCCookie> outputCookies;

	private boolean headerGenerated;


	public RequestContext(OutputStream stream, Map<String, String> parameters,
			Map<String, String> persistentParameters,
			List<RCCookie> outputCookies) {
		Objects.requireNonNull(stream);

		if (parameters == null) {
			this.parameters = new HashMap<>();
		} else {
			this.parameters = parameters;
		}

		if (persistentParameters == null) {
			this.persistentParameters = new HashMap<>();
		} else {
			this.persistentParameters = persistentParameters;
		}

		this.temporaryParameters = new HashMap<>();
	}


	public String getParameter(String name) {
		return parameters.get(name);
	}


	public Set<String> getParameterNames() {
		return new HashSet<>(parameters.keySet());
	}


	public String getPersistentParameter(String name) {
		return persistentParameters.get(name);
	}


	public Set<String> getPersistentParameterNames() {
		return new HashSet<>(persistentParameters.keySet());
	}


	public void setPersistentParameter(String name, String value) {
		persistentParameters.put(name, value);
	}


	public void removePersistentParameter(String name) {
		persistentParameters.remove(name);
	}


	public String getTemporaryParameter(String name) {
		return temporaryParameters.get(name);
	}


	public Set<String> getTemporaryParameterNames() {
		return new HashSet<>(temporaryParameters.keySet());
	}


	public void setTemporaryParameter(String name, String value) {
		temporaryParameters.put(name, value);
	}


	public void removeTemporaryParameter(String name) {
		temporaryParameters.remove(name);
	}


	public RequestContext write(byte[] data) throws IOException {
		Objects.requireNonNull(data);
		generateHeader();
		outputStream.write(data);
		return this;
	}


	public RequestContext write(String text) throws IOException {
		return write(text.getBytes(charset));
	}


	private void generateHeader() {
		if (headerGenerated) {
			return;
		}

		charset = Charset.forName(encoding);

		headerGenerated = true;
	}


	/**
	 * @param encoding
	 *            the encoding to set
	 */
	public void setEncoding(String encoding) {
		checkHeaderGenerated();
		this.encoding = encoding;
	}


	/**
	 * @param statusCode
	 *            the statusCode to set
	 */
	public void setStatusCode(int statusCode) {
		checkHeaderGenerated();
		this.statusCode = statusCode;
	}


	/**
	 * @param statusText
	 *            the statusText to set
	 */
	public void setStatusText(String statusText) {
		checkHeaderGenerated();
		this.statusText = statusText;
	}


	/**
	 * @param mimeType
	 *            the mimeType to set
	 */
	public void setMimeType(String mimeType) {
		checkHeaderGenerated();
		this.mimeType = mimeType;
	}


	private void checkHeaderGenerated() {
		if (headerGenerated) {
			throw new RuntimeException("Warning - Header Already created!");
		}
	}
}
