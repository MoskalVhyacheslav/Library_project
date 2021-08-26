package entity;

public enum Role {
    ADMIN(1), LIBRARIAN(2),USER(3);

    private int roleId;
    Role(int roleId) {
        this.roleId = roleId;
    }

    public static Role getRole(User user) {
        int roleId = user.getRoleId();
        return Role.values()[roleId-1];
    }

    public String getName() {
        return name().toLowerCase();
    }

}
