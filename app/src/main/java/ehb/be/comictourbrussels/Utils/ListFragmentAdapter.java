package ehb.be.comictourbrussels.Utils;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Filter;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.MemoryPolicy;
import com.squareup.picasso.Picasso;

import java.io.File;
import java.io.FileInputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import ehb.be.comictourbrussels.DetailsListActivity;
import ehb.be.comictourbrussels.MapFragment;
import ehb.be.comictourbrussels.R;
import ehb.be.comictourbrussels.Room.Comic;

public class ListFragmentAdapter extends RecyclerView.Adapter<ListFragmentAdapter.FragmentListRowViewHolder> {

    class FragmentListRowViewHolder extends RecyclerView.ViewHolder {

        private TextView personage;
        private ImageView ivImage;
        private Comic selectedComic;


        private FragmentListRowViewHolder(@NonNull final View itemView) {
            super(itemView);
            personage = itemView.findViewById(R.id.tv_personage);
            ivImage = itemView.findViewById(R.id.iv_img);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Context c = itemView.getContext();

                    Intent intent = new Intent(c, DetailsListActivity.class);
                    intent.putExtra("comic", selectedComic);
                    c.startActivity(intent);
                }
            });
        }
    }

    private List<Comic> items, filteredItems;



    public ListFragmentAdapter(List<Comic> items) {
        this.items = items;
        this.filteredItems = items;
    }
    @NonNull
    @Override
    public FragmentListRowViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View rowView = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.comiclistcell, viewGroup, false);
        return new FragmentListRowViewHolder(rowView);
    }

    @Override
    public void onBindViewHolder(@NonNull FragmentListRowViewHolder fragmentListRowViewHolder, int i) {
        Comic currentComic = filteredItems.get(i);

        fragmentListRowViewHolder.selectedComic = currentComic;

        String imageid = currentComic.getImgID();

        String filename = imageid + "ComicRoute.jpg";

        Context c = fragmentListRowViewHolder.itemView.getContext().getApplicationContext();

        String path = c.getFilesDir() + "/" + filename;

        Picasso.get().load("file://" + path).into(fragmentListRowViewHolder.ivImage);
        fragmentListRowViewHolder.personage.setText(currentComic.getPersonage());


    }

    @Override
    public int getItemCount() {
        return filteredItems.size();
    }

    public Filter getFilter(){
        return new Filter() {
            @Override
            protected FilterResults performFiltering(CharSequence constraint) {
                String searchTerm = constraint.toString().toLowerCase();
                if (searchTerm.isEmpty()) {
                    filteredItems = items;
                } else {
                    ArrayList<Comic> tempList = new ArrayList<>();
                    for (Comic comic : items) {
                        String lowerCase = comic.getPersonage().toLowerCase();

                        if (lowerCase.contains(searchTerm)) {
                            tempList.add(comic);
                        }
                    }
                    FilterResults filterResults = new FilterResults();
                    filterResults.values = tempList;
                    return filterResults;
                }
                return null;
            }


            @Override
            protected void publishResults(CharSequence constraint, FilterResults results) {

                if (results != null) {
                    filteredItems = (List<Comic>) results.values;
                    Log.d("TEST", filteredItems + "");
                }
                notifyDataSetChanged();
            }
        };
    }
}




