
package com.canonal.imbkhissesenetleriveendeksler.model.stock;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Status implements Parcelable {

    @SerializedName("isSuccess")
    @Expose
    private Boolean isSuccess;
    @SerializedName("error")
    @Expose
    private Error error;

    public Boolean getIsSuccess() {
        return isSuccess;
    }

    public void setIsSuccess(Boolean isSuccess) {
        this.isSuccess = isSuccess;
    }

    public Error getError() {
        return error;
    }

    public void setError(Error error) {
        this.error = error;
    }


    protected Status(Parcel in) {
        byte isSuccessVal = in.readByte();
        isSuccess = isSuccessVal == 0x02 ? null : isSuccessVal != 0x00;
        error = (Error) in.readValue(Error.class.getClassLoader());
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        if (isSuccess == null) {
            dest.writeByte((byte) (0x02));
        } else {
            dest.writeByte((byte) (isSuccess ? 0x01 : 0x00));
        }
        dest.writeValue(error);
    }

    @SuppressWarnings("unused")
    public static final Parcelable.Creator<Status> CREATOR = new Parcelable.Creator<Status>() {
        @Override
        public Status createFromParcel(Parcel in) {
            return new Status(in);
        }

        @Override
        public Status[] newArray(int size) {
            return new Status[size];
        }
    };
}
