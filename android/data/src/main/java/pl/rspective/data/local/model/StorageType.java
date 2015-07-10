package pl.rspective.data.local.model;

public enum StorageType {

    SURVEY("survey"),
    USERS("users");

    private String tag;

    private StorageType(String tag) {
        this.tag = tag;
    }

    public String getTag() {
        return tag;
    }
}
