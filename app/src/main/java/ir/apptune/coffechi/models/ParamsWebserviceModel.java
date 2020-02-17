package ir.apptune.coffechi.models;

public class ParamsWebserviceModel {
    String key;
    String value;


    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public ParamsWebserviceModel(String key, String value) {
        this.key = key;
        this.value = value;
    }
}
