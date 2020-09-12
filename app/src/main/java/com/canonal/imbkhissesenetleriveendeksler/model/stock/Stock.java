
package com.canonal.imbkhissesenetleriveendeksler.model.stock;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Stock implements Parcelable {

    @SerializedName("id")
    @Expose
    private Integer id;
    @SerializedName("isDown")
    @Expose
    private Boolean isDown;
    @SerializedName("isUp")
    @Expose
    private Boolean isUp;
    @SerializedName("bid")
    @Expose
    private Double bid;
    @SerializedName("difference")
    @Expose
    private Double difference;
    @SerializedName("offer")
    @Expose
    private Double offer;
    @SerializedName("price")
    @Expose
    private Double price;
    @SerializedName("volume")
    @Expose
    private Double volume;
    @SerializedName("symbol")
    @Expose
    private String symbol;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Boolean getIsDown() {
        return isDown;
    }

    public void setIsDown(Boolean isDown) {
        this.isDown = isDown;
    }

    public Boolean getIsUp() {
        return isUp;
    }

    public void setIsUp(Boolean isUp) {
        this.isUp = isUp;
    }

    public Double getBid() {
        return bid;
    }

    public void setBid(Double bid) {
        this.bid = bid;
    }

    public Double getDifference() {
        return difference;
    }

    public void setDifference(Double difference) {
        this.difference = difference;
    }

    public Double getOffer() {
        return offer;
    }

    public void setOffer(Double offer) {
        this.offer = offer;
    }

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

    public Double getVolume() {
        return volume;
    }

    public void setVolume(Double volume) {
        this.volume = volume;
    }

    public String getSymbol() {
        return symbol;
    }

    public void setSymbol(String symbol) {
        this.symbol = symbol;
    }


    protected Stock(Parcel in) {
        id = in.readByte() == 0x00 ? null : in.readInt();
        byte isDownVal = in.readByte();
        isDown = isDownVal == 0x02 ? null : isDownVal != 0x00;
        byte isUpVal = in.readByte();
        isUp = isUpVal == 0x02 ? null : isUpVal != 0x00;
        bid = in.readByte() == 0x00 ? null : in.readDouble();
        difference = in.readByte() == 0x00 ? null : in.readDouble();
        offer = in.readByte() == 0x00 ? null : in.readDouble();
        price = in.readByte() == 0x00 ? null : in.readDouble();
        volume = in.readByte() == 0x00 ? null : in.readDouble();
        symbol = in.readString();
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        if (id == null) {
            dest.writeByte((byte) (0x00));
        } else {
            dest.writeByte((byte) (0x01));
            dest.writeInt(id);
        }
        if (isDown == null) {
            dest.writeByte((byte) (0x02));
        } else {
            dest.writeByte((byte) (isDown ? 0x01 : 0x00));
        }
        if (isUp == null) {
            dest.writeByte((byte) (0x02));
        } else {
            dest.writeByte((byte) (isUp ? 0x01 : 0x00));
        }
        if (bid == null) {
            dest.writeByte((byte) (0x00));
        } else {
            dest.writeByte((byte) (0x01));
            dest.writeDouble(bid);
        }
        if (difference == null) {
            dest.writeByte((byte) (0x00));
        } else {
            dest.writeByte((byte) (0x01));
            dest.writeDouble(difference);
        }
        if (offer == null) {
            dest.writeByte((byte) (0x00));
        } else {
            dest.writeByte((byte) (0x01));
            dest.writeDouble(offer);
        }
        if (price == null) {
            dest.writeByte((byte) (0x00));
        } else {
            dest.writeByte((byte) (0x01));
            dest.writeDouble(price);
        }
        if (volume == null) {
            dest.writeByte((byte) (0x00));
        } else {
            dest.writeByte((byte) (0x01));
            dest.writeDouble(volume);
        }
        dest.writeString(symbol);
    }

    @SuppressWarnings("unused")
    public static final Parcelable.Creator<Stock> CREATOR = new Parcelable.Creator<Stock>() {
        @Override
        public Stock createFromParcel(Parcel in) {
            return new Stock(in);
        }

        @Override
        public Stock[] newArray(int size) {
            return new Stock[size];
        }
    };
}
