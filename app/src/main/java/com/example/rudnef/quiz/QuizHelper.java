package com.example.rudnef.quiz;

import android.annotation.TargetApi;
import android.content.Context;
import android.graphics.Typeface;
import android.media.SoundPool;
import android.os.Build;
import android.os.Build.VERSION;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by Rudnef on 19.03.16.
 */
public class QuizHelper {
    private final static String TYPEFACE = "isocteur.ttf";
    private final static String TYPEBUTTON = "note.ttf";
    private static final int ANIMATION_DURSTION_OFF = 100;
    private static final int ANIMATION_DURSTION_ON = 1000;
    public static final int MAX_SOUND = 2;

    private SoundPool soundPool;
    private Context context;
    private ArrayList<Card> cards;
    private int currentIndex;


    private String[] questions;
    private boolean[] answers;

    public QuizHelper(Context context) {
        this.context = context;
    }

    public void initCards() {
        String[] cardArray = context.getResources().getStringArray(R.array.cards);

        cards = new ArrayList<>();


        for (String card : cardArray) {
            String[] ga = card.split("/");
            if (ga.length > 1) {
                Card newCard = new Card(ga[0], ga[1].trim().equals("1"));
                cards.add(newCard);

            }
        }
    }

    public boolean isReady() {
        return cards != null && !cards.isEmpty();
    }

    public String getNextQuestion() {
        int oldIndex = currentIndex;
        do {
            currentIndex = (int) (Math.random() * cards.size());
        } while (oldIndex == currentIndex);
        return cards.get(currentIndex).getQuestion();
    }

    public boolean getCurrentAnswer() {
        return cards.get(currentIndex).getAnswer();
    }

    public String getStringResultForAnswer(boolean gotAnswer) {
//        return Boolean.toString(gotAnswer == getCurrentAnswer());
        // TODO (обращаться к массивам через getResources)
        return gotAnswer == getCurrentAnswer() ?
                context.getString(R.string.txt_right_answer) :
                context.getString(R.string.txt_wrong_answer);
    }


    public Typeface getCurrentFont() {
        return Typeface.createFromAsset(context.getAssets(), TYPEFACE);
    }

    public Typeface getCurrentFontButton() {
        return Typeface.createFromAsset(context.getAssets(), TYPEBUTTON);
    }

    public String getRightOrWrongAnswer(boolean answer) {
        return answer == getCurrentAnswer() ?
                context.getString(R.string.txt_right_answer1) :
                context.getString(R.string.txt_wrong_answer1);
    }
@TargetApi(16)
    public void animateChanging(final TextView tv_question) {
        tv_question.animate()
                .alpha(0.0f)//пропадает от нуля до еденицы
                .scaleX(0.3f)// маштабирование
                .scaleY(0.3f)// по оси x и y
                .setDuration(ANIMATION_DURSTION_OFF)
                .withEndAction(new Runnable() {
                    @Override
                    public void run() {
                        tv_question.animate().alpha(1.0f)
                                .scaleX(1.0f)
                                .scaleY(1.0f)
                                .setDuration(ANIMATION_DURSTION_ON)
                                .start();
                    }
                });
    }
}
