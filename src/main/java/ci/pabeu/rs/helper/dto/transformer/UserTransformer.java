package ci.pabeu.rs.helper.dto.transformer;

import java.text.ParseException;
import java.util.List;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;
import org.mapstruct.factory.Mappers;

import ci.pabeu.rs.dao.entity.User;
import ci.pabeu.rs.helper.dto.UserDto;

@Mapper
public interface UserTransformer {

	UserTransformer INSTANCE = Mappers.getMapper(UserTransformer.class);


	@Mappings({
			@Mapping(source = "birthDay", dateFormat = "dd/MM/yyyy", target = "birthDay"),
	})
	UserDto toDto(User entity) throws ParseException;
	List<UserDto> toDtos(List<User> entities) throws ParseException;



	@Mappings({ @Mapping(source = "dto.id", target = "id"),
			@Mapping(source = "dto.firstName", target = "firstName"),
			@Mapping(source = "dto.lastName", target = "lastName"),
			@Mapping(source = "dto.email", target = "email"),
			@Mapping(source = "dto.birthDay", dateFormat = "dd/MM/yyyy", target = "birthDay"), })
	User toEntity(UserDto dto) throws ParseException;

}
