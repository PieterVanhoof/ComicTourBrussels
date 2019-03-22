package ehb.be.comictourbrussels;


import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;

import ehb.be.comictourbrussels.Room.Comic;
import ehb.be.comictourbrussels.Room.ComicDao;
import ehb.be.comictourbrussels.Room.ComicDatabase;
import ehb.be.comictourbrussels.Utils.ComicHandler;
import ehb.be.comictourbrussels.Utils.ListFragmentAdapter;

import static android.icu.lang.UCharacter.GraphemeClusterBreak.L;


/**
 * A simple {@link Fragment} subclass.
 */
public class ListFragment extends Fragment {

    private RecyclerView rvFragment;
    private ListFragmentAdapter adapter;

    public ListFragment() {
        // Required empty public constructor
    }

    public static ListFragment newInstance(){
        return new ListFragment();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_list, container, false);


        rvFragment = view.findViewById(R.id.rv_fragment_list);

        adapter = new ListFragmentAdapter((ArrayList<Comic>) ComicDatabase.getInstance(getActivity().getApplicationContext()).getComicDAO().selectAllComic());
        rvFragment.setAdapter(adapter);

        RecyclerView.LayoutManager gridlayoutManager = new GridLayoutManager(getActivity().getApplicationContext(), 2);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getActivity().getApplicationContext());


        rvFragment.setLayoutManager(gridlayoutManager);

        return view;
    }

}
