package org.startinglineup.data;

import java.util.HashMap;

import org.startinglineup.component.Player;
import org.startinglineup.component.Player.Handed;

class PlayerMap {
	
	protected HashMap<String,Player> playerMap;
	
	protected PlayerMap() {
		super();
		playerMap = new HashMap<String,Player>();
	}
	
	public void add(Player player) {
		playerMap.put(getKey(player), player);
	}
	
	public void replace(Player player) throws PlayerNotFoundException {
		try {
			playerMap.replace(getKey(player), player);
		} catch (Exception e) {
			throw new PlayerNotFoundException("Cannot find player: " + player.getFormattedName());
		}
	}
	
	public Player get(Player player) throws PlayerNotFoundException {
		try {
			return playerMap.get(getKey(player));
		} catch (Exception e) {
			throw new PlayerNotFoundException("Cannot find player: " + getKey(player), e);
		}
	}
	
	public Player get(String lastname, String firstname) throws PlayerNotFoundException {
		try {
			return playerMap.get(getKey(lastname, firstname));
		} catch (Exception e) {
			throw new PlayerNotFoundException("Cannot find player: " + getKey(lastname, firstname), e);
		}
	}
	
	private String getKey(String lastname, String firstname) {
		// Create a temporary player object, then return the formatted name
		Player player = new Player(lastname, firstname, Handed.RIGHT, null);
		return player.getFormattedName();
	}

	private String getKey(Player player) {
		return player.getFormattedName();
	}
}
