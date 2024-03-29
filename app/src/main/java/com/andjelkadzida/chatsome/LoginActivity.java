package com.andjelkadzida.chatsome;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class LoginActivity extends AppCompatActivity
{
    FirebaseAuth firebaseAuth;

    DatabaseReference databaseReference;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        firebaseAuth = FirebaseAuth.getInstance();
        databaseReference = FirebaseDatabase.getInstance().getReference().child("Users");

        TextView forgotPass = findViewById(R.id.forgotPass);
        TextView newAcc = findViewById(R.id.newUser);
        EditText emailEdit = findViewById(R.id.loginMail);
        EditText passEdit = findViewById(R.id.loginPass);
        Button btnLogin = findViewById(R.id.btnLogin);
        Button btnPhoneLogin = findViewById(R.id.btnPhoneLogin);
        ProgressDialog progressDialog = new ProgressDialog(this, R.style.CustomDialog);

        //Login korisnika tj dogadjaj koji se okida kada korisnik klikne login dugme
        btnLogin.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                String email = emailEdit.getText().toString();
                String pass = passEdit.getText().toString();

                if(TextUtils.isEmpty(email) || TextUtils.isEmpty(pass))
                {
                    Toast.makeText(LoginActivity.this, getResources().getString(R.string.mandatoryFields), Toast.LENGTH_SHORT).show();
                }
                else
                {
                    //Progres dijalog
                    progressDialog.setTitle(getResources().getString(R.string.login));
                    progressDialog.setIcon(R.drawable.ic_login);
                    progressDialog.setMessage(getResources().getString(R.string.loginInProgress));
                    progressDialog.setCanceledOnTouchOutside(true);
                    progressDialog.show();

                    firebaseAuth.signInWithEmailAndPassword(email, pass).addOnCompleteListener(task -> {
                        if(task.isSuccessful())
                        {
                            progressDialog.dismiss();

                            Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                            startActivity(intent);
                            finish();

                            Toast.makeText(LoginActivity.this, getResources().getString(R.string.user) + firebaseAuth.getCurrentUser().getEmail() + getResources().getString(R.string.successfulLogin), Toast.LENGTH_SHORT).show();
                        }
                        else
                        {
                            Toast.makeText(LoginActivity.this, getResources().getString(R.string.invalidMailOrPass), Toast.LENGTH_SHORT).show();
                            progressDialog.dismiss();
                        }
                    });
                }
            }
        });


        btnPhoneLogin.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                Intent intent = new Intent(LoginActivity.this, PhoneLoginActivity.class);
                startActivity(intent);
            }
        });

    }

    public void redirectToRegister(View view)
    {
        Intent intent = new Intent(LoginActivity.this, RegistrationActivity.class);
        startActivity(intent);
    }

    public void resetPassword(View view)
    {
        Intent intent = new Intent(LoginActivity.this, ResetPasswordActivity.class);
        startActivity(intent);
    }
}