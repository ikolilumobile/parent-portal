package com.ikolilu.ikolilu.portal.ui.dashboardFragments.actions;

import android.app.DownloadManager;
import android.content.Context;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.ikolilu.ikolilu.portal.R;

public class OpenNoticeboardActivity extends AppCompatActivity {

    private String title;
    private String desc;
    private String atta_link;
    private String school_name;

    TextView title_box;
    TextView desc_box;
    TextView downloadlink;

    DownloadManager downloadManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_open_noticeboard);

        title_box = (TextView) findViewById(R.id.title);
        desc_box = (TextView) findViewById(R.id.description);
        downloadlink = (TextView) findViewById(R.id.downloadlink);


        Bundle bundle = getIntent().getExtras();
        if (!bundle.isEmpty()){
            title = bundle.getString("title");
            desc = bundle.getString("desc");
            atta_link  = bundle.getString("attch");
            school_name = bundle.getString("school_name");

            this.getSupportActionBar().setTitle(school_name);

            title_box.setText(title);
            //desc.toString().replaceAll("\n", " ");
            desc_box.setText(desc);

            downloadlink.setText(atta_link);
        }

        downloadlink.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String sUrl = "https://www.ikolilu.com/newsinfo/"+school_name+"/"+Uri.encode(atta_link);


                downloadManager = (DownloadManager)getSystemService(Context.DOWNLOAD_SERVICE);
                Uri uri = Uri.parse(sUrl);
                DownloadManager.Request request = new DownloadManager.Request(uri);
                request.setNotificationVisibility(DownloadManager.Request.VISIBILITY_VISIBLE_NOTIFY_COMPLETED);

                Long reference = downloadManager.enqueue(request);
                Toast.makeText(OpenNoticeboardActivity.this, "File is downloading..", Toast.LENGTH_LONG).show();
            }
        });
    }
}
