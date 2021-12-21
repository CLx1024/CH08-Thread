package tw.tcnr13.m0802a;

import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class M0802a extends AppCompatActivity implements View.OnClickListener{  // 用第二種監聽方法，這邊記得要改

    private TextView t001;
    private Button b001;
    private DatePicker date01;
    private TimePicker time01;
    private TextView ans01;
    private TextView timer;
    private MediaPlayer startmusic;
    private int years01;
    private int months01;
    private int dates01;
    private int hours01;
    private int minius01;
    private Calendar cg;
    private long endTime;
    private Handler handler = new Handler();
    private long spendTime;
    private long hrs, mins, secs;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.m0802a);
        setupViewComponent();  // 自定義函數
    }

    private void setupViewComponent() {
        // 從資源類別R中取得介面元件
        ans01 = (TextView)findViewById(R.id.m0802a_ans01);
        date01 = (DatePicker)findViewById(R.id.m0802a_date01);
        time01 = (TimePicker)findViewById(R.id.m0802a_time01);
        b001 = (Button)findViewById(R.id.m0802a_b001);
        t001 = (TextView)findViewById(R.id.m0802a_t001);
        timer = (TextView)findViewById(R.id.m0802a_timer);

        startmusic = MediaPlayer.create(M0802a.this,R.raw.s01);  // 抓取歌曲

        b001.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {  // 監聽
        String s = getString(R.string.m0802a_ans01);
        years01 = date01.getYear();//取得畫面的"年"
        months01 = date01.getMonth();//取得畫面的"月"
        dates01 = date01.getDayOfMonth();//取得畫面的"日"
        hours01 = time01.getHour();// 取得畫面的"小時"
        minius01 = time01.getMinute();// 取得畫面的"分鐘"

        // 顯示選擇的日期和時間
        ans01.setText(s +
                years01 + getString(R.string.n_yy) +
                (months01 + 1) + getString(R.string.n_mm)     +
                dates01 + getString(R.string.m_dd) +
                hours01 + getString(R.string.d_hh) +
                minius01      + getString(R.string.d_mm));
        //--------------------------------------
        cg = Calendar.getInstance();  // 設定日曆新物件
        cg.set(years01,months01,dates01,hours01,minius01);  // 將日期及時間設定進去物件
        endTime = cg.getTimeInMillis();  // 取得時間毫秒資料
        //設定開始Delay的時間
        handler.postDelayed(updateTimer,100);



    }



    private Runnable updateTimer = new Runnable() {
        @Override
        public void run() {
            spendTime = endTime-System.currentTimeMillis();  // 計算剩餘時間
            hrs = (spendTime/1000)/60/60;  // 經過小時數
            mins = (spendTime/1000)/60%60; // 經過分鐘數
            secs = (spendTime/1000)%60; // 經過秒數 (取餘數)

            if(spendTime<0 || hrs>99){
                Toast.makeText(getApplicationContext(),getString(R.string.m0802a_err),Toast.LENGTH_SHORT).show();
                timer.setText(getString(R.string.m0802a_timer));
                t001.setText(getString(R.string.m0802a_t001));
                handler.removeCallbacks(updateTimer);
            }else{  //開始計時
                t001.setText(getString(R.string.m0802a_alerm));
                music_set();  // 音樂重設
                timer.setText(String.format("%02d",hrs)+":"+String.format("%02d",mins)+":"+String.format("%02d",secs));
                handler.postDelayed(this,1000);
            }
            if(hrs==0 && mins==0 && secs==0){
                startmusic.start();
                t001.setText(getString(R.string.m0802a_t001));
                handler.removeCallbacks(updateTimer);
            }
        }
    };

    // 音樂恢復初始設定值
    private void music_set() {
        // 中斷播放中音樂
        if(startmusic!=null && startmusic.isPlaying()){  // 如果有設定(不是空的)且在播放中
            startmusic.stop();
        }
    }

    @Override
    protected void onStop() {
        super.onStop();
        handler.removeCallbacks(updateTimer);
        this.finish();
    }

    @Override
    public void onBackPressed() {
        //super.onBackPressed();
    }
}