package ua.knu.csc.entity;

import java.io.Serializable;

public class Actor implements Serializable {
    private int actorId;

    private String forename;
    private String surname;

    public int getActorId() {
        return actorId;
    }

    public void setActorId(int actorId) {
        this.actorId = actorId;
    }

    public String getForename() {
        return forename;
    }

    public void setForename(String forename) {
        this.forename = forename;
    }

    public String getSurname() {
        return surname;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    @Override
    public String toString() {
        return "[actorId: " + actorId + ", forename: " + forename + ", surname: " + surname + "]";
    }
}
