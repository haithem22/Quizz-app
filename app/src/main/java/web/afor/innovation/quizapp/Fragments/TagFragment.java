package web.afor.innovation.quizapp.Fragments;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.GestureDetector;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListAdapter;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import okhttp3.OkHttpClient;
import okhttp3.internal.Util;
import web.afor.innovation.quizapp.Activities.MainActivity;
import web.afor.innovation.quizapp.Activities.TagActivity;
import web.afor.innovation.quizapp.Adapters.TagsAdapter;
import web.afor.innovation.quizapp.Config.Constants;
import web.afor.innovation.quizapp.DividerItemDecoration;
import web.afor.innovation.quizapp.Models.Tags;
import web.afor.innovation.quizapp.R;
import web.afor.innovation.quizapp.Utils.Utils;


public class TagFragment extends Fragment {

    List<Tags> answer;
    //List<Tags> taglist = new ArrayList<>();
    RecyclerView recyclerView;
    TagsAdapter tagsAdapter;

    /*
    instead of asynctask , we can use Retrofit :
    http://www.androidhive.info/2016/05/android-working-with-retrofit-http-library/
    */
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        new DownloadTags().execute(Constants.BASE_URL + Constants.URL_TAGS);

        View view = inflater.inflate(R.layout.fragment_tag, container, false);
        recyclerView = (RecyclerView) view.findViewById(R.id.recycler_view);

        return view;
    }

    public class DownloadTags extends AsyncTask<String, Void, String> {

        OkHttpClient client = new OkHttpClient();

        @Override
        protected String doInBackground(String... params) {

            okhttp3.Request.Builder builder = new okhttp3.Request.Builder();
            builder.url(params[0]);
            okhttp3.Request request = builder.build();

            try {
                okhttp3.Response response = client.newCall(request).execute();
                //Log.d("responseRecycle",response.body().string());
                return response.body().string();
            } catch (IOException e) {
                e.printStackTrace();
            }
            return null;
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);

            final JSONArray objArray;
            answer = new ArrayList<Tags>();
            answer.add(new Tags("general"));
            String tagName = "";
            JSONObject jsonObject = null;
            try {
                jsonObject = new JSONObject(s);
                objArray = jsonObject.getJSONArray("result");
                int result_length = objArray.length();
                for (int i = 0; i < result_length; i++) {

                    JSONObject object = objArray.getJSONObject(i);
                    int tagId = object.getInt("id");
                    tagName = object.getString("tag_name");
                    answer.add(new Tags(tagName));

                }

                tagsAdapter = new TagsAdapter(answer, getContext());
                RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getContext());
                recyclerView.setLayoutManager(mLayoutManager);
                recyclerView.setItemAnimator(new DefaultItemAnimator());
                recyclerView.setAdapter(tagsAdapter);
                //recyclerView.addItemDecoration(new DividerItemDecoration(getContext(), LinearLayoutManager.VERTICAL));
                recyclerView.setAdapter(tagsAdapter);

                recyclerView.addOnItemTouchListener(new RecyclerTouchListener(getContext(), recyclerView, new ClickListener() {
                    @Override
                    public void onClick(View view, int position) {


                        Tags tag = answer.get(position);

                        Bundle b = new Bundle();
                        b.putString("tag", tag.getTag_name());
                        Utils.launchActivity(getActivity(), b, TagActivity.class);

                        //((MainActivity) getActivity()).replaceFragmentWithBundle("fragment", tag.getTag_name().toString());

                        Log.d("pos", tag.getTag_name());

                    }

                    @Override
                    public void onLongClick(View view, int position) {

                    }
                }));

                Log.d("Name", "response " + tagName);
            } catch (JSONException e) {
                e.printStackTrace();
            }


        }

    }

    public interface ClickListener {
        void onClick(View view, int position);

        void onLongClick(View view, int position);
    }

    public class RecyclerTouchListener implements RecyclerView.OnItemTouchListener {

        private GestureDetector gestureDetector;
        private TagFragment.ClickListener clickListener;

        public RecyclerTouchListener(Context context, final RecyclerView recyclerView, final TagFragment.ClickListener clickListener) {
            this.clickListener = clickListener;
            gestureDetector = new GestureDetector(context, new GestureDetector.SimpleOnGestureListener() {
                @Override
                public boolean onSingleTapUp(MotionEvent e) {
                    return true;
                }

                @Override
                public void onLongPress(MotionEvent e) {
                    View child = recyclerView.findChildViewUnder(e.getX(), e.getY());
                    if (child != null && clickListener != null) {
                        clickListener.onLongClick(child, recyclerView.getChildPosition(child));
                    }
                }
            });
        }

        @Override
        public boolean onInterceptTouchEvent(RecyclerView rv, MotionEvent e) {

            View child = rv.findChildViewUnder(e.getX(), e.getY());
            if (child != null && clickListener != null && gestureDetector.onTouchEvent(e)) {
                clickListener.onClick(child, rv.getChildPosition(child));
            }
            return false;
        }

        @Override
        public void onTouchEvent(RecyclerView rv, MotionEvent e) {
        }

        @Override
        public void onRequestDisallowInterceptTouchEvent(boolean disallowIntercept) {

        }

    }

}
