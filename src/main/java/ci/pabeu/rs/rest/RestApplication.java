package ci.pabeu.rs.rest;

import javax.ws.rs.ApplicationPath;

import com.sun.jersey.api.core.PackagesResourceConfig;

@ApplicationPath("resources")
public class RestApplication extends PackagesResourceConfig {

	public RestApplication() {
		super("ci.pabeu.rs.rest.UserRest");
	}

}
