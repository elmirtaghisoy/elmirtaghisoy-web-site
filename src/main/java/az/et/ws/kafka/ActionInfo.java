package az.et.ws.kafka;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ActionInfo implements Serializable {

    private Integer userId;
    private String username;
    private String action;
    private LocalDateTime dateTime;

}
