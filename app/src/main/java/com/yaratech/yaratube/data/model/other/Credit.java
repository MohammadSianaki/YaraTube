
package com.yaratech.yaratube.data.model.other;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Credit {

    @SerializedName("price_unit")
    @Expose
    private String priceUnit;
    @SerializedName("cash")
    @Expose
    private int cash;
    @SerializedName("gem")
    @Expose
    private int gem;
    @SerializedName("coin")
    @Expose
    private int coin;

    /**
     * No args constructor for use in serialization
     * 
     */
    public Credit() {
    }

    /**
     * 
     * @param coin
     * @param cash
     * @param gem
     * @param priceUnit
     */
    public Credit(String priceUnit, int cash, int gem, int coin) {
        super();
        this.priceUnit = priceUnit;
        this.cash = cash;
        this.gem = gem;
        this.coin = coin;
    }

    public String getPriceUnit() {
        return priceUnit;
    }

    public void setPriceUnit(String priceUnit) {
        this.priceUnit = priceUnit;
    }

    public int getCash() {
        return cash;
    }

    public void setCash(int cash) {
        this.cash = cash;
    }

    public int getGem() {
        return gem;
    }

    public void setGem(int gem) {
        this.gem = gem;
    }

    public int getCoin() {
        return coin;
    }

    public void setCoin(int coin) {
        this.coin = coin;
    }

}
