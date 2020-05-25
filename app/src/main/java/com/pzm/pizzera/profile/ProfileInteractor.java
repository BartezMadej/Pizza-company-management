package com.pzm.pizzera.profile;


import android.text.TextUtils;
import android.util.Patterns;

import com.pzm.pizzera.UserModel;


public class ProfileInteractor extends UserModel {

    interface OnValidate {

        void onNameError();

        void onSurnameError();

        void onPhoneError();

        void onEmailError();

        void onSalaryError();
    }

    public void validate(String name, String surname, String phone, String email, String salary, OnValidate listener){
        String regexStr = "^[0-9]{9}$";
        if (!phone.matches(regexStr)){
            listener.onPhoneError();
            return;
        }
        regexStr = "^[1-9]+$";
        if (!salary.matches(regexStr)){
            listener.onSalaryError();
            return;
        }
        regexStr = "([A-Z][a-z][a-z]*)";
        if (!name.matches(regexStr)){
            listener.onNameError();
            return;
        }
        if (!surname.matches(regexStr)){
            listener.onSurnameError();
            return;
        }
        if (TextUtils.isEmpty(email) || !Patterns.EMAIL_ADDRESS.matcher(email).matches()){
            listener.onEmailError();
            return;
        }
    }
}
