package pe.edu.cibertec.micro_auth.dto;

import lombok.*;
import pe.edu.cibertec.micro_auth.entity.User;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
public class TemporalRegistrationDto {
    protected Integer id;
    private String asunto;
    private User usuario;
}
