package main.model;

import main.repository.CourseRepository;

import java.util.ArrayList;
import java.util.List;

/**
 * class Student extends Person
 *studentid is a self generated id
 */
public class Student extends Person{
    private static int next_id = 10000;
    private long studentid;
    private int credits;
    private List<Long> enrolledcourse;

    /**
     * constructor
     * @param name name of the student
     * @param vorname surname of the student
     */
    public Student(String name, String vorname){
        super(name, vorname);
        this.studentid = ++next_id;
        this.credits = 0;
        this.enrolledcourse = new ArrayList<>();
    }

    public Student(String name, String vorname, long id,  int credits, List<Long> liste){
        super(name, vorname);
        this.studentid = id;
        this.credits = credits;
        this.enrolledcourse = liste;
    }

    public int getCredits() {
        return credits;
    }

    public long getStudentid() {
        return studentid;
    }

    /**
     * @param courseid Course object the user wants to add
     */
    public void addCourse(long courseid){
        this.enrolledcourse.add(courseid);
    }

    /**
     * removes a course from enrolledcourse and decrements the total credit number
     * or throws exception if the courseid dosen't exist
     *
     * @param courseid id of the course we want to remove
     * @param repo list of courses
     */
    public void removeCourse(long courseid, CourseRepository repo) throws Exception {
        this.enrolledcourse.remove(courseid);
        this.credits -= repo.exist(courseid).getCredits();
    }

    @Override
    public String toString() {
        return "Student{"  + studentid  + " " + this.getName() + " " + this.getVorname()
         + "Credits: " +   credits     + ' ' + enrolledcourse + '}';
    }

    public List<Long> getEnrolledcourse() {
        return enrolledcourse;
    }

    public void setCredits(int credits) {
        this.credits = credits;
    }

    public int compareTo(Student obj){
        return this.credits - obj.getCredits();
    }
}
