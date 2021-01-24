package com.example.notes.view;

import android.app.Activity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.EditText;

import com.example.notes.R;
import com.example.notes.presenter.LoginPresenter;

import javax.inject.Inject;

import androidx.appcompat.app.AppCompatActivity;
import dagger.android.AndroidInjection;

public class LoginActivity extends AppCompatActivity implements ILoginView {

    @Inject
    LoginPresenter loginPresenter;
    private EditText etUsername;
    private EditText etPassword;
    private Button btnLogIn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        AndroidInjection.inject(this);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        init();
    }

    private void init() {
        etUsername = findViewById(R.id.etUsername);
        etPassword = findViewById(R.id.etPassword);
        btnLogIn = findViewById(R.id.btnLogIn);

        btnLogIn.setOnClickListener(v -> loginPresenter.sendUserToServer(etUsername.getText().toString(), etPassword.getText().toString()));
    }

    @Override
    public Activity getActivity() {
        return this;
    }
}