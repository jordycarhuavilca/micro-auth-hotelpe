package pe.edu.cibertec.micro_auth.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class LoginRequest {
    @NotBlank(message = "Correo Requerido")
    private String email;
    @NotBlank(message = "Contraseña Requerida")
    private String password;
}
