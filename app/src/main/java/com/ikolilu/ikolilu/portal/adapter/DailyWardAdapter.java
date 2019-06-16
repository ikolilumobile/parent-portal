package com.ikolilu.ikolilu.portal.adapter;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ResolveInfo;
import android.os.Parcelable;
import android.support.constraint.ConstraintLayout;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.ikolilu.ikolilu.portal.R;
import com.ikolilu.ikolilu.portal.model.WardDailyActivity;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
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

        holder.blog_user_name.setText(wardDailyActivity.getActions());
        holder.blog_desc.setText(wardDailyActivity.getDescription());
//        holder.blog_date.setText(wardDailyActivity.getTeacherName());
        holder.blog_date_new.setText(wardDailyActivity.getDate());

//        holder.blog_image.setImageResource(R.drawable.add_ward);
//        /// No Checking ..
        if(wardDailyActivity.getImage().equals(""))
        {
            Picasso.with(inflater.getContext()).load("https://www.insidehighered.com/sites/default/server_files/styles/large-copy/public/media/student%20shaming.jpg?itok=oH5df-pC")
                    .into(holder.blog_image);
        } else {
            Picasso.with(inflater.getContext()).load("https://www.ikolilu.com/schoolactivities/images/"+ wardDailyActivity.getImage())
                    .into(holder.blog_image);
        }

        final String wardNameAction = wardDailyActivity.getTeacherName() + " - " + wardDailyActivity.getActions();
        final String description = wardDailyActivity.getDescription();
        final String img = "https://www.ikolilu.com/schoolactivities/images/"+ wardDailyActivity.getImage();

        holder.blog_share_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                List<Intent> targetShareIntents=new ArrayList<Intent>();
                Intent shareIntent=new Intent();
                shareIntent.setAction(Intent.ACTION_SEND);
                shareIntent.setType("text/plain");
                List<ResolveInfo> resInfos= mCtx.getPackageManager().queryIntentActivities(shareIntent, 0);
                if(!resInfos.isEmpty()){
                    System.out.println("Have package");
                    for(ResolveInfo resInfo : resInfos){
                        String packageName=resInfo.activityInfo.packageName;
                        Log.i("Package Name", packageName);
                        if(packageName.contains("com.twitter.android") || packageName.contains("com.facebook.katana") || packageName.contains("com.kakao.story")){
                            Intent intent=new Intent();
                            intent.setComponent(new ComponentName(packageName, resInfo.activityInfo.name));
                            intent.setAction(Intent.ACTION_SEND);
                            intent.setType("text/plain");
                            intent.putExtra(Intent.EXTRA_STREAM, img);
                            intent.putExtra(Intent.EXTRA_SUBJECT, wardNameAction);
                            intent.putExtra(Intent.EXTRA_TEXT, description);
                            intent.setPackage(packageName);
                            targetShareIntents.add(intent);
                        }
                    }
                    if(!targetShareIntents.isEmpty()){
                        System.out.println("Have Intent");
                        Intent chooserIntent=Intent.createChooser(targetShareIntents.remove(0), "Choose app to share");
                        chooserIntent.putExtra(Intent.EXTRA_INITIAL_INTENTS, targetShareIntents.toArray(new Parcelable[]{}));
                        mCtx.startActivity(chooserIntent);
                    }else{
//                        System.out.println("Do not Have Intent");
//                        mCtx.showDialaog(this);
                    }
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return  wardDailyActivities.size();
    }

    class DailyWardViewHolder extends RecyclerView.ViewHolder {

        TextView blog_user_name,blog_date, blog_desc,blog_date_new, blog_share_btn;
        ImageView blog_image;

        ConstraintLayout blog_box;

        public DailyWardViewHolder(View itemView) {
            super(itemView);
//            blog_user_name = (TextView) itemView.findViewById(R.id.blog_user_name);
            blog_date      = (TextView) itemView.findViewById(R.id.blog_date);
            blog_desc      = (TextView) itemView.findViewById(R.id.blog_desc);
            blog_image     = (ImageView) itemView.findViewById(R.id.blog_image);

            blog_box       = (ConstraintLayout) itemView.findViewById(R.id.blog_box);

            blog_date_new = (TextView) itemView.findViewById(R.id.blog_date_new);
            blog_share_btn = (TextView) itemView.findViewById(R.id.blog_share_btn);
        }
    }

}
