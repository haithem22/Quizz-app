package web.afor.innovation.quizapp.Models;

/**
 * Created by adminlocal on 27/07/2016.
 */
public class Answer {

    private String response;
    private boolean isValidAnswer;


    public Answer(String response, boolean isValidAnswer) {
        this.response = response;
        this.isValidAnswer = isValidAnswer;
    }

    public String getResponse() {
        return response;
    }

    public void setResponse(String response) {
        this.response = response;
    }

    public boolean isValidAnswer() {
        return isValidAnswer;
    }

    public void setValidAnswer(boolean validAnswer) {
        isValidAnswer = validAnswer;
    }
}
