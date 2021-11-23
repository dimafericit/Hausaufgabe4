package main;

import main.controller.Controller;
import main.model.Course;
import main.model.Person;
import main.model.Student;
import main.repository.CourseRepository;
import main.repository.StudentRepository;

import java.io.IOException;

public class Main {
    public static void main(String[] args) throws Exception {
        //studentii
        Student obj1 = new Student("Dima", "Burlac");
        Student obj2 = new Student("Dimitri", "Burlac");
        Student obj3 = new Student("Domi", "Burlac");

        //cursurile
        Person pers1 = new Person("Benta", "Iulian");
        Course obja = new Course("fp", "  ", 6, 6);
        Course objb = new Course("map", "  ", 8, 6);
/*
        CourseRepository obj = new CourseRepository("cursuri.json");
        try {
            obj.readFromFile();
        } catch (IOException e) {
            e.printStackTrace();
        }
        obj.print();
        System.out.println(obj.size());

        obj.create(obja);
        try {
            obj.writeToFile();
        } catch (IOException e) {
            e.printStackTrace();
        }

        StudentRepository obj = new StudentRepository("student.json");
        try {
            obj.readFromFile();
        } catch (IOException e) {
            e.printStackTrace();
        }
        obj.print();
        */
        Controller obj = new Controller("files/student.json", "files/cursuri.json");
        obj.readAll();
        obj.register(10005, 10001);
        obj.writeAll();

    }
}

