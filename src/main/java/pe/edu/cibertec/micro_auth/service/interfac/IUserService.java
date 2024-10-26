package pe.edu.cibertec.micro_auth.service.interfac;

import pe.edu.cibertec.micro_auth.dto.LoginRequest;
import pe.edu.cibertec.micro_auth.dto.Response;
import pe.edu.cibertec.micro_auth.dto.UserDTO;
import pe.edu.cibertec.micro_auth.dto.VerifyRegistrationDto;

public interface IUserService {
    Response register(UserDTO user);

    Response login(LoginRequest loginRequest);

    Response verifyRegistration(VerifyRegistrationDto verifyRegistrationDto);
}
