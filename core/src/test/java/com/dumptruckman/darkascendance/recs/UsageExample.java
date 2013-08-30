package com.dumptruckman.darkascendance.recs;

import com.dumptruckman.darkascendance.recs.utils.libgdx.RECSMathUtils;
import com.dumptruckman.darkascendance.recs.components.Attack;
import com.dumptruckman.darkascendance.recs.components.Health;
import com.dumptruckman.darkascendance.recs.components.Position;
import com.dumptruckman.darkascendance.recs.components.Velocity;
import com.dumptruckman.darkascendance.recs.entities.Player;
import com.dumptruckman.darkascendance.recs.entities.PlayerWithAttack;
import com.dumptruckman.darkascendance.recs.entities.Zombie;
import com.dumptruckman.darkascendance.recs.systems.HealthSystem;
import com.dumptruckman.darkascendance.recs.systems.MovementSystem;

public class UsageExample {
	//Register all component classes here.
	private static final Class<?>[] COMPONENTS = { Health.class, Position.class, Velocity.class, Attack.class };

	public static void main(String[] args) {
		EntityWorld world = new EntityWorld();
		world.registerComponents(COMPONENTS);

		world.addSystem(new HealthSystem());
		world.addSystem(new MovementSystem());

		world.addEntity(new Player(4, 6));
		world.addEntity(new PlayerWithAttack(12, 9));
		world.addEntity(new Zombie(1, 2));
		world.addEntity(new Player(1,2));

		float totalTime = 0f;
		//game loop
		float accumulator = 0f;
		float timeStep = 1/60f;
		long currentTime = System.nanoTime();
		while (true) {
			long start = System.nanoTime();
			long deltaNano = start - currentTime;
			currentTime = start;
			float deltaSec = deltaNano * RECSMathUtils.nanoToSec;

			accumulator += deltaSec;
			while(accumulator > timeStep) {
				accumulator -= timeStep;
				world.process(timeStep);
			}

			totalTime += deltaSec;
			if(totalTime > 3f) break;
		}
	}
}
