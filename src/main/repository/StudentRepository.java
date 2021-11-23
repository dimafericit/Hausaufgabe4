package main.repository;

import com.fasterxml.jackson.core.util.DefaultPrettyPrinter;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
import main.model.Student;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class StudentRepository extends InMemoryRepository<Student> {

    private String file;

    /**
     * constructor
     * @param file the file we are using for writing and reading
     */
    public StudentRepository(String file){
        super();
        this.file = file;
    }

    /**
     * updates an element in the repository
     * @param obj student object
     * @return the updated student
     */
    @Override
    public Student update(Student obj) {
        Student studentToUpdate = this.repoList.stream()
                .filter(student -> student.getStudentid() == obj.getStudentid())
                .findFirst()
                .orElseThrow();

        studentToUpdate.setName(obj.getName());

        return studentToUpdate;
    }

    /**
     * @param stud the student we are searching for
     * @return true/false if we find/don't find the student
     */
    public boolean exist(Student stud) {
        for (Student index : this.repoList)
            if (index == stud){
                return true;
            }
        return false;
    }

    /**
     * reads data from the file
     * @return the list created with the data from the file
     */
    public List<Student> readFromFile() throws IOException {

        Reader reader = new BufferedReader(new FileReader(file));
        ObjectMapper objectMapper = new ObjectMapper();
        JsonNode parser = objectMapper.readTree(reader);

        for (JsonNode pm : parser) {

            String name = pm.path("name").asText();
            int credits = pm.path("credits").asInt();
            String vorname = pm.path("vorname").asText();
            long id = pm.path("studentid").asLong();

            List<Long> listecourse = new ArrayList<>();
            for (JsonNode v : pm.path("enrolledcourse"))
            {
                listecourse.add(v.asLong());
            }

            Student stud = new Student(name,vorname, id, credits,listecourse);
            repoList.add(stud);
        }

        return repoList;

    }

    /**
     * writes data to the file
     */
    public void writeToFile() throws IOException {
        ObjectMapper objectMapper = new ObjectMapper();
        ObjectWriter writer = objectMapper.writer(new DefaultPrettyPrinter());
        writer.writeValue(new File(file), repoList);

    }

    public void print(){
        for (Student stud: repoList)
            System.out.println(stud);
    }

    public int size(){
        return repoList.size();
    }

    /**
     * function that sorts the student list in ascending order
     */
    public void Studentsort(){
        repoList.sort(Student::compareTo);
    }

}