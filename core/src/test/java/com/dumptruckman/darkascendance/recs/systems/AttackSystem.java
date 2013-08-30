package com.dumptruckman.darkascendance.recs.systems;

import com.dumptruckman.darkascendance.recs.IntervalEntitySystem;
import com.dumptruckman.darkascendance.recs.utils.libgdx.RECSMathUtils;
import com.dumptruckman.darkascendance.recs.components.Attack;
import com.dumptruckman.darkascendance.recs.events.DamageEvent;

public class AttackSystem extends IntervalEntitySystem {
	public AttackSystem() {
		super(1f, Attack.class);
	}

	@Override
	protected void processEntity(int entityId, float deltaInSec) {
		//send damage message 10% chance.
		int random = RECSMathUtils.random(0, 10);
		if(random == 0) {
			world.sendEvent(new DamageEvent(entityId, 1));
		}
	}
}
