package com.ubs.futurity.registrationapp.controllers;

import com.ubs.futurity.registrationapp.models.Attendance;
import com.ubs.futurity.registrationapp.models.Report;
import com.ubs.futurity.registrationapp.models.Visitor;
import com.ubs.futurity.registrationapp.repositories.VisitorAttendance;
import com.ubs.futurity.registrationapp.repositories.Visitors;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Controller
public class AdminController {
    @Autowired
    private Visitors repository;

    @Autowired
    private VisitorAttendance attendanceRepo;

    @Value("${app.reportFileDirectory}")
    private String reportFileDirectory;

    @RequestMapping("/admin")
    public String adminScreen(@ModelAttribute Report report) {
        return "AdminScreen";
    }

    @PostMapping("/attendanceReportByVisitor")
    public @ResponseBody void generateReportByVisitor(HttpServletResponse response, @ModelAttribute Report report)  throws IOException {
        List<Attendance> attendanceRegister =  attendanceRepo.findByVisitorId(report.getVisitorId());
       /* File reportFile = new File(reportFileDirectory, "report-"+report.getVisitorId()+".csv");
        if (!reportFile.exists()) {
            Files.createDirectories(reportFile.getParentFile().toPath());
        }*/
        response.setContentType("text/csv");
        response.setHeader("Content-Disposition","attachment; filename=" + "report-"+report.getVisitorId()+".csv");
        String commaSeparatedStr = attendanceRegister
                .stream()
                .map(attendance -> attendance.toString())
                .collect(Collectors.joining("\n"));
       //Files.write(reportFile.toPath(), commaSeparatedStr.getBytes());
        response.getWriter().print(commaSeparatedStr);
    }

    @PostMapping("/attendanceRegister")
    public @ResponseBody void generateFullRegister(HttpServletResponse response, @ModelAttribute Report report)  throws IOException {
        List<Attendance> attendanceRegister =  attendanceRepo.findAll();
       /* File reportFile = new File(reportFileDirectory, "report-"+report.getVisitorId()+".csv");
        if (!reportFile.exists()) {
            Files.createDirectories(reportFile.getParentFile().toPath());
        }*/
        response.setContentType("text/csv");
        response.setHeader("Content-Disposition","attachment; filename=" + "report-all-visitors.csv");
        String commaSeparatedStr = attendanceRegister
                .stream()
                .map(attendance -> attendance.toString())
                .collect(Collectors.joining("\n"));
        //Files.write(reportFile.toPath(), commaSeparatedStr.getBytes());
        response.getWriter().print(commaSeparatedStr);
    }

}
