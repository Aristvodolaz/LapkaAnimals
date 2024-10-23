package com.example.newanimals.presenter.reg_and_auth;

import com.example.newanimals.db.PersonData;
import com.example.newanimals.view.reg_and_auth.AddUsersView;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class AddUsersPresenter {

    private AddUsersView view;

    public AddUsersPresenter(AddUsersView view) {
        this.view = view;
    }

    public void usersInDB(PersonData data){
        String databaseURL = "https://lapka2-5144b-default-rtdb.europe-west1.firebasedatabase.app";
        FirebaseDatabase database = FirebaseDatabase.getInstance(databaseURL);
        DatabaseReference databaseReference = database.getReference();

        String login = data.getLogin();
        String modifiedLogin = login.replace(".", "_");

        DatabaseReference usersRef = databaseReference.child("Users");
        DatabaseReference userLoginRef = usersRef.child(modifiedLogin);
        userLoginRef.setValue(data);
    }

}
