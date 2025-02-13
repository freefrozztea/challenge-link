package demo.link_challenge.repository;

import demo.link_challenge.models.TransactionModel;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Repository
public interface TransactionRepository extends JpaRepository<TransactionModel, Long>, JpaSpecificationExecutor<TransactionModel> {
    @Query("SELECT t FROM TransactionModel t WHERE t.userId = :userId")
    Page<TransactionModel> findByUserId(String userId, Pageable pageable);

    @Query("SELECT t FROM TransactionModel t WHERE t.status = :status")
    Page <TransactionModel> findByStatus(String status, Pageable pageable);

    TransactionModel findByTransactionId(UUID transactionId);

    List<TransactionModel> findByCreatedAtBetween(LocalDateTime start, LocalDateTime end);
}
