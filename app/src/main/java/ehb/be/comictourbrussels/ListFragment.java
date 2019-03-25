package ehb.be.comictourbrussels;


import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

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

        setHasOptionsMenu(true);

        rvFragment = view.findViewById(R.id.rv_fragment_list);

        adapter = new ListFragmentAdapter((ArrayList<Comic>) ComicDatabase.getInstance(getActivity().getApplicationContext()).getComicDAO().selectAllComic());
        rvFragment.setAdapter(adapter);

        RecyclerView.LayoutManager gridlayoutManager = new GridLayoutManager(getActivity().getApplicationContext(), 2);

        rvFragment.setLayoutManager(gridlayoutManager);

        return view;
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        inflater.inflate(R.menu.main, menu);
        SearchView searchView = (SearchView) menu.findItem(R.id.mi_search).getActionView();
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String s) {
                adapter.getFilter().filter(s);
                return false;
            }

            @Override
            public boolean onQueryTextChange(String s) {
                adapter.getFilter().filter(s);
                return false;
            }
        });
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        return super.onOptionsItemSelected(item);
    }


}
