package pe.edu.cibertec.micro_auth.service.interfac;

import pe.edu.cibertec.micro_auth.entity.TemporalRegistration;

public interface ITemporalRegistrationService {
    public TemporalRegistration save(TemporalRegistration email);
    public TemporalRegistration findById(Integer id);
}
