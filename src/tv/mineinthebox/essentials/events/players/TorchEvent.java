package tv.mineinthebox.essentials.events.players;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.bukkit.block.BlockState;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerKickEvent;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.event.player.PlayerQuitEvent;

import tv.mineinthebox.essentials.xEssentials;
import tv.mineinthebox.essentials.interfaces.XPlayer;

public class TorchEvent implements Listener {
	
	private final Map<String, LinkedList<BlockState>> list = new HashMap<String, LinkedList<BlockState>>();
	
	private final xEssentials pl;
	
	public TorchEvent(xEssentials pl) {
		this.pl = pl;
	}
	
	@SuppressWarnings("deprecation")
	@EventHandler
	public void onTorchMove(PlayerMoveEvent e) {
		if(pl.getManagers().getPlayerManager().isOnline(e.getPlayer().getName())) {
			XPlayer xp = pl.getManagers().getPlayerManager().getPlayer(e.getPlayer().getName());
			if(xp.isTorch()) {
				if(e.getFrom().distanceSquared(e.getTo()) > 0) {
					if(e.getPlayer().getItemInHand().getType() == Material.TORCH) {
						Block block = e.getPlayer().getLocation().getBlock().getRelative(BlockFace.DOWN);
						if(block.getType() == Material.AIR || block.getType() == Material.WATER || block.getType() == Material.STATIONARY_WATER || block.getType() == Material.LAVA || block.getType() == Material.STATIONARY_LAVA || block.getType() == Material.LONG_GRASS || block.getType() == Material.RED_ROSE || (block.getType().isTransparent() && !block.getType().isSolid())) {
							return;
						}
						e.getPlayer().sendBlockChange(block.getLocation(), Material.GLOWSTONE, block.getData());
						getTrailList(e.getPlayer()).add(block.getState());
						removeGlow(e.getPlayer());
					}
				}
			}
		}
	}
	
	@EventHandler
	public void onTorchQuit(PlayerQuitEvent e) {
		if(list.containsKey(e.getPlayer().getName())) {
			List<BlockState> alist = list.get(e.getPlayer().getName());
			alist.clear();
			list.remove(e.getPlayer().getName());
		}
	}
	
	@EventHandler
	public void onTorchQuit(PlayerKickEvent e) {
		if(list.containsKey(e.getPlayer().getName())) {
			List<BlockState> alist = list.get(e.getPlayer().getName());
			alist.clear();
			list.remove(e.getPlayer().getName());
		}
	}
	
	private LinkedList<BlockState> getTrailList(Player p) {
		LinkedList<BlockState> blocklist = list.get(p.getName());
		if(blocklist == null) {
			blocklist = new LinkedList<BlockState>();
			list.put(p.getName(), blocklist);
		}
		return blocklist;
	}

	private void removeGlow(Player p) {
		LinkedList<BlockState> blocklist = list.get(p.getName());
		if(blocklist.size() > 10) {
			BlockState old = blocklist.removeFirst();
			old.update();
		}
	}

}
