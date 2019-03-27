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
import ehb.be.comictourbrussels.Room.WC;

public class WCHandler extends Handler {
    private Context context;

    public WCHandler(Context context) {
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

            if (ComicDatabase.getInstance(context).getComicDAO().selectAllWC().isEmpty()) {

                while (index < nrOfRecords) {
                    JSONObject currentRecord = records.getJSONObject(index);
                    JSONObject fields = currentRecord.getJSONObject("fields");

                    String adressN = (fields.getString("urinoir_nl") !=null) ? fields.getString("urinoir_nl") : "Unknoxn";

                    JSONArray coordinate = fields.getJSONArray("coord_wgs84");

                    double lat = (double) coordinate.get(0);
                    double lon = (double) coordinate.get(1);



                    WC currentWc = new WC(lat, lon, "adres", "adres");
                    ComicDatabase.getInstance(context).getComicDAO().insertWc(currentWc);
                }
            }
        }catch (JSONException e) {
            e.printStackTrace();

        }
    }
}
