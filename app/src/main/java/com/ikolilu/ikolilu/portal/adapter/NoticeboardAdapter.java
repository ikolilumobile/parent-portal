package com.ikolilu.ikolilu.portal.adapter;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

/**
 * Created by Genuis on 01/04/2018.
 */
import com.ikolilu.ikolilu.portal.helper.SchoolDAO;
import com.ikolilu.ikolilu.portal.model.NoticeBoard;

import java.util.List;

import com.ikolilu.ikolilu.portal.R;
import com.ikolilu.ikolilu.portal.ui.dashboardFragments.actions.OpenNoticeboardActivity;

public class NoticeboardAdapter extends RecyclerView.Adapter<NoticeboardAdapter.NoticeboardViewHolder>{

    private Context mCtx;
    private List<NoticeBoard> noticeBoardList;
    private boolean isAttch = false;
    private SchoolDAO schoolDAO;

    public NoticeboardAdapter(Context mCtx, List<NoticeBoard> noticeBoardList) {
        this.mCtx = mCtx;
        this.noticeBoardList = noticeBoardList;
    }

    @Override
    public NoticeboardViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        LayoutInflater inflater = LayoutInflater.from(mCtx);
        View view = inflater.inflate(R.layout.noticeboard_list_layout, null);

        return new NoticeboardViewHolder(view);
    }

    @Override
    public void onBindViewHolder(NoticeboardViewHolder holder, int position) {
        final NoticeBoard noticeBoard = noticeBoardList.get(position);
        schoolDAO = new SchoolDAO(mCtx);
        holder.title.setText(noticeBoard.getnType());

        holder.time.setText(noticeBoard.getnTime());

        schoolDAO = new SchoolDAO(mCtx);
        Cursor cursor = schoolDAO.getSchoolBysId(noticeBoard.getnSchool());
        String schoolName = null;
        if (cursor.getCount() == 0){
            Log.d("Testing SQLite", "count: "+ cursor.getCount());
        }else{
            StringBuffer stringBuffer = new StringBuffer();
            while (cursor.moveToNext() || cursor == null){
                stringBuffer.append("code " + cursor.getString(1) + "\n");
                schoolName = cursor.getString(1);
            }
            Log.d("Testingx", schoolName);
        }

        holder.school_name.setText(schoolName);
        holder.relativeLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String message = null;
                message = noticeBoard.getnAttachment();


                Intent intent = new Intent(mCtx, OpenNoticeboardActivity.class);
                Bundle bundle = new Bundle();

                bundle.putString("title", noticeBoard.getnType());
                bundle.putString("desc", noticeBoard.getnDescription());
                bundle.putString("attch", message);
                bundle.putString("school_name", noticeBoard.getnSchool());
                isAttch = (noticeBoard.getnAttachment() != "" || noticeBoard.getnAttachment() != null);
                bundle.putBoolean("isAttch", isAttch);
                intent.putExtras(bundle);


                mCtx.startActivity(intent);

            }
        });
    }

    @Override
    public int getItemCount() {
        return  noticeBoardList.size();
    }

    class NoticeboardViewHolder extends RecyclerView.ViewHolder{

        TextView title, school_name, time;
        RelativeLayout relativeLayout;

        public NoticeboardViewHolder(View itemView) {
            super(itemView);

            title = (TextView) itemView.findViewById(R.id.title);
            school_name = (TextView) itemView.findViewById(R.id.school_name);
            time = (TextView) itemView.findViewById(R.id.time);
            relativeLayout = (RelativeLayout) itemView.findViewById(R.id.notice_board_box);
        }
    }
}
