package com.example.jobfinderclient.viewmodel;

import android.app.Application;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.util.Log;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.MutableLiveData;

import com.example.jobfinderclient.MainActivity;
import com.example.jobfinderclient.client.AccountClient;
import com.example.jobfinderclient.client.UserClient;
import com.example.jobfinderclient.common.Constant;
import com.example.jobfinderclient.model.account.Account;
import com.example.jobfinderclient.model.account.Role;
import com.example.jobfinderclient.model.person.User;


import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class AccountViewModel extends AndroidViewModel {
    private Context context;

    private MutableLiveData<Boolean> isProgress;
    public AccountViewModel(@NonNull Application application) {
        super(application);
        context = application.getApplicationContext();
        isProgress = new MutableLiveData<>();
    }

    public void findAccountByUsernameAndPassword(String username, String password){
        AccountClient client = AccountClient.getINSTANCE();
        client.findAccountByUsernameAndPassword(username,password).enqueue(new Callback<Account>() {
            @Override
            public void onResponse(Call<Account> call, Response<Account> response) {
                isProgress.setValue(false);
                if(response.isSuccessful()){
                    Account account = response.body();
                    Role role = account.getRole();
                    if(role.getId()==3 || role.getId()==4){
                        Toast.makeText(context,"Đăng nhập thành công",Toast.LENGTH_SHORT).show();
                        saveAccountSession(account);
                        Intent intent = new Intent();
                        intent.setClass(context, MainActivity.class);
                        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                        context.startActivity(intent);
                    }
                    else{
                        Toast.makeText(context,"Ứng dụng không hỗ trợ tài khoản người tuyển dụng",Toast.LENGTH_SHORT).show();
                    }

                }
            }
            @Override
            public void onFailure(Call<Account> call, Throwable t) {
                isProgress.setValue(false);
                Log.d("AccountViewModel", "onFailure",t);
                if(t.getLocalizedMessage().startsWith("failed to connect")){
                    Toast.makeText(context,"Không thể kết nối tới server",Toast.LENGTH_SHORT).show();
                }
                else if(t.getLocalizedMessage().startsWith("End of input")){
                    Toast.makeText(context,"Tài khoản hoặc mật khẩu không chính xác",Toast.LENGTH_SHORT).show();
                }
                else{
                    Toast.makeText(context,"Không có kết nối mạng. Thử lại sau",Toast.LENGTH_SHORT).show();
                }

            }
        });
    }

    private void saveAccountSession(Account account) {
        UserClient userClient = UserClient.getINSTANCE();
        userClient.findByAccountId(account.getId()).enqueue(new Callback<User>() {
            @Override
            public void onResponse(Call<User> call, Response<User> response) {
                if(response.isSuccessful()){
                    User user = response.body();
                    SharedPreferences sharedPreferences = context.getSharedPreferences(
                            Constant.SHARED_ACCOUNT_SESSION, Context.MODE_PRIVATE);
                    SharedPreferences.Editor editor  = sharedPreferences.edit();
                    editor.putLong("id",account.getId());
                    editor.putLong("userId",user.getId());
                    editor.putString("roleName",account.getRole().getName());
                    editor.putLong("profileId",user.getProfile().getId());
                    editor.commit();
                }
            }

            @Override
            public void onFailure(Call<User> call, Throwable t) {
                Log.e("AccountViewModel", "onFailure find user by Account Id: ",t );

            }
        });

    }

    public MutableLiveData<Boolean> getIsProgress() {
        return isProgress;
    }
}
