package pe.edu.cibertec.micro_auth.service.impl;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pe.edu.cibertec.micro_auth.entity.TemporalRegistration;
import pe.edu.cibertec.micro_auth.repo.TemporalRegistrationRepository;
import pe.edu.cibertec.micro_auth.service.interfac.ITemporalRegistrationService;

@Slf4j
@Service
public class TemporalRegistrationService implements ITemporalRegistrationService {
    @Autowired
    private TemporalRegistrationRepository temporalRegistrationRepository;

    @Override
    public TemporalRegistration save(TemporalRegistration email) {
        log.info("saving user");
        return temporalRegistrationRepository.save(email);
    }

    @Override
    public TemporalRegistration findById(Integer id) {
        log.info("Getting user from database: " + id);
         return temporalRegistrationRepository.findById(id).orElse(null);
    }

    /*@CachePut( value="user", key="#id")
    public SecutityCode updateUser(Integer code, SecutityCode user) {
        SecutityCode userUpd = secutityCodeRepository.findById(code)
                .orElseThrow();
        userUpd.setAge(user.getAge());
        userUpd.setName(user.getName());

        log.info("Updating user: ", id);
        return userRepository.save(userUpd);
    }*/
    /*@CacheEvict
            ( value="user")
    public void deleteUser(Integer id) {
        log.info("Updating user: ", id);
        User user = secutityCodeRepository.findById(id)
                .orElseThrow();
        userRepository.delete(user);
    }*/


}
