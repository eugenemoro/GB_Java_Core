package com.company;

public class Team {
	Animal[] members = new Animal[4];

	public Team(Animal a, Animal b, Animal c, Animal d){
		members[0] = a;
		members[1] = b;
		members[2] = c;
		members[3] = d;
	}

	public void showResults(){
		for (int i = 0; i < members.length; i++) {
			members[i].printResult();
		}
	}

	public void teamDoIt(Obstacle obstacle){
		for (int i = 0; i < members.length; i++) {
			if (members[i].onDistance) obstacle.doIt(members[i]);
		}
	}
}
