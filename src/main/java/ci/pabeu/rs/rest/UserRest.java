package ci.pabeu.rs.rest;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.List;

import javax.servlet.annotation.HttpConstraint;
import javax.servlet.annotation.ServletSecurity;
import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.Consumes;
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

import org.apache.commons.lang.StringUtils;

import ci.pabeu.rs.dao.entity.User;
import ci.pabeu.rs.dao.repository.UserRepository;
import ci.pabeu.rs.helper.dto.UserDto;
import ci.pabeu.rs.helper.dto.transformer.UserTransformer;

@Path("/users")
@ServletSecurity(@HttpConstraint(rolesAllowed = "ADMIN"))
public class UserRest {
	
	private HandleLanguage handleLanguage;
	
	private UserRepository userRepository;

	private I18nConfig i18nConfig;

	public UserRest() {
		handleLanguage = new HandleLanguage();
		i18nConfig = new I18nConfig();
		userRepository = new UserRepository();
	}

	@GET()
	@Path("/")
	@Produces(value = {MediaType.APPLICATION_JSON})
	public List<UserDto> getAll(@Context HttpServletRequest req) throws ParseException {
		
		return UserTransformer.INSTANCE.toDtos(userRepository.findAll());
	}

	@GET()
	@Path("/{id}")
	@Produces(value = { MediaType.APPLICATION_JSON })
	public UserDto get(@Context HttpServletRequest req, @PathParam(value = "id") Integer id) throws ParseException {

		return UserTransformer.INSTANCE.toDto(userRepository.getOne(id));
	}

	@POST()
	@Path("/create")
	@Produces(value = { MediaType.APPLICATION_JSON })
	@Consumes(value = { MediaType.APPLICATION_JSON })
	public UserDto create(@Context HttpServletRequest req, UserDto dto) throws ParseException {

		return UserTransformer.INSTANCE.toDto(userRepository.save(UserTransformer.INSTANCE.toEntity(dto)));
	}

	@PUT()
	@Path("/{id}")
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
