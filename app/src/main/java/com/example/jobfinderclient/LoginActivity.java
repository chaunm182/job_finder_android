package com.example.jobfinderclient;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.jobfinderclient.model.account.Account;
import com.example.jobfinderclient.model.person.FullName;
import com.example.jobfinderclient.model.person.User;
import com.example.jobfinderclient.model.profile.Profile;
import com.example.jobfinderclient.viewmodel.AccountViewModel;
import com.facebook.AccessToken;
import com.facebook.AccessTokenTracker;
import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.GraphRequest;
import com.facebook.GraphResponse;
import com.facebook.login.LoginResult;
import com.facebook.login.widget.LoginButton;

import org.json.JSONException;
import org.json.JSONObject;

public class LoginActivity extends AppCompatActivity {

    EditText etUsername, etPassword;
    Button btnLogin;
    ProgressBar progressBar;

    LoginButton btnLoginViaFacebook;

    private AccountViewModel accountViewModel;
    private CallbackManager callbackManager;

    private User user;


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
        checkLoginStatus();
        accountViewModel.getIsProgress().observe(this, new Observer<Boolean>() {
            @Override
            public void onChanged(Boolean aBoolean) {
                if(aBoolean) progressBar.setVisibility(View.VISIBLE);
                else progressBar.setVisibility(View.GONE);
            }
        });

        accountViewModel.getIsEmailExist().observe(this, new Observer<Boolean>() {
            @Override
            public void onChanged(Boolean aBoolean) {
                if(!aBoolean){
                    //user is not exist in system
                    accountViewModel.registerNewUser(user);
                }
            }
        });

    }

    public void addControls(){
        etUsername = findViewById(R.id.etUsername);
        etPassword = findViewById(R.id.etPassword);
        btnLogin = findViewById(R.id.btnLogin);
        progressBar = findViewById(R.id.progressBar);

        //login via facebook
        btnLoginViaFacebook = findViewById(R.id.btnLoginViaFacebook);
        btnLoginViaFacebook.setReadPermissions("email","public_profile");
        callbackManager = CallbackManager.Factory.create();
        btnLoginViaFacebook.registerCallback(callbackManager, new FacebookCallback<LoginResult>() {
            @Override
            public void onSuccess(LoginResult loginResult) {
                loadUser(loginResult.getAccessToken());
            }

            @Override
            public void onCancel() {
                Toast.makeText(LoginActivity.this,"Đăng nhập không thành công",Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onError(FacebookException error) {
                Log.e("LoginActivity", "onError: ",error );
            }
        });
    }

    public void addEvents(){
        btnLogin.setOnClickListener(onClickListener);
    }

    public void hideKeyBoard(View v){
        InputMethodManager inputMethodManager = (InputMethodManager) getSystemService(INPUT_METHOD_SERVICE);
        inputMethodManager.hideSoftInputFromWindow(v.getWindowToken(),0);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        callbackManager.onActivityResult(requestCode,resultCode,data);
        super.onActivityResult(requestCode, resultCode, data);
    }

    private void loadUser(AccessToken accessToken){
        GraphRequest graphRequest = GraphRequest.newMeRequest(accessToken, new GraphRequest.GraphJSONObjectCallback() {
            @Override
            public void onCompleted(JSONObject object, GraphResponse response) {
                try {
                    String firstName = object.getString("first_name");
                    String lastName = object.getString("last_name");
                    String email = object.getString("email");
                    String id = object.getString("id");
                    String imageUrl = "https://graph.facebook.com/"+id+"/picture?type=normal";
                    user = new User();
                    Account account= new Account();
                    FullName fullName = new FullName();
                    fullName.setFirstName(firstName);
                    fullName.setLastName(lastName);
                    user.setFullName(fullName);
                    account.setEmail(email);
                    account.setUsername(email);
                    account.setPassword(id);
                    user.setAccount(account);
                    Profile profile = new Profile();
                    profile.setAvatar(imageUrl);
                    user.setProfile(profile);

                    accountViewModel.findUserByAccountEmail(email);

                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        });

        Bundle bundle = new Bundle();
        bundle.putString("fields","first_name,last_name,email,id");
        graphRequest.setParameters(bundle);
        graphRequest.executeAsync();
    }


    private void checkLoginStatus(){
        if(AccessToken.getCurrentAccessToken()!=null){
            Toast.makeText(LoginActivity.this,"Đăng nhập thành công",Toast.LENGTH_SHORT).show();
            Intent intent = new Intent();
            intent.setClass(this, MainActivity.class);
            startActivity(intent);
            finish();
        }
    }
}