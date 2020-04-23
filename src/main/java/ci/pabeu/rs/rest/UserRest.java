package ci.pabeu.rs.rest;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.List;

import javax.naming.InitialContext;
import javax.servlet.http.HttpServletRequest;
import javax.sql.DataSource;
import javax.ws.rs.Consumes;
import javax.ws.rs.FormParam;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;
import javax.ws.rs.core.SecurityContext;

import org.apache.commons.lang.StringUtils;

import ci.pabeu.rs.dao.entity.User;
import ci.pabeu.rs.dao.repository.UserRepository;
import ci.pabeu.rs.helper.dto.UserDto;
import ci.pabeu.rs.helper.dto.transformer.UserTransformer;
import ci.pabeu.rs.response.Result;
import ci.pabeu.rs.security.Secured;

@Path("/users")
public class UserRest {
	
	
	private DataSource dataSource;

	private UserRepository userRepository;


	public UserRest() {
		userRepository = new UserRepository();
	}

	@GET
	@Path("/")
	@Secured
	@Produces(value = {MediaType.APPLICATION_JSON})
	public List<UserDto> getAll(@Context HttpServletRequest req,
			@Context SecurityContext securityContext)
			throws ParseException {
		
		return UserTransformer.INSTANCE.toDtos(userRepository.findAll());
	}

	@GET
	@Path("/{id}")
	@Secured
	@Produces(value = { MediaType.APPLICATION_JSON })
	public UserDto get(@Context HttpServletRequest req, @PathParam(value = "id") Integer id) throws ParseException {

		return UserTransformer.INSTANCE.toDto(userRepository.getOne(id));
	}

	@POST
	@Path("/create")
	@Secured
	@Produces(value = { MediaType.APPLICATION_JSON })
	@Consumes(value = { MediaType.APPLICATION_JSON })
	public UserDto create(@Context HttpServletRequest req, UserDto dto) throws ParseException {

		return UserTransformer.INSTANCE.toDto(userRepository.save(UserTransformer.INSTANCE.toEntity(dto)));
	}

	@POST
	@Path("/auth/token")
	@Consumes(MediaType.APPLICATION_FORM_URLENCODED)
	public Response auth(@Context HttpServletRequest req,
			@FormParam("userName") String userName,
			@FormParam("password") String password)
			throws ParseException {

		try {
			System.out.println("datasource " + dataSource);
			InitialContext initialCtxt = new InitialContext();
			dataSource = (DataSource) initialCtxt.lookup("java:comp/env/jdbc/appDatasource");
			// Authenticate the user using the credentials provided
			User user = this.userRepository.findByUserNameAndPassword(userName, password);
			if (user == null) {
				return Response.status(Status.UNAUTHORIZED).build();
			}

			// Issue a token for the user
			String token = this.userRepository.issueToken(userName, req.getPathInfo());

			// Return the token on the response
			return Response.ok(new Result(token)).build();

		} catch (Exception e) {
			return Response.status(Status.UNAUTHORIZED).build();
		}
	}

	@PUT
	@Path("/{id}")
	@Secured
	@Produces(value = { MediaType.APPLICATION_JSON })
	@Consumes(value = { MediaType.APPLICATION_JSON })
	public UserDto update(@Context HttpServletRequest req, @PathParam(value = "id") Integer id, UserDto dto)
			throws ParseException {

		User find = this.userRepository.getOne(id);
		if (find == null) {
			throw new WebApplicationException(Response.Status.NOT_FOUND);
		}

		if (StringUtils.isNotBlank(dto.getFirstName())) {
			find.setFirstName(dto.getFirstName());
		}

		if (StringUtils.isNotBlank(dto.getLastName())) {
			find.setLastName(dto.getLastName());
		}

		if (StringUtils.isNotBlank(dto.getEmail())) {
			find.setEmail(dto.getEmail());
		}

		if (StringUtils.isNotBlank(dto.getBirthDay())) {
			find.setBirthDay(new SimpleDateFormat("dd/MM/yyyy").parse(dto.getBirthDay()));
		}
		return UserTransformer.INSTANCE.toDto(userRepository.save(find));
	}

}
