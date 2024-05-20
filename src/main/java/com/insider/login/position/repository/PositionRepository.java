package com.insider.login.position.repository;

import com.insider.login.position.entity.Position;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface PositionRepository extends JpaRepository<Position, Long> {
    Optional<Position> findByPositionName(String existingPositionName);

    void deletePositionByPositionName(String positionName);
}
