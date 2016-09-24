package com.ihandy.a2014011419.activity;

import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;

import com.ihandy.a2014011419.R;

public class ChannelActivity extends AppCompatActivity implements View.OnClickListener{

    SharedPreferences pref;
    SharedPreferences.Editor editor;

    private final String[] TABDEFAULT = {"sports", "top_stories", "technology", "health", "world", "national", "entertainment"};
    private boolean flag[];
    private final int[] imgId = {
            R.id.channel_sports_img,
            R.id.channel_top_stories_img,
            R.id.channel_technology_img,
            R.id.channel_health_img,
            R.id.channel_world_img,
            R.id.channel_national_img,
            R.id.channel_entertainment_img};


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_channel);

        pref = getSharedPreferences("channelData", MODE_PRIVATE);
        editor = pref.edit();
        for (int i = 0; i < TABDEFAULT.length; i++)
            Log.e("channel id", String.valueOf(imgId[i]));
        flag = new boolean[TABDEFAULT.length];
        for (int i = 0; i < TABDEFAULT.length; i++){
            flag[i] = pref.getBoolean(TABDEFAULT[i], true);
            ImageView img = (ImageView) findViewById(imgId[i]);
            img.setOnClickListener(this);
            if (flag[i])
                img.setImageDrawable(this.getResources().getDrawable(R.drawable.yes));
            else
                img.setImageDrawable(this.getResources().getDrawable(R.drawable.no));
        }
    }

    @Override
    public void onClick(View v) {
        int viewId = v.getId();
        Log.e("now click channel id", String.valueOf(viewId));
        for (int i = 0; i < TABDEFAULT.length; i++)
            if (viewId == imgId[i]){
                flag[i] = (! flag[i]);
                editor.putBoolean(TABDEFAULT[i], flag[i]);
                editor.commit();
                ImageView img = (ImageView) findViewById(imgId[i]);
                if (flag[i])
                    img.setImageDrawable(this.getResources().getDrawable(R.drawable.yes));
                else
                    img.setImageDrawable(this.getResources().getDrawable(R.drawable.no));
                break;
            }
    }
}
