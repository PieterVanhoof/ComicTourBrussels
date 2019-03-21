package ehb.be.comictourbrussels.Utils;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.io.File;
import java.util.ArrayList;

import ehb.be.comictourbrussels.R;
import ehb.be.comictourbrussels.Room.Comic;

public class ListFragmentAdapter extends RecyclerView.Adapter<ListFragmentAdapter.FragmentListRowViewHolder> {

    class FragmentListRowViewHolder extends RecyclerView.ViewHolder{

        private TextView personage;
        private ImageView ivImage;



        public FragmentListRowViewHolder(@NonNull View itemView) {
            super(itemView);
            personage = itemView.findViewById(R.id.tv_personage);
            ivImage = itemView.findViewById(R.id.iv_img);

        }
    }

    private ArrayList<Comic> items;
    private File imageFile;

    public ListFragmentAdapter(ArrayList<Comic> items){
        this.items = items;
    }
    @NonNull
    @Override
    public FragmentListRowViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
            View rowView = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.comiclistcell, viewGroup,false);
            return new FragmentListRowViewHolder(rowView);
    }

    @Override
    public void onBindViewHolder(@NonNull FragmentListRowViewHolder fragmentListRowViewHolder, int i) {
        Comic currentComic = items.get(i);
        //link door file vervangen?
        //String imageid = currentComic.getImageId();
        //Log.d("TEST link", imageid);

        Picasso.get().load("https://opendata.brussel.be/explore/dataset/comic-book-route/files/05bf1251965b56241438edcf13aa9ebb/300/").into(fragmentListRowViewHolder.ivImage);
        fragmentListRowViewHolder.personage.setText(currentComic.getPersonage());


    }

    @Override
    public int getItemCount() {
        return items.size();
    }
        public void setItems(ArrayList<Comic> comics) {
            items.addAll(comics);
        }

    }

