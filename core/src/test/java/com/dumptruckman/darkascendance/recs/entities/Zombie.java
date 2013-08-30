package com.dumptruckman.darkascendance.recs.entities;

import com.dumptruckman.darkascendance.recs.Entity;
import com.dumptruckman.darkascendance.recs.components.Position;
import com.dumptruckman.darkascendance.recs.components.Velocity;

public class Zombie extends Entity {
	Position position;
	Velocity velocity;
	public Zombie(float x, float y) {
		position = new Position(x, y);
		velocity = new Velocity(1, 2);
	}
}
