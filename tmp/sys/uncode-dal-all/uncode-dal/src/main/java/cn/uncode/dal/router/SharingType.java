package cn.uncode.dal.router;

public enum SharingType {
    
    RANGE("range"), HASH("hash");

    public final String TYPE;
    
    SharingType(String type) {
      this.TYPE = type;
    }

    public String value(){
        return TYPE;
    }
}
