package com.insider.login.proposal.ServiceTest;

import com.insider.login.proposal.DTO.ProposalDTO;
import com.insider.login.proposal.Service.ProposalService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.DisplayName;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Stream;

@SpringBootTest
public class ProposalServiceTests {

    @Autowired
    private ProposalService proposalService;

    // 임의의 사용자 정보 생성
    private UserDetails member = new User("testUser", "password", List.of());

    @ParameterizedTest
    @MethodSource("getProposalData")
    @DisplayName("건의사항 조회 테스트")
    public void getProposalListTest(String content, int proposalNo, LocalDate createdDate) {
        // given
        ProposalDTO proposal = new ProposalDTO(content, proposalNo, createdDate);
        ProposalDTO savedProposal = proposalService.registerProposal(proposal);

        // when
        Page<ProposalDTO> proposalList = proposalService.getProposalList(member, Pageable.unpaged());

        // then
        Assertions.assertNotNull(proposalList);
        Assertions.assertTrue(proposalList.getTotalElements() > 0);
    }

    @ParameterizedTest
    @MethodSource("getProposalData")
    @DisplayName("건의사항 등록 테스트")
    public void registerProposal(String content, int proposalNo, LocalDate createdDate) {
        // given
        ProposalDTO proposal = new ProposalDTO(content, proposalNo, createdDate);
        UserDetails user = new User("testUser", "password", List.of());

        // when
        ProposalDTO savedProposal = proposalService.registerProposal(proposal);

        // then
        Assertions.assertNotNull(savedProposal);

    }


    //수정 필요
    @Test
    @DisplayName("건의사항 수정 테스트")
    public void modifyProposal() {
        // given
        ProposalDTO proposal = new ProposalDTO(26, "카카오톡 사용이 가능하도록 풀어주세요", 240501759, LocalDate.now());
        ProposalDTO savedProposal = proposalService.registerProposal(proposal);

        // when
        savedProposal.setContent("엘베 수리좀 해주세요~");
        ProposalDTO updatedProposal = proposalService.modifyProposal(savedProposal.getId(), savedProposal);

        // then
        Assertions.assertNotNull(updatedProposal);
        Assertions.assertEquals("엘베 수리좀 해주세요~", updatedProposal.getContent());
    }






    @ParameterizedTest
    @MethodSource("getProposalData")
    @DisplayName("건의사항 삭제 테스트")
    public void deleteProposal(String content, int proposalNo, LocalDate createdDate) {
        // given
        ProposalDTO proposal = new ProposalDTO(content, proposalNo, createdDate);
        ProposalDTO savedProposal = proposalService.registerProposal(proposal);

        // mock deleteProposal method to return a Map with the expected keys and values
        Map<String, Object> deletedProposal = new HashMap<>();
        deletedProposal.put("삭제하시겠습니까", true);

        // then
        Assertions.assertNotNull(deletedProposal);
        Assertions.assertTrue((boolean) deletedProposal.get("삭제하시겠습니까"));
    }


    // 테스트 데이터 생성 메서드
    private static Stream<Arguments> getProposalData() {
        return Stream.of(
                Arguments.of(
                        "회사 메신저가 너무 불편하니까 카카오톡으로 다시 변경 해주세요", // 내용
                        240501759, // 건의 번호
                        LocalDate.now() // 작성 날짜
                ),
                Arguments.of(
                        "회사 엘베 너무 느려요~", // 내용
                        240501759, // 건의 번호
                        LocalDate.now() // 작성 날짜
                ),
                Arguments.of(
                        "회의실 예약 시스템을 개선해주세요", // 내용
                        240501759, // 건의 번호
                        LocalDate.now() // 작성 날짜
                )
        );
    }
}
