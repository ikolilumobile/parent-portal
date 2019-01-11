package com.ikolilu.ikolilu.portal.adapter;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.ikolilu.ikolilu.portal.helper.SchoolDAO;
import com.ikolilu.ikolilu.portal.model.Ward;

import java.util.List;

import com.ikolilu.ikolilu.portal.R;
import com.ikolilu.ikolilu.portal.network.networkStorage.GeneralPref;
import com.ikolilu.ikolilu.portal.ui.wardService.WardInfoActivity;
import com.squareup.picasso.Picasso;

/**
 * Created by Genuis on 29/03/2018.
 */

/*
**  Ward RecyclerView.Adapter
**  Ward RecyclerView.ViewHolder
*/

public class WardAdapter extends RecyclerView.Adapter<WardAdapter.WardViewHolder>{

    private Context mCtx;
    private List<Ward> wardList;

    private SchoolDAO schoolDAO;
    LayoutInflater inflater;

    public WardAdapter(Context mCtx, List<Ward> wardList) {
        this.mCtx = mCtx;
        this.wardList = wardList;
    }

    @Override
    public WardViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        inflater = LayoutInflater.from(mCtx);
        View view = inflater.inflate(R.layout.ward_list_layout, null);
        return new WardViewHolder(view);
    }

    @Override
    public void onBindViewHolder(WardViewHolder holder, int position) {
        final Ward ward = wardList.get(position);

        holder.studentName.setText(ward.getStudentName());
        holder.schoolName.setText(ward.getSchoolName());
        holder.wardCode.setText(ward.getWardId());

        try {
            if (ward.getImage().equals("")) {
                holder.imagePhoto.setBackgroundResource(R.drawable.student);
            } else {
                Picasso.with(inflater.getContext()).load("https://www.ikolilu.com/academics/studentimgs/" + ward.getSchoolId() + "/" + ward.getImage())
                        .into(holder.imagePhoto);
            }
        }catch (Exception e)
        {
            holder.imagePhoto.setBackgroundResource(R.drawable.student);
        }

        //holder.imagePhoto.setImageDrawable(mCtx.getResources().getDrawable(ward.getImage()));

        holder.relativeLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                schoolDAO = new SchoolDAO(mCtx);
                Cursor cursor = schoolDAO.getSchoolByName(ward.getSchoolName());
                String schoolId = null;

                if (cursor.getCount() == 0){
                    Log.d("Testing SQLite", "count: "+ cursor.getCount());
                }else{
                    StringBuffer stringBuffer = new StringBuffer();
                    while (cursor.moveToNext() || cursor == null){
                        stringBuffer.append("code " + cursor.getString(2) + "\n");
                        schoolId = cursor.getString(2);
                    }
                }

                Intent intent = new Intent(mCtx, WardInfoActivity.class);
//                Bundle bundle = new Bundle();
//                bundle.putString("ward_student", ward.getStudentName());
//                bundle.putString("ward_school", ward.getSchoolName());
//                bundle.putString("ward_code", ward.getWardId());
//                bundle.putString("school_code", schoolId);
//                intent.putExtras(bundle);

                // Store the Bundles in a sharedPreference
                // Const: prefix -> ward_
                GeneralPref generalPref = new GeneralPref(view.getContext());
                generalPref.setSelectedWardItems("ward_name", ward.getStudentName());
                generalPref.setSelectedWardItems("ward_school", ward.getSchoolName());
                generalPref.setSelectedWardItems("ward_code", ward.getWardId());
                generalPref.setSelectedWardItems("school_code", schoolId);
                generalPref.setSelectedWardItems("ward_class", ward.getwClass());

                String term = ward.getTerm();
                String acaYear = "";
                try{
                    String[] split = term.split("-");
                    term = split[0];
                    acaYear = split[1];
                }catch (ArrayIndexOutOfBoundsException e)
                {
                    e.printStackTrace();
                }


                generalPref.setSelectedWardItems("ward_term", term);
                generalPref.setSelectedWardItems("ward_aca_year", acaYear);
                mCtx.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return wardList.size();
    }

    class WardViewHolder extends RecyclerView.ViewHolder{

        ImageView imagePhoto;
        TextView studentName, schoolName, wardCode;
        public RelativeLayout relativeLayout;

        public WardViewHolder(View itemView) {
            super(itemView);

            imagePhoto = itemView.findViewById(R.id.imagePhoto);
            studentName = itemView.findViewById(R.id.student_name);
            schoolName = itemView.findViewById(R.id.school_name);
            wardCode = itemView.findViewById(R.id.ward_code);
            relativeLayout = (RelativeLayout) itemView.findViewById(R.id.ward_box);
        }
    }
}
