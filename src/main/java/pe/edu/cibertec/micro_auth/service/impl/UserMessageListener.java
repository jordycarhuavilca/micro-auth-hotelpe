package pe.edu.cibertec.micro_auth.service.impl;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.awspring.cloud.sqs.annotation.SqsListener;
import io.awspring.cloud.sqs.listener.acknowledgement.Acknowledgement;
import io.awspring.cloud.sqs.operations.SqsTemplate;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.messaging.Message;
import org.springframework.stereotype.Service;
import pe.edu.cibertec.micro_auth.dto.UserDTO;
import pe.edu.cibertec.micro_auth.service.interfac.IUserService;

@Slf4j
@Service
public class UserMessageListener {
    @Autowired
    private IUserService userService;
    @Autowired
    private SqsTemplate sqsTemplate;

    @SqsListener( value = "${spring.cloud.aws.sqs.endpoint.register}")
    public void registerUser(Message<?> message) {
        try {
            log.info("processRegisterMessage.init " + message );
            String jsonPayload = (String) message.getPayload();
            ObjectMapper objectMapper = new ObjectMapper();
            objectMapper.configure(DeserializationFeature.ACCEPT_EMPTY_STRING_AS_NULL_OBJECT, true);

            UserDTO user = objectMapper.readValue(objectMapper.readTree(jsonPayload).asText() ,  UserDTO.class);

            log.info("Deserialized UserDTO: {}", user);

            userService.register(user);

            Acknowledgement.acknowledge(message);

        } catch (Exception e) {
            throw new RuntimeException("Cannot process message from SQS", e);
        }
    }

    @SqsListener( value = "${spring.cloud.aws.sqs.endpoint.register.dl.queue}")
    public void processRegisterFailMessage(Message<?> message) {
        try {
            log.info("processRegisterFailMessage.init " + message );
            String jsonPayload = (String) message.getPayload();
            ObjectMapper objectMapper = new ObjectMapper();
            objectMapper.configure(DeserializationFeature.ACCEPT_EMPTY_STRING_AS_NULL_OBJECT, true);

            UserDTO user = objectMapper.readValue(objectMapper.readTree(jsonPayload).asText() ,  UserDTO.class);

            log.info("Deserialized UserDTO: {}", user);

            Acknowledgement.acknowledge(message);

        } catch (Exception e) {
            throw new RuntimeException("Cannot process message from SQS", e);
        }
    }
}
