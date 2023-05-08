package com.mobileclient.activity;

import java.util.Date;
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
public class SeatStateDetailActivity extends Activity {
	// 声明返回按钮
	private Button btnReturn;
	// 声明状态id控件
	private TextView TV_stateId;
	// 声明状态名称控件
	private TextView TV_stateName;
	/* 要保存的座位状态信息 */
	SeatState seatState = new SeatState(); 
	/* 座位状态管理业务逻辑层 */
	private SeatStateService seatStateService = new SeatStateService();
	private int stateId;
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		//去除title 
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		//去掉Activity上面的状态栏
		getWindow().setFlags(WindowManager.LayoutParams. FLAG_FULLSCREEN , WindowManager.LayoutParams. FLAG_FULLSCREEN); 
		// 设置当前Activity界面布局
		setContentView(R.layout.seatstate_detail);
		ImageView search = (ImageView) this.findViewById(R.id.search);
		search.setVisibility(View.GONE);
		TextView title = (TextView) this.findViewById(R.id.title);
		title.setText("查看座位状态详情");
		ImageView back = (ImageView) this.findViewById(R.id.back_btn);
		back.setOnClickListener(new OnClickListener(){ 
			@Override
			public void onClick(View arg0) {
				finish();
			}
		});
		// 通过findViewById方法实例化组件
		btnReturn = (Button) findViewById(R.id.btnReturn);
		TV_stateId = (TextView) findViewById(R.id.TV_stateId);
		TV_stateName = (TextView) findViewById(R.id.TV_stateName);
		Bundle extras = this.getIntent().getExtras();
		stateId = extras.getInt("stateId");
		btnReturn.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				SeatStateDetailActivity.this.finish();
			}
		}); 
		initViewData();
	}
	/* 初始化显示详情界面的数据 */
	private void initViewData() {
	    seatState = seatStateService.GetSeatState(stateId); 
		this.TV_stateId.setText(seatState.getStateId() + "");
		this.TV_stateName.setText(seatState.getStateName());
	} 
}
