package com.insider.login.commute.repository;

import com.insider.login.commute.entity.Commute;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CommuteRepository extends JpaRepository<Commute, Integer> {

    Commute findByCommuteNo(int commuteNo);
}
