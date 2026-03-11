package com.example.extracurricularactivities;

import com.example.extracurricularactivities.Model.Student;
import com.example.extracurricularactivities.Model.Teacher;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * Create a Student or Teacher object with random data
 */
public abstract class RandomUserFactory {



    public static Student getRandomStudent() {
        Student student = new Student();
        student.setParentName(RandomUserFactory.getRandomString(8));
        student.setParentSurname(RandomUserFactory.getRandomString(8));
        student.setChildAge(new Random().nextInt(19));
        student.setChildSurname(RandomUserFactory.getRandomString(8));
        student.setChildName(RandomUserFactory.getRandomString(9));
        student.setEmail(RandomUserFactory.getRandomString(10));
        student.setPhoneNumber(RandomUserFactory.getRandomString(10));
        student.setPassword(RandomUserFactory.getRandomString(20));
        return student;
    }

    public static Teacher getRandomTeacher( boolean isAdmin) {
        Teacher teacher = new Teacher();
        teacher.setName(RandomUserFactory.getRandomString(8));
        teacher.setSurname(RandomUserFactory.getRandomString(8));
        teacher.setEmail(RandomUserFactory.getRandomString(10));
        teacher.setPhoneNumber(RandomUserFactory.getRandomString(10));
        teacher.setPassword(RandomUserFactory.getRandomString(20));
        teacher.setIsAdmin(isAdmin);
        return teacher;
    }




    private static String getRandomString(int length) {
        Random random = new Random();
        random.setSeed(System.currentTimeMillis());
        char[] chars = new char[length];
        for(int i  =0; i < length; i++){
            chars[i] = (char)(random.nextInt(26) + 97);
        }

        return new String(chars);

    }
}
