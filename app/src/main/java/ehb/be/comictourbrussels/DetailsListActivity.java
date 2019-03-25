package ehb.be.comictourbrussels;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

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
        //textViewDescription.setText(detailComic.get);


    }
}
