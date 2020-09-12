
package com.canonal.imbkhissesenetleriveendeksler.model.detail;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class GraphicDatum {

    @SerializedName("day")
    @Expose
    private Integer day;
    @SerializedName("value")
    @Expose
    private Double value;

    public Integer getDay() {
        return day;
    }

    public void setDay(Integer day) {
        this.day = day;
    }

    public Double getValue() {
        return value;
    }

    public void setValue(Double value) {
        this.value = value;
    }

}
