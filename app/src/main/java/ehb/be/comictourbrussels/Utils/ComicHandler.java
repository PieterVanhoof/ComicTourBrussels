package ehb.be.comictourbrussels.Utils;

import android.content.Context;
import android.os.Handler;
import android.os.Message;
import android.util.Log;

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



            while (index < nrOfRecords){

                JSONObject currentRecord = records.getJSONObject(index);
                JSONObject fields = currentRecord.getJSONObject("fields");

                String personage = (fields.getString("personnage_s") != null)? fields.getString("personnage_s"): "Unknown";

                Log.d("TEST personage", personage);

                Comic currentComic = new Comic(personage);
                ComicDatabase.getInstance(context).getComicDAO().insertComic(currentComic);

                index++;

            }


        } catch (JSONException e) {
            e.printStackTrace();

        }
    }
}

