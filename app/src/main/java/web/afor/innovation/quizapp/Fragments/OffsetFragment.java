package web.afor.innovation.quizapp.Fragments;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import butterknife.Bind;
import butterknife.ButterKnife;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import web.afor.innovation.quizapp.Config.Constants;
import web.afor.innovation.quizapp.Models.ResultQuestion;
import web.afor.innovation.quizapp.R;
import web.afor.innovation.quizapp.webservices.ApiClient;
import web.afor.innovation.quizapp.webservices.ApiInterface;


public class OffsetFragment extends Fragment implements View.OnClickListener {

    @Bind(R.id.question)
    TextView question;
    @Bind(R.id.rep1)
    TextView rep1;
    @Bind(R.id.rep2)
    TextView rep2;
    @Bind(R.id.rep3)
    TextView rep3;
    @Bind(R.id.rep4)
    TextView rep4;
    @Bind(R.id.owner)
    TextView owner;
    /*@Bind(R.id.score_view)
    TextView score_view;*/
    ResultQuestion result;
    String category;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.question_tag, container, false);
        ButterKnife.bind(this, view);

        //Bundle b = getArguments();
        //category = b.getString("fragment");

        ApiInterface apiService = ApiClient.getClient().create(ApiInterface.class);

        Call<ResultQuestion> call = apiService.getQuestionByTags(Constants.BASE_URL + Constants.URL_QUESTION_BY_TAG + "math" + "/0" );
        call.enqueue(new Callback<ResultQuestion>() {
            @Override
            public void onResponse(Call<ResultQuestion> call, Response<ResultQuestion> response) {
                result = response.body();
                Log.d("fff", result.getResult().get(0).getOwner());
                owner.setText(result.getResult().get(0).getOwner());
                question.setText(result.getResult().get(0).getQuestion());
                rep1.setText(result.getResult().get(0).getAnswers().get(0).getText());
                rep2.setText(result.getResult().get(0).getAnswers().get(1).getText());
                rep3.setText(result.getResult().get(0).getAnswers().get(2).getText());
                rep4.setText(result.getResult().get(0).getAnswers().get(3).getText());
            }

            @Override
            public void onFailure(Call<ResultQuestion> call, Throwable t) {
                Log.e("log2", t.toString());
            }


        });

        rep1.setOnClickListener(this);
        rep2.setOnClickListener(this);
        rep3.setOnClickListener(this);
        rep4.setOnClickListener(this);


        return view;
    }


    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.rep1:

                break;
            case R.id.rep2:

                break;
            case R.id.rep3:

                break;
            case R.id.rep4:

                break;

        }

    }
}
