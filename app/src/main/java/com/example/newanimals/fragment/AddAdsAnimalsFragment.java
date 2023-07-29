package com.example.newanimals.fragment;

import android.Manifest;
import android.app.AlertDialog;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.widget.Button;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import com.example.newanimals.R;
import com.example.newanimals.presenter.AddDataPresenter;
import com.example.newanimals.view.AddAdsView;

import butterknife.BindView;

public class AddAdsAnimalsFragment extends BaseFragment implements AddAdsView {
    public static final int REQUEST_IMAGE_CAPTURE = 1;
    public static final int REQUEST_IMAGE_PIKE = 3;
    public static final int PERMISSON_REQUEST_CODE = 2;
    @BindView(R.id.add_photo)
    Button btnAddPhoto;
    private AddDataPresenter presenter;

    @Override
    protected void initViews() {
        super.initViews();
        presenter = new AddDataPresenter(this);
        btnAddPhoto.setOnClickListener(v->{
            if(checkPermisson())
                showImageSOurceDialog();
            else requestPermission();
        });

    }

    private void showImageSOurceDialog() {
        String[] options = {"Камера", "Галерея"};
        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
        builder.setTitle("Выберите путь фотографии");
        builder.setItems(options, (dalog,witch) ->{
            if(witch == 0){
                dispatchTakePictureIntent();
            } else if (witch ==1) {
                openGallery();
            }
        });
    }

    private void openGallery() {
        Intent pickPhoto = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        startActivityForResult(pickPhoto, REQUEST_IMAGE_CAPTURE);
    }

    private void dispatchTakePictureIntent() {
        Intent takePicture = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        if(takePicture.resolveActivity(getContext().getPackageManager())!=null){
            startActivityForResult(takePicture, REQUEST_IMAGE_PIKE);
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == REQUEST_IMAGE_CAPTURE){
            Bundle bundle = data.getExtras();
            if(bundle !=null){
                Uri imageUri = data.getData();
                if(imageUri !=null)
                    presenter.uploadPhoto(imageUri, java.util.UUID.randomUUID().toString());
                else Toast.makeText(getContext(),"Ошибка загрузки фотографии!", Toast.LENGTH_SHORT).show();
            }
        } else if (requestCode == REQUEST_IMAGE_PIKE) {
            Uri img = data.getData();
            if(img!=null)
                presenter.uploadPhoto(img, java.util.UUID.randomUUID().toString());
            else Toast.makeText(getContext(),"Ошибка загрузки фотографии!", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if(requestCode == PERMISSON_REQUEST_CODE){
            if(grantResults.length>0 && grantResults[0] == PackageManager.PERMISSION_GRANTED)
                dispatchTakePictureIntent();
            else
                Toast.makeText(getContext(), "Разрешение использования камеры не получено!", Toast.LENGTH_SHORT).show();
        }
    }

    private boolean checkPermisson(){
        return ContextCompat.checkSelfPermission(getContext(), Manifest.permission.CAMERA)
                == PackageManager.PERMISSION_GRANTED;
    }
    private void requestPermission(){
        ActivityCompat.requestPermissions(getActivity(), new String[]{Manifest.permission.CAMERA},
                PERMISSON_REQUEST_CODE);
    }

    @Override
    protected int layoutId() {
        return R.layout.add_ads_animals_layout;
    }

    @Override
    public void sendMessage(String str) {
        Toast.makeText(getContext(), str, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onUploadSuccess(String str) {
        Toast.makeText(getContext(), str, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onUploadError(String localizedMessage) {
        Toast.makeText(getContext(), localizedMessage, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void getCoordinates(String lat, String lon) {

    }
}
