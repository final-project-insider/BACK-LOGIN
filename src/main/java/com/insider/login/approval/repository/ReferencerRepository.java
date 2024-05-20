package com.insider.login.approval.repository;

import com.insider.login.approval.entity.Approval;
import com.insider.login.approval.entity.Referencer;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.Query;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class ReferencerRepository {

    @PersistenceContext
    private EntityManager manager;

    public void save (Referencer referencer){
        manager.persist(referencer);
    }

    public List<Referencer> findByApprovalId(String approvalNo) {
        List<Referencer> referencerList = manager.createQuery("SELECT r FROM Referencer r WHERE r.approvalNo= :approvalNo", Referencer.class)
                .setParameter("approvalNo", approvalNo)
                .getResultList();

        return referencerList;
    }

    public List<Referencer> findByMemberId(int memberId) {
        List<Referencer> referencerList = manager.createQuery("SELECT r FROM Referencer r WHERE r.memberId= :memberId", Referencer.class)
                .setParameter("memberId", memberId)
                .getResultList();

        return referencerList;
    }

    //참조목록 페이지 반환
    public Page<Approval> findByMemberId(int memberId, Pageable pageable, String direction, String title) {

        String sql = "SELECT a.approval_no, a.member_id, a.approval_title, a.approval_content, a.approval_date, a.approval_status, a.reject_reason, a.form_no" +
                " FROM APPROVAL a" +
                " JOIN REFERENCER r ON r.approval_no = a.approval_no" +
                " WHERE r.member_id = ?" +
                " AND a.approval_title LIKE ?"
                ;

        if(direction == "ASC" || direction.equals("ASC")){
            sql += " ORDER BY a.approval_date ASC";
        }else{
            sql += " ORDER BY a.approval_date DESC";
        }

        Query query = manager.createNativeQuery(sql, Approval.class)
                .setParameter(1, memberId)
                .setParameter(2, "%" + title + "%");

        int pageNumber = pageable.getPageNumber();
        int pageSize = pageable.getPageSize();
        int offset = pageNumber * pageSize;

        query.setFirstResult(offset);
        query.setMaxResults(pageSize);

        List<Approval> resultList = query.getResultList();

        int total = getTotalCount(memberId, title);
        return new PageImpl<>(resultList, pageable, total);
    }

    //참조목록 갯수
    private int getTotalCount(int memberId, String title) {
        String countSql = "SELECT COUNT(*)" +
                " FROM APPROVAL a" +
                " JOIN REFERENCER r ON r.approval_no = a.approval_no" +
                " WHERE r.member_id = ?" +
                " AND a.approval_title LIKE ?"
                ;

        Query countQuery = manager.createNativeQuery(countSql);
        countQuery.setParameter(1, memberId)
                .setParameter(2, title);

        return ((Number)countQuery.getSingleResult()).intValue();
    }

    public void deleteById(String approvalNo) {

        List<Referencer> referencerList = findByApprovalId(approvalNo);

        if(!referencerList.isEmpty()){

            for(Referencer referencer : referencerList){
                Referencer managedReferencer = manager.merge(referencer);
                manager.remove(managedReferencer);
            }
        }
        else{

        }

    }


}
