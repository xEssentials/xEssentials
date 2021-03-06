package tv.mineinthebox.essentials.configurations;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import org.bukkit.ChatColor;
import org.bukkit.configuration.file.FileConfiguration;

import tv.mineinthebox.essentials.Configuration;
import tv.mineinthebox.essentials.enums.ConfigType;

public class RulesConfig extends Configuration {
	
	public RulesConfig(File f, FileConfiguration con) {
		super(f, con);
		ArrayList<String> list = new ArrayList<String>();
		list.add("1. No griefing, also random non generated blocks or farms are seen as considered griefing.");
		list.add("2. No asking for operator or any other rank, it's on ours to decide whenever we want.");
		list.add("3. Do not cheat, hacked clients, dupes, spambots, xray mods or xray texture packs are forbidden.");
		list.add("4. addvertising is forbidden, it's a silly thing, also it means you are not allowed to addvertise our server on others");
		list.add("5. swearing is allowed, but limited you are not allowed to offend players or talk about racism/religions or the server it self");
		list.add("6. whining is not allowed, asking items, or complaining about warps, all are falling under the justification of whining use the forums instead ;)");
		preconfig.put("rules.prefix", "&2[Rules]");
		preconfig.put("rules.suffix", "&7");
		preconfig.put("rules.messages", list);
	}

	/**
	 * returns the prefix of the rules
	 * 
	 * @author xize
	 * @return String
	 */
	public String getPrefix() {
		return ChatColor.translateAlternateColorCodes('&', con.getString("rules.prefix"));
	}
	
	/**
	 * returns the suffix of the rules
	 * 
	 * @author xize
	 * @return String
	 */
	public String getSuffix() {
		return ChatColor.translateAlternateColorCodes('&', con.getString("rules.suffix"));
	}
	
	/**
	 * returns a List with rules
	 * 
	 * @author xize
	 * @return List<String>
	 */
	public List<String> getRules() {
		return con.getStringList("rules.messages");
	}

	@Override
	public String getName() {
		return getType().name();
	}

	@Override
	public ConfigType getType() {
		return ConfigType.RULES;
	}
	
	@Override
	public boolean hasAlternativeReload() {
		return false;
	}
}
