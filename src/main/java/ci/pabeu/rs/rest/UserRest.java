package ci.pabeu.rs.rest;

import java.text.ParseException;
import java.util.List;

import javax.persistence.EntityManager;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import ci.pabeu.rs.dao.entity.User;
import ci.pabeu.rs.em.JpaEntityManager;
import ci.pabeu.rs.helper.dto.UserDto;
import ci.pabeu.rs.helper.dto.transformer.UserTransformer;

@Path("/users")
public class UserRest {
	
	private JpaEntityManager jpa = new JpaEntityManager();
	
	@GET()
	@Path("/")
	@Produces(value = {MediaType.APPLICATION_JSON})
	public List<UserDto> get() throws ParseException {
		
		EntityManager em = jpa.getEntityManager();
		String query = "SELECT u FROM User u";
		
		List<User> users = em.createQuery(query, User.class).getResultList();
		em.close();
		return UserTransformer.INSTANCE.toDtos(users);
	}

}
