package com.stx.xhb.demo;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import com.blankj.utilcode.util.FragmentUtils;
import com.stx.xhb.demo.fragment.BannerFragment;

import java.util.ArrayList;
import java.util.List;

/**
 * @author: xiaohaibin.
 * @time: 2019/10/17
 * @mail:xhb_199409@163.com
 * @github:https://github.com/xiaohaibin
 * @describe: Fragment中使用
 */
public class UserInFragmentActivity extends AppCompatActivity {

    private List<Fragment> mFragmentList;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_in_fragment);
        RadioGroup rbTab = findViewById(R.id.rg_tab);
        RadioButton radioButton= (RadioButton) rbTab.getChildAt(0);
        radioButton.setChecked(true);
        mFragmentList=new ArrayList<>();
        mFragmentList.add(BannerFragment.newInstance());
        mFragmentList.add(BannerFragment.newInstance());
        mFragmentList.add(BannerFragment.newInstance());
        mFragmentList.add(BannerFragment.newInstance());
        FragmentUtils.add(getSupportFragmentManager(),mFragmentList,R.id.fragment_content,0);
        rbTab.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                switch (checkedId) {
                    case R.id.tab_1:
                        FragmentUtils.showHide(0,mFragmentList);
                        break;
                    case R.id.tab_2:
                        FragmentUtils.showHide(1,mFragmentList);
                        break;
                    case R.id.tab_3:
                        FragmentUtils.showHide(2,mFragmentList);
                        break;
                    case R.id.tab_4:
                        FragmentUtils.showHide(3,mFragmentList);
                        break;
                    default:
                        break;
                }
            }
        });
    }
}
