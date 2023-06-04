package com.isa.service;

import com.isa.model.Complaint;
import com.isa.model.Facility;
import com.isa.model.StatusOfComplaint;
import com.isa.model.User;
import com.isa.repository.ComplaintRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ComplaintService {

    @Autowired
    ComplaintRepository complaintRepository;

    public List<Complaint> getAllComplaints() {
        return complaintRepository.findAll();
    }

    public Complaint getComplaintById(Integer complaintId) {
        return complaintRepository.findById(complaintId)
                .orElse(null);
    }

    public List<Complaint> getComplaintsByUser(User user) {
        return complaintRepository.findByUser(user);
    }

    public List<Complaint> getComplaintsByFacultyCenter(Facility facility) {
        return complaintRepository.findByFacility(facility);
    }

    public List<Complaint> getComplaintsByStatus(StatusOfComplaint status) {
        return complaintRepository.findByStatusOfComplaint(status);
    }

    public Complaint saveComplaint(Complaint complaint) {
        return complaintRepository.save(complaint);
    }

    public void deleteComplaint(Complaint complaint) {
        complaintRepository.delete(complaint);
    }
}
