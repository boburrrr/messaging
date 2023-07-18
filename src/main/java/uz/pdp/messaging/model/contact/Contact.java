package uz.pdp.messaging.model.contact;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import uz.pdp.messaging.model.BaseModel;

import java.util.UUID;

@NoArgsConstructor
@Getter
@Setter
@JsonInclude(JsonInclude.Include.NON_NULL)
public class Contact extends BaseModel {
    private UUID myId;
    private UUID contactUserId;
    private String contactName;
    private Status status;
    private String updateLocalDate = null;

    public Contact(String localDate, UUID myId, UUID contactUserId, String contactName, Status status) {
        super(localDate);
        this.myId = myId;
        this.contactUserId = contactUserId;
        this.contactName = contactName;
        this.status = status;
    }

    @Override
    public String toString() {
        return contactName+"\t"+localDate+" "+(updateLocalDate != null ? "(update-"+updateLocalDate+")": "");
    }
}
