package com.ikolilu.ikolilu.portal.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.ikolilu.ikolilu.portal.R;
import com.ikolilu.ikolilu.portal.model.AssigmentManager;

import java.util.List;

/**
 * Created by Genuis on 09/04/2018.
 */

public class AssignmentMangAdapter extends RecyclerView.Adapter<AssignmentMangAdapter.AssigmentManagViewHolder>{

    private Context mCtx;
    private List<AssigmentManager> assigmentManagerList;

    public AssignmentMangAdapter(Context mCtx, List<AssigmentManager> assigmentManagerList) {
        this.mCtx = mCtx;
        this.assigmentManagerList = assigmentManagerList;
    }

    @Override
    public AssigmentManagViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(mCtx);
        View v =  inflater.inflate(R.layout.assigment_mang_list_layout, null);
        return new AssigmentManagViewHolder(v);
    }

    @Override
    public void onBindViewHolder(AssigmentManagViewHolder holder, int position) {
        final AssigmentManager assigmentManager = assigmentManagerList.get(position);

        holder.subject.setText(assigmentManager.getSubject());
        holder.markScore.setText(assigmentManager.getMarkScore());
        holder.endDate.setText(assigmentManager.getEndDate());
        holder.viewQuestion.setText(assigmentManager.getViewQuestion());
        holder.viewSubmission.setText(assigmentManager.getViewSubmission());

    }

    @Override
    public int getItemCount() {
        return assigmentManagerList.size();
    }

    class AssigmentManagViewHolder extends   RecyclerView.ViewHolder{
        TextView subject, markScore, endDate, viewQuestion, viewSubmission;
        RelativeLayout relativeLayout;

        public AssigmentManagViewHolder(View itemView) {
            super(itemView);

            subject = (TextView) itemView.findViewById(R.id.subject);
            markScore = (TextView) itemView.findViewById(R.id.marks);
            endDate = (TextView) itemView.findViewById(R.id.enddate);
            viewQuestion = (TextView) itemView.findViewById(R.id.download_question_paper);
            viewSubmission = (TextView) itemView.findViewById(R.id.view_submission);

            relativeLayout = (RelativeLayout) itemView.findViewById(R.id.assig_manag_box);

        }
    }
}
