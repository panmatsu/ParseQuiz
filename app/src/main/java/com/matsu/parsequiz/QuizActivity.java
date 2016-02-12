package com.matsu.parsequiz;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.nifty.cloud.mb.core.FetchCallback;
import com.nifty.cloud.mb.core.NCMB;
import com.nifty.cloud.mb.core.NCMBException;
import com.nifty.cloud.mb.core.NCMBObject;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class QuizActivity extends Activity {

    String Answer;    //正解
    int score;        //点数
    int cntStage = 0; //通しでの並び番号
    int quiz_max = 7; //Quizの最大数
    //Questionの並び順
    List<Integer> Stage = new ArrayList<Integer>();
    //選択肢Buttonの取得
    Button[] btChoice = new Button[4];
    //CountDownの宣言
    MyCountDownTimer cdt;
    TextView timer;
    //CSVから読み取ったQuizデータ収納:[Stage][0~4]: [0]問題、[1]正解、[2~4]不正解*3の順
    String[][] QuizText = new String[quiz_max][5];
    //HPバー
    ProgressBar HpBar;
    int hpbar_max;//HPBarのMAX値
    Intent intent;     //インテント
    //QuizDBのobjectId
    String objectId[] = {"exiTla2x8SZn5jWd",
                         "M4zZMzsr7jJorz6b",
                         "U6Qk6csyZk5xwURf",
                         "G5ZrQ5Cx08u087Sg",
                         "aPFrNxMe3x0tj2Zj",
                         "w6zTHxdzQgPzcinj",
                         "yjyL6VHX0GF2aR8k"};
    int count;//?

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_quiz);

        //CountDownTextの取得
        timer = (TextView) findViewById(R.id.textTimer);

        //ListにQuestionの並び順を代入
        for (int i = 0; i < quiz_max; i++) {
            Stage.add(i);
        }

        //選択肢Buttonの取得
        btChoice[0] = (Button) findViewById(R.id.bt1);
        btChoice[1] = (Button) findViewById(R.id.bt2);
        btChoice[2] = (Button) findViewById(R.id.bt3);
        btChoice[3] = (Button) findViewById(R.id.bt4);

        //HPbバーの取得
        HpBar = (ProgressBar) findViewById(R.id.progreaabar);
        hpbar_max = 30;
        HpBar.setMax(hpbar_max);
    }

    @Override
    protected void onResume() {
        super.onResume();

        //niftyCloudからデータを取得
        setQuizData();

        //初期値30000ミリ秒(=30秒)
        cdt = new MyCountDownTimer(30000, 100);
        //問題文のシャッフル
        setStage();
        //選択肢のセット
        setQuestion();
        //カウントダウンスタート
        cdt.start();
    }

    private void setQuizData(){

        //NCMBを扱うために最初にSDKの初期化を行う
        //(Context, "APP_KEY", "CLIENT_KEY")
        NCMB.initialize(this,
                "1c8f4a612d9ea12236e3e6a9f55f62e950604615c486d2627548f83321582b93",
                "d4d193f3c063c9374c127e9bb0fcef7f95188a1bfee8212c552f95fe048ad8e3");

        /**
         * TODO:for文ができなかった…
         */

            //QuizDBクラスにアクセス
            final NCMBObject obj = new NCMBObject("QuizDB");
            //objectIdをセットする
            obj.setObjectId(objectId[0]);
            obj.fetchInBackground(new FetchCallback<NCMBObject>() {
                @Override
                public void done(NCMBObject object, NCMBException e) {
                    if (e != null) {
                        //エラー時の処理
                        Toast.makeText(getApplicationContext(), "データを読み取れませんでした", Toast.LENGTH_LONG).show();
                        //Toastを出してTitleへ戻る
                        intent = new Intent(QuizActivity.this, TitleActivity.class);
                        startActivity(intent);
                    } else {
                        Log.d("IIIIIIIIIIIIIII","AAAAAAAAAAAAAAAAAAAAAAAAAA");
                        //DBからQuizTextに代入
                        QuizText[0][0] = obj.getString("Text");
                        QuizText[0][1] = obj.getString("Answer");
                        QuizText[0][2] = obj.getString("Dummy_1");
                        QuizText[0][3] = obj.getString("Dummy_2");
                        QuizText[0][4] = obj.getString("Dummy_3");
                        Log.d("Test",QuizText[0][1]+QuizText[0][0]);
                    }
                }
            });

        //objectIdをセットする
        obj.setObjectId(objectId[1]);
        obj.fetchInBackground(new FetchCallback<NCMBObject>() {
            @Override
            public void done(NCMBObject object, NCMBException e) {
                if (e != null) {
                    Log.d("AAAAAAAAAAAAAAA","AAAAAAAAAAAAAAAAAAAAAAAAAA");

                } else {
                    Log.d("IIIIIIIIIIIIIII","AAAAAAAAAAAAAAAAAAAAAAAAAA");
                    //DBからQuizTextに代入
                    QuizText[1][0] = obj.getString("Text");
                    QuizText[1][1] = obj.getString("Answer");
                    QuizText[1][2] = obj.getString("Dummy_1");
                    QuizText[1][3] = obj.getString("Dummy_2");
                    QuizText[1][4] = obj.getString("Dummy_3");
                    Log.d("Test",QuizText[1][1]+QuizText[1][0]);
                }
            }
        });

        //objectIdをセットする
        obj.setObjectId(objectId[2]);
        obj.fetchInBackground(new FetchCallback<NCMBObject>() {
            @Override
            public void done(NCMBObject object, NCMBException e) {
                if (e != null) {
                    Log.d("AAAAAAAAAAAAAAA","AAAAAAAAAAAAAAAAAAAAAAAAAA");

                } else {
                    Log.d("IIIIIIIIIIIIIII","AAAAAAAAAAAAAAAAAAAAAAAAAA");
                    //DBからQuizTextに代入
                    QuizText[2][0] = obj.getString("Text");
                    QuizText[2][1] = obj.getString("Answer");
                    QuizText[2][2] = obj.getString("Dummy_1");
                    QuizText[2][3] = obj.getString("Dummy_2");
                    QuizText[2][4] = obj.getString("Dummy_3");
                    Log.d("Test",QuizText[2][1]+QuizText[2][0]);
                }
            }
        });

        //objectIdをセットする
        obj.setObjectId(objectId[3]);
        obj.fetchInBackground(new FetchCallback<NCMBObject>() {
            @Override
            public void done(NCMBObject object, NCMBException e) {
                if (e != null) {
                    Log.d("AAAAAAAAAAAAAAA","AAAAAAAAAAAAAAAAAAAAAAAAAA");

                } else {
                    Log.d("IIIIIIIIIIIIIII","AAAAAAAAAAAAAAAAAAAAAAAAAA");
                    //DBからQuizTextに代入
                    QuizText[3][0] = obj.getString("Text");
                    QuizText[3][1] = obj.getString("Answer");
                    QuizText[3][2] = obj.getString("Dummy_1");
                    QuizText[3][3] = obj.getString("Dummy_2");
                    QuizText[3][4] = obj.getString("Dummy_3");
                    Log.d("Test",QuizText[3][1]+QuizText[3][0]);
                }
            }
        });

        //objectIdをセットする
        obj.setObjectId(objectId[4]);
        obj.fetchInBackground(new FetchCallback<NCMBObject>() {
            @Override
            public void done(NCMBObject object, NCMBException e) {
                if (e != null) {
                    Log.d("AAAAAAAAAAAAAAA","AAAAAAAAAAAAAAAAAAAAAAAAAA");

                } else {
                    Log.d("IIIIIIIIIIIIIII","AAAAAAAAAAAAAAAAAAAAAAAAAA");
                    //DBからQuizTextに代入
                    QuizText[4][0] = obj.getString("Text");
                    QuizText[4][1] = obj.getString("Answer");
                    QuizText[4][2] = obj.getString("Dummy_1");
                    QuizText[4][3] = obj.getString("Dummy_2");
                    QuizText[4][4] = obj.getString("Dummy_3");
                    Log.d("Test",QuizText[4][1]+QuizText[4][0]);
                }
            }
        });

        //objectIdをセットする
        obj.setObjectId(objectId[5]);
        obj.fetchInBackground(new FetchCallback<NCMBObject>() {
            @Override
            public void done(NCMBObject object, NCMBException e) {
                if (e != null) {
                    Log.d("AAAAAAAAAAAAAAA","AAAAAAAAAAAAAAAAAAAAAAAAAA");

                } else {
                    Log.d("IIIIIIIIIIIIIII","AAAAAAAAAAAAAAAAAAAAAAAAAA");
                    //DBからQuizTextに代入
                    QuizText[5][0] = obj.getString("Text");
                    QuizText[5][1] = obj.getString("Answer");
                    QuizText[5][2] = obj.getString("Dummy_1");
                    QuizText[5][3] = obj.getString("Dummy_2");
                    QuizText[5][4] = obj.getString("Dummy_3");
                    Log.d("Test",QuizText[5][1]+QuizText[5][0]);
                }
            }
        });

        //objectIdをセットする
        obj.setObjectId(objectId[6]);
        obj.fetchInBackground(new FetchCallback<NCMBObject>() {
            @Override
            public void done(NCMBObject object, NCMBException e) {
                if (e != null) {
                    Log.d("AAAAAAAAAAAAAAA","AAAAAAAAAAAAAAAAAAAAAAAAAA");

                } else {
                    Log.d("IIIIIIIIIIIIIII","AAAAAAAAAAAAAAAAAAAAAAAAAA");
                    //DBからQuizTextに代入
                    QuizText[6][0] = obj.getString("Text");
                    QuizText[6][1] = obj.getString("Answer");
                    QuizText[6][2] = obj.getString("Dummy_1");
                    QuizText[6][3] = obj.getString("Dummy_2");
                    QuizText[6][4] = obj.getString("Dummy_3");
                    Log.d("Test",QuizText[6][1]+QuizText[6][0]);
                }
            }
        });

    }

    private void setStage() {
        //Questionの並び順をシャッフル
        Collections.shuffle(Stage);
    }

    private void setQuestion() {

        // 画面↑にあるテキストを「クイズNo. + 問題No で表示
        TextView quizBar = (TextView) findViewById(R.id.textNo);
        quizBar.setText("クイズNo." + Integer.toString(cntStage + 1));

        //問題選択肢の配列
        List<String> Choice = new ArrayList<String>();
        //Databaseから取得したデータを変数にセット
        String questionTitle = QuizText[Stage.get(cntStage)][0];//問題文
        for (int i = 0; i < 4; i++) {
            Choice.add(QuizText[Stage.get(cntStage)][i + 1]);      //選択肢
        }
        Answer = Choice.get(0);       //答え

        //選択肢の並びをシャッフル
        Collections.shuffle(Choice);
        //改行を追加
        //questionTitle = questionTitle.replaceAll("の", "\n\nの");

        //テキストに問題文と質問を配置
        TextView TextQuestion = (TextView) findViewById(R.id.textQuestion);
        TextQuestion.setText(questionTitle);
        for (int i = 0; i < 4; i++) {
            btChoice[i].setText(Choice.get(i));
        }

        //ボタンの有効化
        for (int i = 0; i < 4; i++) {
            btChoice[i].setEnabled(true);
        }
    }

    public void onClick(View v) {

        //ダイアログ表示中はカウントダウン停止
        cdt.cancel();

        //ステージ数をカウント
        cntStage++;

        //ダイアログ
        AlertDialog.Builder alert = new AlertDialog.Builder(this);
        //Button連打を防ぐために一旦無効化
        for (int i = 0; i < 4; i++) {
            btChoice[i].setEnabled(false);
        }
        //Dialogの中でのBackKeyの無効化
        alert.setCancelable(false);
        //ダイアログのTitleの表示
        alert.setTitle("Question:" + cntStage);

        //ダイアログのボタンを押した場合の処理
        //正解の場合
        if (((Button) v).getText().equals(Answer)) {
            score++;  //点数Plus
            alert.setMessage("正解だよ＾＾\n点数：＋１\nただいまの合計：" + score);
        }
        //不正解の場合
        else {
            alert.setMessage("残念不正解＾＾；\n点数：＋０\nただいまの合計：" + score);
        }

        //「次へ進む」ボタンの表示
        alert.setPositiveButton("次へ進む", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {
                /**
                 * 次へ進むボタンが押された時の処理
                 */
                //quiz_max以下の場合
                if (cntStage < quiz_max) {
                    //問題画面を更新させる
                    setQuestion();
                    //中止したtimeを取得
                    String time = timer.getText().toString();
                    //timeを分と秒に分ける
                    String[] time2 = time.split(":", 0);

                    //止めたところからリスタートする
                    cdt = new MyCountDownTimer((Integer.parseInt(time2[0]) * 60 + Integer.parseInt(time2[1])) * 1000
                            + Integer.parseInt(time2[2]), 100);
                    cdt.start();
                }
                //quiz_max以上の場合
                else {
                    //リザルト画面に移動
                    intent = new Intent(QuizActivity.this, ResultActivity.class);
                    intent.putExtra("SCORE", score);
                    startActivity(intent);
                }
            }
        });
        alert.show();
    }

    //CountDownについて
    public class MyCountDownTimer extends CountDownTimer {

        public MyCountDownTimer(long millisInFuture, long countDownInterval) {
            super(millisInFuture, countDownInterval);
        }

        @Override
        public void onFinish() {
            // カウントダウン完了後に呼ばれる
            timer.setText("00:00:0");
            Toast.makeText(getApplicationContext(), "時間切れだよ(´・ω・｀)\nもう一回挑戦してみよう", Toast.LENGTH_LONG).show();
            //その時点でのScoreを引き継いでリザルト画面へ
            intent = new Intent(QuizActivity.this, ResultActivity.class);
            intent.putExtra("SCORE", score);
            startActivity(intent);
        }

        @Override
        public void onTick(long millisUntilFinished) {
            // インターバル(countDownInterval)毎に呼ばれる
            long m = millisUntilFinished / 1000 / 60;  //分
            long s = millisUntilFinished / 1000 % 60;  //秒
            long ms = millisUntilFinished - s * 1000 - m * 1000 * 60;  //ミリ秒

            HpBar.setProgress((int) s);

            timer.setText(String.format("%02d:%02d:%01d", m, s, ms/100)); //桁あわせ
            if (s <= 10.0) {
                //残り10秒になったら赤文字にしサイズを大きく
                timer.setTextColor(Color.RED);
            }
        }
    }

    //BackKeyの無効化
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode != KeyEvent.KEYCODE_BACK) {
            return super.onKeyDown(keyCode, event);
        } else {
            Toast.makeText(this, "Quiz中は戻るボタン禁止です！！！！", Toast.LENGTH_SHORT).show();
            return false;
        }
    }
}