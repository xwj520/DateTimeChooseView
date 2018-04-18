package com.jeekup.datetimechooseview.DateTimeViewUtils;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.widget.LinearLayout;

import com.jeekup.datetimechooseview.R;

import java.util.Calendar;
import java.util.Date;

public class TimerPicker extends LinearLayout implements NumberPicker.OnValueChangeListener {

	private NumberPicker mHour;
	private NumberPicker mMinute;
	private NumberPicker mSecond;
	private Calendar mCalendar;

	private OnTimerChangedListener mOnTimerChangedListener;

	private LayoutInflater mLayoutInflater;

	public TimerPicker(Context context) {
		this(context, null);
	}

	public TimerPicker(Context context, AttributeSet attrs) {
		super(context, attrs);
		mLayoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		init();
	}

	private void init() {
		mLayoutInflater.inflate(R.layout.time_picker_layout, this, true);
		mHour = (NumberPicker) findViewById(R.id.hour_picker);
		mMinute = (NumberPicker) findViewById(R.id.minute_picker);
		mSecond = (NumberPicker) findViewById(R.id.second_picker);

		mHour.setOnValueChangeListener(this);
		mMinute.setOnValueChangeListener(this);
		mSecond.setOnValueChangeListener(this);

		mCalendar = Calendar.getInstance();
		setDate(mCalendar.getTime());
	}

	public TimerPicker setDate(Date date) {
		mCalendar.setTime(date);

		mHour.setCurrentNumber(mCalendar.get(Calendar.HOUR_OF_DAY));
		mMinute.setCurrentNumber(mCalendar.get(Calendar.MINUTE));
		mSecond.setCurrentNumber(mCalendar.get(Calendar.SECOND));
		return this;
	}

	@Override
	public void onValueChange(final NumberPicker picker, final int oldVal, final int newVal) {
		
		if (picker == mHour) {
			mCalendar.set(Calendar.HOUR_OF_DAY, newVal);
		} else if (picker == mHour) {
			mCalendar.set(Calendar.HOUR_OF_DAY, newVal - 1);
		} else if (picker == mMinute) {
			mCalendar.set(Calendar.MINUTE, newVal);
		}else if(picker==mSecond){
			mCalendar.set(Calendar.SECOND, newVal);
		}
		
		notifyDateChanged();
	}
	
	/**
     * The callback used to indicate the user changes\d the date.
     */
    public interface OnTimerChangedListener {

        void onDateChanged(TimerPicker view, int hour, int minute, int second);
    }
    
    public TimerPicker setOnTimerChangedListener(OnTimerChangedListener l) {
    	mOnTimerChangedListener = l;
    	return this;
    }
    
    private void notifyDateChanged() {
    	if (mOnTimerChangedListener != null) {
    		mOnTimerChangedListener.onDateChanged(this, getHour(), getMinute(),getSecond());
    	}
    }
    
    public int getHour() {
    	return mCalendar.get(Calendar.HOUR_OF_DAY);
    }
    
    public int getMinute() {
    	return mCalendar.get(Calendar.MINUTE);
    }
    
    public int getSecond() {
    	return mCalendar.get(Calendar.SECOND);
    }
    
    
    
    public TimerPicker setSoundEffect(Sound sound) {
    	mHour.setSoundEffect(sound);
    	mMinute.setSoundEffect(sound);
    	mSecond.setSoundEffect(sound);
    	return this;
    }
    
    @Override
    public void setSoundEffectsEnabled(boolean soundEffectsEnabled) {
    	super.setSoundEffectsEnabled(soundEffectsEnabled);
    	mHour.setSoundEffectsEnabled(soundEffectsEnabled);
    	mMinute.setSoundEffectsEnabled(soundEffectsEnabled);
    	mSecond.setSoundEffectsEnabled(soundEffectsEnabled);
    }
    
    public TimerPicker setRowNumber(int rowNumber) {
    	mHour.setRowNumber(rowNumber);
    	mMinute.setRowNumber(rowNumber);
    	mSecond.setRowNumber(rowNumber);
    	return this;
    }
    
    public TimerPicker setTextSize(float textSize) {
    	mHour.setTextSize(textSize);
    	mMinute.setTextSize(textSize);
    	mSecond.setTextSize(textSize);
    	return this;
    }
    
    public TimerPicker setFlagTextSize(float textSize) {
    	mHour.setFlagTextSize(textSize);
    	mMinute.setFlagTextSize(textSize);
    	mSecond.setFlagTextSize(textSize);
    	return this;
    }
    
    public TimerPicker setTextColor(int color) {
    	mHour.setTextColor(color);
    	mMinute.setTextColor(color);
    	mSecond.setTextColor(color);
    	return this;
    }
    
    public TimerPicker setFlagTextColor(int color) {
    	mHour.setFlagTextColor(color);
    	mMinute.setFlagTextColor(color);
    	mSecond.setFlagTextColor(color);
    	return this;
    }
    
    public TimerPicker setBackground(int color) {
    	super.setBackgroundColor(color);
    	mHour.setBackground(color);
    	mMinute.setBackground(color);
    	mSecond.setBackground(color);
    	return this;
    }

}
