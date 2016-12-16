package web.afor.innovation.quizapp.webservices;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Url;
import web.afor.innovation.quizapp.Config.Constants;
import web.afor.innovation.quizapp.Models.Result;
import web.afor.innovation.quizapp.Models.ResultQuestion;


public interface ApiInterface {
    @GET(Constants.URL_TAGS)
    Call<Result> getTags();

    @GET()
    Call<ResultQuestion> getQuestionByTags(@Url String url);


}
