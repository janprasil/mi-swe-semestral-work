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
public class TownResult {

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getArea() {
        return area;
    }

    public void setArea(String area) {
        this.area = area;
    }

    public float getVoteRatio() {
        return voteRatio;
    }

    public void setVoteRatio(float voteRatio) {
        this.voteRatio = voteRatio;
    }

    public int getRank() {
        return rank;
    }

    public void setRank(int rank) {
        this.rank = rank;
    }

    public TownResult(BindingSet bs) {
        try {
            this.name = bs.getBinding("city").getValue().stringValue();
            this.area = bs.getBinding("area").getValue().stringValue();
            this.voteRatio = Float.parseFloat(bs.getBinding("voteRatio").getValue().stringValue());
            this.rank = Integer.parseInt(bs.getBinding("e_rank").getValue().stringValue());
        } catch (NumberFormatException | NullPointerException e) {
            throw e;
        }
    }
    
    public TownResult() {
        
    }

    private String name;
    private String area;
    private float voteRatio;
    private int rank;

}
