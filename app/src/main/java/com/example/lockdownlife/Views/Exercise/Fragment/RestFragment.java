package com.example.lockdownlife.Views.Exercise.Fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.lockdownlife.Adapter.Exercise.FragmentAdapter;
import com.example.lockdownlife.R;

public class RestFragment extends Fragment {

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.exercise_list,container,false);
        addRecyclerview(view);
        return view;
    }

    private void addRecyclerview(View view){
        RecyclerView rv = view.findViewById(R.id.rv);
        rv.setHasFixedSize(true);
        rv.setLayoutManager(new GridLayoutManager(getActivity(),1));

        String[] name;
        String[] description;
        int[] image_1 = {R.drawable.dayoff};
        int[] image_2 = {R.drawable.dayoff};
        name = getResources().getStringArray(R.array.rest_name);
        description = getResources().getStringArray(R.array.rest_desc);

        FragmentAdapter adapter = new FragmentAdapter(getActivity(),name,description,image_1,image_2);
        rv.setAdapter(adapter);

    }
}
