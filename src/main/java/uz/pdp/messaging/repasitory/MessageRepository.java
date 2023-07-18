package uz.pdp.messaging.repasitory;

import com.fasterxml.jackson.core.type.TypeReference;
import uz.pdp.messaging.model.message.Message;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

import static uz.pdp.messaging.repasitory.UserRepository.objectMapper;

public interface MessageRepository {
    String path = "D:\\Javac\\arxiv\\messaging\\src\\main\\resources\\messages.json";

    default int messageWrite(Message message){
        ArrayList<Message> messages = messageReads();
        messages.add(message);
        try {
            objectMapper.writeValue(new File(path),messages);
            return 1;
        } catch (IOException e) {
            return -1;
        }
    }
    default int messageUpdate(ArrayList<Message> message){
        try {
            objectMapper.writeValue(new File(path),message);
            return 1;
        } catch (IOException e) {
            return -1;
        }
    }
    default ArrayList<Message> messageReads(){
        try {
            return objectMapper.readValue(new File(path), new TypeReference<>(){});
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
