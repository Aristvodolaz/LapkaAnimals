package com.example.newanimals.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.newanimals.R;
import com.example.newanimals.activity.MainActivity;
import com.example.newanimals.activity.WelcomeActivity;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.util.Objects;

import butterknife.BindView;

public class LoginFragment extends BaseFragment{
    public static LoginFragment newInstance() {
        return new LoginFragment();
    }
    private FirebaseAuth mAuth;
    @BindView(R.id.login)
    EditText loginET;
    @BindView(R.id.password)
    EditText passwordET;
    @BindView(R.id.btn)
    Button btnStart;
    @BindView(R.id.forget_pass)
    TextView forgetPass;

    @Override
    protected int layoutId() {
        return R.layout.login_layout;
    }

    @Override
    protected void initViews() {
        super.initViews();
        mAuth = FirebaseAuth.getInstance();
        forgetPass.setOnClickListener(v->{
            ((MainActivity)getActivity()).replaceFragment(ForgetPassFragment.newInstance(),false);
        });
        btnStart.setOnClickListener(v -> {
            loginApp();
        });
    }
    private void loginApp(){
        String login = "", password = "";
        if(!loginET.getText().equals("") && loginET.getText() != null
                && !passwordET.getText().equals("") && passwordET.getText() !=null) {
            login = loginET.getText().toString();
            password = passwordET.getText().toString();
        } else  Toast.makeText(getContext(),"Введите данные!", Toast.LENGTH_LONG).show();
        if (!login.equals("") && login !=null && !password.equals("") && password !=null){
            mAuth.signInWithEmailAndPassword(login, password).addOnCompleteListener(getActivity(),task->{
                if(task.isSuccessful()){
                    FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
                    if(Objects.requireNonNull(user).isEmailVerified()){
                        startActivity(new Intent(getContext(), WelcomeActivity.class));
                        getActivity().finish();
                    } else{
                        user.sendEmailVerification();
                        Toast.makeText(getContext(), "Введенные данные неверны!\n Проверьте актуальность введенных данных", Toast.LENGTH_LONG).show();
                    }
                }else {
                    Toast.makeText(getContext(), "Данного логина не существует!", Toast.LENGTH_LONG).show();
                }
            });
        }
    }
}
