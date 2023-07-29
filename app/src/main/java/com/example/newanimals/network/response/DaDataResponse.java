package com.example.newanimals.network.response;
import com.google.gson.annotations.SerializedName;
import java.util.List;

public class DaDataResponse {
    @SerializedName("suggestions") List<DaDataResult> suggestions;

    public List<DaDataResult> getSuggestions() {
        return suggestions;
    }

    public static class DaDataResult {
        @SerializedName("value") String value;
        @SerializedName("unrestricted_value") String unrestricted_value;
        @SerializedName("data") DaDataResultData data;

        public String getValue() {
            return value;
        }

        public String getUnrestricted_value() {
            return unrestricted_value;
        }

        public DaDataResultData getData() {
            return data;
        }

        public static class DaDataResultData {
            @SerializedName("postal_code") String postal_code;
            @SerializedName("country") String country;
            @SerializedName("city") String city;

            public String getPostal_code() {
                return postal_code;
            }

            public String getCountry() {
                return country;
            }

            public String getCity() {
                return city;
            }
        }
    }

}
