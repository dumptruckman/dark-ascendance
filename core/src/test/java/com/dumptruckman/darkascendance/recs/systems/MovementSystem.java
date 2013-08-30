package com.dumptruckman.darkascendance.recs.systems;

import com.dumptruckman.darkascendance.recs.ComponentMapper;
import com.dumptruckman.darkascendance.recs.EntitySystem;
import com.dumptruckman.darkascendance.recs.components.Position;
import com.dumptruckman.darkascendance.recs.components.Velocity;

public class MovementSystem extends EntitySystem {
	private ComponentMapper<Position> positionMapper;
	private ComponentMapper<Velocity> velocityMapper;

	public MovementSystem() {
		super(Position.class, Velocity.class);
	}

	@Override
	public void processEntity(int entityId, float deltaInSec) {
		Position position = positionMapper.get(entityId);
		Velocity velocity = velocityMapper.get(entityId);
		position.x += velocity.x * deltaInSec;
		position.y += velocity.y * deltaInSec;
	}
}