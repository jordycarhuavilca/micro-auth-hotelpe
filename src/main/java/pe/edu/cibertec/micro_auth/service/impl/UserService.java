package pe.edu.cibertec.micro_auth.service.impl;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import pe.edu.cibertec.micro_auth.dto.LoginRequest;
import pe.edu.cibertec.micro_auth.dto.Response;
import pe.edu.cibertec.micro_auth.dto.UserDTO;
import pe.edu.cibertec.micro_auth.dto.VerifyRegistrationDto;
import pe.edu.cibertec.micro_auth.entity.TemporalRegistration;
import pe.edu.cibertec.micro_auth.entity.User;
import pe.edu.cibertec.micro_auth.exception.OurException;
import pe.edu.cibertec.micro_auth.helper.EmailHelper;
import pe.edu.cibertec.micro_auth.repo.UserRepository;
import pe.edu.cibertec.micro_auth.service.interfac.IUserService;
import pe.edu.cibertec.micro_auth.utils.JWTUtils;
import pe.edu.cibertec.micro_auth.utils.TemporalRegistrationConstans;
import pe.edu.cibertec.micro_auth.utils.Utils;

@Service
@Slf4j
public class UserService implements IUserService {
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private PasswordEncoder passwordEncoder;
    @Autowired
    private JWTUtils jwtUtils;
    @Autowired
    private AuthenticationManager authenticationManager;
    @Autowired
    private TemporalRegistrationService temporalRegistrationService;
    @Autowired
    private EmailHelper emailHelper;


    @Override
    public Response register(UserDTO user) {
        Response response = new Response();
        try {
            if (user.getRole() == null || user.getRole().isBlank()) {
                user.setRole("USER");
            }
            if (userRepository.existsByEmail(user.getEmail())) {
                throw new OurException(user.getEmail() + "Already Exists");
            }
            user.setPassword(passwordEncoder.encode(user.getPassword()));
            TemporalRegistration tr = Utils.setUpTemporalRegistration(TemporalRegistrationConstans.ASUNTO[0],Utils.generateNumericId(),user);
            log.info("register.temporalRegistration.request" +
                    " id " + tr.getId() +
                    " asunto " + tr.getAsunto() +
                    " usuario" + tr.getUsuario().getName());
            temporalRegistrationService.save(tr);
            emailHelper.sendEmail(tr);
            response.setStatusCode(HttpStatus.OK.value());
        } catch (OurException e) {
            log.info("register.OurException " + e );
            response.setStatusCode(HttpStatus.BAD_REQUEST.value());
            response.setMessage(e.getMessage());
        } catch (Exception e) {
            log.info("register.error " + e );
            response.setStatusCode(HttpStatus.INTERNAL_SERVER_ERROR.value());
            response.setMessage("Se produjo un error durante el registro de usuario " + e.getMessage());

        }
        return response;
    }

    @Override
    public Response login(LoginRequest loginRequest) {

        Response response = new Response();

        try {
            authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(loginRequest.getEmail(), loginRequest.getPassword()));
            var user = userRepository.findByEmail(loginRequest.getEmail()).orElseThrow(() -> new OurException("Usuario no encontrado"));

            var token = jwtUtils.generateToken(user);
            response.setStatusCode(HttpStatus.OK.value());
            response.setToken(token);
            response.setRole(user.getRole());
            response.setExpirationTime("7 Dias");
            response.setMessage("Exitoso");

        } catch (OurException e) {
            response.setStatusCode(HttpStatus.NOT_FOUND.value());
            response.setMessage(e.getMessage());

        } catch (Exception e) {

            response.setStatusCode(HttpStatus.BAD_REQUEST.value());
            response.setMessage("Se produjo un error durante el inicio de sesion de usuario " + e.getMessage());
        }
        return response;
    }


    public Response verifyRegistration(VerifyRegistrationDto verifyRegistrationDto){
        Response response = new Response();
        log.info("verifyRegistration.init " + verifyRegistrationDto.toString());
        TemporalRegistration temporalRegistration = this.temporalRegistrationService.findById(verifyRegistrationDto.getCode());
        log.info("temporalRegistration.get " + temporalRegistration);

        if(temporalRegistration == null) {
            response.setStatusCode(HttpStatus.BAD_REQUEST.value());
            response.setMessage("your code has expired");
            return response;
        }

        User newUser = Utils.mapUserDTOToUserEntity(temporalRegistration.getUsuario());
        userRepository.save(newUser);

        log.info("temporalRegistration.settingToken " + newUser);
        String token = jwtUtils.generateToken(newUser);
        response.setStatusCode(HttpStatus.OK.value());
        response.setToken(token);
        response.setRole(newUser.getRole());
        response.setExpirationTime("7 Dias");
        response.setMessage("Exitoso");
        return response;
    }
}
