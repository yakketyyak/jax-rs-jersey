package ci.pabeu.rs.helper.dto;


import java.io.Serializable;

import lombok.Data;
import lombok.EqualsAndHashCode;


@Data
@EqualsAndHashCode
public class UserDto implements Serializable {

	private static final long serialVersionUID = 1L;

	private Integer id;

	private String email;

	private String firstName;

	private String lastName;

	private String birthDay;



}
