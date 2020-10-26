package com.example.lockdownlife.Views.Exercise.Fragment;

import android.content.Intent;
import android.os.Bundle;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.lockdownlife.Adapter.Exercise.FragmentAdapter;
import com.example.lockdownlife.Views.Exercise.ExerciseVideos;
import com.example.lockdownlife.R;

public class FridayFragment  extends Fragment {

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
        int[] fem = {R.drawable.but_kiks_fem, R.drawable.crunches_fem, R.drawable.jumping_jacks_fem, R.drawable.lunges_fem, R.drawable.plank_fem, R.drawable.push_ups_fem, R.drawable.sit_ups_female, R.drawable.squats_fem, R.drawable.wall_seet_fem};
        int[] male = {R.drawable.but_kiks_male, R.drawable.crunches_male, R.drawable.jumping_jacks_male, R.drawable.lunges_male, R.drawable.plank_male, R.drawable.push_ups_male, R.drawable.sit_ups_male, R.drawable.squats_male, R.drawable.wall_seet_male};

        name = getResources().getStringArray(R.array.name);
        description = getResources().getStringArray(R.array.friday_description);

        FragmentAdapter adapter = new FragmentAdapter(getActivity(),name,description,fem,male);
        rv.setAdapter(adapter);

        rv.addOnItemTouchListener(new RecyclerItemClickListener(getActivity(), rv, new RecyclerItemClickListener.OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                if(position == 0){
                    Intent intent = new Intent(getActivity(), ExerciseVideos.class);
                    intent.putExtra("url","https://www.youtube.com/watch?v=beSvHVN8pyc");
                    getActivity().startActivity(intent);
                }
                if(position == 1){
                    Intent intent = new Intent(getActivity(),ExerciseVideos.class);
                    intent.putExtra("url","https://www.youtube.com/watch?v=OuFDY0fwlvk&t=7s");
                    getActivity().startActivity(intent);
                }
                if(position == 2){
                    Intent intent = new Intent(getActivity(),ExerciseVideos.class);
                    intent.putExtra("url","https://www.youtube.com/watch?v=_M2Etme-tfE");
                    getActivity().startActivity(intent);
                }
                if(position == 3){
                    Intent intent = new Intent(getActivity(),ExerciseVideos.class);
                    intent.putExtra("url","https://www.youtube.com/watch?v=95j1mH27eXc");
                    getActivity().startActivity(intent);
                }
                if(position == 4){
                    Intent intent = new Intent(getActivity(),ExerciseVideos.class);
                    intent.putExtra("url","https://www.youtube.com/watch?v=oyLAcXHZTOc");
                    getActivity().startActivity(intent);
                }
                if(position == 5){
                    Intent intent = new Intent(getActivity(),ExerciseVideos.class);
                    intent.putExtra("url","https://www.youtube.com/watch?v=B5PPBUagc_4");
                    getActivity().startActivity(intent);
                }
                if(position == 6){
                    Intent intent = new Intent(getActivity(),ExerciseVideos.class);
                    intent.putExtra("url","youtube.com/watch?v=1fbU_MkV7NE");
                    getActivity().startActivity(intent);
                }
                if(position == 7){
                    Intent intent = new Intent(getActivity(),ExerciseVideos.class);
                    intent.putExtra("url","https://www.youtube.com/watch?v=-dtvAxibgYQ");
                    getActivity().startActivity(intent);
                }
                if(position == 8){
                    Intent intent = new Intent(getActivity(),ExerciseVideos.class);
                    intent.putExtra("url","https://www.youtube.com/watch?v=o3B_a_qxpfk");
                    getActivity().startActivity(intent);
                }
            }

            @Override
            public void onLongItemClick(View view, int position) {

            }
        }));

    }
}
