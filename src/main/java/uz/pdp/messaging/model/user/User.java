package uz.pdp.messaging.model.user;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import uz.pdp.messaging.model.BaseModel;

@Getter
@Setter
@NoArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class User extends BaseModel {
    private String fullName;
    private String username;
    private String password;
    private String updateLocalDate = null;
    private Role role;
    private Boolean blocked = false;
    private Boolean block = false;

    public User(String localDate, String fullName, String username, String password, Role role) {
        super(localDate);
        this.fullName = fullName;
        this.username = username;
        this.password = password;
        this.role = role;
    }

    @Override
    public String toString() {
        return fullName+" "+username+" "+localDate+" "+(updateLocalDate!=null?"(update-"+updateLocalDate+")":"");
    }
}
