package com.dev.asynctaskdemo;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class NewUserActivity extends AppCompatActivity implements View.OnClickListener {

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
        connectBtn.setText("Ajouter");
        connectBtn.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        if (view.getId() == R.id.connectBtn) {
            String login = loginEdt.getText().toString(), password = passwordEdt.getText().toString();
            NewUserAsyncTask newUserAsyncTask = new NewUserAsyncTask(this);
            newUserAsyncTask.execute(login, password);
        }
    }

    public void onLoginResponse(boolean exceptionOccurred) {
        textView.setText(exceptionOccurred ? "Une exception s'est produite" : "User ajouté avec succès");
    }
}
