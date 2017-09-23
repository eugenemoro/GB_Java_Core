package server;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.time.Clock;
import java.util.Date;

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
			Thread interruptionThread = new Thread(() -> {
				try {
					Thread.sleep(10000);
					socket.close();
				} catch (InterruptedException e) {
					System.out.println("Client authorised");;
				} catch (Exception e) {
					e.printStackTrace();
				}
			});
			interruptionThread.start();
			Thread mainThread = new Thread(() -> {
				try {
					while (true) { // цикл авторизации
						String str = in.readUTF();
						if (str.startsWith("/auth")){
							String[] parts = str.split("\\s");
							String nick;
							nick = myServer.getAuthService().getNickByLoginPass(parts[1], parts[2]);
							if (nick != null) {
								if (!myServer.isNickBusy(nick)) {
									sendMsg("/authok " + nick);
									name = nick ;
									myServer.broadcastMsg(name + " entered chat");
									myServer.subscribe(this);
									interruptionThread.interrupt();
									break;
								} else sendMsg("The account is already in use");
							} else {
								sendMsg("Wrong login/password");
							}
						}
					}
					while (true) { // цикл получения сообщений
						String str = in.readUTF();
						if (str.startsWith("/")) {
							if (str.equals("/end")) break;
							if (str.startsWith("/w ")) {
								String[] tokens = str.split("\\s");
								String nick = tokens[1];
								String msg = str.substring(4 + nick.length());
								myServer.sendMsgToClient(this, nick, msg);
							}
						} else {
							myServer.broadcastMsg(name + ": " + str);
						}
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
			});
			mainThread.start();
			try {
				interruptionThread.join();
				mainThread.join();
			} catch (InterruptedException e) {
				e.printStackTrace();
			}

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
