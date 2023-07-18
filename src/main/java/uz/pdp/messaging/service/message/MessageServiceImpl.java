package uz.pdp.messaging.service.message;

import uz.pdp.messaging.model.message.Message;
import uz.pdp.messaging.model.message.Status;
import uz.pdp.messaging.repasitory.MessageRepository;

import java.util.ArrayList;
import java.util.UUID;

public class MessageServiceImpl implements MessageService, MessageRepository {
    @Override
    public int add(Message message) {
        messageWrite(message);
        return 1;
    }

    @Override
    public Message getById(UUID id) {
        for (Message messageRead : messageReads()) {
            if(messageRead.getId().equals(id)){
                return messageRead;
            }
        }
        return null;
    }

    @Override
    public boolean deleteById(UUID id) {
        for (int i = 0; i < messageReads().size(); i++) {
            if(messageReads().get(i).getId().equals(id)){
                ArrayList<Message> messages = messageReads();
                messages.remove(i);
                messageUpdate(messages);
                return true;
            }
        }
        return false;
    }

    @Override
    public boolean updateById(Message updateT) {
        for (int i = 0; i < messageReads().size(); i++) {
            if(messageReads().get(i).getId().equals(updateT.getId())){
                if(deleteById(updateT.getId())){
                    add(updateT);
                    return true;
                }
            }
        }
        return false;
    }

    @Override
    public ArrayList<Message> show(UUID id, UUID id1) {
        ArrayList<Message> messages = new ArrayList<>();
        for (Message message : messageReads()) {
            if((message.getSentUserId().equals(id) && message.getSeenUserId().equals(id1)) || (message.getSeenUserId().equals(id) && message.getSentUserId().equals(id1))){
                messages.add(message);
            }
        }
        return messages;
    }

    @Override
    public void deleteUser(UUID id) {
        for (Message message : messageReads()) {
            if(message.getSentUserId().equals(id) || message.getSeenUserId().equals(id)){
                deleteById(message.getId());
            }
        }
    }

    @Override
    public ArrayList<Message> messageList(Status userMessage) {
        ArrayList<Message> messages = new ArrayList<>();
        for (Message message : messageReads()) {
            if(message.getStatus().equals(userMessage)){
                messages.add(message);
            }
        }
        return messages;
    }

    @Override
    public ArrayList<Message> ShowGrupMessage(UUID id, UUID id1) {
        ArrayList<Message> messages = new ArrayList<>();
        for (Message message : messageReads()) {
            if(message.getStatus().equals(Status.GRUP_MESSAGE)) {
                if (message.getSeenUserId().equals(id1)) {
                    messages.add(message);
                }
            }
        }
        return messages;
    }
}
