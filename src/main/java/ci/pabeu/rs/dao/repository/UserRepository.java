package ci.pabeu.rs.dao.repository;

import java.util.Collections;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;

import ci.pabeu.rs.dao.entity.User;
import ci.pabeu.rs.em.JpaEntityManager;

public class UserRepository implements BaseRepository<User, Integer> {

	private JpaEntityManager jpa;

	private EntityManager em;

	public UserRepository() {
		jpa = new JpaEntityManager();
		em = jpa.getEntityManager();
	}


	@Override
	public User getOne(Integer id) {

		return em.find(User.class, id);
	}

	@Override
	public List<User> findAll() {
		String query = "SELECT u FROM User u";
		return em.createQuery(query, User.class).getResultList();
	}

	@Override
	public List<User> findAllById(Iterable<Integer> ids) {
		return Collections.emptyList();
	}

	@Override
	public User save(User user) {
		em.getTransaction().begin();
		em.persist(user);
		em.getTransaction().commit();
		return em.merge(user);
	}

	public User findByUserNameAndPassword(String userName, String password) {

		TypedQuery<User> query = em.createQuery(
				"SELECT u FROM User u WHERE u.userName = :userName AND u.password = :password", User.class);
		query.setParameter("userName", userName);
		query.setParameter("password", password);

		return query.getSingleResult();

	}

}
