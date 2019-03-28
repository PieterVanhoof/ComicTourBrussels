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


    public InfoWindowAdapter(Context context){
        this.context = context.getApplicationContext();
    }

    @Override
    public View getInfoWindow(Marker marker) {
        return null;
    }

    @Override
    public View getInfoContents(final Marker marker) {
        //TODO hack code vervangen
        if (marker.getTitle().contains("WC")) {

        } else {

            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            View v = inflater.inflate(R.layout.info_window, null);

        TextView tvPersonage = v.findViewById(R.id.tv_infowindow_personage);
        TextView tvAuthor = v.findViewById(R.id.tv_infowindow_author);
        ImageView ivInfoWindow = v.findViewById(R.id.iv_infowindow_image);


            String path = marker.getTag().toString();
            Log.d("TEST", path);

            Uri uri = Uri.parse("file://" + path);
            ivInfoWindow.setImageURI(uri);


            tvPersonage.setText(context.getString(R.string.txt_infowindowadapter_personage) + marker.getTitle());
            tvAuthor.setText(context.getString(R.string.txt_infowindowadapter_author) + marker.getSnippet());
            return  v;
        }

            return null;
        }


}
