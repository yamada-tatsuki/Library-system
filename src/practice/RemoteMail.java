package practice;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import org.apache.commons.mail.DefaultAuthenticator;
import org.apache.commons.mail.Email;
import org.apache.commons.mail.EmailException;
import org.apache.commons.mail.SimpleEmail;

public class RemoteMail {

	private String to = "houjitya1997@gmail.com";
	private String from = "zidongmeru@gmail.com";

	// Google account mail address
	private String username = "zidongmeru@gmail.com";
	// Google app password
	private String password = "naohiro1997";

	// private String charset = "ISO-2022-JP";
	private String charset = "UTF-8";

	private String encoding = "base64";

	// for gmail
	private String host = "smtp.gmail.com";
	private int port = 587;
	private boolean starttls = true;

	// for local
	// private String host = "localhost";
	// private int port = 2525;
	// private boolean starttls = false;

	private Map<String, String> headers = new HashMap<String, String>() {
		private static final long serialVersionUID = 1L;
		{
			put("Content-Transfer-Encoding", encoding);
		}
	};

	public static void main(String[] args) throws IOException {
		RemoteMail sendMail = new RemoteMail();
		sendMail.send("Commons Email テストメール", "これはテストです。");
	}

	public void send(String subject, String content) {

		Email email = new SimpleEmail();

		try {
			email.setHostName(host);
			email.setSmtpPort(port);
			email.setCharset(charset);
			email.setHeaders(headers);
			email.setAuthenticator(new DefaultAuthenticator(username, password));
			email.setStartTLSEnabled(starttls);
			email.setFrom(from);
			email.addTo(to);
			email.setSubject(subject);
			email.setMsg(content);
			email.setDebug(true);

			email.send();

		} catch (EmailException e) {
			e.printStackTrace();
		}
	}

}
