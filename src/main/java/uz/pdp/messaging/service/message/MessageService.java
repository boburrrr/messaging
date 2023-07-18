package uz.pdp.messaging.service.message;

import uz.pdp.messaging.model.message.Message;
import uz.pdp.messaging.model.message.Status;
import uz.pdp.messaging.service.BaseService;

import java.util.ArrayList;
import java.util.UUID;

public interface MessageService extends BaseService<Message> {
    ArrayList<Message> show(UUID id, UUID id1);

    void deleteUser(UUID id);

    ArrayList<Message> messageList(Status userMessage);

    ArrayList<Message> ShowGrupMessage(UUID id, UUID id1);

}
