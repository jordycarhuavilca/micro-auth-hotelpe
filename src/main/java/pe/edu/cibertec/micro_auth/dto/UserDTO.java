package pe.edu.cibertec.micro_auth.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;

import java.io.Serializable;

@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class UserDTO implements Serializable {

    private int id;
    private String email;
    private String name;
    private String password;
    private String phoneNumber;
    private String role;

}
