package uz.pdp.messaging.repasitory;

import com.fasterxml.jackson.core.type.TypeReference;
import uz.pdp.messaging.model.grup.Grup;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

import static uz.pdp.messaging.repasitory.UserRepository.objectMapper;

public interface GrupRepository {
    String path = "D:\\Javac\\arxiv\\messaging\\src\\main\\resources\\grups.json";

    default int grupWrite(Grup grup){
        ArrayList<Grup> grups = grupReads();
        grups.add(grup);
        try {
            objectMapper.writeValue(new File(path),grups);
            return 1;
        } catch (IOException e) {
            return -1;
        }
    }
    default int grupUpdate(ArrayList<Grup> grups){
        try {
            objectMapper.writeValue(new File(path),grups);
            return 1;
        } catch (IOException e) {
            return -1;
        }
    }
    default ArrayList<Grup> grupReads(){
        try {
            return objectMapper.readValue(new File(path), new TypeReference<>(){});
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
