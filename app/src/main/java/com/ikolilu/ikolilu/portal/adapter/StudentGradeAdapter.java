package com.ikolilu.ikolilu.portal.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.ikolilu.ikolilu.portal.R;
import com.ikolilu.ikolilu.portal.model.StudentGrade;

import java.util.List;

/**
 * Created by Genuis on 10/04/2018.
 */

public class StudentGradeAdapter extends RecyclerView.Adapter<StudentGradeAdapter.StudentGradeViewHolder> {

    private Context mCtx;
    private List<StudentGrade> studentGradeList;

    public StudentGradeAdapter(Context mCtx, List<StudentGrade> studentGradeList) {
        this.mCtx = mCtx;
        this.studentGradeList = studentGradeList;
    }

    @Override
    public StudentGradeViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(mCtx);
        View v =  inflater.inflate(R.layout.grades_aca_list_layout, null);
        return new StudentGradeViewHolder(v);
    }

    @Override
    public void onBindViewHolder(StudentGradeViewHolder holder, int position) {
        final StudentGrade studentGrade = studentGradeList.get(position);

        holder.subject.setText(studentGrade.getSubject());
        holder.classScore.setText(studentGrade.getClassScore());
        holder.examScore.setText(studentGrade.getExamScore());
        holder.total.setText(studentGrade.getTotal());
        holder.classPosition.setText(studentGrade.getPosition());
        holder.grade.setText(studentGrade.getGrade());
        //holder.comment.setText(studentGrade.getComment());
        holder.comment.setText("");
    }

    @Override
    public int getItemCount() {
        return studentGradeList.size();
    }

    class StudentGradeViewHolder extends RecyclerView.ViewHolder{
        TextView subject, classScore, examScore, total, classPosition, grade, comment;
        RelativeLayout relativeLayout;

        public StudentGradeViewHolder(View itemView) {
            super(itemView);

            classPosition = (TextView) itemView.findViewById(R.id.pos_con);
            subject = (TextView) itemView.findViewById(R.id.subject);
            classScore = (TextView) itemView.findViewById(R.id.cs_con);
            examScore = (TextView) itemView.findViewById(R.id.es_con);
            total = (TextView) itemView.findViewById(R.id.total_con);
            grade = (TextView) itemView.findViewById(R.id.grade_con);
            comment = (TextView) itemView.findViewById(R.id.comment_con);

            relativeLayout = (RelativeLayout) itemView.findViewById(R.id.aca_grade_box);
        }
    }
}
