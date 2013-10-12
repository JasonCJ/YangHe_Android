package com.yanghe.sujiu.activity;

import java.util.List;

import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.CompoundButton.OnCheckedChangeListener;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.yanghe.sujiu.BaseActivity;
import com.yanghe.sujiu.R;
import com.yanghe.sujiu.adapter.OrderFormAdapter;
import com.yanghe.sujiu.adapter.OrderFormAdapter.ISelectedCallback;
import com.yanghe.sujiu.adapter.StyleListAdapter;

/**
 * 订单Tab
 * 
 * @author mac_os
 */
public class OrderFormActivity extends BaseActivity implements OnClickListener, OnCheckedChangeListener,
		OnItemClickListener {
	private OrderFormActivity mContext;
	private LinearLayout mTypeSelectLayout;
	private ImageView mArrowsImageView;
	private TextView mTitleTextView;
	// private ArrayList<OrderForm> orderDataList = new ArrayList<OrderForm>();
	private ListView mOrderFormList;
	private OrderFormAdapter orderFormadapter;
	private CheckBox mAllSelectCheckBox;
	private Button mSubmitOrdersButton;
	private View mBlackLine;

	private View typeView;// 类型view
	private ListView typeListView;// 类型listview
	private StyleListAdapter typeAdapter;
	private String[] mStyleArray = new String[] { "未下单", "已下单", "派送中", "已完成" };
	private static final int NOT_OrderForm = 0;
	private static final int YES_OrderForm = 1;
	private static final int ING_OrderForm = 2;
	private static final int FINISH_OrderForm = 3;
	private int mTypeStatus;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_order_form);
		mContext = OrderFormActivity.this;
		mTypeStatus = NOT_OrderForm;
		initView();
	}

	/**
	 * 声明控件
	 */
	private void initView() {
		mTypeSelectLayout = (LinearLayout) findViewById(R.id.type_select_Layout);
		mTypeSelectLayout.setOnClickListener(mContext);

		mArrowsImageView = (ImageView) findViewById(R.id.arrows_ImageView);
		mTitleTextView = (TextView) findViewById(R.id.title_TextView);
		mTitleTextView.setText("未下单");

		mAllSelectCheckBox = (CheckBox) findViewById(R.id.all_select_checkBox);
		mAllSelectCheckBox.setOnCheckedChangeListener(mContext);
		mSubmitOrdersButton = (Button) findViewById(R.id.submit_orders_Button);
		mSubmitOrdersButton.setOnClickListener(mContext);
		mBlackLine = findViewById(R.id.black_line);
		mBlackLine.setVisibility(View.GONE);
		mSubmitOrdersButton.setVisibility(View.GONE);

		mOrderFormList = (ListView) findViewById(R.id.order_form_list);
		orderFormadapter = new OrderFormAdapter(callback, mContext, mTypeStatus);
		mOrderFormList.setAdapter(orderFormadapter);

		typeView = findViewById(R.id.order_form_list_type_linearlayout);
		typeListView = (ListView) findViewById(R.id.order_form_list_type_listview);
		typeAdapter = new StyleListAdapter(mStyleArray, mContext);
		typeListView.setAdapter(typeAdapter);

		mOrderFormList.setOnItemClickListener(mContext);
		typeListView.setOnItemClickListener(mContext);
	}

	private ISelectedCallback callback = new ISelectedCallback() {
		@Override
		public void selectedAllCallback(boolean selectedAll) {
			// 是否全中
			mAllSelectCheckBox.setChecked(selectedAll);
		}

		@Override
		public void hasItemSelected(boolean hasSelected) {
			// 有选中的
			int visible = hasSelected ? View.VISIBLE : View.GONE;
			mSubmitOrdersButton.setVisibility(visible);
			mBlackLine.setVisibility(visible);
		}
	};

	private void showStyleView(int visible) {
		if (visible == View.VISIBLE) {
			mArrowsImageView.setBackgroundResource(R.drawable.icon_arrow_up);
			typeView.setVisibility(View.VISIBLE);
		} else {
			mArrowsImageView.setBackgroundResource(R.drawable.icon_arrow_down);
			typeView.setVisibility(View.GONE);
		}
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.type_select_Layout:
			int vis = (typeView.getVisibility() == View.VISIBLE) ? View.GONE : View.VISIBLE;
			showStyleView(vis);
			break;
		case R.id.submit_orders_Button:
			List<Integer> selectedItemIndexes = orderFormadapter.getSelectedItemIndexes();
			int size = selectedItemIndexes.size();
			if (size == 0) {
				showToast("请勾选需要提交的订单");
			} else {
				StringBuilder bankId = new StringBuilder();
				for (int i = 0; i < size; i++) {
					bankId.append("" + selectedItemIndexes.get(i) + ",");
				}
				String intentBankId = bankId.substring(0, bankId.length() - 1);
				showToast("position--->" + intentBankId);
				// Intent intent = new Intent(BlueCardList.this,
				// BlueCardShopsList.class);
				// intent.putExtra("bank_ids", intentBankId);
				// startActivity(intent);
				// overridePendingTransition(R.anim.push_left_in,
				// R.anim.push_left_out);
			}
			break;
		}
	}

	@Override
	public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
		if (isChecked) {
			orderFormadapter.allCheckedStatus(true);
		} else if (orderFormadapter.isAllSelected()) {
			orderFormadapter.allCheckedStatus(false);
		}
	}

	@Override
	public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
		showStyleView(View.GONE);
		switch (parent.getId()) {
		case R.id.order_form_list:
			if (mTypeStatus == NOT_OrderForm) {
				orderFormadapter.toggle(position);
			} else {
				//编写已下单已完成派送中的逻辑
			}
			break;
		case R.id.order_form_list_type_listview:
			mTitleTextView.setText(mStyleArray[position]);
			switch (position) {
			case NOT_OrderForm:
				mTypeStatus = NOT_OrderForm;
				break;
			case YES_OrderForm:
				mTypeStatus = YES_OrderForm;
				break;
			case ING_OrderForm:
				mTypeStatus = ING_OrderForm;
				break;
			case FINISH_OrderForm:
				mTypeStatus = FINISH_OrderForm;
				break;
				default:
					break;
			}
			orderFormadapter = new OrderFormAdapter(callback, mContext, mTypeStatus);
			mOrderFormList.setAdapter(orderFormadapter);
			break;
		}
	}
}