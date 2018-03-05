package com.example.xinshei.myapplication.mvp;

import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.elriczhan.basecore.base.BaseMVPActivity;
import com.example.xinshei.myapplication.R;
import com.example.xinshei.myapplication.mvp.contract.LoginContract;

public class LoginActivity extends BaseMVPActivity<loginPresenter, loginModel> implements LoginContract.ILoginView {

    private EditText username;
    private EditText password;
    private ProgressBar progressBar;
    private TextView result;


    @Override
    protected void initView() {
        username = findViewById(R.id.username);
        password = findViewById(R.id.password);
        Button login = findViewById(R.id.login);
        progressBar = findViewById(R.id.progress);
        result = findViewById(R.id.result);

        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mPresenter.login(LoginActivity.this);
            }
        });
    }

    @Override
    protected void loadData() {
        ShowSuccessView();

    }

    @Override
    protected View getContentView() {
        return findViewById(R.id.content);
    }

    @Override
    protected int getRootView() {
        return R.layout.mvp_login;
    }


    @Override
    public String getUsername() {
        return username.getText().toString().trim();
    }

    @Override
    public String getPassword() {
        return password.getText().toString().trim();
    }

    @Override
    public void showResult(final String result1) {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                result.setText(result1);
                username.setText("");
                password.setText("");
            }
        });
    }
}
