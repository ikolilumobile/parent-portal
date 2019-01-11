package com.ikolilu.ikolilu.portal.adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.ikolilu.ikolilu.portal.model.StudentScore;

import java.util.List;

import static com.ikolilu.ikolilu.portal.R.*;

/**
 * Created by Genuis on 11/04/2018.
 */


public class StudentScoreAdapter extends RecyclerView.Adapter<StudentScoreAdapter.StudentScoreViewHolder> {

    private Context mCtx;
    private List<StudentScore> studentScoreList;

    public StudentScoreAdapter(Context mCtx, List<StudentScore> studentScoreList) {
        this.mCtx = mCtx;
        this.studentScoreList = studentScoreList;
    }

    @Override
    public StudentScoreViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(mCtx);
        View v = inflater.inflate(layout.scores_aca_list_layout, null);
        return new StudentScoreViewHolder(v);
    }

    @SuppressLint("ResourceAsColor")
    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
    @Override
    public void onBindViewHolder(StudentScoreViewHolder holder, int position) {
        final StudentScore studentScore = studentScoreList.get(position);


        if (studentScore.getSubject().equals("CW:CLASS WORK")) {
            //holder.subject.setTextColor(color.green);
        }else if(studentScore.getSubject().equals("MT:MID TERM"))
        {
            //holder.subject.setTextColor(color.colorAccent);
        }else if(studentScore.getSubject().equals("FE:FINAL EXAM")){
            //holder.subject.setTextColor(color.yello);
        }else {
            //holder.subject.setTextColor(color.colorWhite);
        }

        int pos = studentScore.getSubject().indexOf(":");
        String m = studentScore.getSubject().substring(pos + 1);
        //holder.subject.setText(studentScore.getSubject().toLowerCase());
        holder.subject.setText(m + " - " + studentScore.getSubjectName());
        //holder.classworkId.setText(studentScore.getClassworkId().toLowerCase());
        holder.scores.setText(studentScore.getScore());
        holder.date.setText(studentScore.getData());
    }

    @Override
    public int getItemCount() {
        return studentScoreList.size();
    }

    class StudentScoreViewHolder extends RecyclerView.ViewHolder{
        TextView subject, scores, date, classworkId;
        RelativeLayout relativeLayout;

        public StudentScoreViewHolder(View itemView) {
            super(itemView);

            subject = (TextView) itemView.findViewById(id.score_cat);
            scores = (TextView) itemView.findViewById(id.es_con);
            date = (TextView) itemView.findViewById(id.date);
            //classworkId = (TextView) itemView.findViewById(R.id.cs_con);

            relativeLayout = (RelativeLayout) itemView.findViewById(id.score_box);
        }
    }
}