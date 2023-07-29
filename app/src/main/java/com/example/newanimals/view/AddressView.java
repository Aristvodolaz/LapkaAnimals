package com.example.newanimals.view;

import com.example.newanimals.network.response.DaDataResponse;

import java.util.List;

public interface AddressView {

    void getAddress(List<DaDataResponse.DaDataResult> data);
    void errorMessage(String error);
}
