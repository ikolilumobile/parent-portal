package com.ikolilu.ikolilu.portal.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.ikolilu.ikolilu.portal.R;
import com.ikolilu.ikolilu.portal.model.DailyAttendance;

import java.util.List;

/**
 * Created by Genuis on 08/04/2018.
 */

public class DailyAttendanceAdapter extends RecyclerView.Adapter<DailyAttendanceAdapter.DailyAttendanceViewHolder> {

    private Context mCtx;
    private List<DailyAttendance> dailyAttendanceList;

    public DailyAttendanceAdapter(Context mCtx, List<DailyAttendance> dailyAttendanceList) {
        this.mCtx = mCtx;
        this.dailyAttendanceList = dailyAttendanceList;
    }

    @Override
    public DailyAttendanceViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(mCtx);
        View v = inflater.inflate(R.layout.attendance_list_layout, null);
        return  new DailyAttendanceViewHolder(v);
    }

    @Override
    public void onBindViewHolder(DailyAttendanceViewHolder holder, int position) {
        final DailyAttendance dailyAttendance = dailyAttendanceList.get(position);

        holder.date.setText(dailyAttendance.getData());
        holder.status.setText(dailyAttendance.getStatus());
        holder.topic.setText(dailyAttendance.getTopic());
        holder.time.setText(dailyAttendance.getTime());
        holder.subject.setText(dailyAttendance.getSubject());
    }

    @Override
    public int getItemCount() {
        return dailyAttendanceList.size();
    }

    class DailyAttendanceViewHolder extends   RecyclerView.ViewHolder {

        TextView date, topic, subject, status, time;

        RelativeLayout relativeLayout;

        public DailyAttendanceViewHolder(View itemView) {
            super(itemView);

            date = itemView.findViewById(R.id.date);
            topic = itemView.findViewById(R.id.topic);
            subject = itemView.findViewById(R.id.subject);
            status = itemView.findViewById(R.id.status);
            time = itemView.findViewById(R.id.time);

            relativeLayout = itemView.findViewById(R.id.attendance_box);

        }
    }
}
