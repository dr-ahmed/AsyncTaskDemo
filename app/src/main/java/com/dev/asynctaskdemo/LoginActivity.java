package com.dev.asynctaskdemo;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class LoginActivity extends AppCompatActivity implements View.OnClickListener {

    private TextView textView;
    private EditText loginEdt, passwordEdt;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        initViews();
    }

    private void initViews() {
        textView = findViewById(R.id.resultTxt);
        loginEdt = findViewById(R.id.loginEdt);
        passwordEdt = findViewById(R.id.passwordEdt);
        Button connectBtn = findViewById(R.id.connectBtn);
        connectBtn.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        if (view.getId() == R.id.connectBtn) {
            String login = loginEdt.getText().toString(), password = passwordEdt.getText().toString();
            LoginAsyncTask loginAsyncTask = new LoginAsyncTask(this);
            loginAsyncTask.execute(login, password);
        }
    }

    public void onLoginResponse(String result, boolean exceptionOccurred, boolean userIsConfirmed) {
        if (exceptionOccurred)
            textView.setText("Une exception s'est produite");
        else {
            if (userIsConfirmed)
                textView.setText("Bienvenue cher utilisateur");
            else {
                if (result.isEmpty())
                    textView.setText("Username ou login incorrect");
                else
                    textView.setText("L'entête de JSON est invalide");
            }
        }
    }
}
