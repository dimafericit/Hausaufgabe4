package main.repository;

import com.fasterxml.jackson.core.util.DefaultPrettyPrinter;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
import main.model.Course;
import main.model.Student;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class CourseRepository extends InMemoryRepository<Course> implements FileRepository<Course> {

    private String file;

    /**
     * constructor
     *
     * @param file the file we are using
     */
    public CourseRepository(String file) {
        super();
        this.file = file;
    }

    @Override
    public List<Course> readFromFile() throws IOException {
        Reader reader = new BufferedReader(new FileReader(file));
        ObjectMapper objectMapper = new ObjectMapper();
        JsonNode parser = objectMapper.readTree(reader);

        for (JsonNode pm : parser) {

            String name = pm.path("name").asText();
            String lehrer = pm.path("teacher").asText();
            int maxenrolled = pm.path("maxenrolled").asInt();
            int ECTS = pm.path("credits").asInt();

            List<Long> listeStudenten = new ArrayList<>();
            for (JsonNode v : pm.path("listeStudenten"))
            {
                listeStudenten.add(v.asLong());
            }

            Course kurs = new Course(name, lehrer, maxenrolled, ECTS, listeStudenten);
            repoList.add(kurs);
        }
        return repoList;

    }

    @Override
    public void writeToFile() throws IOException {
        ObjectMapper objectMapper = new ObjectMapper();
        ObjectWriter writer = objectMapper.writer(new DefaultPrettyPrinter());
        writer.writeValue(new File(file), repoList);
    }

    @Override
    public Course update(Course obj) {
        Course CourseToUpdate = this.repoList.stream()
                .filter(course -> Objects.equals(course.getName(), obj.getName()))
                .findFirst()
                .orElseThrow();
        CourseToUpdate.setName(obj.getName());

        return CourseToUpdate;
    }


    public Course exist(long id) throws Exception {
        for (Course elem : this.repoList)
            if (id == elem.getCourseid())
                return elem;
        throw new Exception("ID dosen't exist");
    }

    public void print(){
        for (Course elem : this.repoList)
            System.out.println(elem);
    }

    public int size(){
        return repoList.size();
    }

    public void sort(){
        repoList.sort(Course::compareTo);
    }

}
