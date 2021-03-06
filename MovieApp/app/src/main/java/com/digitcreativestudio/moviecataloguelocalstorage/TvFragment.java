package com.digitcreativestudio.moviecataloguelocalstorage;


import android.app.ProgressDialog;
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
import android.widget.Toast;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;


/**
 * A simple {@link Fragment} subclass.
 */
public class TvFragment extends Fragment {

    private static String URL_TV = "https://api.themoviedb.org/3/tv/popular?api_key=3f7d1c2a7daf6b9f4668c8cf54074ed1&language=en-US";
    private RecyclerView rvTvs;
    private MovieAdapter adapter;
    private ArrayList<Movie> list = new ArrayList<>();

    public TvFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_tv, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        adapter = new MovieAdapter(getActivity());
        adapter.setListMovie(list);

        rvTvs = view.findViewById(R.id.rv_tvs);
        rvTvs.setHasFixedSize(true);
//        list.addAll(TvsData.getListdata());
        rvTvs.setLayoutManager(new GridLayoutManager(getActivity(), 2));
        rvTvs.setAdapter(adapter);

        adapter.notifyDataSetChanged();
        requestList();

        ItemClickSupport.addTo(rvTvs).setOnItemClickListener(new ItemClickSupport.OnItemClickListener() {
            @Override
            public void onItemClicked(RecyclerView recyclerView, int position, View view) {
                Toast.makeText(getActivity(), "Kamu memilih "+list.get(position).getTitle(), Toast.LENGTH_LONG).show();
                Intent moveWithObjectIntent = new Intent(getActivity(), DetailActivity.class);
                moveWithObjectIntent.putExtra(DetailActivity.EXTRA_INTENT, list.get(position));
                startActivity(moveWithObjectIntent);
            }
        });
    }

    private void requestList() {
        final ProgressDialog progress = new ProgressDialog(getActivity());
        progress.setTitle("Loading");
        progress.setMessage("Wait while loading...");
        progress.setCancelable(false); // disable dismiss by tapping outside of the dialog
        progress.show();

        StringRequest srList = new StringRequest(Request.Method.GET, URL_TV,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONObject jsonObject = new JSONObject(response);
                            JSONArray jsonArray = jsonObject.getJSONArray("results");
                            for (int i=0; i<jsonArray.length(); i++) {
                                JSONObject objMovie = jsonArray.getJSONObject(i);
                                Movie obj = new Movie();
                                obj.setId(objMovie.getInt("id"));
                                obj.setTitle(objMovie.getString("name"));
                                obj.setPoster("https://image.tmdb.org/t/p/"+"w500"+objMovie.getString("poster_path"));
                                obj.setDescription(objMovie.getString("overview"));
                                obj.setGenre(objMovie.getString("vote_average"));
                                obj.setRelease(objMovie.getString("first_air_date"));
                                obj.setType("tv");
                                list.add(obj);
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                        adapter.notifyDataSetChanged();
                        progress.dismiss();
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        progress.dismiss();
                    }
                });
        RequestHandler.getInstance(getActivity()).addToRequestQueue(srList);
    }

}

