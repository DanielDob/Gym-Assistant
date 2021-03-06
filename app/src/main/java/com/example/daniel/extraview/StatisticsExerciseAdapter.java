package com.example.daniel.extraview;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.daniel.database.exercise.name.Exercise;
import com.example.daniel.gymassistant.Statistics;
import com.example.daniel.gymassistant.StatisticsCompareExercise;
import com.example.daniel.procedures.Units;

import java.util.List;

/**
 * Created by Daniel on 2017-08-12.
 */

public class StatisticsExerciseAdapter extends RecyclerView.Adapter<StatisticsExerciseAdapter.ExerciseHolder>{

    public static List<Exercise> exerciseList;


    private LayoutInflater inflater;

    public StatisticsExerciseAdapter(List<Exercise> moviesList, Context c) {
        this.exerciseList = moviesList;
        this.inflater = LayoutInflater.from(c);
    }
    public interface ItemClickCallback {
        void onItemClick(int p);
        void onSecondaryIconClick(int p);
    }


    public class ExerciseHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        public TextView name = new TextView(inflater.getContext());
        public StatisticsExerciseAdapter.MyCustomEditTextListener myCustomEditTextListener;

        public ExerciseHolder(View view) {
            super(view);
            ((LinearLayout) view).addView(name);
            myCustomEditTextListener= new MyCustomEditTextListener();
            setNameAndListener(name,myCustomEditTextListener);


        }

        private void setNameAndListener(TextView exerciseName, final MyCustomEditTextListener myCustomEditTextListener) {
            exerciseName.setTextSize(Units.dpToPx(inflater.getContext(),10));
            exerciseName.setPadding(0,0,0,Units.dpToPx(inflater.getContext(),10));
            exerciseName.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    StatisticsCompareExercise.exercise= exerciseList.get(myCustomEditTextListener.position);
                    Intent intent = new Intent(v.getContext(), StatisticsCompareExercise.class);
                    v.getContext().startActivity(intent);
                }
            });

        }

        @Override
        public void onClick(View v) {

        }
    }

    private class MyCustomEditTextListener implements TextWatcher {
        private int position;
        public void updatePosition(int position) {
            this.position = position;
        }

        @Override
        public void beforeTextChanged(CharSequence charSequence, int i, int i2, int i3) {
            // no op
        }

        @Override
        public void onTextChanged(CharSequence charSequence, int i, int i2, int i3) {

        }

        @Override
        public void afterTextChanged(Editable editable) {
            // no op
        }
    }
    @Override
    public void onViewRecycled(ExerciseHolder holder) {
        super.onViewRecycled(holder);
    }





    @Override
    public ExerciseHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = new LinearLayout(inflater.getContext());
        return new ExerciseHolder(itemView);
    }

    @Override
    public void onBindViewHolder(ExerciseHolder holder, int position) {

        holder.myCustomEditTextListener.updatePosition(position);
        holder.name.setText(exerciseList.get(position).getName());
        boolean czyJest=false;
        for(int i = 0; i< Statistics.allExercises.length; i++){
            if(exerciseList.get(position).getName().equals(Statistics.allExercises[i].getName())){
                czyJest=true;
                break;
            }
        }
        if(czyJest){
            holder.name.setTextColor(Color.parseColor("#939393"));
        } else {
            holder.name.setTextColor(Color.parseColor("#000000"));
        }
    }

    @Override
    public int getItemCount() {
        return exerciseList.size();
    }

}
