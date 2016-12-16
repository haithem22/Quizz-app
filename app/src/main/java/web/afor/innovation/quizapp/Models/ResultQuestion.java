package web.afor.innovation.quizapp.Models;

import com.google.gson.JsonObject;

import java.util.List;

/**
 * Created by Administrateur on 30/07/2016.
 */
public class ResultQuestion {
    List<Question> result;

    public ResultQuestion(List<Question> result) {

        this.result = result;
    }

    public List<Question> getResult() {
        return result;
    }

    public void setResult(List<Question> result) {
        this.result = result;
    }
}
