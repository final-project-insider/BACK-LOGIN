package com.insider.login.other.note.repository;

import com.insider.login.other.note.entity.Note;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface NoteRepository extends JpaRepository<Note, Integer> {

//    Page<Note> findBySenderIdAndDeleteYn(int memberId, Pageable pageable, String deleteYn);
//
//    Page<Note> findByReceiverIdAndDeleteYn(int memberId, Pageable pageable, String deleteYn);


    Note findByNoteNo(int noteNo);

    Page<Note> findBySenderIdAndSendDeleteYn(int memberId, Pageable pageable, String sendDeleteYn);

    Page<Note> findByReceiverIdAndReceiveDeleteYn(int memberId, Pageable pageable, String receiveDeleteYn);
}
