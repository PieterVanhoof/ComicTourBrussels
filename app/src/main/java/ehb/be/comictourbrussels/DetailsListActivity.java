package ehb.be.comictourbrussels;

import android.content.Intent;
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

        TextView tvDetails = findViewById(R.id.tv_details);


        tvDetails.setText(getString(R.string.txt_details_personage) +detailComic.getPersonage()+"\n"+getString(R.string.txt_details_author)
                +detailComic.getAuthor()+"\n"+getString(R.string.txt_details_year) +detailComic.getJaar());

        ImageView ivBG = findViewById(R.id.iv_details_bg);

        String imageid = detailComic.getImgID();
        String filename = imageid + "ComicRoute.jpg";
        String path = getFilesDir() + "/" + filename;

        Picasso.get().load("file://" + path).into(ivBG);
    }
}
