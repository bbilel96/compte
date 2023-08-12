package tn.iit.compte.app.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import tn.iit.compte.app.model.Compte;
import tn.iit.compte.app.model.History;

@Repository
public interface HistoryRepository extends JpaRepository<History, String> {
    Page<History> findAllByCompteOrderByCreatedAtDesc(Compte compte, Pageable pageable);
}
