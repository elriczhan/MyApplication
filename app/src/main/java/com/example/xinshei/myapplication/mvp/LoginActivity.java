package com.example.xinshei.myapplication.mvp;

import android.os.Bundle;
import android.support.annotation.Nullable;
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
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.mvp_login);
        initView();
    }

    private void initView() {
        username = (EditText) findViewById(R.id.username);
        password = (EditText) findViewById(R.id.password);
        Button login = (Button) findViewById(R.id.login);
        progressBar = (ProgressBar) findViewById(R.id.progress);
        result = (TextView) findViewById(R.id.result);

        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                presenter.login();
            }
        });
    }


    @Override
    public void showLoading() {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                progressBar.setVisibility(View.VISIBLE);
            }
        });
    }

    @Override
    public void hideLoading() {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                progressBar.setVisibility(View.INVISIBLE);
            }
        });

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
