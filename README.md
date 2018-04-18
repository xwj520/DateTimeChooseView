# DateTimeChooseView
日期时间选择对话框工具

使用方法：
1、直接将该项目inport 工程作为library引用；

使用示例：

import com.jeekup.datetimechooseview.DateDialogFragment;

public class MainActivity extends Activity implements DateDialogFragment.DateTimeChangeListener {

    private Button date_btn;

    private Button datetime_btn;

    private Button time_btn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

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
                DateDialogFragment dialog0 = DateDialogFragment.newInstance(MainActivity.this, "VD");
                dialog0.show(getFragmentManager(), "DateDialog");

            } else if (i == R.id.time_btn) {
                DateDialogFragment dialog1 = DateDialogFragment.newInstance(MainActivity.this, "VT");
                dialog1.show(getFragmentManager(), "DateDialog");

            } else if (i == R.id.datetime_btn) {
                DateDialogFragment dialog2 = DateDialogFragment.newInstance(MainActivity.this, "DT");
                dialog2.show(getFragmentManager(), "DateDialog");
            }
        }
    }

    @Override
    public void onDateTimeComplete(String vStytle,String dateortime) {
        //日期时间回调
        if(vStytle.equals("VD")){
            Toast.makeText(MainActivity.this,"你选择的日期是："+dateortime,Toast.LENGTH_LONG).show();
        }else if(vStytle.equals("VT")){
            Toast.makeText(MainActivity.this,"你选择的时间是："+dateortime,Toast.LENGTH_LONG).show();
        }else{
            Toast.makeText(MainActivity.this,"你选择的日期和时间是："+dateortime,Toast.LENGTH_LONG).show();
        }
    }
}
