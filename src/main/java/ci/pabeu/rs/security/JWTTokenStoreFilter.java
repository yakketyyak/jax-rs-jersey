package ci.pabeu.rs.security;

import java.io.IOException;
import java.security.Key;

import javax.annotation.Priority;
import javax.enterprise.context.ApplicationScoped;
import javax.ws.rs.Priorities;
import javax.ws.rs.container.ContainerRequestContext;
import javax.ws.rs.container.ContainerRequestFilter;
import javax.ws.rs.core.HttpHeaders;
import javax.ws.rs.core.Response;
import javax.ws.rs.ext.Provider;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;

@JWTTokenStore
@Provider
@Priority(Priorities.AUTHENTICATION)
@ApplicationScoped
public class JWTTokenStoreFilter implements ContainerRequestFilter {

	@Override
	public void filter(ContainerRequestContext requestContext) throws IOException {
		// Get the HTTP Authorization header from the request
		String authorizationHeader = requestContext.getHeaderString(HttpHeaders.AUTHORIZATION);

		Key key = Keys.secretKeyFor(SignatureAlgorithm.HS512);
		// Extract the token from the HTTP Authorization header
		String token = authorizationHeader.substring("Bearer".length()).trim();

		try {

			// Validate the token
			Jwts.parserBuilder().setSigningKey(key).build().parseClaimsJws(token);

		} catch (Exception e) {

			requestContext.abortWith(Response.status(Response.Status.UNAUTHORIZED).build());
		}

	}

}
