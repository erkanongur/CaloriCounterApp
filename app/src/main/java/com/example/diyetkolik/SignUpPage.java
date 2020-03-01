package com.example.diyetkolik;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class SignUpPage extends AppCompatActivity {
    private FirebaseAuth mAuth;
    Button signIn;
    Button signUp;
    EditText fullName;
    EditText password;
    EditText passwordAgain;
    EditText email;
    CheckBox userAgreement;
    CheckBox dataAgreement;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up_page);

        mAuth = FirebaseAuth.getInstance();

        fullName = findViewById(R.id.fullName_editText);
        email = findViewById(R.id.email_editText);
        password = findViewById(R.id.password_editText);
        passwordAgain = findViewById(R.id.passwordAgain_editText);
        userAgreement = findViewById(R.id.userAgreement_checkBox);
        dataAgreement = findViewById(R.id.dataAgreement_checkBox);
        signUp = findViewById(R.id.signUp_button);
        signIn = findViewById(R.id.signIn);

        signIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(final View view) {
                toLogInPage(view);
            }
        });

        signUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(final View view) {
                if(userAgreement.isChecked()){
                    if(dataAgreement.isChecked()){
                        if(!TextUtils.isEmpty(fullName.getText())){
                            if(isValidEmail(email.getText())){
                                if (!TextUtils.isEmpty(password.getText())){
                                    if(!TextUtils.isEmpty(passwordAgain.getText())){
                                        if(TextUtils.equals(password.getText().toString(), passwordAgain.getText().toString())){
                                            reqisterUser(email.getText().toString(), password.getText().toString());
                                            toLogInPage(view);
                                        }else{
                                            Toast.makeText(getApplicationContext(),"Şifreler Eşleşmiyor !!", Toast.LENGTH_LONG).show();
                                        }
                                    }else{
                                        Toast.makeText(getApplicationContext(),"Şifre Tekrar Boş Geçilemez !!", Toast.LENGTH_LONG).show();
                                    }
                                }else{
                                    Toast.makeText(getApplicationContext(),"Şifre Boş Geçilemez !!", Toast.LENGTH_LONG).show();
                                }
                            }else{
                                Toast.makeText(getApplicationContext(),"E-Posta Geçersiz !!", Toast.LENGTH_LONG).show();
                            }
                        }else{
                            Toast.makeText(getApplicationContext(),"Ad Soyad Kısmı Doldurulmalıdır !!", Toast.LENGTH_LONG).show();
                        }
                    }else{
                        Toast.makeText(getApplicationContext(),"Veri Sözleşmesini Kabul Etmelisiniz !!", Toast.LENGTH_LONG).show();
                    }
                }else{
                    Toast.makeText(getApplicationContext(),"Kullanıcı Sözleşmesini Kabul Etmelisiniz !!", Toast.LENGTH_LONG).show();
                }
            }
        });
    }
    public void toLogInPage(View view){
        Intent intent = new Intent(getApplicationContext(), MainActivity.class);
        startActivity(intent);
    }
    public boolean isValidEmail(CharSequence target) {
        return (!TextUtils.isEmpty(target) && Patterns.EMAIL_ADDRESS.matcher(target).matches());
    }
    private void reqisterUser(String email, String password){
        mAuth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            Toast.makeText(getApplicationContext(), "Kullanıcı Kaydedildi..", Toast.LENGTH_LONG).show();
                        } else {
                            Toast.makeText(getApplicationContext(), "Kullanıcı Kaydedilemedi !!", Toast.LENGTH_LONG).show();
                            Log.d("ERROR", task.getException().toString());
                        }
                    }
                });
    }
}