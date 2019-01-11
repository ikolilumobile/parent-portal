package com.ikolilu.ikolilu.portal.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.ikolilu.ikolilu.portal.R;
import com.ikolilu.ikolilu.portal.model.LessonPlan;

import java.util.List;

/**
 * Created by Genuis on 09/04/2018.
 */

public class LessonPlanAdapter extends RecyclerView.Adapter<LessonPlanAdapter.LessonPlanViewHolder>{


    private Context mCtx;
    private List<LessonPlan> lessonPlanList;

    public LessonPlanAdapter(Context mCtx, List<LessonPlan> lessonPlanList) {
        this.mCtx = mCtx;
        this.lessonPlanList = lessonPlanList;
    }

    @Override
    public LessonPlanViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(mCtx);
        View v =  inflater.inflate(R.layout.lesson_list_layout, null);
        return new LessonPlanViewHolder(v);
    }

    @Override
    public void onBindViewHolder(LessonPlanViewHolder holder, int position) {
        final LessonPlan lessonPlan = lessonPlanList.get(position);

        holder.subject.setText(lessonPlan.getSubject());
        holder.date.setText(lessonPlan.getDate());
        holder.unit.setText(lessonPlan.getUnit());
        holder.timetable.setText(lessonPlan.getViewTimeTable());
        holder.download_units.setText(lessonPlan.getViewUnits());
    }

    @Override
    public int getItemCount() {
        return lessonPlanList.size();
    }

    class LessonPlanViewHolder extends   RecyclerView.ViewHolder{
        TextView subject, unit, timetable, date, download_units;
        RelativeLayout relativeLayout;

        public LessonPlanViewHolder(View itemView) {
            super(itemView);

            subject = (TextView) itemView.findViewById(R.id.subject);
            unit = (TextView) itemView.findViewById(R.id.unit);
            timetable = (TextView) itemView.findViewById(R.id.view_timetable);
            date = (TextView) itemView.findViewById(R.id.date);
            download_units = (TextView) itemView.findViewById(R.id.download_unit);

            relativeLayout = (RelativeLayout) itemView.findViewById(R.id.lesson_box);

        }
    }
}
