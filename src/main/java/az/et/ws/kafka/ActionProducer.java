//package az.et.ws.kafka;
//
//import org.apache.kafka.clients.admin.NewTopic;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.context.annotation.Bean;
//import org.springframework.kafka.core.KafkaTemplate;
//import org.springframework.stereotype.Component;
//
//@Component
//public class ActionProducer {
//    @Autowired
//    private KafkaTemplate<String, ActionInfo> kafkaTemplate;
//
//    public void sendUserAction(ActionInfo actionInfo) {
//        kafkaTemplate.send("ACTION",actionInfo.getUsername(), actionInfo);
//    }
//
//    @Bean
//    public NewTopic newTopic(){
//        return new NewTopic("ACTION",1,(short) 1);
//    }
//
//}
