package com.ikolilu.ikolilu.portal.adapter;

import android.content.Context;
import android.support.constraint.ConstraintLayout;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.ikolilu.ikolilu.portal.R;
import com.ikolilu.ikolilu.portal.model.WardDailyActivity;

import java.util.List;

/**
 * Created by Genuis on 27/08/2018.
 */

public class DailyWardAdapter extends RecyclerView.Adapter<DailyWardAdapter.DailyWardViewHolder>{

    private Context mCtx;
    private List<WardDailyActivity> wardDailyActivities;

    LayoutInflater inflater;

    public DailyWardAdapter(Context mCtx, List<WardDailyActivity> wardDailyActivities) {
        this.mCtx = mCtx;
        this.wardDailyActivities = wardDailyActivities;
    }

    @Override
    public DailyWardViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        inflater = LayoutInflater.from(mCtx);
        View view = inflater.inflate(R.layout.ward_activity_list, null);

        return new DailyWardViewHolder(view);
    }

    @Override
    public void onBindViewHolder(DailyWardViewHolder holder, int position) {
        WardDailyActivity wardDailyActivity = wardDailyActivities.get(position);
        holder.blog_user_name.setText(wardDailyActivity.getTeacherName());
        holder.blog_desc.setText(wardDailyActivity.getDescription());
        holder.blog_date.setText(wardDailyActivity.getDate());

        holder.blog_image.setImageResource(R.drawable.add_ward);
//        /// No Checking ..
//        Picasso.with(inflater.getContext()).load( "https://www.insidehighered.com/sites/default/server_files/styles/large-copy/public/media/student%20shaming.jpg?itok=oH5df-pC")
//                .into(holder.blog_image);
    }

    @Override
    public int getItemCount() {
        return  wardDailyActivities.size();
    }

    class DailyWardViewHolder extends RecyclerView.ViewHolder {

        TextView blog_user_name,blog_date, blog_desc;
        ImageView blog_image;

        ConstraintLayout blog_box;

        public DailyWardViewHolder(View itemView) {
            super(itemView);
            blog_user_name = (TextView) itemView.findViewById(R.id.blog_user_name);
            blog_date      = (TextView) itemView.findViewById(R.id.blog_date);
            blog_desc      = (TextView) itemView.findViewById(R.id.blog_desc);
            blog_image     = (ImageView) itemView.findViewById(R.id.blog_image);

            blog_box       = (ConstraintLayout) itemView.findViewById(R.id.blog_box);
        }
    }

}
