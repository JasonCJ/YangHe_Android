package com.yanghe.sujiu.view;

import java.util.Timer;
import java.util.TimerTask;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.Handler;
import android.os.Message;
import android.util.AttributeSet;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Gallery;

/***
 * 自定义gallery
 * 
 * @author Administrator
 * 
 */
@SuppressWarnings("deprecation")
public class AdvGallery extends Gallery {
	private static final int TOUCH_STATE_REST = 0;
	private static final int TOUCH_STATE_SCROLLING = 1;
	private int mTouchState = TOUCH_STATE_REST;
	private float mLastMotionX;

	private int currentSelection;// 当前被选中的索引
	private AdvImageAdapter adapter;

	public AdvGallery(Context context, AttributeSet attrs) {
		super(context, attrs);
	}

	@Override
	public void setSelection(int position) {
		super.setSelection(position);
		setCurrentSelection(position);
	}

	public void setCurrentSelection(int position) {
		this.currentSelection = position;
	}

	/**
	 * 显示下一个视图
	 */
	public void showNext() {
		int nextIndex = currentSelection + 1;
		if (nextIndex >= adapter.getCount()) {
			nextIndex = 0;
		}
		currentSelection = nextIndex;
		onKeyDown(KeyEvent.KEYCODE_DPAD_RIGHT, null);
		if (null != itemSelectedCallback) {
			itemSelectedCallback.onItemSelected(currentSelection % adapter.getDataCount());
		}
	}

	/**
	 * 判断向左向右，可以控制一次只滑动一个childView
	 */
	@Override
	public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX, float velocityY) {
		int keyCode;
		if (isScrollingLeft(e1, e2)) {
			keyCode = KeyEvent.KEYCODE_DPAD_LEFT;
		} else {
			keyCode = KeyEvent.KEYCODE_DPAD_RIGHT;
		}
		onKeyDown(keyCode, null);
		return true;
	}

	@Override
	public boolean onInterceptTouchEvent(MotionEvent ev) {
		final int action = ev.getAction();
		if ((action == MotionEvent.ACTION_MOVE) && (mTouchState != TOUCH_STATE_REST)) {
			return true;
		}
		final float x = ev.getX();
		switch (action) {
		case MotionEvent.ACTION_MOVE:
			final int xDiff = (int) Math.abs(mLastMotionX - x);
			if (xDiff > 0) {
				mTouchState = TOUCH_STATE_SCROLLING;
			}
			break;
		case MotionEvent.ACTION_DOWN:
			mLastMotionX = x;
			mTouchState = TOUCH_STATE_REST;
			break;
		case MotionEvent.ACTION_CANCEL:
		case MotionEvent.ACTION_UP:
			mTouchState = TOUCH_STATE_REST;
			break;
		}
		return mTouchState != TOUCH_STATE_REST;
	}

	private boolean isScrollingLeft(MotionEvent e1, MotionEvent e2) {
		// 判断向左还是向右滑动
		float f2 = e2.getX();
		float f1 = e1.getX();
		if (f2 > f1) {// 向左滑动
			return true;
		} else if (f2 < f1) {// 向右滑动
			return false;
		}
		return false;
	}

	@Override
	public boolean onScroll(MotionEvent e1, MotionEvent e2, float distanceX, float distanceY) {
		return super.onScroll(e1, e2, distanceX, distanceY);
	}

	/**
	 * 中间索引，一定是dataCount的倍数
	 */
	public int midIndex = 0;

	public void setDataAdataper(AdvImageAdapter adapter) {
		int dataCount = adapter.getDataCount();
		int count = adapter.getCount();
		midIndex = count / 2;
		int yushu = midIndex % dataCount;
		if (yushu != 0) {
			midIndex = midIndex - yushu;
		}
		this.adapter = adapter;
		this.setSelection(midIndex);
		this.setAdapter(adapter);
	}

	/***************************************************************************/
	private IOnItemCallback itemSelectedCallback;

	/**
	 * 绑定一系列事件
	 */
	public void boundEvent() {
		setOnTouchListener(GalleryTouch);
		setOnItemSelectedListener(GallerySelected);
		setOnItemClickListener(itemClickListener);
	}

	public void lunchTimer() {
		mTimer = new AdvTimer(mHandler, true);// isDeamon是否为精录线程
		mTimer.schedule(1000, 3000);
	}

	/**
	 * 注册onitemselect回调
	 * 
	 * @param itemSelectedCallback
	 */
	public void registCallback(IOnItemCallback itemSelectedCallback) {
		this.itemSelectedCallback = itemSelectedCallback;
	}

	private AdvTimer mTimer;
	@SuppressLint("HandlerLeak")
	private Handler mHandler = new Handler() {
		@Override
		public void handleMessage(Message msg) {
			switch (msg.what) {
			case 1:
				showNext();
				break;
			default:
				break;
			}
			super.handleMessage(msg);
		}
	};

	private OnItemClickListener itemClickListener = new OnItemClickListener() {
		@Override
		public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
			itemSelectedCallback.onItemClick(position % (adapter.getDataCount()));
		}
	};

	private OnItemSelectedListener GallerySelected = new OnItemSelectedListener() {

		@Override
		public void onItemSelected(AdapterView<?> arg0, View arg1, int position, long arg3) {
			// mView.setSeletion(position % (adapter.getDataCount()));
			setCurrentSelection(position);
			if (position == 0) {
				// 当自动轮播到了第一位时，自动定位到中间部位
				setSelection(midIndex);
			}
			if (position == adapter.getCount() - 1) {
				// 当自动轮播到了最后第一位时，自动定位到中间部位
				setSelection(midIndex);
			}
			if (null != itemSelectedCallback) {
				itemSelectedCallback.onItemSelected(position % (adapter.getDataCount()));
			}
		}

		@Override
		public void onNothingSelected(AdapterView<?> arg0) {
		}
	};

	private OnTouchListener GalleryTouch = new OnTouchListener() {
		@Override
		public boolean onTouch(View v, MotionEvent event) {
			switch (event.getAction()) {
			case MotionEvent.ACTION_DOWN:
				mTimer.stop();
				break;
			case MotionEvent.ACTION_UP:
				mTimer.start();
				break;
			}
			return false;
		}
	};

	class AdvTimer extends Timer {
		private Handler advHandler;
		private AdvTimeTask timeTask;

		AdvTimer(Handler handler, boolean isDeamon) {
			super(isDeamon);
			this.advHandler = handler;
		}

		/**
		 * true表示已经停止， false表示未停止
		 */
		private boolean isStoped;

		/**
		 * 计时器暂停
		 */
		public void stop() {
			isStoped = true;
		}

		/**
		 * 计时器开启
		 */
		public void start() {
			isStoped = false;
		}

		public void schedule(long delay, long period) {
			if (null == timeTask) {
				timeTask = new AdvTimeTask();
			}
			super.schedule(timeTask, delay, period);
			start();
		}

		@Override
		public void cancel() {
			super.cancel();
			if (null != timeTask) {
				timeTask.cancel();
				timeTask = null;
			}
		}

		class AdvTimeTask extends TimerTask {

			@Override
			public void run() {
				if (!isStoped) {
					Message msg = advHandler.obtainMessage();
					msg.what = 1;
					advHandler.sendMessage(msg);
				} else {
				}
			}
		}
	}

	public interface IOnItemCallback {
		public void onItemSelected(int position);

		public void onItemClick(int position);
	}

	/***************************************************************************/
}
