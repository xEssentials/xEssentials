package tv.mineinthebox.essentials.events.players;

import org.bukkit.Bukkit;
import org.bukkit.GameMode;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerGameModeChangeEvent;
import org.bukkit.inventory.ItemStack;

import tv.mineinthebox.essentials.xEssentials;
import tv.mineinthebox.essentials.events.customevents.GameModeInventoryChangeEvent;
import tv.mineinthebox.essentials.interfaces.XPlayer;

public class GameModeInvChangeEvent implements Listener {
	
	private final xEssentials pl;
	
	public GameModeInvChangeEvent(xEssentials pl) {
		this.pl = pl;
	}
	
	@EventHandler
	public void onChangeInventory(PlayerGameModeChangeEvent e) {
		XPlayer p = pl.getManagers().getPlayerManager().getPlayer(e.getPlayer().getName());
		if(e.getNewGameMode() == GameMode.SURVIVAL) {
			p.saveCreativeInventory();
			if(p.hasSurvivalInventory()) {
				ItemStack[] contents = p.getSurvivalInventory();
				ItemStack[] armor = p.getSurvivalArmorInventory();
				e.getPlayer().getInventory().setContents(contents);
				e.getPlayer().getInventory().setArmorContents(armor);
			} else {
				e.getPlayer().getInventory().clear();
			}
		} else if(e.getNewGameMode() == GameMode.CREATIVE) {
			p.saveSurvivalInventory();
			if(p.hasCreativeInventory()) {
				ItemStack[] contents = p.getCreativeInventory();
				ItemStack[] armor = p.getCreativeArmorInventory();
				e.getPlayer().getInventory().setContents(contents);
				e.getPlayer().getInventory().setArmorContents(armor);
			} else {
				e.getPlayer().getInventory().clear();
			}
		}
		if(p.hasSurvivalInventory() && p.hasCreativeInventory()) {
			Bukkit.getPluginManager().callEvent(new GameModeInventoryChangeEvent(e.getPlayer(), p.getCreativeInventory(), e.getPlayer().getInventory().getContents(), e.getPlayer().getGameMode(), e.getNewGameMode()));	
		}
	}
}
