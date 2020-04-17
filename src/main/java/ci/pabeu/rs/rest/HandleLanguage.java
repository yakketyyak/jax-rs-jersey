package ci.pabeu.rs.rest;

import java.io.Serializable;
import java.util.Locale;

import javax.ws.rs.container.ContainerRequestContext;

public class HandleLanguage implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private static String defaultLanguage = "fr";

	public void setLocale(ContainerRequestContext req) {

		Locale locale = req.getLanguage();
		if (locale == null) {
			locale = new Locale(defaultLanguage);
		}

		Locale.setDefault(locale);
	}

}
