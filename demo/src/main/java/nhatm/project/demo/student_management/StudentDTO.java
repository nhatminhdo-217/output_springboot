package nhatm.project.demo.student_management;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class StudentDTO {
	private String name;
	private String mail;
	private int age;
	private String major;
	private boolean isGraduated;
}
