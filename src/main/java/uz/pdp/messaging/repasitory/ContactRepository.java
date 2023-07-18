package uz.pdp.messaging.repasitory;

import com.fasterxml.jackson.core.type.TypeReference;
import uz.pdp.messaging.model.contact.Contact;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

import static uz.pdp.messaging.repasitory.UserRepository.objectMapper;

public interface ContactRepository {
    String path = "D:\\Javac\\arxiv\\messaging\\src\\main\\resources\\contacts.json";

    default int contactWrite(Contact contact){
        ArrayList<Contact> contacts = contactReads();
        contacts.add(contact);
        try {
            objectMapper.writeValue(new File(path),contacts);
            return 1;
        } catch (IOException e) {
            return -1;
        }
    }
    default int contactUpdate(ArrayList<Contact> contacts){
        try {
            objectMapper.writeValue(new File(path),contacts);
            return 1;
        } catch (IOException e) {
            return -1;
        }
    }
    default ArrayList<Contact> contactReads(){
        try {
            return objectMapper.readValue(new File(path), new TypeReference<>(){});
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
