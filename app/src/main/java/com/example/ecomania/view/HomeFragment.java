package com.example.ecomania.view;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.SimpleAdapter;

import com.example.ecomania.R;

import java.util.ArrayList;
import java.util.HashMap;

public class HomeFragment extends Fragment {

    View view;
    ListView liste_theme;
    ArrayList<HashMap<String, String>> theme = new ArrayList<HashMap<String,String>>();

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_home, container, false);
        liste_theme = (ListView) view.findViewById(R.id.liste_theme);

        // static data
        HashMap<String,String> map = new HashMap<String,String>();
        map.put("theme", "ecologie");
        map.put("description", "test ecologie");
        theme.add(map);

        HashMap<String,String> map2 = new HashMap<String,String>();
        map2.put("theme", "tri");
        map2.put("description", "test tri");
        theme.add(map2);

        HashMap<String,String> map3 = new HashMap<String,String>();
        map3.put("theme", "eco geste");
        map3.put("description", "test eco geste");
        theme.add(map3);
        // static data end

        //avoir les extras navigating
        /*Bundle extras = getActivity().getIntent().getExtras();
        if(extras != null){
            String id = extras.getString("id");
        }*/

        SimpleAdapter adapter = new SimpleAdapter(view.getContext(), theme, R.layout.theme_item,
                new String[]{"theme", "description"},
                new int[]{R.id.theme, R.id.description}
        );
        liste_theme.setAdapter(adapter);

        return view;
    }
}