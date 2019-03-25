package ehb.be.comictourbrussels.Utils;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.os.Environment;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.widget.ImageView;

import com.squareup.picasso.Callback;
import com.squareup.picasso.MemoryPolicy;
import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

import ehb.be.comictourbrussels.Room.Comic;
import ehb.be.comictourbrussels.Room.ComicDatabase;
import okhttp3.internal.cache.CacheStrategy;

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


            Log.d("TEST", nrOfRecords + "");

            if(ComicDatabase.getInstance(context).getComicDAO().selectAllComic().isEmpty()) {


                while (index < nrOfRecords) {

                    JSONObject currentRecord = records.getJSONObject(index);
                    JSONObject fields = currentRecord.getJSONObject("fields");


                    String personage = (fields.getString("personnage_s") != null) ? fields.getString("personnage_s") : "Unknown";
                    String author = (fields.getString("auteur_s") != null) ? fields.getString("auteur_s") : "Unknown";


                    JSONObject imageArray = fields.getJSONObject("photo");
                    JSONArray coordinate = fields.getJSONArray("coordonnees_geographiques");

                    double lat = (double) coordinate.get(0);
                    double lng = (double) coordinate.get(1);


                    String imgID = (imageArray.getString("id") != null) ? imageArray.getString("id") : "Unknown";

                    // Your image address. ex: "http://http://stackoverflow.com/myImages.jpg"
                    String MY_IMAGE_URL = "https://opendata.brussel.be/explore/dataset/comic-book-route/files/" + imgID + "/300/";
                    final ImageView ivComic = new ImageView(context);
                    final String img_path = context.getFilesDir() + "/" + imgID + "ComicRoute.jpg";
                    Picasso.get().load(MY_IMAGE_URL)
                            .into(ivComic, new com.squareup.picasso.Callback() {
                                @Override
                                public void onSuccess() {
                                    new Handler().postDelayed(new Runnable() {
                                        @Override
                                        public void run() {
                                            // Save bitmap to local
                                            Bitmap bitmap = ((BitmapDrawable) ivComic.getDrawable()).getBitmap();
                                            File file = new File(img_path);
                                            try {
                                                file.createNewFile();
                                                FileOutputStream ostream = new FileOutputStream(file);
                                                bitmap.compress(Bitmap.CompressFormat.JPEG, 100, ostream);
                                                ostream.close();
                                            } catch (IOException e) {
                                                e.printStackTrace();
                                            }
                                        }
                                    }, 100);
                                }

                                @Override
                                public void onError(Exception e) {

                                }
                            });

                    Comic currentComic = new Comic(lat, lng, personage, author, imgID);


                    ComicDatabase.getInstance(context).getComicDAO().insertComic(currentComic);

                    index++;
                }

            }
        } catch (JSONException e) {
            e.printStackTrace();

        }





    }
}

