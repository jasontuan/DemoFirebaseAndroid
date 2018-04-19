package com.example.work.demofirebase;

import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.TextView;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    @BindView(R.id.tv_title)
    TextView tvTitle;
    @BindView(R.id.tv_postUser)
    TextView tvPostUser;
    @BindView(R.id.tv_listuser)
    TextView tvListuser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);

        tvListuser.setOnClickListener(this);
        tvPostUser.setOnClickListener(this);

        replaceFragmentContent(UserListFragment.newInstance());
    }

    private void replaceFragmentContent(Fragment fragment) {
        if (fragment != null) {
            FragmentManager fragmentManager = getSupportFragmentManager();
            FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
            fragmentTransaction.replace(R.id.rl_fragment, fragment);
            fragmentTransaction.commit();
        }
    }

    private void setColorText(int color) {
        switch (color) {
            case 1:
                tvPostUser.setBackgroundColor(Color.parseColor("#ffd500"));
                tvListuser.setBackgroundColor(Color.parseColor("#ffffff"));
                tvTitle.setText("POST USER");
                replaceFragmentContent(PostUserFragment.newInstance());
                break;
            case 2:
                tvPostUser.setBackgroundColor(Color.parseColor("#ffffff"));
                tvListuser.setBackgroundColor(Color.parseColor("#ffd500"));
                tvTitle.setText("USER LIST");
                replaceFragmentContent(UserListFragment.newInstance());
                break;
            default:
                tvPostUser.setBackgroundColor(Color.parseColor("#ffffff"));
                tvListuser.setBackgroundColor(Color.parseColor("#ffd500"));
                replaceFragmentContent(UserListFragment.newInstance());
                break;
        }
    }

    @Override
    public void onClick(View v) {
        if (v == tvPostUser) {
            setColorText(1);
        } else if (v == tvListuser) {
            setColorText(2);
        }
    }
}
