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
public class TownMap {

    public float getLongitude() {
        return longitude;
    }

    public void setLongitude(float longitude) {
        this.longitude = longitude;
    }

    public float getLatitude() {
        return latitude;
    }

    public void setLatitude(float latitude) {
        this.latitude = latitude;
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

    public String getPostcode() {
        return postcode;
    }

    public void setPostcode(String postcode) {
        this.postcode = postcode;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
    
    public TownMap(BindingSet bs) {
        try {
            this.postcode = bs.getBinding("postcode").getValue().stringValue();
            this.name = bs.getBinding("name").getValue().stringValue();
            this.longitude = Float.parseFloat(bs.getBinding("longitude").getValue().stringValue());
            this.latitude = Float.parseFloat(bs.getBinding("latitude").getValue().stringValue());
            this.voteFor = Integer.parseInt(bs.getBinding("voteFor").getValue().stringValue());
            this.voteAgainst = Integer.parseInt(bs.getBinding("voteAgainst").getValue().stringValue());
        } catch (NumberFormatException | NullPointerException e) {
            throw e;
        }
    }
    
    private float longitude;
    private float latitude;
    private int voteFor;
    private int voteAgainst;
    private String postcode;
    private String name;
    
}
