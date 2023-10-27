package fursov.inventorsoftacademyjdbc.domain;

public enum Role {
    USER(1),
    ADMIN(2);

    private final int id;

    Role(int id) {
        this.id = id;
    }

    public int getId() {
        return id;
    }

    public static Role fromId(int id) {
        for (Role role : values()) {
            if (role.getId() == id) {
                return role;
            }
        }
        throw new IllegalArgumentException("No role with such id: " + id);
    }
}