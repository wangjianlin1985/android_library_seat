package com.mobileclient.activity;

import java.util.Date;
import com.mobileclient.domain.Seat;
import com.mobileclient.service.SeatService;
import com.mobileclient.domain.Room;
import com.mobileclient.service.RoomService;
import com.mobileclient.domain.SeatState;
import com.mobileclient.service.SeatStateService;
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
public class SeatDetailActivity extends Activity {
	// 声明返回按钮
	private Button btnReturn;
	// 声明座位id控件
	private TextView TV_seatId;
	// 声明所在阅览室控件
	private TextView TV_roomObj;
	// 声明座位编号控件
	private TextView TV_seatCode;
	// 声明当前状态控件
	private TextView TV_seatStateObj;
	/* 要保存的座位信息 */
	Seat seat = new Seat(); 
	/* 座位管理业务逻辑层 */
	private SeatService seatService = new SeatService();
	private RoomService roomService = new RoomService();
	private SeatStateService seatStateService = new SeatStateService();
	private int seatId;
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		//去除title 
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		//去掉Activity上面的状态栏
		getWindow().setFlags(WindowManager.LayoutParams. FLAG_FULLSCREEN , WindowManager.LayoutParams. FLAG_FULLSCREEN); 
		// 设置当前Activity界面布局
		setContentView(R.layout.seat_detail);
		ImageView search = (ImageView) this.findViewById(R.id.search);
		search.setVisibility(View.GONE);
		TextView title = (TextView) this.findViewById(R.id.title);
		title.setText("查看座位详情");
		ImageView back = (ImageView) this.findViewById(R.id.back_btn);
		back.setOnClickListener(new OnClickListener(){ 
			@Override
			public void onClick(View arg0) {
				finish();
			}
		});
		// 通过findViewById方法实例化组件
		btnReturn = (Button) findViewById(R.id.btnReturn);
		TV_seatId = (TextView) findViewById(R.id.TV_seatId);
		TV_roomObj = (TextView) findViewById(R.id.TV_roomObj);
		TV_seatCode = (TextView) findViewById(R.id.TV_seatCode);
		TV_seatStateObj = (TextView) findViewById(R.id.TV_seatStateObj);
		Bundle extras = this.getIntent().getExtras();
		seatId = extras.getInt("seatId");
		btnReturn.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				SeatDetailActivity.this.finish();
			}
		}); 
		initViewData();
	}
	/* 初始化显示详情界面的数据 */
	private void initViewData() {
	    seat = seatService.GetSeat(seatId); 
		this.TV_seatId.setText(seat.getSeatId() + "");
		Room roomObj = roomService.GetRoom(seat.getRoomObj());
		this.TV_roomObj.setText(roomObj.getRoomName());
		this.TV_seatCode.setText(seat.getSeatCode());
		SeatState seatStateObj = seatStateService.GetSeatState(seat.getSeatStateObj());
		this.TV_seatStateObj.setText(seatStateObj.getStateName());
	} 
}
