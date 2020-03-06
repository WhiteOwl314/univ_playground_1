package file.univ_playground.repository;

import file.univ_playground.domain.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface UserRepository
        extends JpaRepository<User, Long> {
}
