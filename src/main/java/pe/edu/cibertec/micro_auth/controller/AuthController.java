package pe.edu.cibertec.micro_auth.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import pe.edu.cibertec.micro_auth.dto.LoginRequest;
import pe.edu.cibertec.micro_auth.dto.Response;
import pe.edu.cibertec.micro_auth.dto.UserDTO;
import pe.edu.cibertec.micro_auth.dto.VerifyRegistrationDto;
import pe.edu.cibertec.micro_auth.service.interfac.IUserService;

@RestController
@RequestMapping("/auth")
public class AuthController {
    @Autowired
    private IUserService userService;

    @PostMapping("/register")
    public ResponseEntity<Response> register(@RequestBody UserDTO user) {
        Response response = userService.register(user);
        return ResponseEntity.status(response.getStatusCode()).body(response);
    }
    @PostMapping("/verifyRegistration")
    public Response verifyRegistration(@RequestBody VerifyRegistrationDto verifyRegistrationDto) {
        return this.userService.verifyRegistration(verifyRegistrationDto);
    }
    @PostMapping("/login")
    public ResponseEntity<Response> login(@RequestBody LoginRequest loginRequest) {
        Response response = userService.login(loginRequest);
        return ResponseEntity.status(response.getStatusCode()).body(response);
    }
}