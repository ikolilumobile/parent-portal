package com.ikolilu.ikolilu.portal.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.ikolilu.ikolilu.portal.R;
import com.ikolilu.ikolilu.portal.model.SubjectComment;

import java.util.List;

/**
 * Created by Genuis on 06/07/2018.
 */

public class AcaCommentAdapter extends RecyclerView.Adapter<AcaCommentAdapter.AcaCommentViewHolder> {

    private Context mCtx;
    private List<SubjectComment> subjectCommentList;
    SubjectComment subjectComment;

    public AcaCommentAdapter(Context mCtx, List<SubjectComment> subjectCommentList) {
        this.mCtx = mCtx;
        this.subjectCommentList = subjectCommentList;
    }

    @Override
    public AcaCommentAdapter.AcaCommentViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(mCtx);
        View view = inflater.inflate(R.layout.aca_com_list, null);

        return new AcaCommentViewHolder(view);
    }

    @Override
    public void onBindViewHolder(AcaCommentAdapter.AcaCommentViewHolder holder, int position) {
        subjectComment = subjectCommentList.get(position);
        holder.subject.setText(subjectComment.getcSubject());
        holder.comment.setText(subjectComment.getcComment());
        holder.teacher.setText(subjectComment.getcTeacher());
    }

    @Override
    public int getItemCount() {
        return subjectCommentList.size();
    }

    class AcaCommentViewHolder extends RecyclerView.ViewHolder{
        TextView subject, comment, teacher;
        RelativeLayout relativeLayout;

        public AcaCommentViewHolder(View itemView) {
            super(itemView);

            subject = (TextView) itemView.findViewById(R.id.subject);
            comment = (TextView) itemView.findViewById(R.id.comment);
            teacher    = (TextView) itemView.findViewById(R.id.teacher_name);
            relativeLayout = (RelativeLayout) itemView.findViewById(R.id.aca_comment_box);
        }
    }
}


