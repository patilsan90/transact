package com.algo.transact.home.offers;

/**
 * Created by Sandeep Patil on 16/4/17.
 */

public class OfferItem {

    private String offer_id;
    private String offer_name;

    public OfferItem(String offer_id, String offer_name) {
        this.offer_id = offer_id;
        this.offer_name = offer_name;
    }

    public String getOffer_id() {
        return offer_id;
    }

    public String getOffer_name() {
        return offer_name;
    }
}
