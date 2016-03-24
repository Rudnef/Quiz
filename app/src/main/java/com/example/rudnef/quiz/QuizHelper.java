package com.example.rudnef.quiz;

import android.content.Context;
import android.graphics.Typeface;
import android.media.SoundPool;
import android.os.Build;
import android.os.Build.VERSION;

import java.util.ArrayList;

/**
 * Created by Rudnef on 19.03.16.
 */
public class QuizHelper {
    private final static String TYPEFACE = "isocteur.ttf";
    private final static String TYPEBUTTON = "note.ttf";
    private static final int ANIMATION_DURSTION = 200;
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
                        context.getString(R.string.txt_right_answer1):
                        context.getString(R.string.txt_wrong_answer1);
    }
}
