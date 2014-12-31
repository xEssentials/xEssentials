package tv.mineinthebox.essentials.minigames;

import tv.mineinthebox.essentials.interfaces.XPlayer;

public interface MinigameArena {
	
	public MinigameType getType();
	
	public String getName();

	public void remove();
	
	public void addPlayer(XPlayer xp);
	
	public void removePlayer(XPlayer xp);
	
	public boolean isFull();
	
	public void reset();
}
