package ru.dvfedotov.process.domain;

import com.fasterxml.jackson.annotation.JsonAlias;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import java.io.Serializable;


@JsonIgnoreProperties(ignoreUnknown = true)
public class Warrior implements Serializable {

    private static final long serialVersionUID = -8300161615456728969L;
    @JsonAlias("firstname")
    private String firstName;
    @JsonAlias("lastname")
    private String lastName;
    @JsonAlias("uuid")
    private String uuid;
    private Integer hp;
    private Boolean isAlive;

    public Warrior() {
    }

    public String getUuid() {
        return uuid;
    }

    public void setUuid(String uuid) {
        this.uuid = uuid;
    }

    public Warrior(String name, String title, String uuid, Integer hp, Boolean isAlive) {
        this.firstName = name;
        this.lastName = title;
        this.uuid = uuid;
        this.hp = hp;
        this.isAlive = isAlive;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public Integer getHp() {
        return hp;
    }

    public void setHp(Integer hp) {
        this.hp = hp;
    }

    public Boolean getAlive() {
        return isAlive;
    }

    public void setAlive(Boolean alive) {
        isAlive = alive;
    }

    @Override
    public String toString() {
        return "Warrior{"
            + "firstName ='" + firstName + '\''
            + ", lastName ='" + lastName + '\''
            + ", hp =" + hp
            + ", isAlive =" + isAlive
            + '}';
    }
}
