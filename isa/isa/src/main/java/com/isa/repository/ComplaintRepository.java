package com.isa.repository;

import com.isa.model.Complaint;
import com.isa.model.Facility;
import com.isa.model.StatusOfComplaint;
import com.isa.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ComplaintRepository extends JpaRepository<Complaint, Integer> {

    List<Complaint> findByUser(User user);

    List<Complaint> findByFacility(Facility facility);
    List<Complaint> findByStatusOfComplaint(StatusOfComplaint status);
}
