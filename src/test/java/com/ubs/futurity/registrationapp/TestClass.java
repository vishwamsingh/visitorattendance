package com.ubs.futurity.registrationapp;

import com.ubs.futurity.registrationapp.models.Attendance;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class TestClass {

    public static void main(String[] args) {

        /*String now = "4/28/2020, 1:22:54 PM";

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("M/d/yyyy, h:m:s a");

        LocalDateTime formatDateTime = LocalDateTime.parse(now, formatter);

        System.out.println("Before : " + now);

        System.out.println("After : " + formatDateTime);

        System.out.println("After : " + formatDateTime.format(formatter));*/

        Attendance a1 = new Attendance();
        a1.setVisitorId("1");
        a1.setName("Vishwam");
        a1.setVisitId(1);

        Attendance a2 = new Attendance();
        a2.setVisitorId("2");
        a2.setName("VishwamS");
        a2.setVisitId(2);

        List<Attendance> register = new ArrayList<Attendance>();
        register.add(a1);
        register.add(a2);

        String csvRow = register.stream()
                .map(attendance -> attendance.toString())
                .collect(Collectors.joining("\n"));
        System.out.println(csvRow);

    }
}
