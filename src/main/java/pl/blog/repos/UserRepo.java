package pl.blog.repos;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import pl.blog.domain.Users;

import java.util.Optional;

/**
 * Created by Mike on 01.07.2017.
 */
@Repository
public interface UserRepo extends JpaRepository<Users, Long> {
    Optional<Users> findUserByUsername(String username);

    Optional<Users> findUserByMail(String mail);
}
