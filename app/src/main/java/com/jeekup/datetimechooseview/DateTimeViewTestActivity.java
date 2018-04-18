package com.jeekup.datetimechooseview;

import android.app.Activity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

public class DateTimeViewTestActivity extends Activity implements DateDialogFragment.DateTimeChangeListener{

    private Button date_btn;

    private Button datetime_btn;

    private Button time_btn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_date_time_view_test);

        date_btn=(Button)findViewById(R.id.date_btn);
        time_btn=(Button)findViewById(R.id.time_btn);
        datetime_btn=(Button)findViewById(R.id.datetime_btn);
        date_btn.setOnClickListener(new MyDateBtnOnClickListener());
        datetime_btn.setOnClickListener(new MyDateBtnOnClickListener());
        time_btn.setOnClickListener(new MyDateBtnOnClickListener());
    }

    private class MyDateBtnOnClickListener implements View.OnClickListener{
        @Override
        public void onClick(View view) {
            int i = view.getId();
            if (i == R.id.date_btn) {
                DateDialogFragment dialog0 = DateDialogFragment.newInstance(DateTimeViewTestActivity.this, "VD");
                dialog0.show(getFragmentManager(), "DateDialog");

            } else if (i == R.id.time_btn) {
                DateDialogFragment dialog1 = DateDialogFragment.newInstance(DateTimeViewTestActivity.this, "VT");
                dialog1.show(getFragmentManager(), "DateDialog");

            } else if (i == R.id.datetime_btn) {
                DateDialogFragment dialog2 = DateDialogFragment.newInstance(DateTimeViewTestActivity.this, "DT");
                dialog2.show(getFragmentManager(), "DateDialog");
            }
        }
    }

    @Override
    public void onDateTimeComplete(String vStytle,String dateortime) {
        //日期时间回调
        if(vStytle.equals("VD")){
            Toast.makeText(DateTimeViewTestActivity.this,"你选择的日期是："+dateortime,Toast.LENGTH_LONG).show();
        }else if(vStytle.equals("VT")){
            Toast.makeText(DateTimeViewTestActivity.this,"你选择的时间是："+dateortime,Toast.LENGTH_LONG).show();
        }else{
            Toast.makeText(DateTimeViewTestActivity.this,"你选择的日期和时间是："+dateortime,Toast.LENGTH_LONG).show();
        }
    }
}
