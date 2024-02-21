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
        // モックオブジェクトの生成
        AdministratorRepository mockRepository = Mockito.mock(AdministratorRepository.class);
        // テスト対象のクラスを生成
        AdministratorService administratorService = new AdministratorService(mockRepository); 
        // テスト対象のメソッドを実行
        Administrator administrator = new Administrator(1,"サンプル太郎","sample@mail.com","sample");
        administratorService.insert(administrator);
        
        // モックオブジェクトのメソッドが1回呼び出されたか検証
        verify(mockRepository, times(1)).insert(administrator);
    }

    @Test
    void testLogin() {
        // モックオブジェクトの生成
        AdministratorRepository mockRepository = Mockito.mock(AdministratorRepository.class);
        // テスト対象のクラスを生成
        AdministratorService administratorService = new AdministratorService(mockRepository);

        // テスト対象のメソッドを実行
        String mailAddress = "sample@mail.com";
        String password = "sample";
        administratorService.login(mailAddress, password);

        // モックオブジェクトのメソッドが1回呼び出されたか検証
        verify(mockRepository, times(1)).findByMailAddressAndPassward(mailAddress, password);
    }
}
