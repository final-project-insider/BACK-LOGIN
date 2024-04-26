package com.insider.login.other.announce.repository;

import com.insider.login.other.announce.entity.AncFile;
import com.insider.login.other.announce.entity.Announce;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.TypedQuery;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface AnnounceRepository extends JpaRepository<Announce, Integer> {


    @Query("SELECT a FROM Announce a JOIN AncFile f ON a.ancNo = f.ancNo WHERE a.ancNo = :ancNo")
    Announce findAnnounceWithFiles(@Param("ancNo") int ancNo);

    Announce findByAncNo(int ancNo);
}
