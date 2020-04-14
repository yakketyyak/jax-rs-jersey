package ci.pabeu.rs.helper.dto;


import java.io.Serializable;

import javax.ws.rs.core.MultivaluedMap;
import javax.ws.rs.core.Response;

import com.fasterxml.jackson.annotation.JsonInclude;

import lombok.Data;
import lombok.EqualsAndHashCode;


@Data
@EqualsAndHashCode
@JsonInclude(JsonInclude.Include.NON_NULL)
public class UserDto extends Response implements Serializable {

	private static final long serialVersionUID = 1L;

	private Integer id;

	private String email;

	private String userName;

	private String password;

	private String firstName;

	private String lastName;

	private String birthDay;

	@Override
	public Object getEntity() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public int getStatus() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public MultivaluedMap<String, Object> getMetadata() {
		// TODO Auto-generated method stub
		return null;
	}


}
