package com.insider.login.transferredHistory.repository;

import com.insider.login.transferredHistory.entity.TransferredHistory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TransferredHistoryRepository extends JpaRepository<TransferredHistory, Integer> {
}
