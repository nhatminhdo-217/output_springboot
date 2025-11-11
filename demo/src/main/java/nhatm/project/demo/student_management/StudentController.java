package nhatm.project.demo.student_management;

import java.util.List;
import java.util.Optional;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("api/v1/student")
public class StudentController {

	private StudentService studentService;

	public StudentController(StudentService studentService) {
		this.studentService = studentService;
	}

	//THis is nice comment
	@GetMapping("/{userId}")
	public ResponseEntity<Student> getStudentById(@PathVariable("userId") int userid) {

		Optional<Student> student = studentService.getStudentById(userid);

		return student.map(s -> new ResponseEntity<>(s, HttpStatus.OK))
				.orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
	}

	@GetMapping("/search")
	public ResponseEntity<Student> getStudentByName(@RequestParam String name) {
		Optional<Student> student = studentService.getStudentByName(name);

		return student.map(s -> new ResponseEntity<>(s, HttpStatus.OK))
				.orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
	}
	

	@GetMapping
	public ResponseEntity<List<Student>> getAllStudent() {
		List<Student> listStudent = studentService.getAllStudent();

		return new ResponseEntity<>(listStudent, HttpStatus.OK);
	}

	@PostMapping("/add")
	public ResponseEntity<Student> addStudent(@RequestBody StudentDTO studentDto) {
		boolean done = studentService.addNewStudent(studentDto);

		return done ? new ResponseEntity<>(HttpStatus.OK) : new ResponseEntity<>(HttpStatus.NOT_FOUND);
	}
}
