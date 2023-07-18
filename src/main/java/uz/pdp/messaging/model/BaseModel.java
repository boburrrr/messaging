package uz.pdp.messaging.model;


import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;
import java.util.UUID;

@Getter
@Setter
@NoArgsConstructor
public abstract class BaseModel {
    protected UUID id = UUID.randomUUID();
    protected String localDate;

    public BaseModel(String localDate) {
        this.localDate = localDate;
    }
}
