package com.example.extracurricularactivities;

import com.example.extracurricularactivities.Model.Student;
import com.example.extracurricularactivities.Model.Teacher;
import com.example.extracurricularactivities.Repo.TeacherRepo;
import com.example.extracurricularactivities.Service.TeachersService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@SpringBootApplication
public class ExtracurricularActivitiesApplication {





    public static void main(String[] args) {
      ApplicationContext context =  SpringApplication.run(ExtracurricularActivitiesApplication.class, args);

        Teacher teacher = new Teacher();
        teacher.setName("Admin");
        teacher.setSurname("Admin");
        teacher.setPassword("admin");
        teacher.setEmail("admin");
        teacher.setIsAdmin(true);
        teacher.setPhoneNumber("5745243637");

        context.getBean(TeachersService.class).addTeacher(teacher);



    }

}
