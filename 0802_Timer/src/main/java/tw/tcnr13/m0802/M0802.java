package tw.tcnr13.m0802;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.os.Handler;
import android.widget.TextView;

public class M0802 extends AppCompatActivity {

    private TextView time;
    private long startTime;
    private Handler handler = new Handler();  // 宣告物件並找一個空間放 (這裡不new等一下執行就閃退)
    private long spendTime;
    private long mins;
    private long secs;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.m0802);
        setupViewComponent();
    }

    private void setupViewComponent() {
        time = (TextView)findViewById(R.id.m0802_timer);
        startTime = System.currentTimeMillis();  // 取得目前手機時間  毫秒
        handler.postDelayed(updateTimer,100);  // 設定delay的時間 (工作命令單,多久開始執行)

        //time.setText(startTime/1000/60+"");  // 毫秒/1000/60
    }



    private Runnable updateTimer = new Runnable() {  // 這行前面自己手打不要自動生成  Runnable是工作命令單，執行你所要做的內容
        @Override
        public void run() {
            spendTime = System.currentTimeMillis()-startTime;  // 經過時間=現在時間-開始時間
            // 計算目前已過分鐘數
            mins = (spendTime/1000)/60;
            // 計算目前已過秒數
            secs = (spendTime/1000)%60;  // 餘數
            time.setText(String.format("%02d",mins)+":"+String.format("%02d",secs));  // %02d：2位 沒有的話補0 10進位
            handler.postDelayed(this,1000);  // 真正延遲的時間 (若要5秒更新一次 則改成5000)
        }
    };

    @Override
    protected void onStop() {
        super.onStop();
        handler.removeCallbacks(updateTimer);  // updateTimer是你的那個工作單號
        this.finish();
    }


    @Override
    public void onBackPressed() {
        //super.onBackPressed();  // 禁用返回鍵
    }
}