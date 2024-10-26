package pe.edu.cibertec.micro_auth.helper;

import pe.edu.cibertec.micro_auth.entity.TemporalRegistration;

public interface IEmail {
    void sendEmail(TemporalRegistration secutityCode);
}
