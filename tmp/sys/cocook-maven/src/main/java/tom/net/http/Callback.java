package tom.net.http;


import tom.net.http.id.TicketManager.Id;

public class Callback {
	
	public static interface ResultCallback { 
		public void onCompleted(Id result);  
	}

//	public static interface ConnectedCallback { 
//		public void onConnected(Session sess) throws IOException;   
//	}
//	
//	public static interface MessageCallback { 
//		public void onMessage(Id msg, Session sess) throws IOException;   
//	}
//
//	public static interface ErrorCallback { 
//		public void onError(IOException e, Session sess) throws IOException;   
//	}

}
