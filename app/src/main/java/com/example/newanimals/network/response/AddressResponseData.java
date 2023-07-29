package com.example.newanimals.network.response;

import com.google.gson.annotations.SerializedName;

public class AddressResponseData {
    @SerializedName("source") String source;
    @SerializedName("result") String result;
    @SerializedName("region_with_type") String region;

    public String getSource() {
        return source;
    }

    public void setSource(String source) {
        this.source = source;
    }

    public String getResult() {
        return result;
    }

    public void setResult(String result) {
        this.result = result;
    }

    public String getRegion() {
        return region;
    }

    public void setRegion(String region) {
        this.region = region;
    }
}
