package com.example.newanimals.fragment.auth_and_reg;

import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.newanimals.R;
import com.example.newanimals.activity.MainActivity;
import com.example.newanimals.db.PersonData;
import com.example.newanimals.fragment.BaseFragment;
import com.example.newanimals.fragment.PermitFragment;
import com.example.newanimals.presenter.reg_and_auth.AddUsersPresenter;
import com.example.newanimals.view.reg_and_auth.AddUsersView;
import com.google.firebase.auth.FirebaseAuth;

import butterknife.BindView;

public class RegistrationFragment extends BaseFragment implements AddUsersView {
    private final static String TYPE = "type";
    private final static String NAME_TYPE = "name_type";
    private AddUsersPresenter presenter;
    @BindView(R.id.name_ET)
    EditText name;
    @BindView(R.id.surname_ET)
    EditText surname;
//    @BindView(R.id.date_ET)
//    MaskedEditText date;
//    @BindView(R.id.phone_ET)
//    MaskedEditText phone;
//    @BindView(R.id.city_ET)
//    EditText city;
    @BindView(R.id.email_ET)
    EditText login;
    @BindView(R.id.first_pass_ET)
    EditText fPass;
    @BindView(R.id.sec_pass_ET)
    EditText sPass;
    @BindView(R.id.btn)
    Button btn;
    @BindView(R.id.back_arrow)
    ImageView back_arrow;
    String names ="", surnames = "", citys ="", phones="", dates="", logins="", fPasss="", sPasss="";
    String password, log;
    public static RegistrationFragment newInstance(String type, String nameType) {
        Bundle args = new Bundle();
        args.putString(TYPE,type);
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

        back_arrow.setOnClickListener(v->{
            getActivity().getSupportFragmentManager().popBackStack();
        });
        btn.setOnClickListener(v->{
            getInfo();
        });

//        date.setOnClickListener(v->{
//           setInitalDate();
//        });
        presenter= new AddUsersPresenter(this);
    }

    private void setInitalDate() {
//        date.setText(this,t, da);
    }

    private void getInfo() {

        if(name.getText()!=null && !name.getText().equals(""))
            names = name.getText().toString();
        if(surname.getText()!=null && !surname.getText().equals(""))
            surnames = surname.getText().toString();
//        if(date.getText()!=null && !date.getText().equals(""))
//            dates = date.getText().toString();
//        if(phone.getText()!=null && !phone.getText().equals(""))
//            phones = phone.getText().toString();
//        if(city.getText()!=null && !city.getText().equals(""))
//            citys = city.getText().toString();
        if(login.getText()!=null && !login.getText().equals(""))
            logins = login.getText().toString();
        if (fPass.getText()!=null && !fPass.getText().equals(""))
            fPasss = fPass.getText().toString();
        if(sPass.getText()!=null && !sPass.getText().equals(""))
            sPasss = sPass.getText().toString();
        if(!logins.equals("") && !fPasss.equals("") && !sPasss.equals("")){
            if (sPasss.equals(fPasss)) {
                log = logins;
                password = sPasss;
                createUser();
                presenter.usersInDB(new PersonData(getArguments().getString(TYPE), getArguments().getString(NAME_TYPE),
                        names, surnames, "", "", "", logins, ""));
            } else {
//                sPass.setBackgroundTintList(Color.RED_FIELD_NUMBER);
//                fPass.setBackgroundTintList(Color.RED_FIELD_NUMBER);
                Toast.makeText(getContext(), "Пароли не совпадают!", Toast.LENGTH_SHORT).show();
            }
        }
    }

    @Override
    public void createUser() {
        auth.createUserWithEmailAndPassword(log,password).addOnCompleteListener(task->{
            if(task.isSuccessful()){
                ((MainActivity)getActivity()).replaceFragment(PermitFragment.newInstance(), false);
            } else (Toast.makeText(getContext(), "Произошла ошибка! \n Повторите попытку позже", Toast.LENGTH_LONG)).show();
        }).addOnFailureListener(e->Toast.makeText(getContext(),e.getLocalizedMessage(), Toast.LENGTH_LONG).show());
    }
}
