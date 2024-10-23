package com.example.newanimals.fragment.add_ads_service;

import android.Manifest;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import com.example.newanimals.R;
import com.example.newanimals.activity.AddAdsActivity;
import com.example.newanimals.db.AdsData;
import com.example.newanimals.dialog.AddreessDialog;
import com.example.newanimals.fragment.BaseFragment;
import com.example.newanimals.fragment.add_ads_animals.ChooseTypeAnimalFragment;
import com.example.newanimals.presenter.add.AddAdsServicePresenter;
import com.example.newanimals.presenter.add.AddPhotoInDbPresenter;
import com.example.newanimals.utils.SPHelper;
import com.example.newanimals.view.add.AddAdsServiceView;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

public class GetInfoForServiceFragment extends BaseFragment implements AddAdsServiceView {
    public static GetInfoForServiceFragment newInstance() {
        return new GetInfoForServiceFragment();
    }
    public static final int REQUEST_IMAGE_CAPTURE = 1;
    public static final int REQUEST_IMAGE_PIKE = 3;
    public static final int PERMISSON_REQUEST_CODE = 2;

    private AddAdsServicePresenter presenter;

    private ProgressDialog dialog;

    @BindView(R.id.name_ads)
    EditText nameAds;
    @BindView(R.id.opisanie)
    EditText opisanie;
    @BindView(R.id.price)
    EditText price;
    @BindView(R.id.address)
    EditText address;
    @BindView(R.id.back_arrow)
    ImageView backArrow;
    @BindView(R.id.add_photo)
    FrameLayout addPhoto;
    @BindView(R.id.next_btn)
    Button nextBtn;
    @BindView(R.id.camera_image)
    ImageView cameraImage;
    @BindView(R.id.visable_image) ImageView visableImage;
    @BindView(R.id.text) TextView text;
    private boolean check = false;


    @Override
    protected void initViews() {
        super.initViews();

        presenter = new AddAdsServicePresenter(this);
        backArrow.setOnClickListener(l->{
            getActivity().getSupportFragmentManager().popBackStack();
        });

        address.setOnFocusChangeListener((v, focus)->{
            if(focus) {
                AddreessDialog.newInstance((type, address1) -> address.setText(address1), "").show(getChildFragmentManager(), AddreessDialog.TAG);
            } else {
                if(!address.getText().toString().equals("")){
                    check = true;
                }
            }
            if(check){
                SPHelper.AdsHelper.setLocation(address.getText().toString());
                List<String> coord = new ArrayList<>();
                coord.add(address.getText().toString());
                presenter.getCoordiates(coord);
                check = false;
            }

        });

        addPhoto.setOnClickListener(l->{
            if (checkPermisson()) showImageSOurceDialog();
            else requestPermission();
        });

        nextBtn.setOnClickListener(l->{
            visableWaitDialog();
            if(checkService()) {
                presenter.addAdsService(new AdsData("10", "service","0",
                        opisanie.getText().toString(),SPHelper.PersonInfo.getSurname(), SPHelper.PersonInfo.getName(), nameAds.getText().toString(), address.getText().toString(),
                        "0","0",price.getText().toString(),
                        SPHelper.AdsHelper.getLatAds().toString(),SPHelper.AdsHelper.getLonAds().toString(),
                        "0",SPHelper.AdsHelper.getPhotoAnimals(),"0",
                        "0","0","0", SPHelper.PersonInfo.getUrlPhoto(), SPHelper.PersonInfo.getPhone()));
            } else Toast.makeText(getContext(), "Заполните все поля", Toast.LENGTH_SHORT).show();
        });
    }

    private boolean checkService() {
        if( !opisanie.getText().toString().equals("") &&  !nameAds.getText().toString().equals("")
                && !address.getText().toString().equals("") && !price.getText().toString().equals("") && !SPHelper.AdsHelper.getPhotoAnimals().equals("")){
            return true;
        } else return false;
    }

    @Override
    protected int layoutId() {
        return R.layout.add_service_fragment;
    }
    private void visableWaitDialog(){
        dialog = new ProgressDialog(getContext());
        dialog.setIndeterminate(false);
        dialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        dialog.setMessage("Пожалуйста подождите...");
        dialog.setCancelable(false);
        dialog.show();
    }
    private void showImageSOurceDialog() {
        String[] options = {"Камера", "Галерея"};
        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
        builder.setTitle("Выберите путь фотографии");
        builder.setItems(options, (dialog,witch) ->{
            if(witch == 0)dispatchTakePictureIntent();
            else if (witch ==1) openGallery();
        });
        AlertDialog dialog = builder.create();
        dialog.show();
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
                if(imageUri !=null) {
                    presenter.uploadPhotoInDb(imageUri, java.util.UUID.randomUUID().toString());
                    visableWaitDialog();
                }
                else Toast.makeText(getContext(),"Ошибка загрузки фотографии!", Toast.LENGTH_SHORT).show();
            }
        } else if (requestCode == REQUEST_IMAGE_PIKE) {
            Uri img = data.getData();
            if(img!=null) {
                presenter.uploadPhotoInDb(img, java.util.UUID.randomUUID().toString());
                visableWaitDialog();
            }
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
    public void message(String msg) {
        dialog.dismiss();
        Toast.makeText(getContext(), msg, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void successMessage(String msg) {
        dialog.dismiss();
        Toast.makeText(getContext(), msg, Toast.LENGTH_SHORT).show();
        visableImage.setVisibility(View.VISIBLE);
        cameraImage.setVisibility(View.GONE);
        text.setVisibility(View.GONE);
    }

    @Override
    public void successAddAds(String msg) {
        dialog.dismiss();
        Toast.makeText(getContext(), msg, Toast.LENGTH_SHORT).show();
        getActivity().finish();
    }

    @Override
    public void getCoordinates(String lat, String lon) {
        SPHelper.AdsHelper.setLatAds(Float.parseFloat(lat));
        SPHelper.AdsHelper.setLonAds(Float.parseFloat(lon));
    }
}
