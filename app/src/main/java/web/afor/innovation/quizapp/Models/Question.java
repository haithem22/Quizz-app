package web.afor.innovation.quizapp.Models;

import android.nfc.Tag;

import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by Administrateur on 30/07/2016.
 */
public class Question {
    @SerializedName("answers")
    List<Reponse> answers;
    String id;
    @SerializedName("multiple_choice")
    Boolean isMultipleC;
    @SerializedName("owner")
    String Owner;
    @SerializedName("question")
    String question;



    public List<Reponse> getAnswers() {
        return answers;
    }

    public Boolean getMultipleC() {
        return isMultipleC;
    }

    public String getOwner() {
        return Owner;
    }

    public String getQuestion() {
        return question;
    }


}
