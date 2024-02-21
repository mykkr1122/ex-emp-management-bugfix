package com.example.service;


import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import com.example.domain.Administrator;
import com.example.repository.AdministratorRepository;

import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;


public class AdministratorServiceTest {
    @Test
    void testInsert() {
        AdministratorRepository mockRepository = Mockito.mock(AdministratorRepository.class);
        AdministratorService administratorService = new AdministratorService(mockRepository); 
        Administrator administrator = new Administrator(1,"サンプル太郎","sample@mail.com","sample");
        administratorService.insert(administrator);
        
        verify(mockRepository, times(1)).insert(administrator);
    }

    @Test
    void testLogin() {
        AdministratorRepository mockRepository = Mockito.mock(AdministratorRepository.class);
        AdministratorService administratorService = new AdministratorService(mockRepository);

        String mailAddress = "sample@mail.com";
        String password = "sample";
        administratorService.login(mailAddress, password);

        verify(mockRepository, times(1)).findByMailAddressAndPassward(mailAddress, password);
    }
}
