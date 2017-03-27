package com.example.awesoman.animationtest;

import android.app.Activity;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.webkit.WebView;
import android.widget.TextView;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * Created by Awesome on 2017/2/23.
 */

public class AsynActivity extends Activity {

    private TextView web,clock;

    private MyHandler handler = new MyHandler();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.web_activity);
        web = (TextView)findViewById(R.id.webTxt);
        clock = (TextView)findViewById(R.id.clockTxt);

        MyAsyncTask task = new MyAsyncTask();
        task.execute();

    }

    public String connect(){
        try{
            Thread.sleep(5000);
        URL url = new URL("https://www.baidu.com/");
        HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("GET");
            connection.setReadTimeout(8000);
            connection.setConnectTimeout(8000);
            InputStream in = connection.getInputStream();
            BufferedReader reader =new BufferedReader(new InputStreamReader(in));

            StringBuffer stringBuffer = new StringBuffer();
            String shuitong ;
            while((shuitong = reader.readLine())!=null){
                stringBuffer.append(shuitong);
            }
            return stringBuffer.toString();
        }
        catch (Exception e){
            e.printStackTrace();
        }
        return  null;
    }


    class MyHandler  extends Handler{
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            int num = Integer.parseInt(clock.getText().toString());
            num++;
            if(num<=5) {
                clock.setText(num + "");
                Message m = new Message();
                this.sendMessageDelayed(m, 1000);
            }
        }
    }

    class MyAsyncTask extends AsyncTask{

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            Message msg = new Message();
            clock.setText(0+"");
            handler.sendMessageDelayed(msg,1000);
        }

        @Override
        protected void onPostExecute(Object o) {
            super.onPostExecute(o);
            String text = (String)o;
            web.setText(text);
        }

        @Override
        protected Object doInBackground(Object[] params) {
            return connect();
        }


    }
}
