package uz.pdp.messaging.service.user;

import uz.pdp.messaging.model.user.Role;
import uz.pdp.messaging.model.user.User;
import uz.pdp.messaging.repasitory.UserRepository;

import java.util.ArrayList;
import java.util.UUID;

public class UserServiceImpl implements UserService, UserRepository {
    @Override
    public int add(User user) {
        if(checkUsername(user.getUsername())){
            return -1;
        }else{
            userWrite(user);
            return 1;
        }
    }

    @Override
    public User getById(UUID id) {
        for (User user : userReads()) {
            if(user.getId().equals(id)){
                return user;
            }
        }
        return null;
    }

    @Override
    public boolean deleteById(UUID id) {
        for (int i = 0; i < userReads().size(); i++) {
            if(userReads().get(i).getId().equals(id)){
                ArrayList<User> users = userReads();
                users.remove(i);
                userUpdate(users);
                return true;
            }
        }
        return false;
    }

    @Override
    public boolean updateById(User updateT) {
        for (int i = 0; i < userReads().size(); i++) {
            if(userReads().get(i).getId().equals(updateT.getId())){
                if(deleteById(updateT.getId())){
                    add(updateT);
                    return true;
                }
            }
        }
        return false;
    }

    @Override
    public User signIn(String username, String password) {
        for (User userRead : userReads()) {
            if(userRead.getUsername().equals(username) && userRead.getPassword().equals(password)){
                return userRead;
            }
        }
        return null;
    }

    @Override
    public boolean checkUsername(String username) {
        for (User userRead : userReads()) {
            if(userRead.getUsername().equals(username)){
                return true;
            }
        }
        return false;
    }

    @Override
    public UUID returnUsernameId(String username) {
        for (User user : userReads()) {
            if(user.getUsername().equals(username) && user.getBlocked().equals(false)){
                return user.getId();
            }
        }
        return null;
    }

    @Override
    public ArrayList<User> blockList(boolean tree) {
        ArrayList<User> users = new ArrayList<>();
        for (User user : userReads()) {
            if(user.getBlock().equals(tree) && (!user.getRole().equals(Role.ADMIN) && !user.getRole().equals(Role.SUPER_ADMIN))){
                users.add(user);
            }
        }
        return users;
    }

    @Override
    public ArrayList<User> userShow(Role role) {
        ArrayList<User> users = new ArrayList<>();
        for (User user : userReads()) {
            if(user.getRole().equals(role)){
                users.add(user);
            }
        }
        return users;
    }

    @Override
    public ArrayList<User> userListXlsx(Role user) {
        ArrayList<User> users = new ArrayList<>();
        for (User userRead : userReads()) {
            if(userRead.getRole().equals(user)){
                users.add(userRead);
            }
        }
        return users;
    }

}
