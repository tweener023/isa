package com.isa.repository;

import com.isa.model.AnswerToComplaint;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AnswerToComplaintRepository extends JpaRepository<AnswerToComplaint, Integer> {
    AnswerToComplaint findByComplaint_ComplaintId(Integer complaintId);
}
