package com.matsu.parsequiz;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;

import com.nifty.cloud.mb.core.FetchCallback;
import com.nifty.cloud.mb.core.FindCallback;
import com.nifty.cloud.mb.core.NCMB;
import com.nifty.cloud.mb.core.NCMBException;
import com.nifty.cloud.mb.core.NCMBObject;
import com.nifty.cloud.mb.core.NCMBQuery;
import com.parse.Parse;

import java.util.List;

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

        //QuizDBクラスにアクセス
        final NCMBObject obj = new NCMBObject("QuizDB");

        String objectId[] = {"exiTla2x8SZn5jWd",
                "M4zZMzsr7jJorz6b",
                "U6Qk6csyZk5xwURf",
                "G5ZrQ5Cx08u087Sg",
                "aPFrNxMe3x0tj2Zj",
                "w6zTHxdzQgPzcinj",
                "yjyL6VHX0GF2aR8k"};

        obj.setObjectId(objectId[0]);
        obj.fetchInBackground(new FetchCallback<NCMBObject>() {
            @Override
            public void done(NCMBObject object, NCMBException e) {
                if (e != null) {
                    //エラー時の処理
                } else {
                    String string = obj.getString("Text");
                    Log.d("TESTTTTTTT", string);
                }
            }
        });


        obj.setObjectId(objectId[1]);
        obj.fetchInBackground(new FetchCallback<NCMBObject>() {
            @Override
            public void done(NCMBObject object, NCMBException e) {
                if (e != null) {
                    //エラー時の処理
                } else {
                    String string = obj.getString("Text");
                    Log.d("TESTTTTTTT", string);
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

