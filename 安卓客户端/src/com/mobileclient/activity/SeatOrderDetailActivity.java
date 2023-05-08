package com.mobileclient.activity;

import java.util.Date;
import com.mobileclient.domain.SeatOrder;
import com.mobileclient.service.SeatOrderService;
import com.mobileclient.domain.Seat;
import com.mobileclient.service.SeatService;
import com.mobileclient.domain.UserInfo;
import com.mobileclient.service.UserInfoService;
import com.mobileclient.util.HttpUtil;
import com.mobileclient.util.ImageService;
import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import android.widget.Toast;
public class SeatOrderDetailActivity extends Activity {
	// 声明返回按钮
	private Button btnReturn;
	// 声明预约id控件
	private TextView TV_orderId;
	// 声明预约座位控件
	private TextView TV_seatObj;
	// 声明预约日期控件
	private TextView TV_orderDate;
	// 声明开始时间控件
	private TextView TV_startTime;
	// 声明结束时间控件
	private TextView TV_endTime;
	// 声明提交预约时间控件
	private TextView TV_addTime;
	// 声明预约用户控件
	private TextView TV_userObj;
	// 声明预约状态控件
	private TextView TV_orderState;
	// 声明管理回复控件
	private TextView TV_replyContent;
	// 声明预约备注控件
	private TextView TV_orderMemo;
	/* 要保存的座位预约信息 */
	SeatOrder seatOrder = new SeatOrder(); 
	/* 座位预约管理业务逻辑层 */
	private SeatOrderService seatOrderService = new SeatOrderService();
	private SeatService seatService = new SeatService();
	private UserInfoService userInfoService = new UserInfoService();
	private int orderId;
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		//去除title 
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		//去掉Activity上面的状态栏
		getWindow().setFlags(WindowManager.LayoutParams. FLAG_FULLSCREEN , WindowManager.LayoutParams. FLAG_FULLSCREEN); 
		// 设置当前Activity界面布局
		setContentView(R.layout.seatorder_detail);
		ImageView search = (ImageView) this.findViewById(R.id.search);
		search.setVisibility(View.GONE);
		TextView title = (TextView) this.findViewById(R.id.title);
		title.setText("查看座位预约详情");
		ImageView back = (ImageView) this.findViewById(R.id.back_btn);
		back.setOnClickListener(new OnClickListener(){ 
			@Override
			public void onClick(View arg0) {
				finish();
			}
		});
		// 通过findViewById方法实例化组件
		btnReturn = (Button) findViewById(R.id.btnReturn);
		TV_orderId = (TextView) findViewById(R.id.TV_orderId);
		TV_seatObj = (TextView) findViewById(R.id.TV_seatObj);
		TV_orderDate = (TextView) findViewById(R.id.TV_orderDate);
		TV_startTime = (TextView) findViewById(R.id.TV_startTime);
		TV_endTime = (TextView) findViewById(R.id.TV_endTime);
		TV_addTime = (TextView) findViewById(R.id.TV_addTime);
		TV_userObj = (TextView) findViewById(R.id.TV_userObj);
		TV_orderState = (TextView) findViewById(R.id.TV_orderState);
		TV_replyContent = (TextView) findViewById(R.id.TV_replyContent);
		TV_orderMemo = (TextView) findViewById(R.id.TV_orderMemo);
		Bundle extras = this.getIntent().getExtras();
		orderId = extras.getInt("orderId");
		btnReturn.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				SeatOrderDetailActivity.this.finish();
			}
		}); 
		initViewData();
	}
	/* 初始化显示详情界面的数据 */
	private void initViewData() {
	    seatOrder = seatOrderService.GetSeatOrder(orderId); 
		this.TV_orderId.setText(seatOrder.getOrderId() + "");
		Seat seatObj = seatService.GetSeat(seatOrder.getSeatObj());
		this.TV_seatObj.setText(seatObj.getSeatCode());
		Date orderDate = new Date(seatOrder.getOrderDate().getTime());
		String orderDateStr = (orderDate.getYear() + 1900) + "-" + (orderDate.getMonth()+1) + "-" + orderDate.getDate();
		this.TV_orderDate.setText(orderDateStr);
		this.TV_startTime.setText(seatOrder.getStartTime());
		this.TV_endTime.setText(seatOrder.getEndTime());
		this.TV_addTime.setText(seatOrder.getAddTime());
		UserInfo userObj = userInfoService.GetUserInfo(seatOrder.getUserObj());
		this.TV_userObj.setText(userObj.getName());
		this.TV_orderState.setText(seatOrder.getOrderState());
		this.TV_replyContent.setText(seatOrder.getReplyContent());
		this.TV_orderMemo.setText(seatOrder.getOrderMemo());
	} 
}
