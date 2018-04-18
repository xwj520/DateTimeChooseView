package com.jeekup.datetimechooseview;


import android.app.Dialog;
import android.app.DialogFragment;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import com.jeekup.datetimechooseview.DateTimeViewUtils.DatePicker;
import com.jeekup.datetimechooseview.DateTimeViewUtils.TimerPicker;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
/*
 * Auth xiawenjian create 2018-4-17
 */
//日期时间选择对话框
public class DateDialogFragment extends DialogFragment {
    private static Context mycon;
    private TextView tip_date;
    private TextView lbl_date;
    private TextView tip_time;
    private TextView lbl_time;
    private DatePicker dp_test;
    private ImageView open_time_btn;
    private ImageView close_time_btn;
    private TimerPicker tp_test;
    private Button cancle_dt;
    private Button sure_dt;
    private String datetime;
    private Intent data;
    private boolean isshowtime = true;// 时间控件是否可用
    private String vStytle;// 显示样式（VD只显示日期VT只显示时间VDT显示日期和时间）
    // 记录当前的时间
    private String year;
    private String month;
    private String day;
    private int hour;
    private int minute;
    private String date_time = null;

    public DateDialogFragment() {
    }

    public static DateDialogFragment newInstance(Context mcon,String vStytle) {
        DateDialogFragment f = new DateDialogFragment();

        // Supply num input as an argument.
        Bundle args = new Bundle();
        args.putString("VSHOW", vStytle);
        f.setArguments(args);
        mycon=mcon;
        return f;
    }

    public interface DateTimeChangeListener
    {
        void onDateTimeComplete(String vStytle,String dateortime);
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        vStytle = getArguments().getString("VSHOW");
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        Dialog dialog = super.onCreateDialog(savedInstanceState);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);

        return dialog;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View mview=inflater.inflate(R.layout.fragment_date_dialog, container, false);
        tip_date=(TextView)mview.findViewById(R.id.tip_date);
        lbl_date=(TextView)mview.findViewById(R.id.lbl_date);
        tip_time=(TextView)mview.findViewById(R.id.tip_time);
        lbl_time=(TextView)mview.findViewById(R.id.lbl_time);
        dp_test=(DatePicker)mview.findViewById(R.id.dp_test);
        dp_test.setTextColor(0xff267be5).setFlagTextColor(Color.GRAY)
                .setTextSize(dip2px(mycon, 16)).setFlagTextSize(15)
                .setSoundEffectsEnabled(true);

        open_time_btn=(ImageView)mview.findViewById(R.id.open_time_btn);
        close_time_btn=(ImageView)mview.findViewById(R.id.close_time_btn);
        tp_test=(TimerPicker)mview.findViewById(R.id.tp_test);
        tp_test.setTextColor(0xff267be5).setFlagTextColor(Color.GRAY)
                .setTextSize(dip2px(mycon, 16)).setFlagTextSize(15)
                .setSoundEffectsEnabled(true);

        cancle_dt=(Button)mview.findViewById(R.id.cancle_dt);
        sure_dt=(Button)mview.findViewById(R.id.sure_dt);
        close_time_btn.setOnClickListener(new MyOnClickListener());
        open_time_btn.setOnClickListener(new MyOnClickListener());
        cancle_dt.setOnClickListener(new MyOnClickListener());
        sure_dt.setOnClickListener(new MyOnClickListener());

        if (vStytle.equals("VD")) {
            tip_time.setVisibility(View.GONE);
            lbl_time.setVisibility(View.GONE);
            tp_test.setVisibility(View.GONE);
            close_time_btn.setVisibility(View.GONE);
            open_time_btn.setVisibility(View.GONE);
        } else if (vStytle.equals("VT")) {
            tip_date.setVisibility(View.GONE);
            lbl_date.setVisibility(View.GONE);
            dp_test.setVisibility(View.GONE);
            close_time_btn.setVisibility(View.GONE);
            open_time_btn.setVisibility(View.GONE);
        } else if (vStytle.equals("DT")) {
            tip_time.setVisibility(View.VISIBLE);
            lbl_time.setVisibility(View.VISIBLE);
            tp_test.setVisibility(View.VISIBLE);

            tip_date.setVisibility(View.VISIBLE);
            lbl_date.setVisibility(View.VISIBLE);
            dp_test.setVisibility(View.VISIBLE);
            close_time_btn.setVisibility(View.GONE);
            open_time_btn.setVisibility(View.GONE);
        }

        // 初始化日期时间
        initDateTime(vStytle);

        return mview;
    }

    // 初始化
    private void initDateTime(String flag) {
        if (vStytle.equals("VD")) {
            // 日期的初始化
            SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
            Date curDate = new Date(System.currentTimeMillis());// 获取当前时间

            String str = formatter.format(curDate);
            lbl_date.setText(str);

            dp_test.setDate(curDate);
            dp_test.setOnDateChangedListener(new MyOnDateChangedListener());
        } else if (vStytle.equals("VT")) {
            // 时间初始化显示
            Calendar ca = Calendar.getInstance();
            int minute = ca.get(Calendar.MINUTE);// 分
            int hour = ca.get(Calendar.HOUR);// 小时
            int second = ca.get(Calendar.SECOND);// 秒
            lbl_time.setText(((hour < 10) ? ("0" + hour) : hour) + ":"
                    + ((minute < 10) ? ("0" + minute) : minute) + ":"
                    + ((second < 10) ? ("0" + second) : second));
            tp_test.setOnTimerChangedListener(new MyOnTimeChangeListener());
        } else if (vStytle.equals("DT")) {
            // 日期的初始化
            SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
            Date curDate = new Date(System.currentTimeMillis());// 获取当前时间

            String str = formatter.format(curDate);
            lbl_date.setText(str);
            dp_test.setOnDateChangedListener(new MyOnDateChangedListener());
            // 时间初始化显示
            Calendar ca = Calendar.getInstance();
            int minute = ca.get(Calendar.MINUTE);// 分
            int hour = ca.get(Calendar.HOUR);// 小时
            int second = ca.get(Calendar.SECOND);// 秒
            lbl_time.setText(((hour < 10) ? ("0" + hour) : hour) + ":"
                    + ((minute < 10) ? ("0" + minute) : minute) + ":"
                    + ((second < 10) ? ("0" + second) : second));

            date_time = str + " " + lbl_time.getText().toString();
            tp_test.setOnTimerChangedListener(new MyOnTimeChangeListener());
        }
    }

    private class MyOnDateChangedListener implements DatePicker.OnDateChangedListener {
        @Override
        public void onDateChanged(DatePicker view, int year, int monthOfYear,
                                  int dayOfMonth, int dayofweek) {
            if (monthOfYear < 10) {
                month = "0" + monthOfYear;
            } else {
                month = monthOfYear + "";
            }

            if (dayOfMonth < 10) {
                day = "0" + dayOfMonth;
            } else {
                day = dayOfMonth + "";
            }

            lbl_date.setText(year + "-" + month + "-" + day);
        }
    }

    private class MyOnTimeChangeListener implements TimerPicker.OnTimerChangedListener {
        @Override
        public void onDateChanged(TimerPicker view, int hour, int minute,
                                  int second) {
            lbl_time.setText(((hour < 10) ? ("0" + hour) : hour) + ":"
                    + ((minute < 10) ? ("0" + minute) : minute) + ":"
                    + ((second < 10) ? ("0" + second) : second));
        }
    }

    /**
     * 获得星期
     * @return
     */
    public static String getDayOfWeekCN(int day_of_week) {
        String result = null;
        switch (day_of_week) {
            case 1:
                result = "日";
                break;
            case 2:
                result = "一";
                break;
            case 3:
                result = "二";
                break;
            case 4:
                result = "三";
                break;
            case 5:
                result = "四";
                break;
            case 6:
                result = "五";
                break;
            case 7:
                result = "六";
                break;
            default:
                break;
        }
        return result;
    }

    private class MyOnClickListener implements View.OnClickListener {
        @Override
        public void onClick(View v) {
            int i = v.getId();
            if (i == R.id.cancle_dt) {
                dismiss();
            } else if (i == R.id.sure_dt) {
                String datetime_tmp = lbl_date.getText().toString().trim();
                String time_tmp = lbl_time.getText().toString().trim();
                if (vStytle.equals("VD")) {
                    datetime = datetime_tmp.substring(0, datetime_tmp.length());
                    Log.d("xwj", "日期格式：" + datetime);
                } else if (vStytle.equals("VT")) {
                    datetime = time_tmp;
                } else if (vStytle.equals("DT")) {
                    datetime = datetime_tmp + " " + time_tmp;
                }

                DateTimeChangeListener dtcListener = (DateTimeChangeListener) mycon;
                dtcListener.onDateTimeComplete(vStytle, datetime);
                Log.d("xwj", "显示时间：" + datetime);
                dismiss();

            } else if (i == R.id.close_time_btn) {
                close_time_btn.setVisibility(View.GONE);
                open_time_btn.setVisibility(View.VISIBLE);
                lbl_time.setVisibility(View.VISIBLE);
                tp_test.setAlpha(1f);
                tp_test.setEnabled(true);
                isshowtime = true;

            } else if (i == R.id.open_time_btn) {
                close_time_btn.setVisibility(View.VISIBLE);
                open_time_btn.setVisibility(View.GONE);
                lbl_time.setVisibility(View.GONE);
                tp_test.setAlpha(0.3f);
                tp_test.setEnabled(false);
                isshowtime = false;

            }
        }
    }


    /**
     * 根据手机的dp转换为分辨率 px(像素) 的单位
     */
    private int dip2px(Context context, float px) {
        final float scale = getScreenDensity(context);
        return (int) (px * scale + 0.5);
    }

    private float getScreenDensity(Context mcon) {
        DisplayMetrics dm = new DisplayMetrics();
        dm = mcon.getResources().getDisplayMetrics();

        return dm.density;  // 屏幕密度（0.75 / 1.0 / 1.5）
    }


}
