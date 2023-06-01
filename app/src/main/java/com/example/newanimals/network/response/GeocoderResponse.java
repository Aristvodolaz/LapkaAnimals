package com.example.newanimals.network.response;

import com.google.gson.annotations.SerializedName;

public class GeocoderResponse {
    @SerializedName("response") ResponseData response;

    public ResponseData getResponse() {
        return response;
    }

    public class ResponseData {
        @SerializedName("GeoObjectCollection") GeoObjectCollection geoObjectCollection;

        public GeoObjectCollection getGeoObjectCollection() {
            return geoObjectCollection;
        }
    }
    public class GeoObjectCollection {
        @SerializedName("featureMember") FeatureMember featureMember;

        public FeatureMember getFeatureMember() {
            return featureMember;
        }
    }
    public class FeatureMember {
        @SerializedName("GeoObject") GeoObject geoObject;
        public String getFormatedAdress(){
            if(geoObject!=null && geoObject.metaDataProperty!=null &&
            geoObject.metaDataProperty.geocoderMetaData!=null &&
            geoObject.metaDataProperty.geocoderMetaData.address !=null){
                return geoObject.metaDataProperty.geocoderMetaData.address.formatted;
            } else return null;
        }

        public GeoObject getGeoObject() {
            return geoObject;
        }
    }

    public class GeoObject {
        @SerializedName("metaDataProperty")  MetaDataProperty metaDataProperty;

        public MetaDataProperty getMetaDataProperty() {
            return metaDataProperty;
        }
    }

    public class MetaDataProperty {
        @SerializedName("GeocoderMetaData") GeocoderMetaData geocoderMetaData;

        public GeocoderMetaData getGeocoderMetaData() {
            return geocoderMetaData;
        }
    }

    public class GeocoderMetaData {
        @SerializedName("Address") Address address;

        public Address getAddress() {
            return address;
        }
    }

    public class Address {
        @SerializedName("formatted") String formatted;

        public String getFormatted() {
            return formatted;
        }
    }
}
