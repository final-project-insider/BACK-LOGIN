//package com.insider.login.commute.repository;
//
//import com.insider.login.commute.entity.Correction;
//import org.springframework.data.jpa.repository.JpaRepository;
//
//import java.time.LocalDate;
//import java.util.List;
//
//public interface CorrectionRepository extends JpaRepository<Correction, Integer> {
//
//    Correction findByCorrNo(int corrNo);
//
//    List<Correction> findAllBetween(LocalDate startDayOfMonth, LocalDate endDayOfMonth);
//}
