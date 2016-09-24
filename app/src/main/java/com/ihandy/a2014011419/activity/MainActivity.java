package com.ihandy.a2014011419.activity;


import android.content.Intent;
import android.content.SharedPreferences;
import android.support.design.widget.NavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.os.Bundle;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.facebook.drawee.backends.pipeline.Fresco;
import com.github.florent37.materialviewpager.MaterialViewPager;
import com.github.florent37.materialviewpager.header.HeaderDesign;
import com.gitonway.lee.niftymodaldialogeffects.lib.Effectstype;
import com.gitonway.lee.niftymodaldialogeffects.lib.NiftyDialogBuilder;
import com.ihandy.a2014011419.myfragment.NewsRefreshFragment;
import com.ihandy.a2014011419.news.NewsDetail;
import com.ihandy.a2014011419.R;
import com.ihandy.a2014011419.news.NewsItem;

import java.util.ArrayList;


public class MainActivity extends DrawerActivity {

    private MaterialViewPager myViewPager;
    private Toolbar toolbar;
    private Effectstype effect;
    private NiftyDialogBuilder dialogBuilder;

    //  常量
    private final String[] CHANNELDEFAULT = {"sports", "top_stories", "technology", "health", "world", "national", "entertainment"};
    private final int[] CHANNELDRAWABLE = new int[]{R.drawable.sports,
            R.drawable.top_stories,
            R.drawable.technology,
            R.drawable.health,
            R.drawable.world,
            R.drawable.national,
            R.drawable.entertainment
    };;
    private final int[] CHANNELCOLORRES= new int[]{R.color.blue,
            R.color.Color_Aqua,
            R.color.accent,
            R.color.Color_Brown,
            R.color.Color_Chocolate,
            R.color.Color_DarkKhaki,
            R.color.Color_Aquamarine
    };;

    //  分类标签显示
    private boolean[] channelFlag = null;
    //  新闻分类标签
    private ArrayList<String> channelList = null;
    //  新闻碎片页面
    private ArrayList<Fragment> newsFrag = null;
    //  标签颜色
    private ArrayList<Integer> newsColor = null;
    //  标签图片
    private ArrayList<Integer> newsDrawable = null;


    private NavigationView navigationView;
    private DrawerLayout drawerLayout;

    SharedPreferences pref;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Fresco.initialize(this);
        setContentView(R.layout.activity_main);
        setTitle("");
        myViewPager = (MaterialViewPager) findViewById(R.id.materialViewPager);
        toolbar = myViewPager.getToolbar();
        if (toolbar != null) {
            //使用Toolbar代替Actionbar
            setSupportActionBar(toolbar);
        }
        channelFlag = new boolean[CHANNELDEFAULT.length];
        channelList = new ArrayList<>();
        newsFrag = new ArrayList<>();
        newsColor = new ArrayList<>();
        newsDrawable = new ArrayList<>();
        //  adapter 在声明周期中只 set 一次

        dialogBuilder = NiftyDialogBuilder.getInstance(this);
        effect = Effectstype.Slit;


        setAda();
        navigateSet();
    }

    private void navigateSet() {

        //初始化 NavigationView
        navigationView = (NavigationView) findViewById(R.id.navigation_view);

        //Setting Navigation View Item Selected Listener to handle the item click of the navigation menu
        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            // This method will trigger on item Click of navigation menu
            @Override
            public boolean onNavigationItemSelected(MenuItem menuItem) {
                //Checking if the item is in checked state or not, if not make it in checked state
                if(menuItem.isChecked())
                    menuItem.setChecked(false);
                else
                    menuItem.setChecked(true);

                //Closing drawer on item click
                drawerLayout.closeDrawers();
                Intent intent;
                //Check to see which item was being clicked and perform appropriate action
                switch (menuItem.getItemId()){
                    case R.id.nav_manage:
                        Toast.makeText(getApplicationContext(),"Inbox Selected",Toast.LENGTH_SHORT).show();
                        intent = new Intent(MainActivity.this, ChannelActivity.class);
                        startActivity(intent);
                        return true;
                    case R.id.nav_share:
                        Toast.makeText(getApplicationContext(),"Stared Selected",Toast.LENGTH_SHORT).show();
                        intent = new Intent(MainActivity.this, FavouriteListActivity.class);
                        startActivity(intent);
                        return true;
                    case R.id.nav_send:
                        dialogBuilder
                                .withTitle("PROGRAMER")                                  //.withTitle(null)  no title
                                .withTitleColor("#FFFFFF")                                  //def
                                .withDividerColor("#11000000")                              //def
                                .withMessage("白云仁\n2014011419\nphone:18201207096\n")                     //.withMessage(null)  no Msg
                                .withMessageColor("#FFFFFFFF")                              //def  | withMessageColor(int resid)
                                .withDialogColor("#FFE74C3C")                               //def  | withDialogColor(int resid)                               //def
                                .withIcon(getResources().getDrawable(R.drawable.icon))
                                .isCancelableOnTouchOutside(true)                           //def    | isCancelable(true)
                                .withDuration(700)                                          //def
                                .withEffect(effect)                                         //def Effectstype.Slidetop
                                .show();
                        return true;
                    default:
                        Toast.makeText(getApplicationContext(),"Somethings Wrong",Toast.LENGTH_SHORT).show();
                        return true;

                }
            }
        });

        // Initializing Drawer Layout and ActionBarToggle
        drawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
    }

    @Override
    public void onResume(){
        super.onResume();
        Log.e("main activity", "onresume");
//        new TabTask().execute();
        addFcd();
        myViewPager.getViewPager().getAdapter().notifyDataSetChanged();
    }

    void addFcd()
    {
        ArrayList<String > tempList = new ArrayList<>();
        getChannelFlag();
        for (int i = 0; i < CHANNELDEFAULT.length; i++)
            if (channelFlag[i])
                tempList.add(CHANNELDEFAULT[i]);
        ArrayList<Fragment> tempFrag = null;
        ArrayList<Integer> tempColor = null;
        ArrayList<Integer> tempDrawable = null;
        tempFrag = new ArrayList<>();
        tempColor = new ArrayList<>();
        tempDrawable = new ArrayList<>();
        for (int i = 0; i < tempList.size(); i++) {
            tempFrag.add(NewsRefreshFragment.newInstance(tempList.get(i)));
        }
        for (int i = 0; i < channelFlag.length; i++)
            if (channelFlag[i]) {
                tempColor.add(CHANNELCOLORRES[i]);
                tempDrawable.add(CHANNELDRAWABLE[i]);
            }
        //  避免 title 和 fagment 没有同时更新
        //  好像不加 synchronized 也没问题
        synchronized (this){
            newsColor = tempColor;
            newsDrawable = tempDrawable;
            newsFrag = tempFrag;
            channelList = tempList;
        }
    }

    public void getChannelFlag(){
        pref = getSharedPreferences("channelData", MODE_PRIVATE);
        for (int i = 0; i < CHANNELDEFAULT.length; i++)
            channelFlag[i] = pref.getBoolean(CHANNELDEFAULT[i], true);
        for (int i = 0; i < CHANNELDEFAULT.length; i++)
            Log.e("channel flag", String.valueOf(channelFlag[i]));
    }

    void setAda(){
        //FragmentPageAdapter适合固定的页面较少的场合；而FragmentStatePagerAdapter则适合
        //于页面较多或者页面内容非常复杂(需占用大量内存)的情况！
        myViewPager.getViewPager() .setAdapter(new FragmentStatePagerAdapter(getSupportFragmentManager()) {
            @Override
            public Fragment getItem(int position) {
                if (position < getCount())
                    return newsFrag.get(position);
                return null;
            }

            //  如果是 POSITION_NONE，那么该 Item 会被
            // destroyItem(ViewGroup container, int position, Object object) 方法 remove 掉，然后重新加载
            //  有个很明显的缺陷，那就是会刷新所有的 Item，这将导致系统资源的浪费，所以这种方式不适合数据量较大的场景。
            @Override
            public int getItemPosition(Object object) {
                return FragmentStatePagerAdapter.POSITION_NONE;
            }

            @Override
            public int getCount() {
                return newsFrag.size();
            }

            @Override
            public CharSequence getPageTitle(int position) {
                if (position < getCount())
                    return channelList.get(position);
                return null;

            }
        });


        myViewPager.setMaterialViewPagerListener(new MaterialViewPager.Listener() {
            @Override
            public HeaderDesign getHeaderDesign(int page) {
                if (page < channelList.size())
//                    return HeaderDesign.fromColorResAndDrawable(
//                            CHANNELCOLORRES[page],
//                            getResources().getDrawable(CHANNELDRAWABLE[page]));
                    return HeaderDesign.fromColorResAndDrawable(
                            newsColor.get(page),
                            getResources().getDrawable(newsDrawable.get(page)));

                //execute others actions if needed (ex : modify your header logo)
                return HeaderDesign.fromColorResAndUrl(
                        R.color.salmon,
                        "https://fs01.androidpit.info/a/63/0e/android-l-wallpapers-630ea6-h900.jpg");
            }
        });
        try {
            myViewPager.getViewPager().setOffscreenPageLimit(myViewPager.getViewPager().getAdapter().getCount());
        }
        catch (Exception e){
            e.printStackTrace();
        }
        myViewPager.getPagerTitleStrip().setViewPager(myViewPager.getViewPager());

        View logo = findViewById(R.id.logo_white);
        if (logo != null) {
            logo.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    myViewPager.notifyHeaderChanged();
                    //Toast.makeText(getApplicationContext(), "Yes, the title is clickable", Toast.LENGTH_SHORT).show();
                    NewsDetail.showDetail(MainActivity.this, NewsItem.getDefaultNewsItem());
                }
            });
        }
    }

/*
    //  后台获取信息
    class TabTask extends AsyncTask<String, Void, ArrayList<String>> {

        ArrayList<Fragment> tempFrag = null;
        ArrayList<Integer> tempColor = null;
        ArrayList<Integer> tempDrawable = null;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        @Override
        protected ArrayList<String> doInBackground(String... params) {
          //    在 background 中不能直接修改 ui 中的数据！！！
          ArrayList<String> channelList = new ArrayList<>();
            *//**
             * 下面两行注释用于 tab 也在后台获取,
              *//*
          *//*   MyJsonParser temp = new MyJsonParser(channelList, newsFrag);
            temp.obtain();
          *//*
            if (channelList.size() == 0){
                getChannelFlag();
                for (int i = 0; i < CHANNELDEFAULT.length; i++)
                    if (channelFlag[i]){
                        channelList.add(CHANNELDEFAULT[i]);
                //    newsFrag.add(NewsRefreshFragment.newInstance(CHANNELDEFAULT[i]));     不能在 background 中操作 UI 数据 ！！！
                }
            }
            tempFrag = new ArrayList<>();
            tempColor = new ArrayList<>();
            tempDrawable = new ArrayList<>();
            for (int i = 0; i < channelList.size(); i++) {
                tempFrag.add(NewsRefreshFragment.newInstance(channelList.get(i)));
            }
            for (int i = 0; i < channelFlag.length; i++)
                if (channelFlag[i]) {
                    tempColor.add(CHANNELCOLORRES[i]);
                    tempDrawable.add(CHANNELDRAWABLE[i]);
                }
            return channelList;
        }

        //  该函数在ui线程中执行
        @Override
        protected void onPostExecute(ArrayList<String> data) {
            super.onPostExecute(data);
            Log.e("MainActivity onpost", "exe");
            for (int i = 0; i < data.size(); i++)
                Log.e("onpost main activity", data.get(i));
            Log.e("before", String.valueOf(channelList.size()));

            channelList.clear();
            channelList.addAll(data);

            Log.e("after", String.valueOf(channelList.size()));
            //  必须将此行为用 synchronize 设置为原子操作
            // 不然清空后，用户突然操作，会出现 null 或 outbound 的exception
            //  以下两段效率都不行，都有延迟
            *//*synchronized (this) {
                newsDrawable.clear();
                newsColor.clear();
                newsFrag.clear();
                for (int i = 0; i < channelList.size(); i++) {
                    newsFrag.add(NewsRefreshFragment.newInstance(channelList.get(i)));
                }
                for (int i = 0; i < channelFlag.length; i++)
                    if (channelFlag[i]) {
                        newsColor.add(CHANNELCOLORRES[i]);
                        newsDrawable.add(CHANNELDRAWABLE[i]);
                    }
            }*//*


            synchronized (this) {
                ArrayList<Integer> swapD = newsDrawable;
                ArrayList<Fragment> swapF = newsFrag;
                ArrayList<Integer> swapC = newsColor;
                Log.e("onpost", "we");
                newsColor = tempColor;
                Log.e("onpost", "C");
                newsDrawable = tempDrawable;
                Log.e("onpost", "D");
                newsFrag = tempFrag;
                Log.e("onpost", "F");

            }

            //  通知数据有变化
            myViewPager.getViewPager().getAdapter().notifyDataSetChanged();
        }
    }*/


}
