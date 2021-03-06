package tv.mineinthebox.essentials.events.chairs;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityTargetEvent;
import org.bukkit.event.entity.EntityTargetLivingEntityEvent;

import tv.mineinthebox.essentials.xEssentials;
import tv.mineinthebox.essentials.interfaces.XPlayer;

public class ChairDisableMonsterEvent implements Listener {
	
	private final xEssentials pl;
	
	public ChairDisableMonsterEvent(xEssentials pl) {
		this.pl = pl;
	}
	
	@EventHandler
	public void onTarget(EntityTargetEvent e) {
		if(e.getTarget() instanceof Player) {
			Player p = (Player) e.getTarget();
			if(pl.getManagers().getPlayerManager().isOnline(p.getName())) {
				XPlayer xp = pl.getManagers().getPlayerManager().getPlayer(p.getName());
				if(xp.isInChair()) {
					e.setCancelled(true);
				}
			}
		}
	}

	@EventHandler
	public void onTarget(EntityTargetLivingEntityEvent e) {
		if(e.getTarget() instanceof Player) {
			Player p = (Player) e.getTarget();
			if(pl.getManagers().getPlayerManager().isOnline(p.getName())) {
				XPlayer xp = pl.getManagers().getPlayerManager().getPlayer(p.getName());
				if(xp.isInChair()) {
					e.setCancelled(true);
				}
			}
		}
	}

}
