package ci.pabeu.rs.security;

import java.security.Key;
import java.util.Base64;

import javax.annotation.Priority;
import javax.ws.rs.Priorities;
import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.Response.Status;
import javax.ws.rs.ext.Provider;

import com.sun.jersey.spi.container.ContainerRequest;
import com.sun.jersey.spi.container.ContainerRequestFilter;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;

@JWTTokenStore
@Provider
@Priority(Priorities.AUTHENTICATION)
public class ContainerRequestFilterImpl implements ContainerRequestFilter {

	private ConfigProperties configProperties;
	public ContainerRequestFilterImpl() {
		configProperties = new ConfigProperties();
	}

	@Override
	public ContainerRequest filter(ContainerRequest request) {

		String authorizationHeader = request.getHeaderValue("authorization");

		if (request.getAbsolutePath().getPath().endsWith("/users/login")) {
			return request;
		}

		if (authorizationHeader == null) {
			throw new WebApplicationException(Status.UNAUTHORIZED);
		}

		// Extract the token from the HTTP Authorization header
		String token = authorizationHeader.substring("Bearer".length()).trim();

		try {
			byte[] decodedKey = Base64.getDecoder().decode(configProperties.getSecret());
			Key key = Keys.hmacShaKeyFor(decodedKey);
			// Validate the token
			Jwts.parserBuilder().setSigningKey(key).build().parseClaimsJws(token);

		} catch (Exception e) {
			throw new WebApplicationException(Status.UNAUTHORIZED);
		}

		return request;
	}

}
