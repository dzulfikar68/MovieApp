package com.digitcreativestudio.moviecataloguelocalstorage;


import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;


/**
 * A simple {@link Fragment} subclass.
 */
public class FavTvFragment extends Fragment {

    private DatabaseHelper db;
    private ArrayList<Movie> list = new ArrayList<>();
    private RecyclerView rvTvs;
    private MovieAdapter adapter;
    private View viewed;
    private TextView empty;

    public FavTvFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_fav_tv, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        db = new DatabaseHelper(getActivity());

        viewed = view;
    }

    private void getList(View view){
        adapter = new MovieAdapter(getActivity());
        adapter.setListMovie(list);

        rvTvs = view.findViewById(R.id.rv_tvs);
        rvTvs.setHasFixedSize(true);
        list.addAll(db.getAllNotes("tv"));
        rvTvs.setLayoutManager(new GridLayoutManager(getActivity(),2));
        rvTvs.setAdapter(adapter);

        adapter.notifyDataSetChanged();

        empty = view.findViewById(R.id.empty);
        if(list.isEmpty()){
            empty.setVisibility(View.VISIBLE);
        } else {
            empty.setVisibility(View.GONE);
        }

        ItemClickSupport.addTo(rvTvs).setOnItemClickListener(new ItemClickSupport.OnItemClickListener() {
            @Override
            public void onItemClicked(RecyclerView recyclerView, int position, View view) {
                Toast.makeText(getActivity(), "Kamu memilih (favorite) "+list.get(position).getTitle(), Toast.LENGTH_LONG).show();
                Intent moveWithObjectIntent = new Intent(getActivity(), DetailActivity.class);
                moveWithObjectIntent.putExtra(DetailActivity.EXTRA_INTENT, list.get(position));
                startActivity(moveWithObjectIntent);
            }
        });
    }

    @Override
    public void onResume() {
        super.onResume();

        list.clear();
        getList(viewed);
    }

}
