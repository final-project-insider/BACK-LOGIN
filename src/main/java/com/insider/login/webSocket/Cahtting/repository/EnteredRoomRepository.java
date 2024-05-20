package com.insider.login.webSocket.Cahtting.repository;

import com.insider.login.member.entity.Member;
import com.insider.login.webSocket.Cahtting.entity.EnteredRoom;
import com.insider.login.webSocket.Cahtting.entity.RoomStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface EnteredRoomRepository extends JpaRepository<EnteredRoom, Integer> {

    @Query("SELECT er FROM EnteredRoom er WHERE er.member = :member or er.receiverMember = :member AND er.roomStatus = :roomStatus")
    List<EnteredRoom> findAllList(@Param("roomStatus") RoomStatus roomStatus, @Param("member") Optional<Member> member);

}
