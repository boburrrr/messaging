package uz.pdp.messaging.service.contact;

import uz.pdp.messaging.model.contact.Contact;
import uz.pdp.messaging.model.grup.Grup;
import uz.pdp.messaging.service.BaseService;

import java.util.ArrayList;
import java.util.UUID;

public interface ContactService extends BaseService<Contact> {
    boolean checkContact(UUID myId,UUID contactUserId);

    Contact getContact(UUID id, UUID contactUserId);

    ArrayList<Contact> myShow(UUID id);

    ArrayList<Contact> myNoShow(UUID id);

    void deleteUser(UUID id);

    ArrayList<Contact> getMyGrupContacts(UUID id);

}
