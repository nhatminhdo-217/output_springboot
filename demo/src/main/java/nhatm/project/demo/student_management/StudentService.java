package nhatm.project.demo.student_management;

import java.util.List;
import java.util.Optional;

public interface StudentService {

	Optional<Student> getStudentById(int userId);

	List<Student> getAllStudent();

	Optional<Student> getStudentByName(String name);

	boolean deleteStudentById(int userId);

	boolean addNewStudent(StudentDTO studentDto);
}
