package org.startinglineup.component;

import java.util.Iterator;
import java.util.List;

import org.startinglineup.utils.Formatter;

import java.util.ArrayList;

public class Innings {

    public static final int REGULATION_INNINGS = 9;
    private List<Inning> innings;

    public Innings() {
        innings = new ArrayList<Inning>();
        for (int x=0; x < REGULATION_INNINGS; x++) {
            innings.add(new Inning());
        }
    }

    public List<Inning> getInnings() {
        return innings;
    } 

    public void addExtraInning() {
        innings.add(new Inning());
    }

    public List<HalfInning> getTopHalfInnings() {
        return getTopOrBottomInnings(HalfInning.Half.TOP);
    }

    public List<HalfInning> getBottomHalfInnings() {
        return getTopOrBottomInnings(HalfInning.Half.BOTTOM);
    }

    public static List<Run> getRunsForHalfInnings(List<HalfInning> halfInnings) {
        Iterator<HalfInning> i = halfInnings.iterator();
        List<Run> runs = new ArrayList<Run>();
        while (i.hasNext()) {
            HalfInning halfInning = (HalfInning) i.next();
            runs.addAll(halfInning.getRuns());
        }

        return runs;
    }

    public static List<Outcome> getHitsForHalfInnings(List<HalfInning> halfInnings) {
        Iterator<HalfInning> i = halfInnings.iterator();
        List<Outcome> hits = new ArrayList<Outcome>();
        while (i.hasNext()) {
            HalfInning halfInning = (HalfInning) i.next();
            hits.addAll(halfInning.getHits());
        }

        return hits;
    }

    private List<HalfInning> getTopOrBottomInnings(HalfInning.Half half) {
        List<HalfInning> rtnInnings = new ArrayList<HalfInning>();
        Iterator<Inning> i = innings.iterator();
        while (i.hasNext()) {
            Inning inning = (Inning) i.next();
            if (half == HalfInning.Half.TOP) {
                rtnInnings.add(inning.getTopHalf());
            } else {
                rtnInnings.add(inning.getBottomHalf());
            }
        }

        return rtnInnings;
    }

    private String getTopOrBottomInningsStr(HalfInning.Half half) {
        String returnStr = "";
        Iterator<Inning> i = innings.iterator();
        int runs = 0;
        int hits = 0;
        while (i.hasNext()) {
            Inning inning = (Inning) i.next();
            if (half.equals(HalfInning.Half.TOP)) {
                returnStr += inning.getTopHalf() + " | ";
                runs += inning.getTopHalf().getRuns().size();
                hits += inning.getTopHalf().getHits().size();
            } else {
                returnStr += inning.getBottomHalf() + " | ";
                runs += inning.getBottomHalf().getRuns().size();
                hits += inning.getBottomHalf().getHits().size();
            }
        }

        returnStr = returnStr + Formatter.getPaddedInt(runs,2,true) + 
            " | " + Formatter.getPaddedInt(hits,2,true);

        return returnStr;
    }

    public String toString() {
        return getTopOrBottomInningsStr(HalfInning.Half.TOP) + "\n" +
               getTopOrBottomInningsStr(HalfInning.Half.BOTTOM);
    }
} 
