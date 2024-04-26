package com.insider.login.other.note.service;

import com.insider.login.other.note.dto.NoteDTO;
import com.insider.login.other.note.entity.Note;
import com.insider.login.other.note.repository.NoteRepository;
import jakarta.transaction.Transactional;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;


@Service
@Slf4j
public class NoteService {

    private final ModelMapper modelMapper;

    private final NoteRepository noteRepository;

    @Autowired
    public NoteService(ModelMapper modelMapper, NoteRepository noteRepository) {
        this.modelMapper = modelMapper;
        this.noteRepository = noteRepository;
    }

    /**
     * 쪽지 전체 목록 조회 + 페이징
     */
    public Page<NoteDTO> selectNoteList(int memberId, Integer receiverId, Integer senderId, Pageable pageable, String deleteYn) {

        Page<Note> notes;

        if (deleteYn != null) {
            if (receiverId != null && deleteYn.equals("N")) {
                notes = noteRepository.findByReceiverIdAndDeleteYn(memberId, pageable, "N");
            } else if (senderId != null && deleteYn.equals("N")) {
                notes = noteRepository.findBySenderIdAndDeleteYn(memberId, pageable, "N");
            } else {
                // 빈 페이지 반환
                return Page.empty();
            }
        } else {
            return Page.empty();
        }


        if (notes != null) {
            return notes.map(note -> modelMapper.map(note, NoteDTO.class));
        } else {
            return Page.empty();
        }
    }

    /** 상세 조회 */
    public Optional<Note> findNoteByNoteNo(int noteNo) {

        return noteRepository.findById(noteNo);
    }


    /** 쪽지 등록 */
    @Transactional
    public Map<String, Object> insertNote(NoteDTO noteDTO) {

        Map<String, Object> result = new HashMap<>();

        try {
            Note note = modelMapper.map(noteDTO, Note.class);
            noteRepository.save(note);

            result.put("result", true);
        } catch (Exception e) {

            log.error(e.getMessage());
            result.put("result", false);
        }
        return result;
    }

    /** 쪽지 삭제여부 업데이트 */

    public Map<String, Object> deleteNote(int noteNo, String deleteYn) {

        Map<String, Object> result = new HashMap<>();

        Note note = noteRepository.findByNoteNo(noteNo);

        if (note != null) {
            NoteDTO noteDTO = modelMapper.map(note, NoteDTO.class);

            noteDTO.setDeleteYn(deleteYn);

            Note updateNote = modelMapper.map(noteDTO, Note.class);
            noteRepository.save(updateNote);

            result.put("result", true);

        } else {
            result.put("result", false);
        }
        return result;
    }


}
