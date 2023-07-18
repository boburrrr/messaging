package uz.pdp.messaging.model.grup;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import uz.pdp.messaging.model.BaseModel;

import java.util.UUID;

@NoArgsConstructor
@Getter
@Setter
@JsonInclude(JsonInclude.Include.NON_NULL)
public class Grup extends BaseModel {
    private UUID adminId;
    private String grupName;
    private String updateLocalDate = null;
    private Boolean blocked = false;

    public Grup(String localDate, UUID adminId, String grupName) {
        super(localDate);
        this.adminId = adminId;
        this.grupName = grupName;
    }

    @Override
    public String toString() {
        return grupName+" "+localDate+" "+(updateLocalDate!=null?"(update-"+updateLocalDate:"");
    }
}
