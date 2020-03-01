package com.example.diyetkolik;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import org.w3c.dom.Text;

public class UserPage extends AppCompatActivity {

    private final FirebaseAuth firebaseAuth = FirebaseAuth.getInstance();

    Button back_button;
    Button updateInfo_button;
    Button logOut_button;

    EditText userMail_editText;
    EditText userPassword_editText;
    EditText newPassword_editText;
    EditText newPasswordAgain_editText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_page);

        Bundle extras = getIntent().getExtras();
        final String lastPage = extras.getString("lastPage");

        back_button = findViewById(R.id.back_button);
        updateInfo_button = findViewById(R.id.updateInfo_button);
        logOut_button = findViewById(R.id.logOut_button);

        userMail_editText = findViewById(R.id.userMail_editText);
        userPassword_editText = findViewById(R.id.userPassword_editText);
        newPassword_editText = findViewById(R.id.newPassword_editText);
        newPasswordAgain_editText = findViewById(R.id.newPasswordAgain_editText);

        String userEmail = firebaseAuth.getCurrentUser().getEmail();
        userMail_editText.setText(userEmail);

        logOut_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                firebaseAuth.signOut();
                Toast.makeText(getApplicationContext(), "Çıkış Yapıldı..", Toast.LENGTH_LONG).show();
                toSignInPage();
            }
        });

        back_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    toLastPage(lastPage);
                } catch (ClassNotFoundException e) {
                    e.printStackTrace();
                }
            }
        });

        updateInfo_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final String mail, password, newPassword, newPasswordAgain;
                mail = userMail_editText.getText().toString();
                password = userPassword_editText.getText().toString();
                newPassword = newPassword_editText.getText().toString();
                newPasswordAgain = newPasswordAgain_editText.getText().toString();

                if(!TextUtils.isEmpty(mail)){
                    if(!TextUtils.isEmpty(password)){
                        if(!TextUtils.isEmpty(newPassword)){
                            if(!TextUtils.isEmpty(newPasswordAgain)){
                                if(newPassword.equals(newPasswordAgain)) {
                                    firebaseAuth.signInWithEmailAndPassword(mail, password)
                                            .addOnCompleteListener(UserPage.this, new OnCompleteListener<AuthResult>() {
                                                @Override
                                                public void onComplete(@NonNull Task<AuthResult> task) {
                                                    if (task.isSuccessful()) {
                                                        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
                                                        user.updatePassword(newPassword);
                                                        Toast.makeText(getApplicationContext(), "Şifre Güncellendi", Toast.LENGTH_LONG).show();
                                                    } else {
                                                        Toast.makeText(getApplicationContext(), "Mail adresi veya şifre hatalı !!", Toast.LENGTH_LONG).show();
                                                    }
                                                }
                                            });
                                }else{
                                    Toast.makeText(getApplicationContext(), "Yeni şifre ve yeni şifre tekrar kısmı aynı değil !!", Toast.LENGTH_LONG).show();
                                }
                            }else{
                                Toast.makeText(getApplicationContext(), "Yeni şifre tekrar kısmı boş bırakılamaz !!", Toast.LENGTH_LONG).show();
                            }
                        }else{
                            Toast.makeText(getApplicationContext(), "Yeni şifre kısmı boş bırakılamaz !!", Toast.LENGTH_LONG).show();
                        }
                    }else{
                        Toast.makeText(getApplicationContext(), "Kullanılan şifre kısmı boş bırakılamaz !!", Toast.LENGTH_LONG).show();
                    }
                }else{
                    Toast.makeText(getApplicationContext(), "Mail kısmı boş bırakılamaz !!", Toast.LENGTH_LONG).show();
                }
            }
        });

    }
    public void toLastPage(String lastPage) throws ClassNotFoundException {
        lastPage = "com.example.diyetkolik." + lastPage;
        Intent intent = new Intent(getApplicationContext(), Class.forName(lastPage));
        startActivity(intent);
    }
    public void toSignInPage(){
        Intent intent = new Intent(getApplicationContext(), MainActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(intent);
    }
}
