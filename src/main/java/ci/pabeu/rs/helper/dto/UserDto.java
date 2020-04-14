package ci.pabeu.rs.helper.dto;


import java.io.Serializable;

import com.fasterxml.jackson.annotation.JsonInclude;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.RequiredArgsConstructor;


@Data
@EqualsAndHashCode
@RequiredArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class UserDto implements Serializable {

	private static final long serialVersionUID = 1L;

	private Integer id;

	private String email;

	private String userName;

	private String password;

	private String firstName;

	private String lastName;

	private String birthDay;


}
