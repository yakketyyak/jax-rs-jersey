package ci.pabeu.rs.rest;

import java.util.ResourceBundle;

public class I18nConfig {

	private static final String RESOURCE_BUNDLE = "messages";

	ResourceBundle mybundle = ResourceBundle.getBundle(RESOURCE_BUNDLE);

	public String getMessage(String key) {
		return mybundle.getString(key);
	}

}
