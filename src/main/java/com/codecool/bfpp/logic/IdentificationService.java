package com.codecool.bfpp.logic;

import com.codecool.bfpp.data.Identification;
import com.codecool.bfpp.database.IdentificationRepository;

import java.util.List;

public class IdentificationService {
    private final IdentificationRepository identificationRepository;

    public IdentificationService(IdentificationRepository identificationRepository) {
        this.identificationRepository = identificationRepository;
    }

    public List<String> findAllUsernames() {
        return identificationRepository.findAll().stream()
                .map(Identification::username)
                .toList();
    }

    public void save(String username, String password) {
        Identification identification = new Identification(username, password);
        identificationRepository.save(identification);
    }

    public boolean exists(String username) {
        return identificationRepository.findOneByUsername(username).isPresent();
    }
}
