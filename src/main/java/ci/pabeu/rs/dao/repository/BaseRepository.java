package ci.pabeu.rs.dao.repository;

import java.util.List;

public interface BaseRepository<T extends Object, ID> {

	T getOne(ID id);

	List<T> findAll();

	List<T> findAllById(Iterable<ID> ids);

	T save(T o);

}
