package com.dumptruckman.darkascendance.recs.systems;

import com.dumptruckman.darkascendance.recs.ComponentMapper;
import com.dumptruckman.darkascendance.recs.EntityTaskSystem;
import com.dumptruckman.darkascendance.recs.components.Position;
import com.dumptruckman.darkascendance.recs.components.Velocity;

public class ThreadedMovementSystem extends EntityTaskSystem {
	private ComponentMapper<Position> positionManager;
	private ComponentMapper<Velocity> velocityManager;

	public ThreadedMovementSystem() {
		super(Position.class, Velocity.class);
	}

	@Override
	protected void processEntity(int entityId, float deltaInSec) {
		Position position = positionManager.get(entityId);
		Velocity velocity = velocityManager.get(entityId);

		position.x += velocity.x * deltaInSec;
		position.y += velocity.y * deltaInSec;
	}
}
