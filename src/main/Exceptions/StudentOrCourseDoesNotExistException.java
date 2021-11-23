package main.Exceptions;


/**
 * is thrown when the course or the student doesn't exist
 */
public class StudentOrCourseDoesNotExistException extends Exception{
    public StudentOrCourseDoesNotExistException(String message){
        super(message);
    }
}
