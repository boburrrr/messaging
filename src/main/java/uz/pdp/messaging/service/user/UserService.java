package uz.pdp.messaging.service.user;

import uz.pdp.messaging.model.user.Role;
import uz.pdp.messaging.model.user.User;
import uz.pdp.messaging.service.BaseService;

import java.util.ArrayList;
import java.util.UUID;

public interface UserService extends BaseService<User> {
    User signIn(String username, String password);
    boolean checkUsername(String username);
    UUID returnUsernameId(String username);

    ArrayList<User> blockList(boolean tree);

    ArrayList<User> userShow(Role role);

    ArrayList<User> userListXlsx(Role user);

}
