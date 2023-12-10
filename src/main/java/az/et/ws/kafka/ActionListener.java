//package az.et.ws.kafka;
//
//import lombok.extern.slf4j.Slf4j;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.kafka.annotation.KafkaListener;
//import org.springframework.messaging.handler.annotation.Payload;
//import org.springframework.messaging.simp.SimpMessagingTemplate;
//import org.springframework.stereotype.Component;
//
//@Slf4j
//@Component
//public class ActionListener {
//
//    @Autowired
//    private SimpMessagingTemplate simpMessagingTemplate;
//
//    @KafkaListener(topics = "ACTION")
//    public void consume(@Payload ActionInfo actionInfo) {
//        System.out.println(actionInfo.toString());
//        simpMessagingTemplate.convertAndSendToUser(actionInfo.getUsername(), "/private", actionInfo);
//    }
//
//}
