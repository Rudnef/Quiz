package com.example.rudnef.quiz;


public class Card {

        private String question;
        private boolean answer;

        public Card(String question, boolean answer) {
            this.question = question;
            this.answer = answer;
        }

        public String getQuestion() {
            return question;
        }

        public boolean getAnswer() {
            return answer;
        }
    }
