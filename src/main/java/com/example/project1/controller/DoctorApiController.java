package com.example.project1.controller;

import com.example.project1.model.Doctor;
import com.example.project1.service.DoctorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping("/api/doctors")
public class DoctorApiController {

    @Autowired
    private DoctorService doctorService;


    @PostMapping
    public Doctor createDoctor(@RequestBody Doctor doctor) {
        return doctorService.createDoctor(doctor);
    }


    @PutMapping("/{id}")
    public Doctor updateDoctor(@PathVariable Long id, @RequestBody Doctor doctor) {
        return doctorService.updateDoctor(id, doctor);
    }

    
    @DeleteMapping("/{id}")
    public String deleteDoctor(@PathVariable Long id) {
        Optional<Doctor> doctor = doctorService.getDoctorById(id);
        if (doctor.isPresent()) {
            doctorService.deleteDoctor(id);
            return "Doctor with ID " + id + " has been deleted.";
        } else {
            return "Doctor with ID " + id + " not found.";
        }
    }
}
