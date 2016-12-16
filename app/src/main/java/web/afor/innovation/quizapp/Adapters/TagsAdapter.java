package web.afor.innovation.quizapp.Adapters;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.List;

import web.afor.innovation.quizapp.DividerItemDecoration;
import web.afor.innovation.quizapp.Models.Tags;
import web.afor.innovation.quizapp.R;

/**
 * Created by adminlocal on 30/07/2016.
 */
public class TagsAdapter extends RecyclerView.Adapter<TagsAdapter.MyViewHolder> {


    private List<Tags> TagsList;
    private Context context;

    public class MyViewHolder extends RecyclerView.ViewHolder {

        private TextView tagText;
        private ImageView tagImage;


        public MyViewHolder(View view) {
            super(view);
            tagText = (TextView) view.findViewById(R.id.TagText);
            tagImage = (ImageView) view.findViewById(R.id.TagImage);

        }
    }


    public TagsAdapter(List<Tags> TagsList, Context context) {
        this.TagsList = TagsList;
        this.context = context;
    }


    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_tags, parent, false);

        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {

        Tags tag = TagsList.get(position);
        holder.tagText.setText(tag.getTag_name());
        //Picasso.with(context).load("http://hockey3contre3-stconstant.com/resizer.php?imgfile=img/no_team.png&max_width=250&max_height=250").into(holder.tagImage);
    }

    @Override
    public int getItemCount() {

        return TagsList.size();
    }
}
