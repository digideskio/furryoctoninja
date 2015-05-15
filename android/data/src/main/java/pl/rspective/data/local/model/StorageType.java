package pl.rspective.data.local.model;

public enum StorageType {

    SURVEY("mckinsey_survey"),
    USERS("mckinsey_users");

    private String tag;

    private StorageType(String tag) {
        this.tag = tag;
    }

    public String getTag() {
        return tag;
    }
}
