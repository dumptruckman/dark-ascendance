package com.dumptruckman.darkascendance.recs.entities;

import com.dumptruckman.darkascendance.recs.Entity;
import com.dumptruckman.darkascendance.recs.components.Health;
import com.dumptruckman.darkascendance.recs.components.Position;
import com.dumptruckman.darkascendance.recs.components.Velocity;

public class Player extends Entity {
	public Position position;
	public Velocity velocity = new Velocity(2, 1);
	public Health health = new Health(10, 15);

	public Player(float x, float y) {
		position = new Position(x, y);
	}

	public Player() {}
}
