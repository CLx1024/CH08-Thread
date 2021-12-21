package tw.tcnr13.m0801;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.TextView;
import android.widget.TimePicker;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class M0801 extends AppCompatActivity implements View.OnClickListener{  // 用第二種監聽方法，這邊記得要改

    private TextView t001;
    private Button b001;
    private DatePicker date01;
    private TimePicker time01;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.m0801);
        setupViewComponent();  // 自定義函數
    }

    private void setupViewComponent() {
        // 從資源類別R中取得介面元件
        t001 = (TextView)findViewById(R.id.m0801_t001);
        b001 = (Button)findViewById(R.id.m0801_b001);
        date01 = (DatePicker)findViewById(R.id.m0801_date01);
        time01 = (TimePicker)findViewById(R.id.m0801_time01);

        b001.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        String s = getString(R.string.m0801_t001);
        // 自定義方法，先格式化成要求的格式
        String ss = Convert24to12(time01.getHour()+":"+time01.getMinute()+":00");  // 沒有秒所以補00 才能滿足hh:mm:ss格式

//        //24小時制
//        t001.setText(s + "\n" +
//                date01.getYear() + getString(R.string.year) +
//                (date01.getMonth() + 1) + getString(R.string.month) +
//                date01.getDayOfMonth() + getString(R.string.day) + "\n" +
//                time01.getHour() + getString(R.string.hour) +
//                time01.getMinute() + getString(R.string.minute));

        t001.setText(s + "\n" +
                date01.getYear() + getString(R.string.year) +
                (date01.getMonth() + 1) + getString(R.string.month) +
                date01.getDayOfMonth() + getString(R.string.day) + "\n" +
                "("+ss+")");


    }

    private String Convert24to12(String time) {
        String convertedTime="";
        try{
            SimpleDateFormat parseFormat = new SimpleDateFormat("hh:mm:ss");  // input (parseFormat是變數名可自取)
            // 格式化12小時制
            SimpleDateFormat displayFormat = new SimpleDateFormat("hh:mm a");  // output (displayFormat是變數名可自取)
            Date date = parseFormat.parse(time);
            convertedTime = displayFormat.format(date);

        }catch(ParseException e){
            e.printStackTrace();
        }
        return convertedTime;
    }
}