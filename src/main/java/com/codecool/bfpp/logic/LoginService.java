package com.codecool.bfpp.logic;

import com.codecool.bfpp.database.IdentificationRepository;

public class LoginService {
    private final IdentificationRepository identificationRepository;

    public LoginService(IdentificationRepository identificationRepository) {
        this.identificationRepository = identificationRepository;
    }

    public boolean login(String username, String password) {
        return identificationRepository.findOneByUsername(username)
                .map(identification -> identification.password().equals(password))
                .orElse(false);
    }
}
