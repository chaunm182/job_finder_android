package com.example.jobfinderclient;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import android.os.Bundle;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.example.jobfinderclient.viewmodel.AccountViewModel;

public class LoginActivity extends AppCompatActivity {

    EditText etUsername, etPassword;
    Button btnLogin;
    TextView tvShowRegisterPage;
    ProgressBar progressBar;

    private AccountViewModel accountViewModel;


    private View.OnClickListener onClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            switch (v.getId()){
                case R.id.btnLogin:{
                    hideKeyBoard(v);
                    accountViewModel.getIsProgress().setValue(true);
                    String username = etUsername.getText().toString();
                    String password = etPassword.getText().toString();
                    accountViewModel.findAccountByUsernameAndPassword(username,password);
                    break;
                }
            }
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        accountViewModel = ViewModelProviders.of(this).get(AccountViewModel.class);
        addControls();
        addEvents();
        accountViewModel.getIsProgress().observe(this, new Observer<Boolean>() {
            @Override
            public void onChanged(Boolean aBoolean) {
                if(aBoolean) progressBar.setVisibility(View.VISIBLE);
                else progressBar.setVisibility(View.GONE);
            }
        });

    }

    public void addControls(){
        etUsername = findViewById(R.id.etUsername);
        etPassword = findViewById(R.id.etPassword);
        btnLogin = findViewById(R.id.btnLogin);
        progressBar = findViewById(R.id.progressBar);
        tvShowRegisterPage = findViewById(R.id.tvShowRegisterPage);
    }

    public void addEvents(){
        btnLogin.setOnClickListener(onClickListener);
    }

    public void hideKeyBoard(View v){
        InputMethodManager inputMethodManager = (InputMethodManager) getSystemService(INPUT_METHOD_SERVICE);
        inputMethodManager.hideSoftInputFromWindow(v.getWindowToken(),0);
    }
}