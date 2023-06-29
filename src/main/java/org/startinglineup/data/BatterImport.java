package org.startinglineup.data;

import org.startinglineup.Properties;
import org.startinglineup.StartingLineupException;
import org.startinglineup.component.Batter;
import org.startinglineup.component.UniqueComponent;

public class BatterImport extends TemplattedFileImport {
	
	private Batter batter;
	
	public BatterImport(String pathname) throws FileImportException {
		super(pathname);
	}
	
	/**
	 * Processes the batter import file, with an option to normalize batter
	 * stats for batters who don't meet the minimum number of at bats.
	 * @param normalize True if normalizing batter stats for ineligible batters.
	 * @throws FileImportException Error processing the import file.
	 */
	public void run(boolean normalize) throws FileImportException {
		super.run();
		if (normalize) {
			try {
				BatterMap.getInstance().normalize();
			} catch (Exception e) {
				throw new FileImportException(e);
			}
		}
	}
	
	protected void instantiateTemplate() throws FileImportException {
      this.reader = new TemplateReader(Properties.getInstance().get(
    		  Properties.BATTER_IMPORT_TEMPLATE_PROP));
        reader.run();		
	}

	protected boolean instantiateTarget(UniqueComponent target) throws StartingLineupException {
		boolean rtnBoolean = false;
   		if (target instanceof Batter) {
			batter = (Batter) target;
			rtnBoolean = true;
		}
		
		return rtnBoolean;
	}
	
	protected void processTarget(UniqueComponent target) throws StartingLineupException {
		try {
    		batter = (Batter) reader.getCurrentTarget();
        	batter = (Batter) TemplateReader.parsePlayerName(batter);
        	
			// @todo Find a smarter way of determining if this is a pitcher (i.e., don't normalize)
			boolean normalize = !("Spot,Pitchers".equals(batter.getLastname() + "," + batter.getFirstname()));

			// Set number of singles (usually not part of metrics map
			batter.setSingles(batter.getHits()-batter.getDoubles()-batter.getTriples()-batter.getHomeRuns());
			
			// Reinstantiate the batter object to set the normalize field
			batter = new Batter(batter.getLastname(), batter.getFirstname(), batter.getHanded(), batter.getAtBats(), 
					batter.getWalks(), batter.getHbp(), batter.getSingles(), batter.getDoubles(), batter.getTriples(), 
					batter.getHomeRuns());
                        batter.setNormalize(normalize);
			
			BatterMap.getInstance().add(batter);
		} catch (IllegalAccessException iae) {
			throw new StartingLineupException(iae);
		}
	}
}
