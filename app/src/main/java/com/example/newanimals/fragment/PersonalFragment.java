package com.example.newanimals.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.newanimals.R;
import com.example.newanimals.activity.MainActivity;
import com.example.newanimals.utils.SPHelper;
import com.google.firebase.auth.FirebaseAuth;
import com.squareup.picasso.Picasso;

import org.w3c.dom.Text;

import java.text.DecimalFormat;
import java.text.MessageFormat;
import java.util.Calendar;

import butterknife.BindView;

public class PersonalFragment extends BaseFragment{
    public static PersonalFragment newInstance() {
        return new PersonalFragment();
    }
    @BindView(R.id.name)
    TextView name;
    @BindView(R.id.surname) TextView surname;
    @BindView(R.id.age_years)
    TextView age;
    @BindView(R.id.phone)
    TextView phone;
    @BindView(R.id.city)
    TextView city;
    @BindView(R.id.email)
    TextView email;
    @BindView(R.id.back_arrow)
    ImageView back_arrow;
    @BindView(R.id.logout)
    ImageView logout;
    @BindView(R.id.image_user) ImageView image;
    FirebaseAuth auth;
    int howAge = 0;
    DecimalFormat phoneFormatD = new DecimalFormat("0000000000");
    MessageFormat phoneFormatM = new MessageFormat("({0}) {1}-{2}");

    @Override
    protected int layoutId() {
        return R.layout.personal_fragment_layout;
    }

    @Override
    protected void initViews() {
        auth = FirebaseAuth.getInstance();
        super.initViews();
        name.setText(SPHelper.PersonInfo.getName() );
        surname.setText(SPHelper.PersonInfo.getSurname());


        try {
            if(!SPHelper.PersonInfo.getPhone().isEmpty() && !SPHelper.PersonInfo.getPhone().equals("")) phone.setText(formatPhoneNumber(SPHelper.PersonInfo.getPhone()));
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        if (!SPHelper.PersonInfo.getCity().equals("")) city.setText("г. " + SPHelper.PersonInfo.getCity());
        if (!SPHelper.getLogin().equals("")) email.setText(SPHelper.getLogin());
        if (!SPHelper.PersonInfo.getUrlPhoto().equals("")) Picasso.get().load(SPHelper.PersonInfo.getUrlPhoto()).into(image);
        if (!SPHelper.PersonInfo.getDateBirth().equals("")) {
            howAge = Calendar.getInstance().get(Calendar.YEAR) - Integer.parseInt(SPHelper.PersonInfo.getDateBirth().substring(6, SPHelper.PersonInfo.getDateBirth().length()));
        }age.setText(howAge+ " год");
        back_arrow.setOnClickListener(v->{
            getActivity().getSupportFragmentManager().popBackStack();
        });
        logout.setOnClickListener(v->{
            auth.signOut();
            startActivity(new Intent(getActivity(), MainActivity.class));
            getActivity().finish();
        });
    }

    public String formatPhoneNumber(Object phone) throws Exception {

        double p = 0;

        if (phone instanceof String)
            p = Double.parseDouble((String) phone);

        if (phone instanceof Integer)
            p = (Integer) phone;

        if (phone instanceof Float)
            p = (Float) phone;

        if (phone instanceof Double)
            p = (Double) phone;

        if (p == 0 || String.valueOf(p) == "" || String.valueOf(p).length() < 7)
            throw new Exception("Paramenter is no valid");

        String fot = phoneFormatD.format(p);

        String extra = fot.length() > 10 ? fot.substring(0, fot.length() - 10) : "";
        fot = fot.length() > 10 ? fot.substring(fot.length() - 10, fot.length()) : fot;

        String[] arr = {
                (fot.charAt(0) != '0') ? fot.substring(0, 3) : (fot.charAt(1) != '0') ? fot.substring(1, 3) : fot.substring(2, 3),
                fot.substring(3, 6),
                fot.substring(6)
        };
        String r = phoneFormatM.format(arr);
        r = (r.contains("(0)")) ? r.replace("(0) ", "") : r;
        r = (extra != "") ? ("+" + extra + " " + r) : r;
        return (r);
    }
}
