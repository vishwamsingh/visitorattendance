package com.ubs.futurity.registrationapp.controllers;

import com.ubs.futurity.registrationapp.models.Attendance;
import com.ubs.futurity.registrationapp.models.Visitor;
import com.ubs.futurity.registrationapp.repositories.VisitorAttendance;
import com.ubs.futurity.registrationapp.repositories.Visitors;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.View;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Base64;
import java.util.List;
import java.util.Locale;
import java.util.Optional;

@Controller
public class VisitorController {
    @Autowired
    private Visitors repository;

    @Autowired
    private VisitorAttendance attendanceRepo;

    @Value("${app.photoFileDirectory}")
    private String photoFileDirectory;

    @RequestMapping("/register")
    public String registrationForm(@ModelAttribute Visitor visitor) {
        return "Registration";
    }

    @RequestMapping(value="/registered", method = RequestMethod.POST)
    public String registered(@ModelAttribute Visitor visitor) {
        return "RegisteredVisitor";
    }

   @PostMapping("/submit")
    public String submitForm(@ModelAttribute Visitor visitor)  throws IOException  {
        byte[] bytes = visitor.getPhoto().replaceAll("data:image/.+;base64,", "").getBytes();
        //File image = new File(photoFileDirectory, "img-"+visitor.getName()+"-"+visitor.getPhone()+".png");
        //visitor.setPhotoURL(image.getPath());
        visitor.setId(visitor.getPhone());
        /*if (!image.exists()) {
            Files.createDirectories(image.getParentFile().toPath());
        }
        Files.write(image.toPath(), Base64.getDecoder().decode(bytes));*/
        Visitor savedVisitor = repository.save(visitor);
        List<Visitor> fetchedVisitors = repository.findByName(visitor.getName());
        return "Success";
    }

    @PostMapping("/search")
    public @ResponseBody Visitor search(@RequestParam String phone)  throws IOException  {
        Visitor fetchedVisitors = repository.findByPhone(phone);
        return fetchedVisitors;
    }

    @PostMapping("/logout")
    public @ResponseBody Visitor logout(@RequestParam String phone)  throws IOException  {
        Visitor fetchedVisitor = repository.findByPhone(phone);
        fetchedVisitor.setLoggedIn(false);
        repository.save(fetchedVisitor);
        List<Attendance> visitorAttendances = attendanceRepo.findByVisitorId(fetchedVisitor.getId());
        for(Attendance attendance:visitorAttendances) {
            if(attendance.getLogoutTime() == null) {
                attendance.setLogoutTime(LocalDateTime.now(ZoneId.systemDefault()));
                attendanceRepo.save(attendance);
            }
        }
        return fetchedVisitor;
    }

    @PostMapping("/login")
    public String login(@ModelAttribute Visitor visitor)  throws IOException  {
        Attendance attendance = new Attendance();
        attendance.setName(visitor.getName());
        attendance.setVisitorId(visitor.getId().toString());
        attendance.setVisitId(attendanceRepo.findAll().size()+1);
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("M/d/yyyy, h:m:s a");
        //attendance.setDatetime(LocalDateTime.parse(visitor.getLastLogIn(),formatter));
        attendance.setLoginTime(LocalDateTime.now(ZoneId.systemDefault()));
        attendanceRepo.save(attendance);
        Optional<Visitor> fetchedVisitor = repository.findById(visitor.getId());
        if(fetchedVisitor.isPresent()) {
            fetchedVisitor.get().setLoggedIn(true);
            fetchedVisitor.get().setLastLogIn(visitor.getLastLogIn());
            repository.save(fetchedVisitor.get());
        }
        return "VisitorLoggedIn";
    }
}
