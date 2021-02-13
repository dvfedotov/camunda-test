package ru.dvfedotov.process.domain;

import java.io.Serializable;

public class Warrior implements Serializable {

    private static final long serialVersionUID = -8300161615456728969L;
    private String name;
    private String title;
    private Integer hp;
    private Boolean isAlive;

    public Warrior() {
    }

    public Warrior(String name, String title, Integer hp, Boolean isAlive) {
        this.name = name;
        this.title = title;
        this.hp = hp;
        this.isAlive = isAlive;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
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
            + "name='" + name + '\''
            + ", title='" + title + '\''
            + ", hp=" + hp
            + ", isAlive=" + isAlive
            + '}';
    }
}
