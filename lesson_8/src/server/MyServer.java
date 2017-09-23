package server;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Vector;

public class MyServer {
	private ServerSocket server ;
	private Vector <ClientHandler> clients ;
	private AuthService authService ;
	public AuthService getAuthService () {
		return authService ;
	}
	private final int PORT = 8189 ;
	public MyServer () {
		try {
			server = new ServerSocket ( PORT );
			Socket socket = null ;
			authService = new BaseAuthService ();
			authService.start ();
			clients = new Vector<>();
			while (true) {
				System.out.println ("Server awaits connection");
				socket = server.accept ();
				System.out.println ( "Client connected" );
				new ClientHandler(this, socket);
			}
		} catch (IOException e) {
			System.out.println ( "Server Error" );
		} finally {
			try {
				server.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
			authService.stop();
		}
	}
	public synchronized boolean isNickBusy (String nick) {
		for (ClientHandler o : clients) {
			if (o.getName().equals(nick)) return true;
		}
		return false;
	}
	public synchronized void broadcastMsg (String msg) {
		for (ClientHandler o : clients) {
			o.sendMsg(msg);
		}
	}

	public synchronized void sendMsgToClient(ClientHandler from , String nickTo , String msg) {
		for (ClientHandler o : clients) {
			if (o.getName().equals(nickTo)) {
				o.sendMsg("from " + from.getName() + ": " + msg);
				from.sendMsg("to " + nickTo + ": " + msg);
				return;
			}
		}
		from.sendMsg("Client " + nickTo + " is not in the chat room");
	}
	public synchronized void broadcastClientList() {
		StringBuilder sb = new StringBuilder ("/clients ");
		for ( ClientHandler o : clients ) {
			sb.append(o.getName() + " ");
		}
		String msg = sb.toString();
		broadcastMsg(msg);
	}

	public synchronized void unsubscribe(ClientHandler o) {
		clients.remove(o);
		broadcastClientList();
	}
	public synchronized void subscribe(ClientHandler o) {
		clients.add(o);
		broadcastClientList();
	}
}
