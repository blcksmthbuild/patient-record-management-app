package com.starterapp.fxgarage.test.backend.service;

import com.starterapp.fxgarage.test.backend.entity.Doctor;
import com.starterapp.fxgarage.test.backend.entity.Patient;
import com.starterapp.fxgarage.test.backend.repository.DoctorRepository;
import com.starterapp.fxgarage.test.backend.repository.PatientRepository;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.util.List;
import java.util.Random;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Service
public class PatientService {
    private static final Logger LOGGER = Logger.getLogger(PatientService.class.getName());
    private PatientRepository patientRepository;
    private DoctorRepository doctorRepository;

    public PatientService(PatientRepository patientRepository,
                          DoctorRepository doctorRepository) {
        this.patientRepository = patientRepository;
        this.doctorRepository = doctorRepository;
    }

    public List<Patient> findAll() {
        return patientRepository.findAll();
    }

    public List<Patient> findAll(String filterText) {
        if (filterText == null || filterText.isEmpty()) {
            return patientRepository.findAll();
        } else {
            return patientRepository.search(filterText);
        }
    }

    public long count() {
        return patientRepository.count();
    }

    public void delete(Patient contact) {
        patientRepository.delete(contact);
    }

    public void save(Patient contact) {
        if (contact == null) {
            LOGGER.log(Level.SEVERE,
                    "Contact is null. Are you sure you have connected your form to the application?");
            return;
        }
        patientRepository.save(contact);
    }

    @PostConstruct
    public void populateTestData() {
        if (doctorRepository.count() == 0) {
            doctorRepository.saveAll(
                    Stream.of("Fekete Peter", "Nagy Anna Eniko", "Gabor MArton")
                            .map(Doctor::new)
                            .collect(Collectors.toList()));
        }

        if (patientRepository.count() == 0) {
            Random r = new Random(0);
            List<Doctor> doctors = doctorRepository.findAll();
            patientRepository.saveAll(
                    Stream.of("Gabrielle Patel", "Brian Robinson", "Eduardo Haugen",
                            "Koen Johansen", "Alejandro Macdonald", "Angel Karlsson", "Yahir Gustavsson", "Haiden Svensson",
                            "Emily Stewart", "Corinne Davis", "Ryann Davis", "Yurem Jackson", "Kelly Gustavsson",
                            "Eileen Walker", "Katelyn Martin", "Israel Carlsson", "Quinn Hansson", "Makena Smith",
                            "Danielle Watson", "Leland Harris", "Gunner Karlsen", "Jamar Olsson", "Lara Martin",
                            "Ann Andersson", "Remington Andersson", "Rene Carlsson", "Elvis Olsen", "Solomon Olsen",
                            "Jaydan Jackson", "Bernard Nilsen")
                            .map(name -> {
                                String[] split = name.split(" ");
                                Patient contact = new Patient();
                                contact.setFirstName(split[0]);
                                contact.setLastName(split[1]);
                                contact.setDoctor(doctors.get(r.nextInt(doctors.size())));
                                contact.setChronicDisease(Patient.ChronicDisease.values()[r.nextInt(Patient.ChronicDisease.values().length)]);
                                contact.setSimpleDisease(Patient.SimpleDisease.values()[r.nextInt(Patient.SimpleDisease.values().length)]);
                                return contact;
                            }).collect(Collectors.toList()));
        }
    }
}
