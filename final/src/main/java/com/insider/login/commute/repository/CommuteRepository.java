package com.insider.login.commute.repository;

import com.insider.login.commute.entity.Commute;
import com.insider.login.commute.entity.CommuteMember;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface CommuteRepository extends JpaRepository<Commute, Integer> {

    Commute findByCommuteNo(int commuteNo);

    List<Commute> findByMemberIdAndWorkingDateBetween(int memberId, LocalDate startWeek, LocalDate endWeek);

    List<Commute> findByCommuteMemberAndWorkingDateBetween(CommuteMember commuteMember, LocalDate startDayOfMonth, LocalDate endDayOfMonth);
}
