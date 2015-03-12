package tv.mineinthebox.essentials.events.protection;

import java.util.Arrays;

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

import tv.mineinthebox.essentials.xEssentials;
import tv.mineinthebox.essentials.enums.PermissionKey;
import tv.mineinthebox.essentials.instances.ProtectedBlock;

public class DispenserProtectionEvent implements Listener {

	private final xEssentials pl;
	
	public DispenserProtectionEvent(xEssentials pl) {
		this.pl = pl;
	}
	
	@EventHandler(ignoreCancelled = true)
	public void onPlace(BlockPlaceEvent e) {
		if(e.getBlock().getType() == Material.DISPENSER) {
			ProtectedBlock pblock = new ProtectedBlock(pl, e.getBlock());
			pblock.addProtection(e.getPlayer().getUniqueId());
			e.getPlayer().sendMessage(ChatColor.GREEN + "registered private dispenser");
		}
	}
	
	@EventHandler(ignoreCancelled = true)
	public void onBreak(BlockBreakEvent e) {
		if(e.isCancelled()) {
			return;
		}
		if(e.getBlock().getType() == Material.DISPENSER) {
			ProtectedBlock pblock = new ProtectedBlock(pl, e.getBlock());
			if(pblock.isProtected()) {
				if(pblock.isMember(e.getPlayer().getUniqueId()) || e.getPlayer().hasPermission(PermissionKey.IS_ADMIN.getPermission())) {
					pblock.removeAll();
					e.getPlayer().sendMessage(ChatColor.GREEN + "unregistered that dispenser.");
				} else {
					e.getPlayer().sendMessage(pl.getConfiguration().getProtectionConfig().getDisallowMessage().replace("%BLOCK%", e.getBlock().getType().name()));
					e.setCancelled(true);
				}
			}
		}
	}

	@EventHandler(ignoreCancelled = true)
	public void onInteract(PlayerInteractEvent e) {
		if(e.getAction() == Action.RIGHT_CLICK_BLOCK) {
			if(e.getClickedBlock().getType() == Material.DISPENSER) {
				ProtectedBlock pblock = new ProtectedBlock(pl, e.getClickedBlock());
				if(pblock.isProtected()) {
					if(pblock.isMember(e.getPlayer().getUniqueId()) || e.getPlayer().hasPermission(PermissionKey.IS_ADMIN.getPermission())) {
						e.getPlayer().sendMessage(ChatColor.GREEN + "opening privated dispenser of " + (pblock.isMember(e.getPlayer().getUniqueId()) ? "you" : Arrays.toString(pblock.getMembers().toArray())));
					} else {
						e.getPlayer().sendMessage(pl.getConfiguration().getProtectionConfig().getDisallowMessage().replace("%BLOCK%", e.getClickedBlock().getType().name()));
						e.setCancelled(true);
					}
				}
			}
		}
	}

	@EventHandler
	public void onPiston(BlockPistonExtendEvent e) {
		for(Block block : e.getBlocks()) {
			if(block.getType() == Material.DISPENSER) {
				ProtectedBlock pblock = new ProtectedBlock(pl, block);
				if(pblock.isProtected()) {
					e.setCancelled(true);
					break;
				}
			}
		}
	}

	@EventHandler(priority = EventPriority.LOWEST, ignoreCancelled = false)
	public void onExplosion(EntityExplodeEvent e) {
		for(Block block : e.blockList()) {
			if(block.getType() == Material.DISPENSER) {
				ProtectedBlock pblock = new ProtectedBlock(pl, block);
				if(pblock.isProtected()) {
					e.setCancelled(true);
				}
			}
		}
	}

}
