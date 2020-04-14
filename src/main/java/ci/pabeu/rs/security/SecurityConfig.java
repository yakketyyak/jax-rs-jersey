package ci.pabeu.rs.security;

import static javax.security.enterprise.identitystore.CredentialValidationResult.INVALID_RESULT;

import java.util.Arrays;
import java.util.HashSet;

import javax.enterprise.context.ApplicationScoped;
import javax.security.enterprise.credential.UsernamePasswordCredential;
import javax.security.enterprise.identitystore.CredentialValidationResult;
import javax.security.enterprise.identitystore.IdentityStore;

import ci.pabeu.rs.dao.entity.User;
import ci.pabeu.rs.dao.repository.UserRepository;

@ApplicationScoped
public class SecurityConfig implements IdentityStore {

	private UserRepository userRepository;

	public SecurityConfig() {
		userRepository = new UserRepository();
	}

	public CredentialValidationResult validate(UsernamePasswordCredential usernamePasswordCredential) {
		String caller = usernamePasswordCredential.getCaller();
		String password = usernamePasswordCredential.getPasswordAsString();
		
		User user  = this.userRepository.findByUserNameAndPassword(caller, password);
		if (user == null) {
			return INVALID_RESULT;
		}

		if (usernamePasswordCredential.compareTo(user.getUserName(), user.getPassword())) {
			// return a VALID result with the caller and the set of groups s/he belongs to.
			return new CredentialValidationResult(caller, new HashSet<>(Arrays.asList("ADMIN")));
		}

		return INVALID_RESULT;
	}

}
