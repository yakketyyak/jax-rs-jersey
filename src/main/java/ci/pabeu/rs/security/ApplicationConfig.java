package ci.pabeu.rs.security;

import javax.inject.Named;
import javax.security.enterprise.authentication.mechanism.http.BasicAuthenticationMechanismDefinition;

@BasicAuthenticationMechanismDefinition(realmName = "file")

@Named
public class ApplicationConfig {

}
