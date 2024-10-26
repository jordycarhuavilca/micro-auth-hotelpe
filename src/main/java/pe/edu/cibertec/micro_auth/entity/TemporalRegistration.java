package pe.edu.cibertec.micro_auth.entity;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import org.springframework.data.annotation.Id;
import org.springframework.data.redis.core.RedisHash;
import pe.edu.cibertec.micro_auth.dto.UserDTO;

import java.io.Serializable;

@NoArgsConstructor
@Getter
@Setter
@ToString
@RedisHash(value = "TemporalRegistration",timeToLive = 60)
public class TemporalRegistration implements Serializable {
        @Id
        public Integer id;
        private String asunto;
        private UserDTO usuario;
}
