package sjtusummerproject.emailmicroservice.DataModel.Domain;

import javax.persistence.*;

@Entity
@Table( name = "UserUuid")
public class UserUuidEntity {
    @Id
    String uuid;
    String username;

    public String getUuid() {
        return uuid;
    }

    public void setUuid(String uuid) {
        this.uuid = uuid;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }
}
