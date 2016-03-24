package com.example.rudnef.quiz;

import android.content.Context;
import android.media.AudioManager;
import android.media.SoundPool;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

public class QuizActivity extends AppCompatActivity {

    private SoundPool mSoundPool;
    private SoundPool mSoundPooltrue;
    private SoundPool mSoundPoolfalse;
    private int mSoundId = 1;
    private int mStreamId;


    private TextView tv_question;
    private TextView tv_category;
    private TextView tv_right;
    private TextView tv_wrong;
    private TextView tv_point;
    private Button btn_false;
    private Button btn_true;

    private Button btn_next;

    private QuizHelper helper;
    int wrong = 0;
    int right = 0;
    int point = 0;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_quiz);

        mSoundPool = new SoundPool(3, AudioManager.STREAM_MUSIC, 100);
        mSoundPooltrue = new SoundPool(3, AudioManager.STREAM_MUSIC, 100);
        mSoundPoolfalse = new SoundPool(3, AudioManager.STREAM_MUSIC, 100);
        mSoundPool.load(this, R.raw.next, 1);
        mSoundPooltrue.load(this, R.raw.right, 1);
        mSoundPoolfalse.load(this, R.raw.wrong, 1);

        helper = new QuizHelper(this);

        initializeWidgets();
        initializeCards();
        chooseCategory();

        if (isStartPermission()) {
            startQuiz();
        } else {
            showToast(getString(R.string.txt_something_wrong));
        }

    }

    private void initializeWidgets() {
        tv_question = (TextView) findViewById(R.id.tv_question);
        tv_category = (TextView) findViewById(R.id.tv_category);
        tv_right = (TextView) findViewById(R.id.tv_right);
        tv_wrong = (TextView) findViewById(R.id.tv_wrong);
        tv_point = (TextView) findViewById(R.id.tv_point);

        btn_false = (Button) findViewById(R.id.btn_false);
        btn_true = (Button) findViewById(R.id.btn_true);
        btn_next = (Button) findViewById(R.id.btn_next);
        btn_false.setEnabled(true);
        btn_true.setEnabled(true);
        btn_next.setEnabled(false);

        tv_question.setTypeface(helper.getCurrentFont());
        btn_true.setTypeface(helper.getCurrentFontButton());
        btn_false.setTypeface(helper.getCurrentFontButton());
        btn_next.setTypeface(helper.getCurrentFontButton());
        tv_point.setTypeface(helper.getCurrentFont());
        tv_right.setTypeface(helper.getCurrentFont());
        tv_right.setTextColor(getResources().getColor(R.color.colorLightGreen));
        tv_wrong.setTypeface(helper.getCurrentFont());
        tv_wrong.setTextColor(getResources().getColor(R.color.colorRed));
        tv_category.setTypeface(helper.getCurrentFontButton());


        btn_true.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showResultToastForButton(true);
                showRightOrWrongAnswer(true);
                btn_next.setEnabled(true);
                btn_false.setEnabled(false);
                btn_true.setEnabled(false);
            }
        });

        btn_false.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                showResultToastForButton(false);
                showRightOrWrongAnswer(false);
                btn_next.setEnabled(true);
                btn_false.setEnabled(false);
                btn_true.setEnabled(false);

            }
        });


        btn_next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int iiiii;
                showNextQuestion();
                btn_next.setEnabled(false);
                btn_true.setEnabled(true);
                btn_false.setEnabled(true);

                AudioManager audioManager = (AudioManager) getSystemService(Context.AUDIO_SERVICE);
                float curVolume = audioManager.getStreamVolume(AudioManager.STREAM_MUSIC);
                float maxVolume = audioManager.getStreamMaxVolume(AudioManager.STREAM_MUSIC);
                float leftVolume = curVolume / maxVolume;
                float rightVolume = curVolume / maxVolume;
                int priority = 1;
                int no_loop = 0;
                float normal_playback_rate = 1f;
                iiiii = mSoundPool.play(mSoundId, leftVolume, rightVolume, priority, no_loop,
                        normal_playback_rate);
            }
        });
    }

    //region Helpers
    private void showResultToastForButton(boolean gotAnswer) {

        showToast(helper.getStringResultForAnswer(gotAnswer));
    }

    private void initializeCards() {
        helper.initCards();
    }


    private boolean isStartPermission() {
        return helper.isReady();
    }

    private void startQuiz() {
        showNextQuestion();
    }


    private void showToast(String massage) {
        Toast.makeText(this, massage, Toast.LENGTH_SHORT).show();
    }

    private void showNextQuestion() {
        tv_question.setText(helper.getNextQuestion());
    }

    private void showRightOrWrongAnswer(boolean gotAnswer) {
        int rightAnswer;
        int wrongAnswer;

        if (helper.getRightOrWrongAnswer(gotAnswer).equals("1")) {
            right = right + 1;
            point = point + 1;
            String mright = String.valueOf(right);
            String mpoint = String.valueOf(point);
            tv_right.setText("правильных ответов: " + mright);
            tv_point.setText("Баллов: " + mpoint);
            rightAnswer = mSoundPooltrue.play(mSoundId, 1.0f, 1.0f, 1, 0,
                    1.0f);
        } else if (helper.getRightOrWrongAnswer(gotAnswer).equals("0")) {
            wrong = wrong + 1;
            point = point - 1;
            String mwrong = String.valueOf(wrong);
            String mpoint = String.valueOf(point);
            tv_wrong.setText("неправильных ответов: " + mwrong);
            tv_point.setText("Баллов: " + mpoint);
            rightAnswer = mSoundPoolfalse.play(mSoundId, 1.0f, 1.0f, 1, 0,
                    1.0f);
        }
    }

    //endregion

    // TODO реализовать метод с выбором категории
    private void chooseCategory() {
        tv_category.setText((R.string.categoria));
    }
}

