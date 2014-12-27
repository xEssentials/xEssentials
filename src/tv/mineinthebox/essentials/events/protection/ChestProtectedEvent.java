package tv.mineinthebox.essentials.events.protection;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockPistonExtendEvent;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.event.entity.EntityExplodeEvent;
import org.bukkit.event.player.PlayerInteractEvent;

import tv.mineinthebox.essentials.Configuration;
import tv.mineinthebox.essentials.xEssentials;
import tv.mineinthebox.essentials.enums.PermissionKey;

public class ChestProtectedEvent implements Listener {

	@EventHandler(ignoreCancelled = true)
	public void onPlace(BlockPlaceEvent e) {
		if(e.getBlock().getType() == Material.CHEST || e.getBlock().getType() == Material.TRAPPED_CHEST) {
			if(xEssentials.getManagers().getProtectionDBManager().register(e.getPlayer().getName(), e.getBlock())) {
				e.getPlayer().sendMessage(ChatColor.GREEN + "registered private chest");
			} else {
				e.getPlayer().sendMessage(Configuration.getProtectionConfig().getDisallowMessage().replace("%BLOCK%", e.getBlock().getType().name()));
				e.setCancelled(true);
			}
		}
	}
	
	@EventHandler(ignoreCancelled = true)
	public void onBreak(BlockBreakEvent e) {
		if(e.isCancelled()) {
			return;
		}
		if(e.getBlock().getType() == Material.CHEST || e.getBlock().getType() == Material.TRAPPED_CHEST) {
			if(xEssentials.getManagers().getProtectionDBManager().isRegistered(e.getBlock())) {
				if(xEssentials.getManagers().getProtectionDBManager().isOwner(e.getPlayer().getName(), e.getBlock()) || e.getPlayer().hasPermission(PermissionKey.IS_ADMIN.getPermission())) {
					xEssentials.getManagers().getProtectionDBManager().unregister(e.getPlayer().getName(), e.getBlock());
					e.getPlayer().sendMessage(ChatColor.GREEN + "unregistered that chest.");
				} else {
					e.getPlayer().sendMessage(Configuration.getProtectionConfig().getDisallowMessage().replace("%BLOCK%", e.getBlock().getType().name()));
					e.setCancelled(true);
				}
			}
		}
	}

	@EventHandler(ignoreCancelled = true)
	public void onInteract(PlayerInteractEvent e) {
		if(e.getAction() == Action.RIGHT_CLICK_BLOCK) {
			if(e.getClickedBlock().getType() == Material.CHEST || e.getClickedBlock().getType() == Material.TRAPPED_CHEST) {
				if(xEssentials.getManagers().getProtectionDBManager().isRegistered(e.getClickedBlock())) {
					if(xEssentials.getManagers().getProtectionDBManager().isOwner(e.getPlayer().getName(), e.getClickedBlock()) || e.getPlayer().hasPermission(PermissionKey.IS_ADMIN.getPermission())) {
						e.getPlayer().sendMessage(ChatColor.GREEN + "opening privated chest of " + (xEssentials.getManagers().getProtectionDBManager().isOwner(e.getPlayer().getName(), e.getClickedBlock()) ? "you" : xEssentials.getManagers().getProtectionDBManager().getOwners(e.getClickedBlock())));
					} else {
						e.getPlayer().sendMessage(Configuration.getProtectionConfig().getDisallowMessage().replace("%BLOCK%", e.getClickedBlock().getType().name()));
						e.setCancelled(true);
					}
				}
			}
		}
	}

	@EventHandler
	public void onPiston(BlockPistonExtendEvent e) {
		for(Block block : e.getBlocks()) {
			if(block.getType() == Material.CHEST || block.getType() == Material.TRAPPED_CHEST) {
				if(xEssentials.getManagers().getProtectionDBManager().isRegistered(block)) {
					e.setCancelled(true);
					break;
				}
			}
		}
	}

	@EventHandler(priority = EventPriority.LOWEST, ignoreCancelled = false)
	public void onExplosion(EntityExplodeEvent e) {
		for(Block block : e.blockList()) {
			if(block.getType() == Material.CHEST || block.getType() == Material.TRAPPED_CHEST) {
				if(xEssentials.getManagers().getProtectionDBManager().isRegistered(block)) {
					e.setCancelled(true);
				}
			}
		}
	}
	
}