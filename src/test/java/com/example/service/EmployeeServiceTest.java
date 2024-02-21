package com.example.service;

import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import com.example.domain.Employee;
import com.example.repository.EmployeeRepository;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class EmployeeServiceTest {
    
    
    @Test
    void testShowDetail() {
        Date date=new Date();
        Calendar cal=Calendar.getInstance();
        cal.set(2020,Calendar.DECEMBER,11);
        date=cal.getTime();
        
        EmployeeRepository mockRepository=Mockito.mock(EmployeeRepository.class);

        List<Employee> expected=
        Arrays.asList(new Employee
        (1,"サンプル太郎","e1.png","男性",date
        ,"sample@mail.com","000-0000","鳥取県鳥取市1-1-11","000-0000-0000"
        ,400000,"社員として優秀です",1));
        when(mockRepository.findAll()).thenReturn(expected);

        EmployeeService employeeService=new EmployeeService(mockRepository);
        List<Employee> actual=employeeService.showList();

        assertEquals(expected, actual);
    }

 
    @Test
    void testShowList() {
        Date date=new Date();
        Calendar cal=Calendar.getInstance();
        cal.set(2020,Calendar.DECEMBER,11);
        date=cal.getTime();

        Date date2=new Date();
        Calendar cal2=Calendar.getInstance();
        cal2.set(2023,Calendar.MAY,5);
        date2=cal2.getTime();

        EmployeeRepository mockRepository=Mockito.mock(EmployeeRepository.class);
        List<Employee> expected=Arrays.asList(new Employee
        (1,"サンプル太郎","e1.png","男性",date
        ,"sample@mail.com","000-0000","鳥取県鳥取市1-1-11","000-0000-0000"
        ,400000,"社員として優秀です",1)
        ,new Employee
        (2,"サンプル花子","e2.png","女性",date2
        ,"sample-hana@mail.com","555-5555","沖縄県沖縄市1-1-1","111-1111-1111"
        ,300000,"社員として優秀です",3));
        when(mockRepository.findAll()).thenReturn(expected);
        
        EmployeeService employeeService=new EmployeeService(mockRepository);
        List<Employee> actual=employeeService.showList();

        assertEquals(expected, actual);
    }
}
