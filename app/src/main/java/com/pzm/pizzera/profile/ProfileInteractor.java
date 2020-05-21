package com.pzm.pizzera.profile;


import android.text.TextUtils;
import android.util.Patterns;

import com.pzm.pizzera.UserModel;


public class ProfileInteractor extends UserModel {

    interface OnValidate {

        public boolean validate();
    }
}
