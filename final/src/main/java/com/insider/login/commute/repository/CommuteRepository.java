package com.insider.login.commute.repository;

import com.insider.login.commute.entity.Commute;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDate;
import java.util.List;

public interface CommuteRepository extends JpaRepository<Commute, Integer> {

    Commute findByCommuteNo(int commuteNo);

//    List<Commute> findByMemberId(int memberId);

    List<Commute> findByMemberIdAndWorkingDateBetween(int memberId, LocalDate startWeek, LocalDate endWeek);

    List<Commute> findByMemberDepartmentDepartNoAndWorkingDateBetween(int departNo, LocalDate startDayOfMonth, LocalDate endDayOfMonth);

}
