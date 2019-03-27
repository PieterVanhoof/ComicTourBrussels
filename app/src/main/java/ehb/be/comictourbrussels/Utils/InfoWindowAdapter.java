package ehb.be.comictourbrussels.Utils;

import android.content.Context;
import android.net.Uri;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.model.Marker;
import com.squareup.picasso.Picasso;

import java.io.File;

import ehb.be.comictourbrussels.R;

public class InfoWindowAdapter implements GoogleMap.InfoWindowAdapter {

    private Context context;
    private TextView tvPersonage, tvAuthor;
    private ImageView ivInfoWindow;




    public InfoWindowAdapter(Context context){
        this.context = context.getApplicationContext();
    }

    @Override
    public View getInfoWindow(Marker marker) {
        return null;
    }

    @Override
    public View getInfoContents(final Marker marker) {

        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View v = inflater.inflate(R.layout.info_window, null);

        tvPersonage = v.findViewById(R.id.tv_infowindow_personage);
        tvAuthor = v.findViewById(R.id.tv_infowindow_author);
        ivInfoWindow = v.findViewById(R.id.iv_infowindow_image);

        String path = marker.getTag().toString();
        Log.d("TEST", path);

        Uri uri = Uri.parse("file://"+path);
        ivInfoWindow.setImageURI(uri);


        tvPersonage.setText("Personage: "+ marker.getTitle());
        tvAuthor.setText("Author: " + marker.getSnippet());

        return v;
    }

}