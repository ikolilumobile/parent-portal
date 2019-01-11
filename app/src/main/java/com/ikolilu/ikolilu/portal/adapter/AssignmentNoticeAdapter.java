package com.ikolilu.ikolilu.portal.adapter;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

/**
 * Created by Genuis on 01/04/2018.
 */
import com.ikolilu.ikolilu.portal.R;
import com.ikolilu.ikolilu.portal.model.AssignmentNotice;
import com.ikolilu.ikolilu.portal.ui.dashboardFragments.actions.OpenAssignmentboardActivity;

import java.util.List;

public class AssignmentNoticeAdapter extends RecyclerView.Adapter<AssignmentNoticeAdapter.AssignmentViewHolder>{

    private Context mCtx;
    private List<AssignmentNotice> assignmentNotices;

    public AssignmentNoticeAdapter(Context mCtx, List<AssignmentNotice> assignmentNotices) {
        this.mCtx = mCtx;
        this.assignmentNotices = assignmentNotices;
    }

    @Override
    public AssignmentViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(mCtx);
        View view = inflater.inflate(R.layout.assignmentboard_list_layout, null);

        return new AssignmentViewHolder(view);
    }

    @Override
    public void onBindViewHolder(AssignmentViewHolder holder, int position) {
        final AssignmentNotice assignmentNotice = assignmentNotices.get(position);
        holder.title.setText(assignmentNotice.getnTitle());
        holder.school_name.setText(assignmentNotice.getnSchool());
        holder.time.setText(assignmentNotice.getnTime());

        holder.relativeLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //Toast.makeText(mCtx, "You clicked: "+ assignmentNotice.getnDescription(), Toast.LENGTH_LONG).show();
                Intent intent = new Intent(mCtx, OpenAssignmentboardActivity.class);
                Bundle bundle = new Bundle();
                bundle.putString("title", assignmentNotice.getnTitle());
                bundle.putString("desc", assignmentNotice.getnDescription());
                bundle.putString("attch", assignmentNotice.getnAttachment());
                bundle.putString("school_name", assignmentNotice.getnSchool());
                intent.putExtras(bundle);

                mCtx.startActivity(intent);
            }
        });

    }

    @Override
    public int getItemCount() {
        return assignmentNotices.size();
    }

    class AssignmentViewHolder extends RecyclerView.ViewHolder{

        TextView title, school_name, time;
        RelativeLayout relativeLayout;

        public AssignmentViewHolder(View itemView) {
            super(itemView);

            title = (TextView) itemView.findViewById(R.id.title);
            school_name = (TextView) itemView.findViewById(R.id.school_name);
            time = (TextView) itemView.findViewById(R.id.time);
            relativeLayout = (RelativeLayout) itemView.findViewById(R.id.assign_board_box);
        }

    }
}
