package pe.edu.cibertec.micro_auth.config;
import io.awspring.cloud.sqs.config.SqsMessageListenerContainerFactory;
import io.awspring.cloud.sqs.listener.acknowledgement.AcknowledgementResultCallback;
import io.awspring.cloud.sqs.listener.acknowledgement.handler.AcknowledgementMode;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.messaging.Message;
import software.amazon.awssdk.services.sqs.SqsAsyncClient;

import java.time.Duration;
import java.time.OffsetDateTime;
import java.util.Collection;

@Slf4j
@Configuration
public class AwsSQSConfig {

    /*@Bean
    SqsAsyncClient sqsAsyncClient(){
        return SqsAsyncClient
                .builder()
                .region(Region.of(region))
                .credentialsProvider(StaticCredentialsProvider
                        .create(AwsBasicCredentials.create(accessKey, secretKey)))
                .build();
    }*/

    @Bean
    SqsMessageListenerContainerFactory<Object> defaultSqsListenerContainerFactory(
            SqsAsyncClient sqsAsyncClient) {
        return SqsMessageListenerContainerFactory.builder()
                .configure(options -> options.acknowledgementMode(AcknowledgementMode.MANUAL)
                        .acknowledgementInterval(
                                Duration.ofSeconds(3)) // NOTE: With acknowledgementInterval 3 seconds,
                        .acknowledgementThreshold(0)
                )
                .acknowledgementResultCallback(new AckResultCallback()).sqsAsyncClient(sqsAsyncClient)
                .build();
    }


    static class AckResultCallback implements AcknowledgementResultCallback<Object> {

        @Override
        public void onSuccess(Collection<Message<Object>> messages) {
            log.info("Ack with success at {}", OffsetDateTime.now());
        }

        @Override
        public void onFailure(Collection<Message<Object>> messages, Throwable t) {
            log.error("Ack with fail", t);
        }
    }
    /*@Bean
    public MessagingMessageConverter customMessageConverter() {
        MessagingMessageConverter converter = new MessagingMessageConverter() {
            @Override
            public Message<?> toMessagingMessage(Object source) {
                return null;
            }

            @Override
            public Object fromMessagingMessage(Message message) {
                return null;
            }
        };
        converter.getHeaderMapper().setTypeIdMappings(Map.of(
                "com.hotelpe.HotelPe_Backend.dto.UserDTO", UserDTO.class.getName()
        ));
        return converter;
    }*/

}
