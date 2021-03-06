package tv.mineinthebox.essentials.hook;


public class WorldGuardHook_org {
/*

	public static Location getRegionLocation(String region, World w) {
		WorldGuardPlugin wg = (WorldGuardPlugin) Bukkit.getPluginManager().getPlugin("WorldGuard");
		ProtectedRegion reg = wg.getRegionManager(w).getRegion(region);
		Location loc = new Location(w, reg.getMaximumPoint().getX(), 70, reg.getMinimumPoint().getZ(), 0,0);
		return loc;
	}

	public static boolean isValidRegion(String region, World w) {
		WorldGuardPlugin wg = (WorldGuardPlugin) Bukkit.getPluginManager().getPlugin("WorldGuard");
		if(wg.getRegionManager(w).hasRegion(region)) {
			return true;
		}
		return false;
	}

	public static void sendVanishQuitMessage(Player p) {
		XPlayer xp = xEssentials.getPlugin().getManagers().getPlayerManager().getPlayer(p.getName());
		if(!xp.isVanished()) {
			if(Bukkit.getPluginManager().isPluginEnabled("WorldGuard")) {
				WorldGuardPlugin wg = (WorldGuardPlugin) Bukkit.getPluginManager().getPlugin("WorldGuard");
				for(ProtectedRegion region : wg.getRegionManager(p.getWorld()).getApplicableRegions(p.getLocation())) {
					if(region.getFlag(DefaultFlag.MOB_SPAWNING) == State.DENY) {
						Bukkit.broadcastMessage(ChatColor.RED + "Whoosh!" + ChatColor.GRAY + " staff member " + ChatColor.GREEN + p.getDisplayName() + ChatColor.GRAY + " has left the game safely!");
						xp.vanish();
						return;
					}
				}
				Bukkit.broadcastMessage(ChatColor.RED + "Whoosh!" + ChatColor.GRAY + " staff member " + ChatColor.GREEN + p.getDisplayName() + ChatColor.GRAY + " has left the game in wild!");
				xp.vanish();
			}
		} else {
			p.sendMessage(ChatColor.RED + "you are allready vanished so you can't fake quit, use /vanish fakejoin instead or /vanish");
		}
	}

	public static void sendVanishJoinMessage(Player p) {
		XPlayer xp = xEssentials.getPlugin().getManagers().getPlayerManager().getPlayer(p.getName());
		if(xp.isVanished()) {
			if(Bukkit.getPluginManager().isPluginEnabled("WorldGuard")) {
				WorldGuardPlugin wg = (WorldGuardPlugin) Bukkit.getPluginManager().getPlugin("WorldGuard");
				for(ProtectedRegion region : wg.getRegionManager(p.getWorld()).getApplicableRegions(p.getLocation())) {
					if(region.getFlag(DefaultFlag.MOB_SPAWNING) == State.DENY) {
						Bukkit.broadcastMessage(ChatColor.GRAY + "a safe staff member " + p.getDisplayName() + ChatColor.GRAY + " has been appeared!");
						xp.vanish();
						return;
					}
				}
				Bukkit.broadcastMessage(ChatColor.GRAY + "a wild staff member " + ChatColor.GREEN + p.getDisplayName() + ChatColor.GRAY + " has been appeared!");
				xp.unvanish();
			}
		} else {
			p.sendMessage(ChatColor.RED + "you are allready are unvanished so you can't fake join, use /vanish fakequit instead or /vanish");
		}
	}

	public static String sendJoinMessage(Player p ) {
		WorldGuardPlugin wg = (WorldGuardPlugin) Bukkit.getPluginManager().getPlugin("WorldGuard");
		if(p.getPlayer().hasPermission("xEssentials.isStaff")) {
			for(ProtectedRegion region : wg.getRegionManager(p.getPlayer().getWorld()).getApplicableRegions(p.getPlayer().getLocation())) {
				if(region.getFlag(DefaultFlag.MOB_SPAWNING) == State.DENY) {
					String message;
					if(p.getName().equalsIgnoreCase("xize")) {
						message = ChatColor.GRAY + "a safe Developer of xEssentials " + ChatColor.GREEN + p.getDisplayName() + ChatColor.GRAY + " has been appeared!";	
					} else {
						message = ChatColor.GRAY + "a safe staff member " + ChatColor.GREEN + p.getDisplayName() + ChatColor.GRAY + " has been appeared!";	
					}
					return message;
				}
			}
			String message;
			if(p.getName().equalsIgnoreCase("xize")) {
				message = ChatColor.GRAY + "a wild Developer of xEssentials " + ChatColor.GREEN + p.getDisplayName() + ChatColor.GRAY + " has been appeared!";	
			} else {
				message = ChatColor.GRAY + "a wild staff member " + ChatColor.GREEN + p.getDisplayName() + ChatColor.GRAY + " has been appeared!";	
			}
			return message;
		} else {
			for(ProtectedRegion region : wg.getRegionManager(p.getPlayer().getWorld()).getApplicableRegions(p.getPlayer().getLocation())) {
				if(region.getFlag(DefaultFlag.MOB_SPAWNING) == State.DENY) {
					String message = ChatColor.GRAY + "a safe " + ChatColor.GREEN + p.getDisplayName() + ChatColor.GRAY + " has been appeared!";
					return message;
				}
			}
			String message = ChatColor.GRAY + "a wild " + ChatColor.GREEN + p.getDisplayName() + ChatColor.GRAY + " has been appeared!";
			return message;
		}
	}

	public static String sendQuitMessage(Player p) {
		WorldGuardPlugin wg = (WorldGuardPlugin) Bukkit.getPluginManager().getPlugin("WorldGuard");
		if(p.getPlayer().hasPermission("xEssentials.isStaff")) {
			for(ProtectedRegion region : wg.getRegionManager(p.getPlayer().getWorld()).getApplicableRegions(p.getPlayer().getLocation())) {
				if(region.getFlag(DefaultFlag.MOB_SPAWNING) == State.DENY) {
					String message = ChatColor.RED + "Whoosh!" + ChatColor.GRAY + " staff member " + ChatColor.GREEN + p.getDisplayName() + ChatColor.GRAY + " has left the game safely!";
					return message;
				}
			}
			String message = ChatColor.RED + "Whoosh!" + ChatColor.GRAY + " staff member " + ChatColor.GREEN + p.getDisplayName() + ChatColor.GRAY + " has left the game in wild!";
			return message;
		} else {
			for(ProtectedRegion region : wg.getRegionManager(p.getPlayer().getWorld()).getApplicableRegions(p.getPlayer().getLocation())) {
				if(region.getFlag(DefaultFlag.MOB_SPAWNING) == State.DENY) {
					String message = ChatColor.RED + "Whoosh! " + ChatColor.GREEN + p.getDisplayName() + ChatColor.GRAY + " has left the game safely!";
					return message;
				}
			}
			String message = ChatColor.RED + "Whoosh! " + ChatColor.GREEN + p.getDisplayName() + ChatColor.GRAY + " has left the game in wild!";
			return message;
		}
	}

	public static boolean isInRegion(Location loc) {
		WorldGuardPlugin wg = (WorldGuardPlugin) Bukkit.getServer().getPluginManager().getPlugin("WorldGuard");
		for(ProtectedRegion region : wg.getRegionManager(loc.getWorld()).getApplicableRegions(loc)) {
			if(region.getFlag(DefaultFlag.MOB_SPAWNING) == State.DENY || region.getFlag(DefaultFlag.PVP) == State.DENY) {
				//player has entered
				return true;
			}
		}
		return false;
	}


	public static void sendRegionMessage(Player p, Chunk from, Chunk to) {
		if(from.getX() != to.getX() || from.getZ() != to.getZ()) {
			if(!isInRegion(from.getBlock(1, 1, 1).getLocation()) && isInRegion(to.getBlock(1, 1, 1).getLocation())) {
				p.getPlayer().sendMessage(ChatColor.GOLD + ".oO___[Entering safe zone]___Oo.");
			} else if(isInRegion(from.getBlock(1, 1, 1).getLocation()) && !isInRegion(to.getBlock(1, 1, 1).getLocation())) {
				p.getPlayer().sendMessage(ChatColor.GOLD + ".oO___[Leaving safe zone]___Oo.");
			}
		}
	}
*/
}
