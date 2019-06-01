package com.ikolilu.ikolilu.portal.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.ikolilu.ikolilu.portal.R;
import com.ikolilu.ikolilu.portal.model.WardSubjectComment;

import java.util.List;

public class PreschoolReportAdapter extends RecyclerView.Adapter<PreschoolReportAdapter.PreschoolReportAdapterViewHolder> {

    private Context mCtx;
    private List<WardSubjectComment> wardSubjectCommentList;
    WardSubjectComment wardSubjectComment;

    public PreschoolReportAdapter(Context mCtx, List<WardSubjectComment> wardSubjectCommentList) {
        this.mCtx = mCtx;
        this.wardSubjectCommentList = wardSubjectCommentList;
    }

    @Override
    public PreschoolReportAdapterViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(mCtx);
        View view = inflater.inflate(R.layout.aca_sub_com_preschool, null);

        return new PreschoolReportAdapter.PreschoolReportAdapterViewHolder(view);
    }

    @Override
    public void onBindViewHolder(PreschoolReportAdapterViewHolder holder, int position) {

        wardSubjectComment = wardSubjectCommentList.get(position);

        if (wardSubjectComment.getcSubject().equals(""))
        {
            holder.subject.setHeight(0);
            holder.subject.setText(wardSubjectComment.getcSubject());
            holder.comment.setText(wardSubjectComment.getcComment());
            holder.teacher.setText(wardSubjectComment.getcTeacher());
            holder.total.setText(wardSubjectComment.getcTotal());
        }else {
            holder.subject.setHeight(100);
            holder.subject.setText(wardSubjectComment.getcSubject());
            holder.comment.setText(wardSubjectComment.getcComment());
            holder.teacher.setText(wardSubjectComment.getcTeacher());
            holder.total.setText(wardSubjectComment.getcTotal());
        }

    }

    @Override
    public int getItemCount() {
        return wardSubjectCommentList.size();
    }

    public class PreschoolReportAdapterViewHolder extends RecyclerView.ViewHolder {
        TextView subject, comment, teacher, total;
        RelativeLayout relativeLayout;
        public PreschoolReportAdapterViewHolder(View itemView) {
            super(itemView);


            subject = (TextView) itemView.findViewById(R.id.subject);
            comment = (TextView) itemView.findViewById(R.id.comment);
            teacher    = (TextView) itemView.findViewById(R.id.teacher_name);
            total   = (TextView) itemView.findViewById(R.id.total);

            relativeLayout = (RelativeLayout) itemView.findViewById(R.id.aca_sub_comment_box);
        }
    }
}


