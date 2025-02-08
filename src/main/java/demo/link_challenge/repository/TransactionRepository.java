package demo.link_challenge.repository;

import demo.link_challenge.models.TransactionModel;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TransactionRepository extends JpaRepository<TransactionModel, Long>, JpaSpecificationExecutor<TransactionModel> {
    Page<TransactionModel> findByUserId(String userId, Pageable pageable);
    List<TransactionModel> findByStatus(String status);
}
