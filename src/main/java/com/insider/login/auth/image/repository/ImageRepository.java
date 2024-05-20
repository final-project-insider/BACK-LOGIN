package com.insider.login.auth.image.repository;

import com.insider.login.member.entity.Member;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ImageRepository extends JpaRepository<Member, String> {


}
