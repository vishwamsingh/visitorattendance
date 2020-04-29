package com.ubs.futurity.registrationapp.repositories;

import com.ubs.futurity.registrationapp.models.Attendance;
import com.ubs.futurity.registrationapp.models.Visitor;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;

public interface VisitorAttendance extends MongoRepository<Attendance, String> {
    public List<Attendance> findByVisitorId(String visitorId);
}
