package ci.pabeu.rs.security;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class ConfigProperties {

	public Properties loadProperties(String filename) throws IOException {
	Properties prop = new Properties();
	InputStream input = getClass().getClassLoader().getResourceAsStream(filename);
	if (input == null) {
		throw new FileNotFoundException(filename);
	}
	prop.load(input);
	return prop;
}
}