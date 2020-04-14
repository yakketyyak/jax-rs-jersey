package ci.pabeu.rs.security;

import javax.enterprise.context.ApplicationScoped;
import javax.security.enterprise.authentication.mechanism.http.BasicAuthenticationMechanismDefinition;

@BasicAuthenticationMechanismDefinition(realmName = "file")
@ApplicationScoped
public class ApplicationConfig {

}
