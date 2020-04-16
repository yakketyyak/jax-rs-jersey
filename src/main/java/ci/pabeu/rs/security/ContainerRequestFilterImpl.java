package ci.pabeu.rs.security;

import java.io.IOException;
import java.security.Key;
import java.util.Base64;
import java.util.Optional;

import javax.annotation.Priority;
import javax.ws.rs.Priorities;
import javax.ws.rs.WebApplicationException;
import javax.ws.rs.container.ContainerRequestContext;
import javax.ws.rs.container.ContainerRequestFilter;
import javax.ws.rs.core.HttpHeaders;
import javax.ws.rs.core.Response.Status;
import javax.ws.rs.ext.Provider;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;

@JWTTokenStore
@Provider
@Priority(Priorities.AUTHENTICATION)
public class ContainerRequestFilterImpl implements ContainerRequestFilter {

	private ConfigProperties configProperties;
	private static final String BEARER = "Bearer";
	public ContainerRequestFilterImpl() {
		configProperties = new ConfigProperties();
	}


	@Override
	public void filter(ContainerRequestContext requestContext) throws IOException {
		final Optional<String> authorizationHeader = Optional
				.ofNullable(requestContext.getHeaderString(HttpHeaders.AUTHORIZATION));

		if (!authorizationHeader.isPresent()) {
			throw new WebApplicationException(Status.UNAUTHORIZED);
		}

		// Extract the token from the HTTP Authorization header
		String token = authorizationHeader.get().substring(BEARER.length()).trim();

		try {
			byte[] decodedKey = Base64.getDecoder().decode(configProperties.getSecret());
			Key key = Keys.hmacShaKeyFor(decodedKey);
			// Validate the token
			Jwts.parserBuilder().setSigningKey(key).build().parseClaimsJws(token);

		} catch (Exception e) {
			throw new WebApplicationException(Status.UNAUTHORIZED);
		}

	}

}
