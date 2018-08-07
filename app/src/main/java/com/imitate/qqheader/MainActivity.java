package com.imitate.qqheader;

import android.media.MediaCodec;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ImageView;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    ImitateQQHeaderSrollView imitateQQHeaderSrollView;
    private ImageView iv;
    private List<String> listData = new ArrayList<>();

    //
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        imitateQQHeaderSrollView = findViewById(R.id.lv);
        for (int i = 0; i < 30; i++) {
            listData.add("data" + i);
        }
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1,
                listData
        );
        View header = View.inflate(this, R.layout.listview_header, null);
        iv = (ImageView) header.findViewById(R.id.layout_header_image);
        imitateQQHeaderSrollView.setZoomImageView(iv);
        imitateQQHeaderSrollView.addHeaderView(header);
        imitateQQHeaderSrollView.setAdapter(adapter);

    }

}
