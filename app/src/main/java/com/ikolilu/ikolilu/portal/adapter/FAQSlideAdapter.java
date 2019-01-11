package com.ikolilu.ikolilu.portal.adapter;

import android.content.Context;
import android.support.v4.view.PagerAdapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.ikolilu.ikolilu.portal.R;

/**
 * Created by Genuis on 22/05/2018.
 */

public class FAQSlideAdapter extends PagerAdapter {

    Context mCtx;
    LayoutInflater layoutInflater;

    public FAQSlideAdapter( Context context ){
        this.mCtx = context;
    }


    public String[] slide_headings =  {
            "How to add a Ward?",
            "What is my Ward's ID?",
            "How do I know my firstname?",
            "I am having issues while trying to add my Ward?",
    };

    public String[] slide_desc = {
            "Before adding a ward, make sure that the email and firstname is the same as what you have provided to the school; this will be needed during verification of your ward. to add your ward you the \"ward's ID\", select the ward's school and enter your firstname.",
            "Your ward's ID is automatically generated, which can be seen on the bill or report card.",
            "You need to contact the school for further guardian information.",
            "Check email and firstname provided to the school. This should be the same as what has been provided to the school; because these credentials will be march on the values entering on the ADD WARD form.",
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
        View view = layoutInflater.inflate(R.layout.faq_slide_adapter, container, false);

        TextView slideHeading = (TextView) view.findViewById(R.id.slide_headingFQA);
        TextView slideDesc = (TextView) view.findViewById(R.id.slide_bodyFAQ);

        slideHeading.setText(slide_headings[position]);
        slideDesc.setText(slide_desc[position]);

        container.addView(view);

        return view;
    }

    public void destroyItem( ViewGroup container, int position, Object object){

        container.removeView( (RelativeLayout)object);

    }
}
