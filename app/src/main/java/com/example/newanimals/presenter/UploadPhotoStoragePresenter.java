package com.example.newanimals.presenter;

import android.net.Uri;

import com.example.newanimals.utils.UploadPhotoStorageUtil;
import com.example.newanimals.view.UploadPhotoStorageView;

public class UploadPhotoStoragePresenter {
    private UploadPhotoStorageView view;

    public UploadPhotoStoragePresenter( UploadPhotoStorageView view) {
        this.view = view;
    }
    public void uploadPhoto(Uri img, String file){
        UploadPhotoStorageUtil.uploadePhoto(img, file).subscribe(
                ()-> {view.onUploadSuccess("Фотография загружена");},
                error ->{
                    view.onUploadError(error.getLocalizedMessage());
                }
        );
    }

}
