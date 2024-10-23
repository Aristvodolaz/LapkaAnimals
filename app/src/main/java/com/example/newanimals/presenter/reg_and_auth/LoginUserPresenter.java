package com.example.newanimals.presenter.reg_and_auth;

import androidx.annotation.NonNull;

import com.example.newanimals.db.PersonData;
import com.example.newanimals.utils.SPHelper;
import com.example.newanimals.view.reg_and_auth.LoginUserView;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class LoginUserPresenter {
    private LoginUserView view;

    public LoginUserPresenter(LoginUserView view) {
        this.view = view;
    }

    public void getUserInfo(String login){
        String databaseURL = "https://lapka2-5144b-default-rtdb.europe-west1.firebasedatabase.app";
        FirebaseDatabase database = FirebaseDatabase.getInstance(databaseURL);
        DatabaseReference databaseReference = database.getReference();
        String modifiedLogin = login.replace(".", "_"); //

        DatabaseReference userLoginRef = databaseReference.child("Users").child(modifiedLogin);
        userLoginRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.exists()) {
                    PersonData personData = snapshot.getValue(PersonData.class);
                    if (personData != null) {
                        SPHelper.setLogin(personData.getLogin());
                        SPHelper.PersonInfo.setName(personData.getName());
                        SPHelper.PersonInfo.setSurname(personData.getSurname());
                        SPHelper.PersonInfo.setUrlPhoto(personData.getImg());
                        SPHelper.PersonInfo.setPhone(personData.getPhone());
                        SPHelper.PersonInfo.setCity(personData.getCity());
                        SPHelper.PersonInfo.setDateBirth(personData.getDate_birth());
                        view.getType(personData.getType());
                    }
                } else {
                    view.getType("Пользователь не найден");
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                view.getType(databaseError.getMessage());
            }
        });
    }
}
