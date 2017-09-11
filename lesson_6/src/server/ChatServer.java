package server;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketException;
import java.util.Scanner;

public class ChatServer {
	public static void main(String[] args) {
		ServerSocket server = null;
		Socket socket = null;
		try{
			server = new ServerSocket(8189);
			System.out.println("Сервер запущен, ждем клиентов");
			socket = server.accept(); //режим ожидания, возвращает объект типа сокет, блокирует выполнение кода
			System.out.println("Клиент подключился");
			DataInputStream in = new DataInputStream(socket.getInputStream());
			Scanner serverInputScanner = new Scanner(new DataInputStream(System.in));
			DataOutputStream out = new DataOutputStream(socket.getOutputStream());

			Thread threadClientMsg = new Thread(new Runnable() {
				@Override
				public void run() {
					while(true){
						String msg = null;
						try {
							msg = in.readUTF();
							System.out.println("Client: " + msg);
							out.writeUTF("You: " + msg);
							out.flush();
						} catch (IOException e) {
							e.printStackTrace();
						}
					}
				}
			});
			Thread threadServerMsg = new Thread(new Runnable() {
				@Override
				public void run() {
					while(true){
						try {
							String msgFromServer = serverInputScanner.nextLine();
							out.writeUTF("Server: " + msgFromServer);
							out.flush();
						} catch (IOException e) {
							e.printStackTrace();
						}
					}
				}
			});
			threadClientMsg.start();
			threadServerMsg.start();

			try {
				threadClientMsg.join();
				threadServerMsg.join();
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}catch(SocketException e){
			System.out.println("Соединение разорвано");
			try {
				socket.close();
				server.close();
			} catch (IOException e1) {
				e1.printStackTrace();
			}
		}catch(IOException e){
			e.printStackTrace();
			System.out.println("Не удалось запустить сервер");
		}finally{
			try{
				socket.close();
				server.close();
			}catch(IOException e){
				e.printStackTrace();
			}
		}
	}


}
