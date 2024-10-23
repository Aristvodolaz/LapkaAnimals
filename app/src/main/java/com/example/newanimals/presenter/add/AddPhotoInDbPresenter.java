package com.example.newanimals.presenter.add;

import android.net.Uri;

import com.example.newanimals.utils.SPHelper;
import com.example.newanimals.utils.UploadPhotoStorageUtil;
import com.example.newanimals.view.add.AddPhotoView;

public class AddPhotoInDbPresenter {
    private AddPhotoView view;

    public AddPhotoInDbPresenter(AddPhotoView view) {
        this.view = view;
    }

    public void uploadPhotoInDb(Uri img, String file){
        UploadPhotoStorageUtil.uploadePhoto(img, file).subscribe(
                ()-> {view.successMessage("Фотография загружена");
                    SPHelper.AdsHelper.setPhotoAnimals(SPHelper.getPhotoUrlForDownload());
                },
                error ->{
                    view.message(error.getLocalizedMessage());
                }
        );
    }
}
