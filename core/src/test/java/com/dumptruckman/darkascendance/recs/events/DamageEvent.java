package com.dumptruckman.darkascendance.recs.events;

public class DamageEvent {
	public int entityId;
	public int damage;

	public DamageEvent(int entityId, int damage) {
		this.entityId = entityId;
		this.damage = damage;
	}
}
