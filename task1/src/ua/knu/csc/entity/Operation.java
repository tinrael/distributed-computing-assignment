package ua.knu.csc.entity;

public enum Operation {
    ADD_ACTOR(0),
    UPDATE_ACTOR(1),
    DELETE_ACTOR(3),
    GET_ACTOR(4),

    ADD_FILM(5),
    UPDATE_FILM(6),
    DELETE_FILM(7),
    GET_FILM(8);

    private final int code;

    Operation(int code) {
        this.code = code;
    }

    public int getCode() {
        return code;
    }
}
