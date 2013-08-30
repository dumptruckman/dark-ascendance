package com.dumptruckman.darkascendance.recs.systems;

import com.dumptruckman.darkascendance.recs.ComponentMapper;
import com.dumptruckman.darkascendance.recs.EntitySystem;
import com.dumptruckman.darkascendance.recs.EventListener;
import com.dumptruckman.darkascendance.recs.components.Health;
import com.dumptruckman.darkascendance.recs.events.DamageEvent;

public class HealthSystem extends EntitySystem {
	public ComponentMapper<Health> healthManager;

	public EventListener<DamageEvent> damageListener;

	public HealthSystem() {
		super(Health.class);
	}

	@Override
	protected void processSystem(float deltaInSec) {
		for(DamageEvent damageEvent: damageListener.pollEvents()) {
			Health health = healthManager.get(damageEvent.entityId);
			health.amount -= damageEvent.damage;
		}
		super.processSystem(deltaInSec);
	}

	@Override
	protected void processEntity(int entityId, float deltaInSec) {
		Health health = healthManager.get(entityId);
		if (health.amount <= 0) {
			world.removeEntity(entityId);
		}
	}
}