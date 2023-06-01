package com.example.newanimals.fragment;

import android.os.Bundle;
import android.widget.Toast;

import com.example.newanimals.R;
import com.example.newanimals.activity.MainActivity;
import com.google.firebase.auth.FirebaseAuth;

public class RegistrationFragment extends BaseFragment{
    private final static String TYPE = "type";
    private final static String NAME_TYPE = "name_type";
    public static RegistrationFragment newInstance(int type, String nameType) {
        Bundle args = new Bundle();
        args.putInt(TYPE,type);
        args.putString(NAME_TYPE,nameType);
        RegistrationFragment fragment = new RegistrationFragment();
        fragment.setArguments(args);
        return fragment;
    }
    private FirebaseAuth auth;
    @Override
    protected int layoutId() {
        return R.layout.registration_layout;
    }

    @Override
    protected void initViews() {
        super.initViews();
        auth = FirebaseAuth.getInstance();

        getInfo();
    }

    private void getInfo() {

    }

    private void createUser(String name, String surname, String phone, String city,String password, String login, String date ) {
        auth.createUserWithEmailAndPassword(login,password).addOnCompleteListener(task->{
           if(task.isSuccessful()){
               ((MainActivity)getActivity()).replaceFragment(PermitFragment.newInstance(), false);
           } else (Toast.makeText(getContext(), "Произошла ошибка! \n Повторите попытку позже", Toast.LENGTH_LONG)).show();
        }).addOnFailureListener(e->Toast.makeText(getContext(),e.getLocalizedMessage(), Toast.LENGTH_LONG).show());
    }
}
