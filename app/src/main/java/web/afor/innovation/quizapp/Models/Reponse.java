package web.afor.innovation.quizapp.Models;

import com.google.gson.annotations.SerializedName;

/**
 * Created by Administrateur on 30/07/2016.
 */
public class Reponse {

    @SerializedName("answer_text")
    String text;
    @SerializedName("correct")
    boolean isCorrect;


    public Boolean getCorrect() {
        return isCorrect;
    }

    public void setCorrect(Boolean correct) {
        isCorrect = correct;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

}
