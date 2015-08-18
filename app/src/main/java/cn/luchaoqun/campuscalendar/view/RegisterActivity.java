package cn.luchaoqun.campuscalendar.view;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import butterknife.Bind;
import butterknife.ButterKnife;
import cn.luchaoqun.campuscalendar.R;
import cn.luchaoqun.campuscalendar.commen.ACache;
import cn.luchaoqun.campuscalendar.commen.BaseActivity;
import cn.luchaoqun.campuscalendar.contants.RestContants;
import cn.luchaoqun.campuscalendar.model.User;
import cn.luchaoqun.campuscalendar.model.UserReturn;
import cn.luchaoqun.campuscalendar.rest.RestClient;
import retrofit.Callback;
import retrofit.RetrofitError;
import retrofit.client.Response;

/**
 * Created by Administrator on 2015/8/10.
 */
public class RegisterActivity extends BaseActivity implements View.OnClickListener {
    @Bind(R.id.register_username)
    EditText registerUsername;
    @Bind(R.id.register_btn)
    Button registerBtn;
    @Bind(R.id.to_login_btn)
    Button toLoginBtn;

    private String username;
    private ACache aCache;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        ButterKnife.bind(this);
        aCache = ACache.get(this);

        initEvent();
    }

    private void initEvent() {
        registerBtn.setOnClickListener(this);
        toLoginBtn.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.register_btn:
                username = registerUsername.getText().toString();
                if (username.length() == 0) {
                    Snackbar.make(registerUsername,"请输入昵称~",Snackbar.LENGTH_SHORT).show();
                } else {
                    User user = new User();
                    user.setUsername(username);
                    user.setPassword(RestContants.DEFAULT_PASSWORD);

                    RestClient.getInstance().postRegister(user, new Callback<UserReturn>() {
                        @Override
                        public void success(UserReturn userReturn, Response response) {
                            aCache.put("token", userReturn.getSessionToken());
                            aCache.put("objectId", userReturn.getObjectId());
                            aCache.put("username", username);
                            Intent homeIntent = new Intent(RegisterActivity.this, HomeActivity.class);
                            startActivity(homeIntent);
                        }

                        @Override
                        public void failure(RetrofitError error) {
                            Snackbar.make(registerBtn,"注册失败，请检查网络是否连接",Snackbar.LENGTH_SHORT).show();
                        }
                    });
                }
                break;
            case R.id.to_login_btn:
                username = registerUsername.getText().toString();
                if (username.length() == 0) {
                    Snackbar.make(registerUsername,"请输入昵称~",Snackbar.LENGTH_SHORT).show();
                } else {
                    RestClient.getInstance().getLogin(username, RestContants.DEFAULT_PASSWORD, new Callback<UserReturn>() {
                        @Override
                        public void success(UserReturn userReturn, Response response) {
                            aCache.put("token", userReturn.getSessionToken());
                            aCache.put("objectId", userReturn.getObjectId());
                            aCache.put("username", username);
                            Intent homeIntent = new Intent(RegisterActivity.this, HomeActivity.class);
                            startActivity(homeIntent);
                        }

                        @Override
                        public void failure(RetrofitError error) {
                            Snackbar.make(toLoginBtn,"登录失败，请检查昵称是否正确？",Snackbar.LENGTH_SHORT).show();
                        }
                    });
                }
                break;
            default:
                break;
        }
    }
}
