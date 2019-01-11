package com.ikolilu.ikolilu.portal.ui.wardService.wardOptionInfo.WardActivity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;

import com.ikolilu.ikolilu.portal.R;
import com.ikolilu.ikolilu.portal.adapter.DailyWardAdapter;
import com.ikolilu.ikolilu.portal.model.WardDailyActivity;

import java.util.ArrayList;
import java.util.List;

public class WardDailyxActivity extends AppCompatActivity {

    RecyclerView recyclerView;
    DailyWardAdapter adapter;

    List<WardDailyActivity> wardDailyActivities;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ward_daily);

        //this.getSupportActionBar().setTitle("Ward Daily Activities");
        this.getSupportActionBar().setTitle("UNDER DEVELOPMENT");

//        recyclerView = (RecyclerView) findViewById(R.id.ward_activity_list_view);
//        recyclerView.setHasFixedSize(true);
//        recyclerView.setLayoutManager(new LinearLayoutManager(getApplicationContext()));

        wardDailyActivities = new ArrayList<>();

        wardDailyActivities.clear();
        wardDailyActivities.add(new WardDailyActivity(
                1, "Mrs. Jane OpoKu",
                "Cultural Dance",
                "dance is an integral part of ceremonies, festivals, and rites. African dances are done in many countries throughout the world. ... Japanese cultural dance forms and styles span historical court dances, religious dances, and traditional folk dances.",
                "",
                "2mins"
        ));

        wardDailyActivities.add(new com.ikolilu.ikolilu.portal.model.WardDailyActivity(
                2, "Mrs. Jane OpoKu",
                "Cultural Dance",
                "dance is an integral part of ceremonies, festivals, and rites. African dances are done in many countries throughout the world. ... Japanese cultural dance forms and styles span historical court dances, religious dances, and traditional folk dances.",
                "",
                "2mins"
        ));

        adapter = new DailyWardAdapter(getApplicationContext(), wardDailyActivities);
        //recyclerView.setAdapter(adapter);
    }
}
