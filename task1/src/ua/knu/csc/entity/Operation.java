package ua.knu.csc.entity;

import java.util.Map;
import java.util.HashMap;

public enum Operation {
    UNDEFINED_OPERATION(-1),

    ADD_ACTOR(0),
    UPDATE_ACTOR(1),
    DELETE_ACTOR(2),
    GET_ACTOR(3),

    ADD_FILM(4),
    UPDATE_FILM(5),
    DELETE_FILM(6),
    GET_FILM(7);

    private static final Map<Integer, Operation> operations = new HashMap<>();
    static {
        for (Operation operation : Operation.values()) {
            operations.put(operation.code, operation);
        }
    }

    private final int code;

    Operation(int code) {
        this.code = code;
    }

    public int getCode() {
        return code;
    }

    public static Operation getOperation(int code) {
        Operation operation = operations.get(code);
        if (operation == null) {
            return Operation.UNDEFINED_OPERATION;
        } else {
            return operation;
        }
    }
}
