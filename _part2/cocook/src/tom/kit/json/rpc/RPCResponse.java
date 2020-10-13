package tom.kit.json.rpc;


public class RPCResponse {
	private String id;
	private Object result;
	private RPCError error;

	public String getId() {
		return this.id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public Object getResult() {
		return this.result;
	}

	public void setResult(Object result) {
		this.result = result;
	}

	public RPCError getError() {
		return this.error;
	}

	public void setError(RPCError error) {
		this.error = error;
	}
}
