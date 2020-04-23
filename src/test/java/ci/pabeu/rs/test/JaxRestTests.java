package ci.pabeu.rs.test;

import static org.junit.Assert.assertEquals;

import javax.ws.rs.client.Entity;
import javax.ws.rs.core.Application;
import javax.ws.rs.core.Form;
import javax.ws.rs.core.Response;

import org.glassfish.jersey.server.ResourceConfig;
import org.glassfish.jersey.test.JerseyTest;
import org.junit.Assert;
import org.junit.Test;

import ci.pabeu.rs.response.Result;
import ci.pabeu.rs.rest.UserRest;

public class JaxRestTests extends JerseyTest {

	@Test
	public void contextLoads() {
		assertEquals(true, Boolean.TRUE);
	}

	@Override
	protected Application configure() {
		return new ResourceConfig(UserRest.class);
	}

	@Test
	public void auth() {

		Form form = new Form().param("userName", "admin").param("password",
				"$2y$12$qt.NAUEFGTaodTap/k5eoe.De60vYimtO3XgUrABjWmtm/HmAe8Jm");
		Response response = target("users/auth/token").request().post(Entity.form(form));
		Assert.assertTrue(response.getEntity().getClass() == Result.class);
		Assert.assertTrue(200 == response.getStatus());

	}

}
