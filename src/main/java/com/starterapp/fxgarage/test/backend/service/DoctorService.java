package com.starterapp.fxgarage.test.backend.service;

import com.starterapp.fxgarage.test.backend.entity.Doctor;
import com.starterapp.fxgarage.test.backend.repository.DoctorRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class DoctorService {

    private DoctorRepository doctorRepository;

    public DoctorService(DoctorRepository doctorRepository) {
        this.doctorRepository = doctorRepository;
    }

    public List<Doctor> findAll() {
        return doctorRepository.findAll();
    }

}

