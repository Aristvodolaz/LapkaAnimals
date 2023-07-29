package com.example.newanimals.fragment;

import android.Manifest;
import android.app.AlertDialog;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import com.example.newanimals.R;
import com.example.newanimals.db.AdsDataKt;
import com.example.newanimals.dialog.AddreessDialog;
import com.example.newanimals.network.request.GeocoderAddressBody;
import com.example.newanimals.presenter.AddDataPresenter;
import com.example.newanimals.utils.SPHelper;
import com.example.newanimals.view.AddAdsView;

import org.checkerframework.checker.units.qual.A;
import org.checkerframework.checker.units.qual.C;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

public class AddAdsFindHomeFragment extends BaseFragment implements AddAdsView {
    public static final int REQUEST_IMAGE_CAPTURE = 1;
    public static final int REQUEST_IMAGE_PIKE = 3;
    public static final int PERMISSON_REQUEST_CODE = 2;
    @BindView(R.id.add_photo)
    Button btnAddPhoto;
    @BindView(R.id.add_ads)
    Button btnAddAds;
    @BindView(R.id.address)
    EditText address;

    @BindView(R.id.poroda) EditText poroda;
    @BindView(R.id.colors) EditText colors;
    @BindView(R.id.ages) EditText age;
    @BindView(R.id.opisanie) EditText description;
    @BindView(R.id.dog)
    CheckBox dog;
    @BindView(R.id.cat) CheckBox cats;
    @BindView(R.id.other) CheckBox other;
    @BindView(R.id.girl) CheckBox girl;
    @BindView(R.id.man) CheckBox man;

    private String typeAnimals;
    private String polAnimals;
    private String photoAnimals;

    private String LAT, LON;

    private AddDataPresenter presenter;

    public static AddAdsFindHomeFragment newInstance() {
        return new AddAdsFindHomeFragment();
    }

    @Override
    protected void initViews() {
        super.initViews();
        presenter = new AddDataPresenter(this);

        handleCheckBox();
        btnAddPhoto.setOnClickListener(v->{
            if(checkPermisson())
                showImageSOurceDialog();
            else requestPermission();
        });

        btnAddAds.setOnClickListener(v->{
            if(address.getText().toString()!=null && !address.getText().equals("")){
                List<String> data = new ArrayList<>();
                data.add(address.getText().toString());
                presenter.getCoordinates(data);
            }
            checkFullInfo();
        });

        address.setOnFocusChangeListener((v, hasFocus) -> {
            if(hasFocus) {
                AddreessDialog.newInstance((type, addresss) -> address.setText(addresss), "Адрес нахождения животного").show(getChildFragmentManager(), AddreessDialog.TAG);
            }
        });

    }

    private void checkFullInfo() {

        if(!address.getText().toString().equals("") && !poroda.getText().toString().equals("")
                && !colors.getText().toString().equals("") && !age.getText().toString().equals("")
                && !description.getText().toString().equals("") && !photoAnimals.equals("") && !typeAnimals.equals("") && !polAnimals.equals("")){

            presenter.writeData(new AdsDataKt("0","findHome","0",description.getText().toString(),"SPHelper.getName()",
                    "SPHelper.getSurname()","0", address.getText().toString(), "0","0", "0",Float.parseFloat(LAT),Float.parseFloat(LON), typeAnimals, photoAnimals,
                    age.getText().toString(), poroda.getText().toString(),colors.getText().toString(), polAnimals,"0","SPHelper.getPhone()"));

        } else Toast.makeText(getContext(),"Ошибка публикации объявления!",Toast.LENGTH_SHORT).show();

    }

    private void handleCheckBox() {
        cats.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (cats.isChecked()) {
                    dog.setClickable(false);
                    other.setClickable(false);
                    typeAnimals = "Кошка";
                } else {
                    dog.setClickable(true);
                    other.setClickable(true);
                    btnAddAds.setClickable(false);
                }
            }
        });
        dog.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (dog.isChecked()){
                    cats.setClickable(false);
                    other.setClickable(false);
                    typeAnimals = "Собака";
                } else {
                    cats.setClickable(true);
                    other.setClickable(true);
                    btnAddAds.setClickable(false);
                }
            }
        });
        other.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (other.isChecked()){
                    cats.setClickable(false);
                    dog.setClickable(false);
                    typeAnimals = "Другие";
                } else {
                    cats.setClickable(true);
                    dog.setClickable(true);
                    btnAddAds.setClickable(false);
                }
            }
        });


        girl.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (girl.isChecked()){
                    man.setClickable(false);
                    polAnimals = "Ж";
                } else {
                    man.setClickable(true);
                    btnAddAds.setClickable(false);
                }
            }
        });

        man.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (man.isChecked()){
                    girl.setClickable(false);
                    polAnimals = "М";
                } else {
                    girl.setClickable(true);
                    btnAddAds.setClickable(false);
                }
            }
        });
    }

    private void showImageSOurceDialog() {
        String[] options = {"Камера", "Галерея"};
        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
        builder.setTitle("Выберите путь фотографии");
        builder.setItems(options, (dialog,witch) ->{
            if(witch == 0){
                dispatchTakePictureIntent();
            } else if (witch ==1) {
                openGallery();
            }
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
        return R.layout.add_ads_find_home_layout;
    }

    @Override
    public void sendMessage(String str) {
        Toast.makeText(getContext(), str, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onUploadSuccess(String str) {
        Toast.makeText(getContext(), str, Toast.LENGTH_SHORT).show();
        btnAddPhoto.setVisibility(View.GONE);
        photoAnimals = SPHelper.getUrlPhoto();
    }

    @Override
    public void onUploadError(String localizedMessage) {
        Toast.makeText(getContext(), localizedMessage, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void getCoordinates(String lat, String lon) {
        LAT = lat;
        LON = lon;
    }
}
