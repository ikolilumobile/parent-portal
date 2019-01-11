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

/**
 * Created by Genuis on 25/07/2018.
 */

public class WardSubjectCommentAdapter extends RecyclerView.Adapter<WardSubjectCommentAdapter.WardSubjectCommentViewHolder> {

    private Context mCtx;
    private List<WardSubjectComment> wardSubjectCommentList;
    WardSubjectComment wardSubjectComment;

    public WardSubjectCommentAdapter(Context mCtx, List<WardSubjectComment> wardSubjectCommentList) {
        this.mCtx = mCtx;
        this.wardSubjectCommentList = wardSubjectCommentList;
    }

    @Override
    public WardSubjectCommentViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(mCtx);
        View view = inflater.inflate(R.layout.aca_sub_com_list, null);

        return new WardSubjectCommentViewHolder(view);
    }

    @Override
    public void onBindViewHolder(WardSubjectCommentAdapter.WardSubjectCommentViewHolder holder, int position) {
        wardSubjectComment = wardSubjectCommentList.get(position);

        holder.subject.setText(wardSubjectComment.getcSubject());
        holder.comment.setText(wardSubjectComment.getcComment());
        holder.teacher.setText(wardSubjectComment.getcTeacher());
        holder.total.setText(wardSubjectComment.getcTotal());

    }

    @Override
    public int getItemCount() {
        return wardSubjectCommentList.size();
    }

    public class WardSubjectCommentViewHolder extends RecyclerView.ViewHolder {
        TextView subject, comment, teacher, total;
        RelativeLayout relativeLayout;

        public WardSubjectCommentViewHolder(View itemView) {
            super(itemView);

            subject = (TextView) itemView.findViewById(R.id.subject);
            comment = (TextView) itemView.findViewById(R.id.comment);
            teacher    = (TextView) itemView.findViewById(R.id.teacher_name);
            total   = (TextView) itemView.findViewById(R.id.total);

            relativeLayout = (RelativeLayout) itemView.findViewById(R.id.aca_sub_comment_box);
        }
    }
}


