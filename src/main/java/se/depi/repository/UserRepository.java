package se.depi.repository;

import java.util.Collection;

import org.springframework.data.repository.PagingAndSortingRepository;

import se.depi.model.User;

public interface UserRepository extends PagingAndSortingRepository<User, Long> {

	User findByUsername(String username);

	Collection<User> findByFirstname(String firstname);

	Collection<User> findByLastname(String lastname);

	Collection<User> findByTeamId(Long id);
}
