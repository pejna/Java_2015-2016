package hr.fer.zemris.java.webserver.test;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;

import org.junit.Assert;
import org.junit.Test;

import hr.fer.zemris.java.webserver.RequestContext;
import hr.fer.zemris.java.webserver.RequestContext.RCCookie;

public class RequestContextTest {

	@Test(expected = NullPointerException.class)
	public void testCookieNameNull() {
		// will throw
		RCCookie cookie = new RCCookie(null, "value", null, null, null);
	}


	@Test(expected = NullPointerException.class)
	public void testCookieValueNull() {
		// will throw
		RCCookie cookie = new RCCookie("name", null, null, null, null);
	}


	@Test(expected = IllegalArgumentException.class)
	public void testCookiAgeLessThanZero() {
		// will throw
		RCCookie cookie = new RCCookie("name", "value", -5, null, null);
	}


	@Test
	public void testCookieFullConstructor() {
		RCCookie cookie = new RCCookie("name", "value", 5, "domain", "path");
	}


	@Test
	public void testCookieSemiFullConstructor() {
		RCCookie cookie = new RCCookie("name", "value", null, null, null);
	}


	@Test(expected = NullPointerException.class)
	public void testRequestContextConstructorNull() {
		// will throw
		RequestContext rc = new RequestContext(null, null, null, null);
	}


	@Test
	public void testRequestContextSemiFullConstructor() {
		RequestContext rc = new RequestContext(System.out, null, null, null);
	}


	@Test
	public void testRequestContextFullConstructor() {
		RequestContext rc = new RequestContext(System.out, new HashMap<>(),
				new HashMap<>(), new ArrayList<>());
	}


	@Test(expected = NullPointerException.class)
	public void testRequestContextWriteStringNull() throws IOException {
		RequestContext rc = new RequestContext(System.out, new HashMap<>(),
				new HashMap<>(), new ArrayList<>());
		// will throw
		rc.write((String) null);
	}


	@Test(expected = NullPointerException.class)
	public void testRequestContextWriteArrayNull() throws IOException {
		RequestContext rc = new RequestContext(System.out, new HashMap<>(),
				new HashMap<>(), new ArrayList<>());
		// will throw
		rc.write((byte[]) null);
	}


	@Test
	public void testRequestContextWriteStringGood() throws IOException {
		ByteArrayOutputStream os = new ByteArrayOutputStream();
		RequestContext rc = new RequestContext(os,
				new HashMap<String, String>(), new HashMap<String, String>(),
				new ArrayList<RequestContext.RCCookie>());
		String encoding = "ISO-8859-2";
		rc.setEncoding(encoding);
		rc.setMimeType("text/plain");
		rc.setStatusCode(205);
		rc.setStatusText("Idemo dalje");
		rc.write("Čevapčići i Šiščevapčići.");
		Assert.assertEquals(
				"HTTP/1.1 205 Idemo dalje\r\n"
						+ "Content-Type: text/plain; charset=ISO-8859-2\r\n"
						+ "\r\n" + "Čevapčići i Šiščevapčići.",
				os.toString(encoding));
	}

	@Test
	public void testRequestContextWriteArrayGood() throws IOException {
		ByteArrayOutputStream os = new ByteArrayOutputStream();
		RequestContext rc = new RequestContext(os,
				new HashMap<String, String>(), new HashMap<String, String>(),
				new ArrayList<RequestContext.RCCookie>());
		String encoding = "ISO-8859-2";
		rc.setEncoding(encoding);
		rc.setMimeType("text/plain");
		rc.setStatusCode(205);
		rc.setStatusText("Idemo dalje");
		rc.write("Čevapčići i Šiščevapčići.".getBytes(encoding));
		Assert.assertEquals(
				"HTTP/1.1 205 Idemo dalje\r\n"
						+ "Content-Type: text/plain; charset=ISO-8859-2\r\n"
						+ "\r\n" + "Čevapčići i Šiščevapčići.",
				os.toString(encoding));
	}

	@Test (expected = RuntimeException.class)
	public void testRequestContextAfterWrite() throws IOException {
		ByteArrayOutputStream os = new ByteArrayOutputStream();
		RequestContext rc = new RequestContext(os,
				new HashMap<String, String>(), new HashMap<String, String>(),
				new ArrayList<RequestContext.RCCookie>());
		String encoding = "ISO-8859-2";
		rc.setEncoding(encoding);
		rc.setMimeType("text/plain");
		rc.setStatusCode(205);
		rc.setStatusText("Idemo dalje");
		rc.write("Čevapčići i Šiščevapčići.");
		//will throw
		rc.setMimeType("mime");
	}
	
}
