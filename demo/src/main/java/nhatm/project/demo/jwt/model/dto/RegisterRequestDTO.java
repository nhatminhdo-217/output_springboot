package nhatm.project.demo.jwt.model;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class RegisterRequestDTO {
    @Size(min = 1, max = 50)
    @NotNull(message = "Name cannot be null")
    private String fullName;

    @Email
    @Pattern(regexp = "^[\\w-.]+@([\\w-]+\\.)+[\\w-]{2,4}$")
    @Size(min = 1, max = 100)
    @NotNull(message = "Email cannot be null")
    private String email;

    @Size(min = 1, max = 100)
    @NotNull(message = "Password cannot be null")
    private String password;

    @NotNull(message = "Confirm password cannot be null")
    private String confirmPassword;
}
