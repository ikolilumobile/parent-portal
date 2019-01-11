package com.ikolilu.ikolilu.portal.adapter;

import android.content.Context;
import android.support.v4.view.PagerAdapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

/**
 * Created by Genuis on 07/04/2018.
 */
import com.ikolilu.ikolilu.portal.R;
public class SlideAdapter extends PagerAdapter{

    Context mCtx;
    LayoutInflater layoutInflater;

    public SlideAdapter( Context context ){
        this.mCtx = context;
    }

    public int[] slideImages = {
            R.drawable.getstared1,
            R.drawable.add_ward,
            R.drawable.register_ward,
            R.drawable.seleted_ward,
            R.drawable.view_academics,
            R.drawable.view_ward_bills,
            R.drawable.ward_payment
    };

    public String[] slide_headings =  {
            "MENU OPTIONS",
            "DASHBOARD [NOTICE BOARD]",
            "DASHBOARD [ADD WARD]",
            "DASHBOARD [MY WARDS]",
            "VIEWING WARD ACADEMIC INFO",
            "VIEWING WARD BILLS",
            "PAY WARD BILL"
    };

    public String[] slide_desc = {
            "",
            "",
            "",
            "",
            "",
            "",
            ""
    };

    @Override
    public int getCount() {
        return slide_headings.length;
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view == (RelativeLayout) object;
    }

    public Object instantiateItem(ViewGroup container, int position ){

        layoutInflater = (LayoutInflater) mCtx.getSystemService(mCtx.LAYOUT_INFLATER_SERVICE);
        View view = layoutInflater.inflate(R.layout.slide_layout, container, false);

        ImageView imageView = (ImageView) view.findViewById(R.id.imagepack);
        TextView slideHeading = (TextView) view.findViewById(R.id.slide_heading);
        TextView slideDesc = (TextView) view.findViewById(R.id.slide_body);

        imageView.setImageResource(slideImages[position]);
        slideHeading.setText(slide_headings[position]);
        slideDesc.setText(slide_desc[position]);

        container.addView(view);

        return view;
    }

    public void destroyItem( ViewGroup container, int postion, Object object){

        container.removeView( (RelativeLayout)object);

    }
}
