package web.afor.innovation.quizapp.Utils;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;

import web.afor.innovation.quizapp.R;

/**
 * Created by adminlocal on 01/08/2016.
 */
public class Utils {

    public static void launchActivity(Activity activity, Bundle bundle, Class<?> cls) {

        Intent intent = new Intent(activity, cls);
        if (bundle != null)
            intent.putExtras(bundle);
        activity.startActivity(intent);
        activity.overridePendingTransition(R.anim.activity_open_translate, R.anim.activity_close_scale);


    }
}
