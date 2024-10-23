package com.example.newanimals.fragment.add_ads_animals;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.newanimals.R;
import com.example.newanimals.db.AdsData;
import com.example.newanimals.dialog.AddreessDialog;
import com.example.newanimals.fragment.BaseFragment;
import com.example.newanimals.presenter.add.AddAdsInDBPresenter;
import com.example.newanimals.utils.SPHelper;
import com.example.newanimals.view.AddAdsInDBView;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

public class AddressFragment extends BaseFragment implements AddAdsInDBView {

    public static AddressFragment newInstance() {
        return new AddressFragment();
    }
    @BindView(R.id.address)
    EditText address;
    @BindView(R.id.date) EditText date;
    @BindView(R.id.back_arrow)
    ImageView backArrow;
    @BindView(R.id.next_btn)
    Button nextBtn;

    private boolean check = false;
    private ProgressDialog dialog;

    private AddAdsInDBPresenter presenter;

    @Override
    protected void initViews() {
        super.initViews();

        presenter = new AddAdsInDBPresenter(this);

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

        backArrow.setOnClickListener(l->{getActivity().getSupportFragmentManager().popBackStack();});

        nextBtn.setOnClickListener(l->{
            visableWaitDialog();
            if(SPHelper.AdsHelper.getVidAdd().equals("2")){
                SPHelper.AdsHelper.setDateLose(date.getText().toString());
            if(!address.getText().toString().equals("") && !date.getText().toString().equals("") && checkPredectly(true)){
                presenter.postDataInDb(new AdsData(SPHelper.AdsHelper.getVidAdd(), SPHelper.getNametype(),SPHelper.AdsHelper.getNameAnimals(),
                        SPHelper.AdsHelper.getOpisanie(),SPHelper.PersonInfo.getSurname(), SPHelper.PersonInfo.getName(), "0", SPHelper.AdsHelper.getLocation(),
                        SPHelper.AdsHelper.getDateLose(),"0","0",
                        SPHelper.AdsHelper.getLatAds().toString(),SPHelper.AdsHelper.getLonAds().toString(),
                        SPHelper.AdsHelper.getTypeAnimals(),SPHelper.AdsHelper.getPhotoAnimals(),SPHelper.AdsHelper.getAgeAnimals(),
                        SPHelper.AdsHelper.getPorodaAnimals(),SPHelper.AdsHelper.getOkras(),SPHelper.AdsHelper.getPolAnimals(), SPHelper.PersonInfo.getUrlPhoto(),
                        SPHelper.PersonInfo.getPhone()));

            }
            } else {
                if(!address.getText().toString().equals("") && !date.getText().toString().equals("") && checkPredectly(false)){
                    presenter.postDataInDb(new AdsData(SPHelper.AdsHelper.getVidAdd(), SPHelper.getNametype(),SPHelper.AdsHelper.getNameAnimals(),
                            SPHelper.AdsHelper.getOpisanie(),SPHelper.PersonInfo.getSurname(), SPHelper.PersonInfo.getName(), "0", SPHelper.AdsHelper.getLocation(),
                            "0","0","0",
                            SPHelper.AdsHelper.getLatAds().toString(),SPHelper.AdsHelper.getLonAds().toString(),
                            SPHelper.AdsHelper.getTypeAnimals(),SPHelper.AdsHelper.getPhotoAnimals(),SPHelper.AdsHelper.getAgeAnimals(),
                            SPHelper.AdsHelper.getPorodaAnimals(),SPHelper.AdsHelper.getOkras(),SPHelper.AdsHelper.getPolAnimals(), SPHelper.PersonInfo.getUrlPhoto(),
                            SPHelper.PersonInfo.getPhone()));

                }
            }
        });


    }

    private boolean checkPredectly(boolean poteryashka) {
        if(!poteryashka) {
            return !SPHelper.AdsHelper.getOkras().equals("") && !SPHelper.AdsHelper.getOpisanie().equals("")
                    && !SPHelper.AdsHelper.getPhotoAnimals().equals("") && !SPHelper.AdsHelper.getNameAnimals().equals("") &&
                    !SPHelper.AdsHelper.getVidAdd().equals("") && !SPHelper.AdsHelper.getPolAnimals().equals("") &&
                    !SPHelper.AdsHelper.getPorodaAnimals().equals("") && !SPHelper.AdsHelper.getTypeAnimals().equals("") &&
                    !SPHelper.getNametype().equals("");
        }else {
            return !SPHelper.AdsHelper.getOkras().equals("") && !SPHelper.AdsHelper.getOpisanie().equals("")
                    && !SPHelper.AdsHelper.getPhotoAnimals().equals("") && !SPHelper.AdsHelper.getNameAnimals().equals("") &&
                    !SPHelper.AdsHelper.getVidAdd().equals("") && !SPHelper.AdsHelper.getPolAnimals().equals("") &&
                    !SPHelper.AdsHelper.getPorodaAnimals().equals("") && !SPHelper.AdsHelper.getTypeAnimals().equals("") &&
                    !SPHelper.AdsHelper.getDateLose().equals("") && !SPHelper.AdsHelper.getDateLose().equals("");
        }
    }
    private void visableWaitDialog(){
        dialog = new ProgressDialog(getContext());
        dialog.setIndeterminate(false);
        dialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        dialog.setMessage("Пожалуйста подождите...");
        dialog.setCancelable(false);
        dialog.show();
    }
    @Override
    protected int layoutId() {
        return R.layout.address_fragment;
    }

    @Override
    public void message(String message) {
        dialog.dismiss();
        Toast.makeText(getContext(),message, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void getCoordinates(String lat, String lon) {
        SPHelper.AdsHelper.setLatAds(Float.parseFloat(lat));
        SPHelper.AdsHelper.setLonAds(Float.parseFloat(lon));
    }

    @Override
    public void successMessage(String success) {
        dialog.dismiss();
        Toast.makeText(getContext(),success, Toast.LENGTH_SHORT).show();
        getActivity().finish();
    }
}
