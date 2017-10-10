package pl.blog.repos;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import pl.blog.domain.Role;

/**
 * Created by Mike on 20.07.2017.
 */
@Repository
public interface RolesRepo extends CrudRepository<Role, Long> {

}
