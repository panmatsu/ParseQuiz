package com.matsu.parsequiz;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.Window;

import com.nifty.cloud.mb.core.DoneCallback;
import com.nifty.cloud.mb.core.NCMB;
import com.nifty.cloud.mb.core.NCMBException;
import com.nifty.cloud.mb.core.NCMBObject;

public class TitleActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);//タイトルバーの非表示
        setContentView(R.layout.activity_title);

        //MBasSを扱うために最初にSDKの初期化を行う
        //(Context, "APP_KEY", "CLIENT_KEY")
        NCMB.initialize(this,
                "1c8f4a612d9ea12236e3e6a9f55f62e950604615c486d2627548f83321582b93",
                "d4d193f3c063c9374c127e9bb0fcef7f95188a1bfee8212c552f95fe048ad8e3");
        NCMBObject obj = new NCMBObject("TestClass");
        obj.put("message", "Hello, NCMB!");
        obj.saveInBackground(new DoneCallback() {
            @Override
            public void done(NCMBException e) {
                if (e != null) {
                    //保存失敗
                } else {
                    //保存成功
                }
            }
        });
    }

    public void onClick(View v){
        //Quiz画面へ繊維
        Intent intent = new Intent(TitleActivity.this,QuizActivity.class);
        startActivity(intent);
    }
}

