package com.mobileclient.activity;

import java.util.Date;
import com.mobileclient.domain.SelectSeat;
import com.mobileclient.service.SelectSeatService;
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
public class SelectSeatDetailActivity extends Activity {
	// 声明返回按钮
	private Button btnReturn;
	// 声明选座id控件
	private TextView TV_selectId;
	// 声明座位编号控件
	private TextView TV_seatObj;
	// 声明选座用户控件
	private TextView TV_userObj;
	// 声明选座开始时间控件
	private TextView TV_startTime;
	// 声明选座结束时间控件
	private TextView TV_endTime;
	// 声明选座状态控件
	private TextView TV_seatState;
	/* 要保存的选座信息 */
	SelectSeat selectSeat = new SelectSeat(); 
	/* 选座管理业务逻辑层 */
	private SelectSeatService selectSeatService = new SelectSeatService();
	private SeatService seatService = new SeatService();
	private UserInfoService userInfoService = new UserInfoService();
	private int selectId;
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		//去除title 
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		//去掉Activity上面的状态栏
		getWindow().setFlags(WindowManager.LayoutParams. FLAG_FULLSCREEN , WindowManager.LayoutParams. FLAG_FULLSCREEN); 
		// 设置当前Activity界面布局
		setContentView(R.layout.selectseat_detail);
		ImageView search = (ImageView) this.findViewById(R.id.search);
		search.setVisibility(View.GONE);
		TextView title = (TextView) this.findViewById(R.id.title);
		title.setText("查看选座详情");
		ImageView back = (ImageView) this.findViewById(R.id.back_btn);
		back.setOnClickListener(new OnClickListener(){ 
			@Override
			public void onClick(View arg0) {
				finish();
			}
		});
		// 通过findViewById方法实例化组件
		btnReturn = (Button) findViewById(R.id.btnReturn);
		TV_selectId = (TextView) findViewById(R.id.TV_selectId);
		TV_seatObj = (TextView) findViewById(R.id.TV_seatObj);
		TV_userObj = (TextView) findViewById(R.id.TV_userObj);
		TV_startTime = (TextView) findViewById(R.id.TV_startTime);
		TV_endTime = (TextView) findViewById(R.id.TV_endTime);
		TV_seatState = (TextView) findViewById(R.id.TV_seatState);
		Bundle extras = this.getIntent().getExtras();
		selectId = extras.getInt("selectId");
		btnReturn.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				SelectSeatDetailActivity.this.finish();
			}
		}); 
		initViewData();
	}
	/* 初始化显示详情界面的数据 */
	private void initViewData() {
	    selectSeat = selectSeatService.GetSelectSeat(selectId); 
		this.TV_selectId.setText(selectSeat.getSelectId() + "");
		Seat seatObj = seatService.GetSeat(selectSeat.getSeatObj());
		this.TV_seatObj.setText(seatObj.getSeatCode());
		UserInfo userObj = userInfoService.GetUserInfo(selectSeat.getUserObj());
		this.TV_userObj.setText(userObj.getName());
		this.TV_startTime.setText(selectSeat.getStartTime());
		this.TV_endTime.setText(selectSeat.getEndTime());
		this.TV_seatState.setText(selectSeat.getSeatState());
	} 
}
