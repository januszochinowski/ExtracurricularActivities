package com.example.extracurricularactivities;

import com.example.extracurricularactivities.Model.Teacher;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@SpringBootApplication
public class ExtracurricularActivitiesApplication {



    public static void main(String[] args) {
        SpringApplication.run(ExtracurricularActivitiesApplication.class, args);
    }

}
