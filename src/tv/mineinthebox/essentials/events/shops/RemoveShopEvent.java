package tv.mineinthebox.essentials.events.shops;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.bukkit.block.Sign;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;

import tv.mineinthebox.essentials.xEssentials;
import tv.mineinthebox.essentials.enums.PermissionKey;
import tv.mineinthebox.essentials.interfaces.EventTemplate;
import tv.mineinthebox.essentials.interfaces.XOfflinePlayer;
import tv.mineinthebox.essentials.interfaces.XPlayer;

public class RemoveShopEvent extends EventTemplate implements Listener {
	
	public RemoveShopEvent(xEssentials pl) {
		super(pl, "Shop");
	}
	
	@EventHandler
	public void onRemove(BlockBreakEvent e) {
		if(e.isCancelled()) {return;}
		
		if(e.getBlock().getType() == Material.SIGN_POST || e.getBlock().getType() == Material.WALL_SIGN) {
			Sign sign = (Sign) e.getBlock().getState();
			if(isShopSign(sign.getLines(), false)) {
				XPlayer xp = pl.getManagers().getPlayerManager().getPlayer(e.getPlayer().getName());
				if(xp.isShop(e.getBlock().getLocation())) {
					xp.removeShop(e.getBlock().getLocation());
					sendMessage(e.getPlayer(), ChatColor.GREEN + "[Shop] " + ChatColor.GRAY + " shop unregistered!");
				} else if(e.getPlayer().hasPermission(PermissionKey.IS_ADMIN.getPermission())) {
					for(XOfflinePlayer off : pl.getManagers().getPlayerManager().getOfflinePlayers()) {
						if(off.isShop(e.getBlock().getLocation())) {
							off.removeShop(e.getBlock().getLocation());
							sendMessage(e.getPlayer(), ChatColor.GREEN + "[Shop] " + ChatColor.GRAY + " shop of " + off.getName() + " is unregistered!");
							return;
						}
					}
				} else {
					sendMessage(e.getPlayer(), ChatColor.RED + "you are not allowed to destroy a shop sign you dont own!");
					e.setCancelled(true);
				}
			}
		} else if(e.getBlock().getType() == Material.CHEST) {
			if(isSignNearby(e.getBlock())) {
				Sign sign = getAttachedSign(e.getBlock());
				if(isShopSign(sign.getLines(), false)) {
					sendMessage(e.getPlayer(), ChatColor.RED + "please remove first the sign in order to destroy the chest!");
					e.setCancelled(true);
				}
			}
		}
		
	}
	
	private boolean isSignNearby(Block block) {
		BlockFace[] faces = {BlockFace.NORTH, BlockFace.EAST, BlockFace.SOUTH, BlockFace.WEST, BlockFace.UP, BlockFace.DOWN};
		for(BlockFace face : faces) {
			if(block.getRelative(face).getType() == Material.SIGN_POST || block.getRelative(face).getType() == Material.WALL_SIGN) {
				return true;
			}
		}
		return false;
	}
	
	private Sign getAttachedSign(Block block) {
		BlockFace[] faces = {BlockFace.NORTH, BlockFace.EAST, BlockFace.SOUTH, BlockFace.WEST, BlockFace.UP, BlockFace.DOWN};
		for(BlockFace face : faces) {
			if(block.getRelative(face).getType() == Material.SIGN_POST || block.getRelative(face).getType() == Material.WALL_SIGN) {
				return (Sign) block.getRelative(face).getState();
			}
		}
		return null;
	}
	
	private boolean isShopSign(String[] lines, boolean isAdmin) {
		if(isAdmin) {
			if(lines[0].equalsIgnoreCase(ChatColor.stripColor(pl.getConfiguration().getShopConfig().getAdminShopPrefix()))) {
				if(lines[1].matches("[0-9]+")) {
					int amount = Integer.parseInt(lines[1]);
					if(amount <= 64) {
						//System.out.print("the amount has a good limit of 64");
						if(lines[2].matches("(?i)^b \\d+ s \\d+$") || lines[2].matches("(?i)^b \\d+$") || lines[2].matches("(?i)^s \\d+$")) {
							if(lines[3].matches("([0-9]+):([0-9]+)")) {
								return true;
							} else {
								try {
									Material.valueOf(lines[3].toUpperCase().replaceAll(" ", "_"));
									return true;
								} catch(IllegalArgumentException e) {
									return false;
								}
							}
						}
					}
				}
			}
		} else {
			if(lines[1].matches("[0-9]+")) {
				int amount = Integer.parseInt(lines[1]);
				if(amount <= 64) {
					if(lines[2].matches("(?i)^b \\d+ s \\d+$") || lines[2].matches("(?i)^b \\d+$") || lines[2].matches("(?i)^s \\d+$")) {
						if(lines[3].matches("([0-9]+):([0-9]+)")) {
							return true;
						} else {
							try {
								Material.valueOf(lines[3].toUpperCase().replaceAll(" ", "_"));
								return true;
							} catch(IllegalArgumentException e) {
								return false;
							}
						}
					}
				}
			}
		}
		return false;
	}

}
