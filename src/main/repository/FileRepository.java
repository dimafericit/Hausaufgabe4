package main.repository;

import java.io.IOException;
import java.util.List;

public interface FileRepository<T> extends ICrudRepository<T>{

    List<T> readFromFile() throws IOException;

    void writeToFile() throws IOException;
}
