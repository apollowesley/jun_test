package x.javaplus.mysql.db;

public class Condition {

	private String statement  = "";
	
	public Condition( String sql ) {
		this.statement  = sql;
	}

	public Condition AND( String sql ) {
		statement += (" AND " + sql);
		return this;
	}

	public Condition OR( String sql ) {
		statement += (" OR " + sql);
		return this;
	}

	public String toString(){
		return statement ;
	}
	
}
