package ci.pabeu.rs.rest;

import java.io.Serializable;
import java.util.Locale;

import javax.servlet.http.HttpServletRequest;

public class HandleLanguage implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private static String defaultLanguage = "fr";

	public Locale getLang(HttpServletRequest req) {

		Locale locale = req.getLocale();
		if (locale == null) {
			locale = new Locale(defaultLanguage);
		}
		return locale;
	}

}
