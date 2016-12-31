/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cvut.fit.swe.models;

import org.eclipse.rdf4j.query.BindingSet;

/**
 *
 * @author janprasil
 */
public class TownDifference {

    public String getTown() {
        return town;
    }

    public void setTown(String town) {
        this.town = town;
    }

    public int getVoteFor() {
        return voteFor;
    }

    public void setVoteFor(int voteFor) {
        this.voteFor = voteFor;
    }

    public int getVoteAgainst() {
        return voteAgainst;
    }

    public void setVoteAgainst(int voteAgainst) {
        this.voteAgainst = voteAgainst;
    }
    
    
    public TownDifference(BindingSet bs) {
        
        try {
            this.town = bs.getBinding("town").getValue().stringValue();
            this.voteFor = Integer.parseInt(bs.getBinding("voteFor").getValue().stringValue());
            this.voteAgainst = Integer.parseInt(bs.getBinding("voteAgainst").getValue().stringValue());
        } catch (NumberFormatException | NullPointerException e) {
            throw e;
        }
        
    }
    
    private String town;
    private int voteFor;
    private int voteAgainst;
    
}
