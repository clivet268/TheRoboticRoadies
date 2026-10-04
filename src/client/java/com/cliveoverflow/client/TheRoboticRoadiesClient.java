package com.cliveoverflow.client;

import com.cliveoverflow.client.robot.AbstractRobot;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents;

public class TheRoboticRoadiesClient implements ClientModInitializer {
	public static AbstractRobot abby = null;
	@Override
	public void onInitializeClient() {
		// 1. Register Chat Toggle Commands
		ClientCommandHandler.register();

		// 2. Register HUD Overlay Render Engine
		StateHUDOverlay.register();
				// 3. FIXED: Changed 'abby::advance' to a dynamic lambda.
				// This stops the boot crash because it won't evaluate 'abby' until the game is ticking and abby is instantiated.
				ClientTickEvents.END_CLIENT_TICK.register(client -> {
					if (abby != null && abby.isEnabled) {
						abby.advance(client); // Or abby.advance() depending on your method's arguments
					}
				});
	}
}