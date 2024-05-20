package com.insider.login.calendar.repository;

import com.insider.login.calendar.entity.Calendar;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CalendarRepository extends JpaRepository<Calendar, Integer> {

    @Query("SELECT c FROM Calendar c WHERE c.department = 'all' OR c.department = :department")
    List<Calendar> findBydepartment(@Param("department") String department);
}
