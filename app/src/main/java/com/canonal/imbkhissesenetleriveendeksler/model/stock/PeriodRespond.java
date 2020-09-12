
package com.canonal.imbkhissesenetleriveendeksler.model.stock;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.ArrayList;
import java.util.List;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class PeriodRespond implements Parcelable {

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


    protected PeriodRespond(Parcel in) {
        if (in.readByte() == 0x01) {
            stocks = new ArrayList<Stock>();
            in.readList(stocks, Stock.class.getClassLoader());
        } else {
            stocks = null;
        }
        status = (Status) in.readValue(Status.class.getClassLoader());
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        if (stocks == null) {
            dest.writeByte((byte) (0x00));
        } else {
            dest.writeByte((byte) (0x01));
            dest.writeList(stocks);
        }
        dest.writeValue(status);
    }

    @SuppressWarnings("unused")
    public static final Parcelable.Creator<PeriodRespond> CREATOR = new Parcelable.Creator<PeriodRespond>() {
        @Override
        public PeriodRespond createFromParcel(Parcel in) {
            return new PeriodRespond(in);
        }

        @Override
        public PeriodRespond[] newArray(int size) {
            return new PeriodRespond[size];
        }
    };
}
