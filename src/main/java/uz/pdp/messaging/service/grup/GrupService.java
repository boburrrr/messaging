package uz.pdp.messaging.service.grup;

import uz.pdp.messaging.model.grup.Grup;
import uz.pdp.messaging.service.BaseService;

import java.util.ArrayList;
import java.util.UUID;

public interface GrupService extends BaseService<Grup> {
    boolean checkGrupName(String grupName);

    ArrayList<Grup> grupShow(UUID id);

    ArrayList<Grup> grupsList();

    UUID returnGrupNameId(String grupName);

}
