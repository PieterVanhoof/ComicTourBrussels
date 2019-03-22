package ehb.be.comictourbrussels.Utils;

import android.content.Context;
import android.os.Handler;
import android.os.Message;
import android.util.Log;

import com.google.android.gms.maps.model.LatLng;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import ehb.be.comictourbrussels.Room.Comic;
import ehb.be.comictourbrussels.Room.ComicDatabase;

public class ComicHandler extends Handler {

    private Context context;

    public ComicHandler(Context context) {
        this.context = context;
    }

    @Override
    public void handleMessage(Message msg) {
        super.handleMessage(msg);

        String data = (String) msg.obj;

        try {
            JSONObject rootObject = new JSONObject(data);
            JSONArray records = rootObject.getJSONArray("records");
            int nrOfRecords = records.length();
            int index = 0;


           // Log.d("TEST", nrOfRecords+"");

            while (index < nrOfRecords){

                JSONObject currentRecord = records.getJSONObject(index);
                JSONObject fields = currentRecord.getJSONObject("fields");

                JSONObject imageArray = fields.getJSONObject("photo");

                String personage = (fields.getString("personnage_s") != null)? fields.getString("personnage_s"): "Unknown";
                String author = (fields.getString("auteur_s") != null)? fields.getString("auteur_s"): "Unknown";
                String Coordinates = (fields.getString("coordonnees_geographiques") != null)? fields.getString("coordonnees_geographiques"): "Unknown";
                String image = (fields.getString("photo") != null)? fields.getString("photo"): "Unknown";


                int lengte = Coordinates.length();

                String CutCoord = Coordinates.substring(1,lengte-1);
                Log.d("Test", CutCoord);

                String filename = (imageArray.getString("filename") != null)? imageArray.getString("filename"): "Unknown";

                Comic currentComic = new Comic(personage, CutCoord);
                ComicDatabase.getInstance(context).getComicDAO().insertComic(currentComic);



                index++;

                //Log.d("TEST personage", personage);
                //Log.d("TEST image" , Coordinates);

            }






        } catch (JSONException e) {
            e.printStackTrace();

        }
    }
}

