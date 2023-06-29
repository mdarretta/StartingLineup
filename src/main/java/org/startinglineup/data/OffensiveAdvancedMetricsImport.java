package org.startinglineup.data;

import org.startinglineup.Properties;
import org.startinglineup.StartingLineupException;
import org.startinglineup.component.Batter;
import org.startinglineup.component.Player;
import org.startinglineup.component.UniqueComponent;

public class OffensiveAdvancedMetricsImport extends AdvancedMetricsImport {
	
	public OffensiveAdvancedMetricsImport(String pathname) throws FileImportException {
		super(pathname);
	}

	protected void instantiateTemplate() throws FileImportException {
        this.reader = new TemplateReader(
        		Properties.getInstance().get(
        				Properties.OFFENSIVE_ADVANCED_METRICS_IMPORT_TEMPLATE_PROP));
        reader.run();		
	}

	protected boolean instantiateTarget(UniqueComponent target) throws StartingLineupException {
   		return target instanceof Batter;
	}
	
	protected Player getPlayerFromMap(Player player) throws PlayerNotFoundException {
		return BatterMap.getInstance().get(player);
	}
		
}
