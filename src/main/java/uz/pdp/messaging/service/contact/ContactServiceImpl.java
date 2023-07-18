package uz.pdp.messaging.service.contact;
import uz.pdp.messaging.model.contact.Contact;
import uz.pdp.messaging.model.contact.Status;
import uz.pdp.messaging.model.grup.Grup;
import uz.pdp.messaging.repasitory.ContactRepository;

import java.awt.geom.AffineTransform;
import java.util.ArrayList;
import java.util.UUID;

public class ContactServiceImpl implements ContactService, ContactRepository {
    @Override
    public int add(Contact contact) {
        if(checkContact(contact.getMyId(),contact.getContactUserId())){
            return -1;
        }else {
            contactWrite(contact);
            return 1;
        }
    }

    @Override
    public Contact getById(UUID id) {
        for (Contact contactRead : contactReads()) {
            if(contactRead.getId().equals(id)){
                return contactRead;
            }
        }
        return null;
    }

    @Override
    public boolean deleteById(UUID id) {
        for (int i = 0; i < contactReads().size(); i++) {
            if(contactReads().get(i).getId().equals(id)){
                ArrayList<Contact> contacts = contactReads();
                contacts.remove(i);
                contactUpdate(contacts);
                return true;
            }
        }
        return false;
    }

    @Override
    public boolean updateById(Contact updateT) {
        for (int i = 0; i < contactReads().size(); i++) {
            if(contactReads().get(i).getId().equals(updateT.getId())){
                if(deleteById(updateT.getId())){
                    add(updateT);
                    return true;
                }
            }
        }
        return false;
    }

    @Override
    public boolean checkContact(UUID myId, UUID contactUserId) {
        for (Contact contactRead : contactReads()) {
            if(contactRead.getMyId().equals(myId) && contactRead.getContactUserId().equals(contactUserId)){
                return true;
            }
        }
        return false;
    }

    @Override
    public Contact getContact(UUID id, UUID contactUserId) {
        for (Contact contactRead : contactReads()) {
            if(contactRead.getMyId().equals(id) && contactRead.getContactUserId().equals(contactUserId)){
                return contactRead;
            }
        }
        return null;
    }

    @Override
    public ArrayList<Contact> myShow(UUID id) {
        ArrayList<Contact> contacts = new ArrayList<>();
        for (Contact contact : contactReads()) {
            if(contact.getMyId().equals(id) && contact.getStatus().equals(Status.USER_CONTACT)){
                contacts.add(contact);
            }
        }
        return contacts;
    }

    @Override
    public ArrayList<Contact> myNoShow(UUID id) {
        ArrayList<Contact> contacts = new ArrayList<>();
        for (Contact contact : contactReads()) {
            if(!contact.getMyId().equals(id) && contact.getContactUserId().equals(id)){
                contacts.add(contact);
            }
        }
        return contacts;
    }

    @Override
    public void deleteUser(UUID id) {
        for (Contact contact : contactReads()) {
            if(contact.getMyId().equals(id) || contact.getContactUserId().equals(id)){
                deleteById(contact.getId());
            }
        }
    }

    @Override
    public ArrayList<Contact> getMyGrupContacts(UUID id) {
        ArrayList<Contact> grupContacts = new ArrayList<>();
        for (Contact contact : contactReads()) {
            if(contact.getMyId().equals(id) && contact.getStatus().equals(Status.GRUP_CONTACT)){
                grupContacts.add(contact);
            }
        }
        return grupContacts;
    }
}
