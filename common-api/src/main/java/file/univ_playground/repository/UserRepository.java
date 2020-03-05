package file.univ_playground.repository;

import file.univ_playground.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository
        extends JpaRepository<User, Long> {
}
