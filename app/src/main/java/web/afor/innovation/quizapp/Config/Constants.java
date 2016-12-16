package web.afor.innovation.quizapp.Config;

import android.support.v4.app.Fragment;

import web.afor.innovation.quizapp.Fragments.OffsetFragment;

/**
 * Created by adminlocal on 27/07/2016.
 */
public class Constants {

    public final static String BASE_URL="http://web4innovation.bianucci.org/";
    public final static String URL_RANDOM="api/question/random";

    public final static String URL_LOGIN = "api/authenticate";
    public final static String URL_SIGNUP ="api/register";
    public final static String URL_TAGS = "api/tags";
    public final static String URL_QUESTION_BY_TAG = "api/question/bytag/";
    public final static String URL_ADD_QUESTION = "api/question";

    public static int MY_SOCKET_TIMEOUT_MS = 50000;

    public final static int INC_VALUE = 10;
    public final static int DEC_VALUE = -3;
    public final static int Splash_DELAY_VALUE = 2000;

    private static String PACKAGE_NAME= "web.afor.innovation.quizapp.Fragments.";
    public static String[] ARRAY_FRAGMENTS = {PACKAGE_NAME + "QuizFragment", PACKAGE_NAME + "scoreFragment", PACKAGE_NAME + "TagFragment", PACKAGE_NAME + "CategoryFragment", PACKAGE_NAME + "OffsetFragment" , PACKAGE_NAME + "AddNewQuestionFragment", PACKAGE_NAME + "AddNewQuestionFragment"};
}
