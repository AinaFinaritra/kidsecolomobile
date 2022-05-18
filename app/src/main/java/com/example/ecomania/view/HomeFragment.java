package com.example.ecomania.view;

import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.SimpleAdapter;

import com.example.ecomania.R;
import com.example.ecomania.controller.ThemeController;
import com.example.ecomania.model.Theme;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;

public class HomeFragment extends Fragment {

    View view;
    ListView liste_theme;
    public JSONObject tmp_theme;
    private ThemeController themeController;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        this.themeController = ThemeController.getInstance();

        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_home, container, false);
        liste_theme = (ListView) view.findViewById(R.id.liste_theme);

        //dynamique test
        Theme themeModel = new Theme();
        themeModel.getThemes(view.getContext());

        // static data
        this.themeController.makeThemeTest();
        ArrayList<HashMap<String, String>> theme = new ArrayList<HashMap<String,String>>();
        theme = this.themeController.getThemeTest();
        // static data end


        // affichage de la liste
        SimpleAdapter adapter = new SimpleAdapter(view.getContext(), theme, R.layout.theme_item,
                new String[]{"theme", "description"},
                new int[]{R.id.theme, R.id.description}
        );
        liste_theme.setAdapter(adapter);
        // affichage de la liste end

        // click sur liste
        liste_theme.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Intent intent = new Intent(view.getContext().getApplicationContext(), DetailHomeActivity2.class);
                intent.putExtra("position", i);
                startActivity(intent);
            }
        });
        // click sur liste end

        return view;
    }
}