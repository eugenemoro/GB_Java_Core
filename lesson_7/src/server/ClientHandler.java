package server;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;

public class ClientHandler {
	private MyServer myServer;
	private Socket socket;
	private DataInputStream in;
	private DataOutputStream out;
	private String name;
	public String getName() {
		return name;
	}
	public ClientHandler(MyServer myServer, Socket socket) {
		try {
			this.myServer = myServer;
			this.socket = socket;
			this.in = new DataInputStream(socket.getInputStream());
			this.out = new DataOutputStream(socket.getOutputStream());
			this.name = "" ;
			new Thread(() -> {
				try {
					while (true) { // цикл авторизации
						String str = in.readUTF();
						if (str.startsWith("/auth")){
							String[] parts = str.split("\\s");
							String nick = myServer.getAuthService().getNickByLoginPass(parts[1], parts[2]);
							if (nick != null) {
								if (!myServer.isNickBusy(nick)) {
									sendMsg("/authok " + nick);
									name = nick ;
									myServer.broadcastMsg(name + " entered chat");
									myServer.subscribe(this);
									break;
								} else sendMsg("The account is already in use");
							} else {
								sendMsg("Wrong login/password");
							}
						}
					}
					while (true) { // цикл получения сообщений
						String str = in.readUTF();
						System.out.println(name + ": " + str);
						if (str.equals("/end")) break;
						myServer.broadcastMsg(name + ": " + str);
					}
				} catch (IOException e) {
					e.printStackTrace();
				} finally {
					myServer.unsubscribe(this);
					myServer.broadcastMsg(name + " left chat");
					try {
						socket.close();
					} catch (IOException e) {
						e.printStackTrace();
					}
				}
			}).start();
		} catch (IOException e) {
			throw new RuntimeException("Troubles with ClientHandler creation");
		}
	}
	public void sendMsg (String msg) {
		try {
			out.writeUTF(msg);
			out.flush();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
