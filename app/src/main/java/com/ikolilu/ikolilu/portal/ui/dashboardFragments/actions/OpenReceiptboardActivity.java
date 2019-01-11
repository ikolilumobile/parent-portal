package com.ikolilu.ikolilu.portal.ui.dashboardFragments.actions;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

import com.ikolilu.ikolilu.portal.R;

public class OpenReceiptboardActivity extends AppCompatActivity {

    private String title;
    private String desc;
    private String atta_link;
    private String school_name;

    TextView title_box;
    TextView desc_box;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_open_receiptboard);

        title_box = (TextView) findViewById(R.id.title);
        desc_box = (TextView) findViewById(R.id.description);

        Bundle bundle = getIntent().getExtras();
        if (!bundle.isEmpty()){
            title = bundle.getString("title");
            desc = bundle.getString("desc");
            atta_link  = bundle.getString("attch");
            school_name = bundle.getString("school_name");

            this.getSupportActionBar().setTitle(school_name);

            title_box.setText(title);
            desc_box.setText(desc);
        }
    }
}
