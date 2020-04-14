package ci.pabeu.rs.security;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

import org.apache.commons.lang.StringUtils;

public class ConfigProperties {

	public String getSecret() {
		try (InputStream input = ConfigProperties.class.getClassLoader().getResourceAsStream("app.properties")) {

            Properties prop = new Properties();
            prop.load(input);
            return prop.getProperty("secret");
	}catch (IOException ex) {
        ex.printStackTrace();
    }
	return StringUtils.EMPTY;

}
}