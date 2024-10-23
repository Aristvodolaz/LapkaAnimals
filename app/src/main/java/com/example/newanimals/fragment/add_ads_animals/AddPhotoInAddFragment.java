package com.example.newanimals.fragment.add_ads_animals;

import android.app.ProgressDialog;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.app.AlertDialog;

import com.example.newanimals.R;
import com.example.newanimals.activity.AddAdsActivity;
import com.example.newanimals.activity.WelcomeActivity;
import com.example.newanimals.fragment.BaseFragment;
import com.example.newanimals.presenter.add.AddPhotoInDbPresenter;
import com.example.newanimals.view.add.AddPhotoView;

import butterknife.BindView;

public class AddPhotoInAddFragment extends BaseFragment implements AddPhotoView {
    public static AddPhotoInAddFragment newInstance() {
        return new AddPhotoInAddFragment();
    }
    public static final int REQUEST_IMAGE_CAPTURE = 1;
    public static final int REQUEST_IMAGE_PIKE = 3;
    public static final int PERMISSON_REQUEST_CODE = 2;

    private AddPhotoInDbPresenter presenter;
    private ProgressDialog dialog;

    @BindView(R.id.back_arrow)
    ImageView backArrow;
    @BindView(R.id.add_photo)
    FrameLayout addPhoto;
    @BindView(R.id.next_btn)
    Button nextBtn;
    @BindView(R.id.camera_image)
    ImageView cameraImage;
    @BindView(R.id.text)
    TextView text;

    @BindView(R.id.visable_image) ImageView visableImage;
    @Override
    protected void initViews() {
        super.initViews();

        presenter = new AddPhotoInDbPresenter(this);

        backArrow.setOnClickListener(l->{
            getActivity().getSupportFragmentManager().popBackStack();
        });

        addPhoto.setOnClickListener(l->{
            if (checkPermisson()) showImageSOurceDialog();
            else requestPermission();
        });

        nextBtn.setOnClickListener(l->{
            ((AddAdsActivity)getActivity()).replaceFragment(ChooseTypeAnimalFragment.newInstance(),true);
        });
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
    protected int layoutId() {
        return R.layout.add_photo_in_add;
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
}
