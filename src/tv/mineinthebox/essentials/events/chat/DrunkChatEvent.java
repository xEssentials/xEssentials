      package tv.mineinthebox.essentials.events.chat;

import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerChatEvent;

import tv.mineinthebox.essentials.xEssentials;
import tv.mineinthebox.essentials.interfaces.XPlayer;

@SuppressWarnings("deprecation")
public class DrunkChatEvent implements Listener {
	
	private final xEssentials pl;
	
	public DrunkChatEvent(xEssentials pl) {
		this.pl = pl;
	}
	
	@EventHandler(priority = EventPriority.HIGH)
	public void onDrunk(PlayerChatEvent e) {
		if(e.isCancelled()) {
			return;
		}
		
		XPlayer xp = pl.getManagers().getPlayerManager().getPlayer(e.getPlayer().getName());
		if(xp.isDrunk()) {
			String derp = xp.getDrunkMessageFrom(e.getMessage(), false);
			e.setMessage(derp);
		}
	}

}
