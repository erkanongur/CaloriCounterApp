package com.example.diyetkolik;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;


public class MainActivity extends AppCompatActivity {

    private final FirebaseAuth firebaseAuth = FirebaseAuth.getInstance();
    EditText email, password;
    Button logIn;
    Button signIn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        email = findViewById(R.id.email);
        logIn = findViewById(R.id.login_button);
        password = findViewById(R.id.password);
        logIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(final View view) {
                String emailAddr = email.getText().toString();
                String pass = password.getText().toString();
                if(emailAddr.isEmpty() || pass.isEmpty()){
                    Toast.makeText(getApplicationContext(), "Yukarıdaki Alanlar Boş Bırakılamaz !!", Toast.LENGTH_LONG).show();
                }
                else{
                    firebaseAuth.signInWithEmailAndPassword(emailAddr, pass)
                            .addOnCompleteListener(MainActivity.this, new OnCompleteListener<AuthResult>() {
                                @Override
                                public void onComplete(@NonNull Task<AuthResult> task) {
                                    if (task.isSuccessful()) {
                                        Toast.makeText(getApplicationContext(),"Giriş Başarılı.", Toast.LENGTH_LONG).show();
                                        toMainPage(view);
                                    } else {
                                        Toast.makeText(getApplicationContext(),"Kullanıcı adı veya şifre hatalı !!", Toast.LENGTH_LONG).show();
                                        Log.e("Giriş Hatası",task.getException().getMessage());
                                    }
                                }
                            });
                }

            }
        });
        signIn = findViewById(R.id.signIn);
        signIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(final View view) {
                toSignUpPage(view);
            }
        });
    }

    public void toMainPage(View view) {
        Intent intent = new Intent(getApplicationContext(), MainPage.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(intent);
    }
    public void toSignUpPage(View view) {
        Intent intent = new Intent(getApplicationContext(), SignUpPage.class);
        startActivity(intent);
    }
}
