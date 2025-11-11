package nhatm.project.demo.student_management;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

@Service
public class StudentServiceImpl implements StudentService {

	private StudentRepository studentRepo;

	public StudentServiceImpl(StudentRepository studentRepo) {
		this.studentRepo = studentRepo;
	}

	@Override
	public Optional<Student> getStudentById(int userId) {
		return studentRepo.findById(userId);
	}

	@Override
	public List<Student> getAllStudent() {

		return studentRepo.findAll();
	}

	@Override
	public Optional<Student> getStudentByName(String name) {
		return studentRepo.findByName(name);
	}

	@Override
	public boolean deleteStudentById(int userId) {
		studentRepo.deleteById(userId);
		return true;
	}

	@Override
	public boolean addNewStudent(StudentDTO studentDto) {
		Student student = Student.builder()
				.name(studentDto.getName())
				.email(studentDto.getMail())
				.age(studentDto.getAge())
				.major(studentDto.getMajor())
				.isGraduated(studentDto.isGraduated()).build();

		if (student == null) {
			return false;
		}

		studentRepo.save(student);
		return false;
	}

}
