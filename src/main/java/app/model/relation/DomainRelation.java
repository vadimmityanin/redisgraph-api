package app.model.relation;

public enum DomainRelation {
    GENERIC(""),
    CONNECTS("connects"),
    LOGIN("login"),
    LOGOUT("logout");

    DomainRelation(String value) {
        this.value = value;
    }

    private String value;

    public String getValue() {
        return value;
    }
}
