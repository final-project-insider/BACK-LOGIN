package com.insider.login.commute.repository;

import com.insider.login.commute.entity.Correction;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;

@Repository
public interface CorrectionRepository extends JpaRepository<Correction, Integer> {

    Correction findByCorrNo(int corrNo);

    Page<Correction> findAllByCorrRegistrationDateBetween(LocalDate startDayOfMonth, LocalDate endDayOfMonth, Pageable pageable);


    Page<Correction> findByCommuteMemberIdAndCorrRegistrationDateBetween(int memberId, LocalDate startDayOfMonth, LocalDate endDayOfMonth, Pageable pageable);
}
