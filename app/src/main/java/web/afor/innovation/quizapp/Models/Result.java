package web.afor.innovation.quizapp.Models;

import android.nfc.Tag;

import com.google.gson.JsonObject;

import java.util.List;

/**
 * Created by Administrateur on 30/07/2016.
 */
public class Result {
    List<Tag> result;

    public Result(List<Tag> result) {
        this.result = result;
    }

    public List<Tag> getResult() {
        return result;
    }

    public void setResult(List<Tag> result) {
        this.result = result;
    }
}
