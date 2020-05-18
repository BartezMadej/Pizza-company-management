package com.pzm.pizzera.reset_password;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;

import androidx.fragment.app.Fragment;

import com.pzm.pizzera.R;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import lombok.NonNull;

public class ResetPasswordFragment extends Fragment implements ResetPasswordView {

    public FirebaseAuth mAuth;
    private TextView comments;
    private EditText email;

    @Override
    public View onCreateView(@androidx.annotation.NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_register, container,false);

        email = view.findViewById(R.id.editText);
        comments = view.findViewById(R.id.textView);
        mAuth = FirebaseAuth.getInstance();

        view.findViewById(R.id.buttonCreateUser).setOnClickListener(v -> resetPassword());

        return view;
    }

    public void resetPassword() {

        String email = this.email.getText().toString();

        if( email.isEmpty() || email.equals("") ) {
            comments.setTextColor(Color.RED);
            comments.setText("Email field cannot be empty!");
            comments.setVisibility(View.VISIBLE);
            return;
        }

        mAuth.sendPasswordResetEmail(email)
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if (task.isSuccessful()) {
                            comments.setTextColor(Color.BLACK);
                            Log.d("Success:", "Email sent.");
                            comments.setText("Email sent successfully!");
                            comments.setVisibility(View.VISIBLE);
                        }
                        else {
                            Log.d("Failed", "Email not sent");
                            comments.setTextColor(Color.RED);
                            comments.setText("Email not found!");
                            comments.setVisibility(View.VISIBLE);
                        }
                    }
                });
    }

}
