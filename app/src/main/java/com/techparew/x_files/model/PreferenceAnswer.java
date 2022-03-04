package com.techparew.x_files.model;

public class PreferenceAnswer {
    private int idPreferenceQuestion;
    private int idAccount;
    private int idPreferenceAnswer;
    private String answer;

    PreferenceAnswer() {

    }

    PreferenceAnswer(int idPreferenceQuestion,int idAccount,int idPreferenceAnswer, String answer){
        this.setAnswer(answer);
        this.setIdAccount(idAccount);
        this.setIdPreferenceQuestion(idPreferenceQuestion);
        this.setIdPreferenceAnswer(idPreferenceAnswer);
    }


    public int getIdPreferenceQuestion() {
        return idPreferenceQuestion;
    }

    public void setIdPreferenceQuestion(int idPreferenceQuestion) {
        this.idPreferenceQuestion = idPreferenceQuestion;
    }

    public int getIdAccount() {
        return idAccount;
    }

    public void setIdAccount(int idAccount) {
        this.idAccount = idAccount;
    }

    public int getIdPreferenceAnswer() {
        return idPreferenceAnswer;
    }

    public void setIdPreferenceAnswer(int idPreferenceAnswer) {
        this.idPreferenceAnswer = idPreferenceAnswer;
    }

    public String getAnswer() {
        return answer;
    }

    public void setAnswer(String answer) {
        this.answer = answer;
    }
}
