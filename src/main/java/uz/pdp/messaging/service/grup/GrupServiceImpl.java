package uz.pdp.messaging.service.grup;

import uz.pdp.messaging.model.grup.Grup;
import uz.pdp.messaging.model.user.User;
import uz.pdp.messaging.repasitory.GrupRepository;

import java.util.ArrayList;
import java.util.UUID;

public class GrupServiceImpl implements GrupService, GrupRepository {
    @Override
    public int add(Grup grup) {
        if(checkGrupName(grup.getGrupName())){
            return -1;
        }else{
            grupWrite(grup);
            return 1;
        }
    }

    @Override
    public Grup getById(UUID id) {
        for (Grup grupRead : grupReads()) {
            if(grupRead.getId().equals(id)){
                return grupRead;
            }
        }
        return null;
    }

    @Override
    public boolean deleteById(UUID id) {
        for (int i = 0; i < grupReads().size(); i++) {
            if(grupReads().get(i).getId().equals(id)){
                ArrayList<Grup> grups = grupReads();
                grups.remove(i);
                grupUpdate(grups);
                return true;
            }
        }
        return false;
    }

    @Override
    public boolean updateById(Grup updateT) {
        for (int i = 0; i < grupReads().size(); i++) {
            if(grupReads().get(i).getId().equals(updateT.getId())){
                if(deleteById(updateT.getId())){
                    add(updateT);
                    return true;
                }
            }
        }
        return false;
    }

    @Override
    public boolean checkGrupName(String grupName) {
        for (Grup grupRead : grupReads()) {
            if(grupRead.getGrupName().equals(grupName)){
                return true;
            }
        }
        return false;
    }

    @Override
    public ArrayList<Grup> grupShow(UUID id) {
        ArrayList<Grup> grups = new ArrayList<>();
        for (Grup grupRead : grupReads()) {
            if(grupRead.getAdminId().equals(id)){
                grups.add(grupRead);
            }
        }
        return grups;
    }

    @Override
    public ArrayList<Grup> grupsList() {
        return grupReads();
    }

    @Override
    public UUID returnGrupNameId(String grupName) {
        for (Grup grup:grupReads()) {
            if(grup.getGrupName().equals(grupName) && grup.getBlocked().equals(false)){
                return grup.getId();
            }
        }
        return null;
    }
}
