package br.com.dito.ditosdk.model;

import br.com.dito.ditosdk.interfaces.DitoSDKCallback;

/**
 * Created by gabriel.araujo on 01/04/15.
 */
public class DitoCredentials {

    private String id;
    private String facebookID;
    private String googlePlusID;
    private String twitterID;
    private String reference;


    /**
     * @param id
     * @param facebookID
     * @param googlePlusID
     * @param twitterID
     * @param reference
     * @return void
     */
    public DitoCredentials(String id, String facebookID, String googlePlusID, String twitterID, String reference) {
        this.id = id;
        this.facebookID = facebookID;
        this.googlePlusID = googlePlusID;
        this.twitterID = twitterID;
        this.reference = reference;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getFacebookID() {
        return facebookID;
    }

    public void setFacebookID(String facebookID) {
        this.facebookID = facebookID;
    }

    public String getGooglePlusID() {
        return googlePlusID;
    }

    public void setGooglePlusID(String googlePlusID) {
        this.googlePlusID = googlePlusID;
    }

    public String getTwitterID() {
        return twitterID;
    }

    public void setTwitterID(String twitterID) {
        this.twitterID = twitterID;
    }

    public String getReference() {
        return reference;
    }

    public void setReference(String reference) {
        this.reference = reference;
    }
}
