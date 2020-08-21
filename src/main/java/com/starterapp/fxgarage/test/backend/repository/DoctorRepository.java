package com.starterapp.fxgarage.test.backend.repository;

import com.starterapp.fxgarage.test.backend.entity.Doctor;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DoctorRepository extends JpaRepository<Doctor, Long> {
}
