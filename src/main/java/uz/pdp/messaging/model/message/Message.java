package uz.pdp.messaging.model.message;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import uz.pdp.messaging.model.BaseModel;

import java.util.UUID;

@Getter
@Setter
@NoArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class Message extends BaseModel {
    private UUID sentUserId;
    private UUID seenUserId;
    private String message;
    private Status status;
    private String updateLocalDate = null;

    public Message(String localDate, UUID sentUserId, UUID seenUserId, String message,Status status) {
        super(localDate);
        this.sentUserId = sentUserId;
        this.seenUserId = seenUserId;
        this.message = message;
        this.status = status;
    }

    @Override
    public String toString() {
        return message+ "\t("+localDate+" "+(updateLocalDate != null ? "(update-"+updateLocalDate+")":")");
    }
}
