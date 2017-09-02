package com.company;

public class Course {
	Obstacle[] course = new Obstacle[4];

	public Course(Obstacle a, Obstacle b, Obstacle c, Obstacle d){
		course[0] = a;
		course[1] = b;
		course[2] = c;
		course[3] = d;
	}

	public void doIt(Team team){
		for (int i = 0; i < course.length; i++) {
			team.teamDoIt(course[i]);
		}
	}
}
