package org.startinglineup.simulator;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;

public class Series {

    private Collection<Game> games;

    public Series(Collection<Game> games) {
        this.games = games;
    }

    public Collection<Game> getGames() {
        return games;
    }

    public int getSeriesLength() {
        return games.size();
    }

    public Collection<GameStats> play() {
   
    	Collection<GameStats> gameStats = new ArrayList<GameStats>();
        Iterator<Game> i = games.iterator();
        Game game = null;
        while (i.hasNext()) {
            game = (Game) i.next();
            gameStats.add(game.play());
        }
        
        return gameStats;
    }
}
