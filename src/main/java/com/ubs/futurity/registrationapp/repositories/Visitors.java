package com.ubs.futurity.registrationapp.repositories;
import java.util.List;
import com.ubs.futurity.registrationapp.models.Visitor;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface Visitors extends MongoRepository<Visitor, String> {
        public List<Visitor> findByName(String name);
        public Visitor findByPhone(String phone);
}
