package tn.iit.compte.app.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import tn.iit.compte.app.model.Compte;

public interface CompteRepository extends JpaRepository<Compte, String> {
    @Query(
            "select C from Compte C where C.user.id = :#{#id}"
    )
    public Page<Compte> findAllByUsers(@Param("id") String id,
                                       Pageable pageable);
}
