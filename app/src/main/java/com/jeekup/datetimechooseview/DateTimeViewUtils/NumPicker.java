package com.jeekup.datetimechooseview.DateTimeViewUtils;

import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;
import android.widget.LinearLayout;

public class NumPicker extends LinearLayout {
	private NumWheelView numwv;
	private OnChangeListener onChangeListener; // onChangeListener
	private int num = 0;
	private float ratio = 0.5f;

	// Constructors
	public NumPicker(Context context) {
		super(context);
		init(context);
	}

	public NumPicker(Context context, AttributeSet attrs) {
		super(context, attrs);
		init(context);
	}

	/**
	 * 初始化组件
	 *
	 * @param context
	 */
	private void init(Context context) {
		int width0 = 300;
		int height0 = 600;
		numwv = new NumWheelView(context);

		int W = getScreenWidth(context);
		int H = getScreenHeight(context);

		Log.d("xwj", "屏幕分辨率：" + W + "****" + H);
		switch (W) {
		case 540:
			width0 = px2dip(context, 240);
			height0 = px2dip(context, 480);
			break;
		case 1080:
			width0 = px2dip(context, 600);
			height0 = px2dip(context, 1200);
			break;
		default:
			width0 = px2dip(context, 240);
			height0 = px2dip(context, 480);
			break;
		}

		LayoutParams lparams_hours = new LayoutParams(width0, height0);
		lparams_hours.setMargins(0, 0, 0, 0);
		numwv.setLayoutParams(lparams_hours);
		numwv.setAdapter(new NumericWheelAdapter(1, 30));
		numwv.setVisibleItems(3);
		numwv.addChangingListener(onNumsChangedListener);
		addView(numwv);
	}

	private OnNumWheelChangedListener onNumsChangedListener = new OnNumWheelChangedListener() {
		@Override
		public void onChanged(NumWheelView wv, int oldValue, int newValue) {
			// setNum(num);
			Log.d("xwj", "数字改变：" + oldValue + "***" + newValue);
			onChangeListener.onChange(newValue);
		}
	};

	/**
	 * 定义了监听时间改变的监听器借口
	 * 
	 * @author Wang_Yuliang
	 * 
	 */
	public interface OnChangeListener {
		void onChange(int num);
	}

	/**
	 * 设置监听器的方法
	 * 
	 * @param onChangeListener
	 */
	public void setOnChangeListener(OnChangeListener onChangeListener) {
		this.onChangeListener = onChangeListener;
	}

	@Override
	protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
		super.onMeasure(widthMeasureSpec, heightMeasureSpec);
		// 父容器传过来的宽度方向上的模式
		int widthMode = MeasureSpec.getMode(widthMeasureSpec);
		// 父容器传过来的高度方向上的模式
		int heightMode = MeasureSpec.getMode(heightMeasureSpec);

		// 父容器传过来的宽度的值
		int width = MeasureSpec.getSize(widthMeasureSpec) - getPaddingLeft()
				- getPaddingRight();
		// 父容器传过来的高度的值
		int height = MeasureSpec.getSize(heightMeasureSpec) - getPaddingLeft()
				- getPaddingRight();

		if (widthMode == MeasureSpec.EXACTLY
				&& heightMode != MeasureSpec.EXACTLY && ratio != 0.0f) {
			// 判断条件为，宽度模式为Exactly，也就是填充父窗体或者是指定宽度；
			// 且高度模式不是Exaclty，代表设置的既不是fill_parent也不是具体的值，于是需要具体测量
			// 且图片的宽高比已经赋值完毕，不再是0.0f
			// 表示宽度确定，要测量高度
			height = (int) (width / ratio + 0.5f);
			heightMeasureSpec = MeasureSpec.makeMeasureSpec(height,
					MeasureSpec.EXACTLY);
		} else if (widthMode != MeasureSpec.EXACTLY
				&& heightMode == MeasureSpec.EXACTLY && ratio != 0.0f) {
			// 判断条件跟上面的相反，宽度方向和高度方向的条件互换
			// 表示高度确定，要测量宽度
			width = (int) (height * ratio + 0.5f);

			widthMeasureSpec = MeasureSpec.makeMeasureSpec(width,
					MeasureSpec.EXACTLY);
		}

		// 默认选中
		setNum(numwv.getCurrentItem());
		super.onMeasure(widthMeasureSpec, heightMeasureSpec);
	}

	/**
	 * 设置数字
	 */
	public void setNum(int num) {
		numwv.setCurrentItem(num);
	}

	/**
	 * 获得数字
	 * 
	 * @return
	 */
	public int getNum() {
		return numwv.getCurrentItem();
	}

	/** * 根据手机的分辨率从 px(像素) 的单位 转成为 dp */
	public static int px2dip(Context context, float pxValue) {
		final float scale = context.getResources().getDisplayMetrics().density;
		return (int) (pxValue / scale + 0.5f);
	}

	/**
	 * 获取屏幕宽度
	 */
	public static int getScreenWidth(Context context) {
		return context.getResources().getDisplayMetrics().widthPixels;
	}

	/**
	 * 获取屏幕高度
	 */
	public static int getScreenHeight(Context context) {
		return context.getResources().getDisplayMetrics().heightPixels;
	}

}
