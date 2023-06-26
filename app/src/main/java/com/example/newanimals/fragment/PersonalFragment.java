package com.example.newanimals.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.newanimals.R;
import com.example.newanimals.activity.MainActivity;
import com.example.newanimals.utils.SPHelper;
import com.google.firebase.auth.FirebaseAuth;

import org.w3c.dom.Text;

import java.util.Calendar;

import butterknife.BindView;

public class PersonalFragment extends BaseFragment{

    @BindView(R.id.name)
    TextView name;
    @BindView(R.id.age)
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
    FirebaseAuth auth;
    public static PersonalFragment newInstance() {
        return new PersonalFragment();
    }
    @Override
    protected int layoutId() {
        return R.layout.personal_fragment_layout;
    }

    @Override
    protected void initViews() {
        auth = FirebaseAuth.getInstance();
        super.initViews();
        name.setText(SPHelper.getName().toUpperCase() + " "+ SPHelper.getSurname().toUpperCase());
        phone.setText(SPHelper.getPhone());
        city.setText(SPHelper.getCity());
        email.setText(SPHelper.getLogin());
//        age.setText(Integer.parseInt(SPHelper.getDate().substring(4, SPHelper.getDate().length())) - Calendar.getInstance().get(Calendar.YEAR));
        back_arrow.setOnClickListener(v->{
            getActivity().getSupportFragmentManager().popBackStack();
        });
        logout.setOnClickListener(v->{
            auth.signOut();
            startActivity(new Intent(getActivity(), MainActivity.class));
            getActivity().finish();
        });
    }
}
