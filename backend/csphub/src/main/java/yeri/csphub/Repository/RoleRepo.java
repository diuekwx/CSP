package yeri.csphub.Repository;

import org.springframework.context.support.BeanDefinitionDsl;
import org.springframework.stereotype.Repository;
import yeri.csphub.Entities.ERole;
import yeri.csphub.Entities.Role;

import java.util.Optional;

@Repository
public interface RoleRepo {

    Optional<Role> findByName(ERole name);
}
