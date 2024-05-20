package com.insider.login.approval.repository;

import com.insider.login.approval.entity.Approval;
import com.insider.login.approval.entity.Form;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.Query;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class ApprovalRepository {

    @PersistenceContext
    private EntityManager manager;

    public void save(Approval approval){
        manager.persist(approval);
    }

    public Approval findById(String approvalNo){
        return manager.find(Approval.class, approvalNo);
    }

    public void update(Approval approval){
        manager.merge(approval);
    }


    //상신 목록 리스트 반환
    public List<Approval> findByMemberId(int memberId) {
        String query = "SELECT approval_no, member_id, approval_title, approval_content, approval_date,  approval_status, reject_reason, form_no" +
                " FROM approval" +
                " WHERE member_id = ?" +
                " AND approval_status != '임시저장'";

        List<Approval> approvalList = manager.createNativeQuery(query, Approval.class)
                .setParameter(1, memberId)
                .getResultList();

        return approvalList;
    }

    //상신 목록 페이지 반환
    public Page<Approval> findByMemberId(int memberId, Pageable pageable, String direction, String title){
        String sql = "SELECT approval_no, member_id, approval_title, approval_content, approval_date,  approval_status, reject_reason, form_no" +
                " FROM approval" +
                " WHERE member_id = ?" +
                " AND approval_status != '임시저장' "+
                " AND approval_title LIKE ?";

        if(direction == "ASC" || direction.equals("ASC")){
            sql += " ORDER BY approval_date ASC";
        }
        else{
            sql += " ORDER BY approval_date DESC";
        }

        Query query = manager.createNativeQuery(sql, Approval.class);
        query.setParameter(1, memberId)
            .setParameter(2, "%" + title + "%");

        int pageNumber = pageable.getPageNumber();
        int pageSize = pageable.getPageSize();
        int offset = pageNumber * pageSize;

        query.setFirstResult(offset);
        query.setMaxResults(pageSize);

        List<Approval> resultList = query.getResultList();

        int total = getNotTempTotalCount(memberId, title);
        System.out.println("전체 갯수 : " + total);

        return new PageImpl<>(resultList, pageable, total);
    }

    //상신 목록 총 갯수
    private int getNotTempTotalCount(int memberId, String title) {

        String countSql = "SELECT COUNT(*)" +
                " FROM approval" +
                " WHERE member_id = ?" +
                " AND approval_status != '임시저장'" +
                " AND approval_title LIKE ?";

        Query countQuery = manager.createNativeQuery(countSql);
        countQuery.setParameter(1, memberId)
                .setParameter(2, "%" + title + "%");

        return ((Number)countQuery.getSingleResult()).intValue();

    }

    //임시저장목록 리스트 반환
    public List<Approval> findTempByMemberId(int memberId) {
        String query =  "SELECT approval_no, member_id, approval_title, approval_content, approval_date,  approval_status, reject_reason, form_no" +
                " FROM approval" +
                " WHERE member_id = ?" +
                " AND approval_status = '임시저장'";

        List<Approval> approvalList = manager.createNativeQuery(query, Approval.class)
                .setParameter(1, memberId)
                .getResultList();

        return approvalList;
    }

    //임시저장목록 페이지 반환
    public Page<Approval> findTempByMemberId(int memberId, Pageable pageable, String direction, String title){
        String sql =  "SELECT approval_no, member_id, approval_title, approval_content, approval_date,  approval_status, reject_reason, form_no" +
                " FROM approval" +
                " WHERE member_id = ?" +
                " AND approval_status = '임시저장'" +
                " AND approval_title LIKE ?";

        if(direction == "ASC" || direction.equals("ASC")){
            sql += " ORDER BY approval_date ASC";
        }
        else{
            sql += " ORDER BY approval_date DESC";
        }

        Query query = manager.createNativeQuery(sql, Approval.class);
        query.setParameter(1, memberId)
                .setParameter(2, "%" + title + "%");

        int pageNumber = pageable.getPageNumber();
        int pageSize = pageable.getPageSize();
        int offset = pageNumber * pageSize;

        query.setFirstResult(offset);
        query.setMaxResults(pageSize);

        List<Approval> resultList = query.getResultList();


        int total = getTempTotalCount(memberId, title);
        return new PageImpl<>(resultList, pageable, total);

    }

    //임시저장 목록 총 갯수
    private int getTempTotalCount(int memberId, String title) {

        String countSql = "SELECT COUNT(*)" +
                " FROM approval" +
                " WHERE member_id = ?" +
                " AND approval_status = '임시저장'" +
                " AND approval_title LIKE ?";

        Query countQuery = manager.createNativeQuery(countSql);
        countQuery.setParameter(1, memberId)
                .setParameter(2, "%" + title + "%");

        return ((Number)countQuery.getSingleResult()).intValue();

    }

    //전자결재 삭제
    public void deleteById(String approvalNo) {
        Approval approval = findById(approvalNo);
        Approval managedApproval = manager.merge(approval);
        manager.remove(managedApproval);
    }


    //가장 마지막 전자결재 번호 조회
    public String findByApprovalNo(String yearFormNo) {

        String sql = "SELECT approval_no" +
                " FROM approval" +
                " WHERE approval_no LIKE ?" +
                " ORDER BY approval_date desc" +
                " limit 1";

        Query query = manager.createNativeQuery(sql)
                .setParameter(1, "%" + yearFormNo + "%");



        return (String)query.getSingleResult();
    }
}

