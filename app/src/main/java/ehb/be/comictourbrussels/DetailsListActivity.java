package ehb.be.comictourbrussels;

import android.content.Context;
import android.content.Intent;
import android.support.constraint.ConstraintLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import ehb.be.comictourbrussels.Room.Comic;

public class DetailsListActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details_list);

        Intent intent = getIntent();
        Comic detailComic = (Comic) intent.getSerializableExtra("comic");

        TextView textViewPersonage = findViewById(R.id.tv_details_personage);
        TextView textViewAuthor = findViewById(R.id.tv_details_author);
        TextView textViewDescription = findViewById(R.id.tv_details_description);

        textViewPersonage.setText(detailComic.getPersonage());
        textViewAuthor.setText( detailComic.getAuthor() );
        //textViewDescription.setText(detailComic);

        ImageView ivBG = findViewById(R.id.iv_details_bg);

        String imageid = detailComic.getImgID();
        String filename = imageid + "ComicRoute.jpg";
        String path = getFilesDir() + "/" + filename;

        Picasso.get().load("file://" + path).into(ivBG);
    }
}
