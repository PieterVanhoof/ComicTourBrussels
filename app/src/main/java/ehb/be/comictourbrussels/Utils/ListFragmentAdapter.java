package ehb.be.comictourbrussels.Utils;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;

import ehb.be.comictourbrussels.R;
import ehb.be.comictourbrussels.Room.Comic;

public class ListFragmentAdapter extends RecyclerView.Adapter<ListFragmentAdapter.FragmentListRowViewHolder> {

    class FragmentListRowViewHolder extends RecyclerView.ViewHolder{

        private TextView personage;


        public FragmentListRowViewHolder(@NonNull View itemView) {
            super(itemView);
            personage = itemView.findViewById(R.id.tv_personage);
        }
    }

    private ArrayList<Comic> items;

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

