package org.startinglineup.data;

import java.io.File;

import org.startinglineup.StartingLineupException;
import org.startinglineup.component.Player;
import org.startinglineup.component.UniqueComponent;
import org.startinglineup.component.AdvancedMetric;
import org.startinglineup.component.AdvancedMetrics;

public abstract class AdvancedMetricsImport extends PlayerImport {
	
	public AdvancedMetricsImport(File file) throws FileImportException {
		super(file);
	}

	protected void processTarget(UniqueComponent target) throws StartingLineupException {
		try {
    		if (target instanceof Player) {
    			Player player = (Player) target;
    			
    			addMetric(AdvancedMetric.MetricType.WAR, player, player.getWar());   			
    			addMetric(AdvancedMetric.MetricType.WAA, player, player.getWaa());
     		}
		} catch (Exception e) {
			throw new StartingLineupException(e);
		}
	}
        
    private void addMetric(AdvancedMetric.MetricType type, Player player, float metric) 
    		throws PlayerNotFoundException {
    	
		Player p = getPlayerFromMap(player);
		if (p == null) {
			return;
		}
		
		AdvancedMetric advancedMetric = new AdvancedMetric(type, metric);
		
		if (p.getAdvancedMetrics() == null) {
		    AdvancedMetrics advancedMetrics = new AdvancedMetrics();
		    p.setAdvancedMetrics(advancedMetrics);
		}
		
		p.getAdvancedMetrics().addMetric(type, advancedMetric);
		PitcherMap.getInstance().replace(p);
    }
    
    protected abstract Player getPlayerFromMap(Player player) throws PlayerNotFoundException;
}
