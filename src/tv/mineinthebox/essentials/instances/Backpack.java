package tv.mineinthebox.essentials.instances;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.configuration.InvalidConfigurationException;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import tv.mineinthebox.essentials.xEssentials;

public class Backpack {
	
	private final File f;
	private final FileConfiguration con;
	private final xEssentials pl;
	
	public Backpack(File f, FileConfiguration con, xEssentials pl) {
		this.f = f;
		this.con = con;
		this.pl = pl;
	}
	
	/**
	 * @author xize
	 * @param returns the unique id where this backpack for is created.
	 * @return String
	 */
	public String getUniqueId() {
		update();
		return con.getString("id");
	}
	
	/**
	 * @author xize
	 * @param returns the size of the backpack
	 * @return int
	 */
	public int getAmountContents() {
		update();
		return getContents().length;
	}
	
	/**
	 * @author xize
	 * @param returns the holder of this backpack.
	 * @return ItemStack
	 */
	public ItemStack getBackPackItem() {
		update();
		ItemStack item = (ItemStack)con.get("backpack-item");
		ItemMeta meta = item.getItemMeta();
		List<String> lores = meta.getLore();
		lores.set(6, "amount: "+ getSize());
		meta.setLore(lores);
		item.setItemMeta(meta);
		return item;
	}
	
	/**
	 * @author xize
	 * @param returns the inventory.
	 * @return Inventory
	 */
	public Inventory getInventory() {
		Inventory inv = Bukkit.createInventory(null, 36, getUniqueId());
		inv.setContents(getContents());
		return inv;
	}
	
	public int getSize() {
		int num = 0;
		for(ItemStack stack : getContents()) {
			if(stack != null && stack instanceof ItemStack) {
				num++;	
			}
		}
		return num;
	}
	
	/**
	 * @author xize
	 * @param returns safely the contents of the inventory.
	 * @return ItemStack[]
	 */
	@SuppressWarnings("unchecked")
	public ItemStack[] getContents() {
		update();
		return ((List<ItemStack>)con.get("contents")).toArray(new ItemStack[0]);
	}
	
	/**
	 * @author xize
	 * @param stacks - the item stacks
	 * @param sets the new items in the inventory.
	 */
	public void setContents(ItemStack[] stacks) {
		con.set("contents", stacks);
		try {
			con.save(f);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		update();
	}
	
	public void remove() {
		if(pl.getManagers().getBackPackManager().isBackpack(this)) { 
			pl.getManagers().getBackPackManager().removeBackpack(this);
		}
		f.delete();
	}
	
	public void update() {
		try {
			con.load(f);
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (InvalidConfigurationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
