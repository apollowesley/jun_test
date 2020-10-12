package priv.mdc.index.dumper.model;

/**
 * 索引更新类型
 * @author xuhuahai
 *
 */
public enum UpdateType {

	CREATE(1, "CREATE DOC"),
    UPDATE(2, "UPDATE DOC"),
    DELETE(3, "DELETE DOC"),
    ;
	
    private int value;
    private String desc;
    
    UpdateType(int value_, String desc_){
    	value = value_;
    	desc = desc_;
    }

	public int getValue() {
		return value;
	}

	public void setValue(int value) {
		this.value = value;
	}

	public String getDesc() {
		return desc;
	}

	public void setDesc(String desc) {
		this.desc = desc;
	}
    
    public String toString(){
    	return desc;
    }
}
