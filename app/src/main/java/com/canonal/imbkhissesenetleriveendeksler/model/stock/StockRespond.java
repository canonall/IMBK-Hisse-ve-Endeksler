
package com.canonal.imbkhissesenetleriveendeksler.model.stock;

import java.util.List;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class StockRespond {

    @SerializedName("stocks")
    @Expose
    private List<Stock> stocks = null;
    @SerializedName("status")
    @Expose
    private Status status;

    public List<Stock> getStocks() {
        return stocks;
    }

    public void setStocks(List<Stock> stocks) {
        this.stocks = stocks;
    }

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }

}
