package com.pzm.pizzera.profile;

import android.text.TextUtils;
import android.util.Patterns;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ProfileModel {

    private String id;
    private String salary;
    private String name;
    private String surname;
    private String role;
    private String phone;
    private String email;
    private String photo;


    public ProfileModel(String id, String name, String surname, String salary, String role, String phone, String email, String photo) {
        this.id = id;
        this.salary = salary;
        this.name = name;
        this.surname = surname;
        this.role = role;
        this.phone = phone;
        this.email = email;
        this.photo = photo;
    }

    public ProfileModel(){

    }


    public boolean validate(){
        String regexStr = "^[0-9]{9}$";
        if (!this.phone.matches(regexStr)){
            return false;
        }
        regexStr = "^[1-9]+$";
        if (!this.salary.matches(regexStr)){
            return false;
        }
        regexStr = "([A-Z][a-z][a-z]*)";
        if (!this.name.matches(regexStr) || !this.surname.matches(regexStr)){
            return false;
        }
        if (TextUtils.isEmpty(email) || !Patterns.EMAIL_ADDRESS.matcher(email).matches()){
            return false;
        }
        return true;
    }
}
