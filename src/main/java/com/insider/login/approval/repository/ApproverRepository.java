package com.insider.login.approval.repository;

import com.insider.login.approval.entity.Approver;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class ApproverRepository {

    @PersistenceContext
    private EntityManager manager;


    public void save(Approver approver){
        manager.persist(approver);
    }

    public List<Approver> findByApprovalId(String approvalNo) {
        List<Approver> approverList = manager.createQuery("SELECT a FROM Approver a WHERE a.approvalNo = :approvalNo", Approver.class)
                .setParameter("approvalNo", approvalNo)
                .getResultList();

        return approverList;
    }

    public void update(Approver approver) {

        manager.merge(approver);
    }

    public Approver findStatusById(String approvalNo) {
        String query = "SELECT approver_no, approval_no, approver_order, approver_status, approver_date, member_id" +
                " FROM approver" +
                " WHERE approval_no = ?" +
                " AND approver_status = '대기'" +
                " ORDER BY approver_order ASC" +
                " LIMIT 1";

        List<Approver> approverList = manager.createNativeQuery(query, Approver.class)
                .setParameter(1, approvalNo)
                .getResultList();

        Approver approver = null;

        if(approverList.size() > 0 || !approverList.isEmpty())
        {
            approver = approverList.get(0);
        }

        return approver;
    }

    public List<Approver> findByStandById(String title) {
        String query = "SELECT a.approver_no, a.approval_no, a.approver_order, a.approver_status, a.approver_date, a.member_id" +
                " FROM approver a" +
                " JOIN (" +
                "       SELECT d.approval_no, MIN(d.approver_order) AS min_order" +
                "       FROM approver d" +
                "       WHERE d.approver_status = '대기'" +
                "       GROUP BY d.approval_no" +
                ") b ON a.approval_no = b.approval_no AND a.approver_order = b.min_order" +
                " WHERE a.approval_no IN (" +
                "   SELECT c.approval_no" +
                "   FROM approval c" +
                "   WHERE c.approval_status = '처리 중'" +
                ") AND EXISTS(" +
                "   SELECT 1" +
                "   FROM approval e" +
                "   WHERE e.approval_no = a.approval_no" +
                "   AND e.approval_title LIKE ?" +
                ")";

        List<Approver> approverList = manager.createNativeQuery(query, Approver.class)
                .setParameter(1, "%" + title + "%")
                .getResultList();

        return approverList;
    }


    public void deleteById(String approvalNo) {

        List<Approver> approverList = findByApprovalId(approvalNo);

        for(Approver approver : approverList){
            Approver managedApprover = manager.merge(approver);
            manager.remove(managedApprover);
        }
    }
}
