package com.insider.login.other.announce.repository;

import com.insider.login.other.announce.entity.AncFile;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface AnnounceFileRepository extends JpaRepository<AncFile, Integer> {

    List<AncFile> findByAncNo(int ancNo);
}
