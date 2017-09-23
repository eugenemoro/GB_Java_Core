package client;

import java.util.ArrayList;
import java.util.Iterator;

public class UserList {
	private ArrayList<User> list = new ArrayList<>();

	public ArrayList<User> getList() {
		return list;
	}

	public void addUser(String name){
		list.add(new User(name));
	}

	public boolean containsUser(String name){
		for (User el : list) {
			if (el.getName().equals(name)) {
				return true;
			}
		}
		return false;
	}

	public void renew(String[] users){
		Iterator<User> itr = list.iterator();
		while (itr.hasNext()){
			boolean notDeleted = false;
			User nextUser = itr.next();
			for (String el : users) {
				if (nextUser.getName().equals(el)) {
					notDeleted = true;
					break;
				}
			}
			if (!notDeleted) itr.remove();
		}
		for (int i = 0; i < users.length; i++) {
			if (!this.containsUser(users[i])) this.addUser(users[i]);
		}
	}
}
