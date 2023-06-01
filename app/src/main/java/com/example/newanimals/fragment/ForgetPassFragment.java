package com.example.newanimals.fragment;

import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.newanimals.R;
import com.google.firebase.auth.FirebaseAuth;

import butterknife.BindView;

public class ForgetPassFragment extends  BaseFragment{

    @BindView(R.id.forgetET)
    EditText forgetET;
    @BindView(R.id.btnAdd)
    Button btnAdd;
    private FirebaseAuth auth;
    public static ForgetPassFragment newInstance() {
        return new ForgetPassFragment();
    }

    @Override
    protected void initViews() {
        super.initViews();
        auth = FirebaseAuth.getInstance();
        btnAdd.setOnClickListener(v->{
            resetData();
        });
    }

    private void resetData() {
        if (!forgetET.getText().toString().equals("") && forgetET.getText().toString() !=null){
            auth.sendPasswordResetEmail(forgetET.getText().toString())
                    .addOnCompleteListener(getActivity(), task->{
                       if(task.isSuccessful()){
                           Toast.makeText(getContext(), "На вашу почту отправлено письмо для восстановления!"
                           ,Toast.LENGTH_LONG).show();
                       }else Toast.makeText(getContext(), "Ошибка! \nПопробуйте позднее.", Toast.LENGTH_SHORT).show();
                    }).addOnFailureListener(e->{
                        Toast.makeText(getContext(), e.getLocalizedMessage(), Toast.LENGTH_LONG).show();
                    });
        } else Toast.makeText(getContext(), "Введите почту!", Toast.LENGTH_LONG).show();
    }

    @Override
    protected int layoutId() {
        return R.layout.forget_pass_layout;
    }
}
