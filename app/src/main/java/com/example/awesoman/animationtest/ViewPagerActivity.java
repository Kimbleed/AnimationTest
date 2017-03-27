package com.example.awesoman.animationtest;

import android.app.Activity;
import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.text.Layout;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;

import java.io.File;
import java.net.URI;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Created by Awesome on 2017/2/24.
 */

public class ViewPagerActivity extends Activity {

    private ViewPager vp ;
    public static final String PROJECT_PATH ="/data/data/com.example.awesoman.animationtest";
    public static final String DATABASE_PATH = PROJECT_PATH+ File.separator+"databases";
    public static final String DATABASE_NAME = "owe2comic.db";
    public static final String COMIC_PATH = PROJECT_PATH+File.separator+"files"+File.separator +"ComicAll";

private ImageLoader imageLoader;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.vp_activity);
        initImageLoader();
        imageLoader = ImageLoader.getInstance();

        File file = new File(COMIC_PATH);

        File[] arrFile  = file.listFiles();
        Log.i("cenjunhui",file.getAbsolutePath()+"");
        Log.i("cenjunhui",file.exists()+"");
        Log.i("cenjunhui",arrFile.length+"");
        String chapterPath = arrFile[0].list()[1];
        Log.i("cenjunhui",arrFile[0].getName());
        Log.i("cenjunhui",arrFile[0].list()[1]);
        vp = (ViewPager)findViewById(R.id.myVp);
        vp.setAdapter(new MyVPAdapter(arrFile[0].getAbsolutePath()+File.separator+chapterPath,this));


    }

    class MyVPAdapter extends PagerAdapter{

        String chapterPath;
        List<String> photos;
        Context CTX;


        public MyVPAdapter(String chapterPath,Context context) {
            this.chapterPath = chapterPath;
            this.CTX = context;
            photos = new ArrayList<>();
            File file = new File(chapterPath);
            String[] arrFile = file.list();
            for(String str :arrFile) {
                photos.add(str);
            }
        }

        public MyVPAdapter(){

        }

        @Override
        public int getCount() {
            return photos.size();
        }

        @Override
        public Object instantiateItem(ViewGroup container, int position) {
            ImageView view = new ImageView(CTX);
            imageLoader.displayImage("file:///"+chapterPath+File.separator+photos.get(position),view);
            container.addView(view);
            return view;
        }

        @Override
        public boolean isViewFromObject(View view, Object object) {
            return view == object;
        }

        @Override
        public void destroyItem(ViewGroup container, int position, Object object) {
            container.removeView((View)object);
        }
    }

    public void initImageLoader(){
        DisplayImageOptions displayImageOptions = new DisplayImageOptions.Builder()
                .cacheInMemory(true)
                .cacheOnDisk(true)
                .resetViewBeforeLoading(true)
                .build();
        ImageLoaderConfiguration configuration = new ImageLoaderConfiguration.Builder(this)
                .memoryCacheSize(4*1024*1024)
                .defaultDisplayImageOptions(displayImageOptions)
                .build();
        ImageLoader.getInstance().init(configuration);
    }
}
