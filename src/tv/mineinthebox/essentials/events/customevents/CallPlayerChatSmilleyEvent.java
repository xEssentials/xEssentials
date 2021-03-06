package tv.mineinthebox.essentials.events.customevents;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerChatEvent;

import tv.mineinthebox.essentials.xEssentials;

@SuppressWarnings("deprecation")
public class CallPlayerChatSmilleyEvent implements Listener {
	
	private final xEssentials pl;
	
	public CallPlayerChatSmilleyEvent(xEssentials pl) {
		this.pl = pl;
	}

	@EventHandler
	public void onChatSmilleyEvent(PlayerChatEvent e) {
		List<String> SmilleyList = new ArrayList<String>();
		String[] args = e.getMessage().split(" ");
		for(int i = 0; i < args.length; i++) {
			if(args[i].equalsIgnoreCase(":D")) {
				SmilleyList.add(args[i]);
			} else if(args[i].equalsIgnoreCase(":@")) {
				SmilleyList.add(args[i]);
			} else if(args[i].equalsIgnoreCase("<3")) {
				SmilleyList.add(args[i]);
			} else if(args[i].equalsIgnoreCase(":)")) {
				SmilleyList.add(args[i]);
			}
		}
		String[] SmilleyNames = SmilleyList.toArray(new String[SmilleyList.size()]);
		if(SmilleyNames.length > 0) {
			Bukkit.getPluginManager().callEvent(new PlayerChatSmilleyEvent(e, SmilleyNames, pl));
		}
	}

}
