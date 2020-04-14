package ci.pabeu.rs.security;

import static javax.security.enterprise.identitystore.CredentialValidationResult.Status.VALID;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.security.enterprise.AuthenticationException;
import javax.security.enterprise.AuthenticationStatus;
import javax.security.enterprise.authentication.mechanism.http.HttpAuthenticationMechanism;
import javax.security.enterprise.authentication.mechanism.http.HttpMessageContext;
import javax.security.enterprise.credential.UsernamePasswordCredential;
import javax.security.enterprise.identitystore.CredentialValidationResult;
import javax.security.enterprise.identitystore.IdentityStoreHandler;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@ApplicationScoped
public class AuthenticationMechanism implements HttpAuthenticationMechanism {

	@Inject
	private IdentityStoreHandler identityStoreHandler;

	@Override
	public AuthenticationStatus validateRequest(HttpServletRequest request, HttpServletResponse response,
			HttpMessageContext httpMessageContext) throws AuthenticationException {

		String name = request.getParameter("caller");
		String password = request.getParameter("password");

		if (name != null && password != null) {

			// Delegate the {credentials in -> identity data out} function to
			// the Identity Store
			CredentialValidationResult result = identityStoreHandler
					.validate(new UsernamePasswordCredential(name, password));

			if (result.getStatus() == VALID) {
				// Communicate the details of the authenticated user to the
				// container. In many cases the underlying handler will just store the details
				// and the container will actually handle the login after we return from
				// this method.
				return httpMessageContext.notifyContainerAboutLogin(result.getCallerPrincipal(),
						result.getCallerGroups());
			}
		}
		return httpMessageContext.responseUnauthorized();

	}

}
