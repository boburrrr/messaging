package uz.pdp.messaging.repasitory;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import uz.pdp.messaging.model.contact.Contact;
import uz.pdp.messaging.model.user.User;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

public interface UserRepository {

    ObjectMapper objectMapper = new ObjectMapper().enable(SerializationFeature.INDENT_OUTPUT);
    String path = "D:\\Javac\\arxiv\\messaging\\src\\main\\resources\\users.json";

    default int userWrite(User user){
        ArrayList<User> users = userReads();
        users.add(user);
        try {
            objectMapper.writeValue(new File(path),users);
            return 1;
        } catch (IOException e) {
            return -1;
        }
    }
    default int userUpdate(ArrayList<User> users){
        try {
            objectMapper.writeValue(new File(path),users);
            return 1;
        } catch (IOException e) {
            return -1;
        }
    }
    default ArrayList<User> userReads(){
        try {
            return objectMapper.readValue(new File(path), new TypeReference<>(){});
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
