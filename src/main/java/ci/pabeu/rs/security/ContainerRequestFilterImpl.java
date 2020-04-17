package ci.pabeu.rs.security;

import java.io.IOException;
import java.security.Key;
import java.util.Base64;
import java.util.Date;
import java.util.Optional;

import javax.annotation.Priority;
import javax.ws.rs.Priorities;
import javax.ws.rs.WebApplicationException;
import javax.ws.rs.container.ContainerRequestContext;
import javax.ws.rs.container.ContainerRequestFilter;
import javax.ws.rs.core.HttpHeaders;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;
import javax.ws.rs.ext.Provider;

import ci.pabeu.rs.rest.HandleLanguage;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;

@Secured
@Provider
@Priority(Priorities.AUTHENTICATION)
public class ContainerRequestFilterImpl implements ContainerRequestFilter {

	private ConfigProperties configProperties;
	private HandleLanguage handleLanguage;
	private static final String BEARER = "Bearer";
	public ContainerRequestFilterImpl() {
		configProperties = new ConfigProperties();
		handleLanguage = new HandleLanguage();
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
		this.handleLanguage.setLocale(requestContext);
		try {
			byte[] decodedKey = Base64.getDecoder()
					.decode(this.configProperties.loadProperties("app.properties").getProperty("secret"));
			Key key = Keys.hmacShaKeyFor(decodedKey);
			// Validate the token
			Jws<Claims> claims = Jwts.parserBuilder().setSigningKey(key).build().parseClaimsJws(token);
			Long checkExpires = claims.getBody().getExpiration().getTime();
			if (checkExpires < new Date().getTime()) {
				requestContext.abortWith(Response.status(Response.Status.EXPECTATION_FAILED).build());
			}
		} catch (Exception e) {
			requestContext.abortWith(Response.status(Response.Status.UNAUTHORIZED).build());
		}
	}

}
