package com.techparew.x_files.model;

public class PreferenceQuestion {

    private int idPreferenceQuestion;
    private int questionType;
    private String question;


    public PreferenceQuestion(){

    }

    public  PreferenceQuestion(int idPreferenceQuestion,String question,int questionType){
        this.setIdPreferenceQuestion(idPreferenceQuestion);
        this.setQuestion(question);
        this.setQuestionType(questionType);
    }


    public int getIdPreferenceQuestion() {
        return idPreferenceQuestion;
    }

    public void setIdPreferenceQuestion(int idPreferenceQuestion) {
        this.idPreferenceQuestion = idPreferenceQuestion;
    }

    public String getQuestion() {
        return question;
    }

    public void setQuestion(String question) {
        this.question = question;
    }

    public int getQuestionType() {
        return questionType;
    }

    public void setQuestionType(int questionType) {
        this.questionType = questionType;
    }
}
