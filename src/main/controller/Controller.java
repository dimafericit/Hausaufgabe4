package main.controller;


import main.Exceptions.StudentOrCourseDoesNotExistException;
import main.Exceptions.StudentalreadyeonrolledException;
import main.model.Course;
import main.model.Student;
import main.repository.CourseRepository;
import main.repository.StudentRepository;

import java.io.IOException;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

public class Controller {

    private StudentRepository studenten;
    private CourseRepository course;


    /**
     * Constructor
     * initializes two repos (for Students and Courses)
     */
    public Controller(String filestudent,String filecourse){
        studenten = new StudentRepository(filestudent);
        course = new CourseRepository(filecourse);
    }


    /**
     * function to register a student to a course using id's
     * if the id's are valid we are adding the student to the course and incrementing his credits
     *
     *
     * @param cursid id of the course
     * @param studid id of the student we want to register
     *
     * @throws Exception when the student or the course doesn't exist
     * @throws Exception when the course doesn't have enough places or the student doesn't
     * have enough credits
     * @throws Exception when she student is already enrolled for the course
     */
    public void register(long cursid, long studid) throws Exception {
        Student studentaux = null;
        Course cursaux = null;

        for (Course elem: this.course.getAll())
            if (elem.getCourseid() == cursid){
                cursaux = elem;
                break;
            }

        for (Student elem: this.studenten.getAll())
            if (elem.getStudentid() == studid){
                studentaux = elem;
                break;
            }

        if (cursaux == null || studentaux == null)
            throw new StudentOrCourseDoesNotExistException("Student or Course doesn't exist");

        if (studentaux.getEnrolledcourse().contains(cursaux.getCourseid()))
            throw new StudentalreadyeonrolledException("Student already enrolled");

        if (cursaux.free() && ((cursaux.getCredits()+studentaux.getCredits()) <= 30)){
            cursaux.addStudent(studid);
            studentaux.addCourse(cursid);
            studentaux.setCredits(studentaux.getCredits() + cursaux.getCredits());
        }
        else throw new Exception("Course doesn't have free places or Student " +
                "doesn't have credits left");
    }


    /**
     * function that returns every student enrolled for a course
     *
     * @param courseId id of the course
     * @return a list with the students enrolled for the course given as parameter
     */
    public List<Student> retrieveStudentsEnrolledForACourse(long courseId){
        List<Student> studentsEnrolledForTheCourse = new LinkedList<>();
        for (Student student : studenten.getAll()){
            if (student.getEnrolledcourse().contains(courseId)){
                studentsEnrolledForTheCourse.add(student);
            }
        }
        return studentsEnrolledForTheCourse;
    }

    /**
     * @return al courses with free places
     */
    public List<Course> retrieveCoursesWithFreePlaces(){
        List<Course> freeCourses = new ArrayList<>();
        for (Course index : this.course.getAll())
            if (index.free()) {
                freeCourses.add(index);
            }
        return freeCourses;
    }

    /**
     * reads the data from students and courses
     */
    public void readAll (){
        try {
            studenten.readFromFile();
        } catch (IOException e) {
            e.printStackTrace();
        }
        try {
            course.readFromFile();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * writes the data from students and courses
     */
    public void writeAll (){
        try {
            studenten.writeToFile();
        } catch (IOException e) {
            e.printStackTrace();
        }
        try {
            course.writeToFile();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public CourseRepository getAllCourse() {
        return course;
    }

    public StudentRepository getAllStudenten() {
        return studenten;
    }

    public void sortStudents(){
        studenten.sort();
    }

    public void sortCourses(){
        course.sort();
    }

    public void printStudents(){
        studenten.print();
    }

    public void printCourses(){
        course.print();
    }

    public List<Student> filterStudents(){
        List<Student> students = studenten.getAll();
        return students.stream().filter(stud -> stud.getCredits() > 0).toList();
    }

    public List<Course> filterCourses(){
        List<Course> courses = course.getAll();
        return courses.stream().filter(curs -> curs.getCredits() == 6).toList();
    }

}
