package com.mobileclient.activity;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import com.mobileclient.util.HttpUtil;

import java.sql.Timestamp;
import java.util.Date;
import java.util.List;

import com.mobileclient.domain.SeatOrder;
import com.mobileclient.service.SeatOrderService;
import com.mobileclient.domain.Seat;
import com.mobileclient.service.SeatService;
import com.mobileclient.domain.UserInfo;
import com.mobileclient.service.UserInfoService;
import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ImageView.ScaleType;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;
public class SeatOrderAddActivity extends Activity {
	// 声明确定添加按钮
	private Button btnAdd;
	// 声明预约座位下拉框
	private Spinner spinner_seatObj;
	private ArrayAdapter<String> seatObj_adapter;
	private static  String[] seatObj_ShowText  = null;
	private List<Seat> seatList = null;
	/*预约座位管理业务逻辑层*/
	private SeatService seatService = new SeatService();
	// 出版预约日期控件
	private DatePicker dp_orderDate;
	// 声明开始时间输入框
	private EditText ET_startTime;
	// 声明结束时间输入框
	private EditText ET_endTime;
	// 声明提交预约时间输入框
	private EditText ET_addTime;
	// 声明预约用户下拉框
	private Spinner spinner_userObj;
	private ArrayAdapter<String> userObj_adapter;
	private static  String[] userObj_ShowText  = null;
	private List<UserInfo> userInfoList = null;
	/*预约用户管理业务逻辑层*/
	private UserInfoService userInfoService = new UserInfoService();
	// 声明预约状态输入框
	private EditText ET_orderState;
	// 声明管理回复输入框
	private EditText ET_replyContent;
	// 声明预约备注输入框
	private EditText ET_orderMemo;
	protected String carmera_path;
	/*要保存的座位预约信息*/
	SeatOrder seatOrder = new SeatOrder();
	/*座位预约管理业务逻辑层*/
	private SeatOrderService seatOrderService = new SeatOrderService();

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		//去除title
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		//去掉Activity上面的状态栏
		getWindow().setFlags(WindowManager.LayoutParams. FLAG_FULLSCREEN , WindowManager.LayoutParams. FLAG_FULLSCREEN); 
		// 设置当前Activity界面布局
		setContentView(R.layout.seatorder_add); 
		ImageView search = (ImageView) this.findViewById(R.id.search);
		search.setVisibility(View.GONE);
		TextView title = (TextView) this.findViewById(R.id.title);
		title.setText("添加座位预约");
		ImageView back = (ImageView) this.findViewById(R.id.back_btn);
		back.setOnClickListener(new OnClickListener(){ 
			@Override
			public void onClick(View arg0) {
				finish();
			}
		});
		spinner_seatObj = (Spinner) findViewById(R.id.Spinner_seatObj);
		// 获取所有的预约座位
		try {
			seatList = seatService.QuerySeat(null);
		} catch (Exception e1) { 
			e1.printStackTrace(); 
		}
		int seatCount = seatList.size();
		seatObj_ShowText = new String[seatCount];
		for(int i=0;i<seatCount;i++) { 
			seatObj_ShowText[i] = seatList.get(i).getSeatCode();
		}
		// 将可选内容与ArrayAdapter连接起来
		seatObj_adapter = new ArrayAdapter<String>(this,android.R.layout.simple_spinner_item, seatObj_ShowText);
		// 设置下拉列表的风格
		seatObj_adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		// 将adapter 添加到spinner中
		spinner_seatObj.setAdapter(seatObj_adapter);
		// 添加事件Spinner事件监听
		spinner_seatObj.setOnItemSelectedListener(new OnItemSelectedListener() {
			@Override
			public void onItemSelected(AdapterView<?> arg0, View arg1,int arg2, long arg3) {
				seatOrder.setSeatObj(seatList.get(arg2).getSeatId()); 
			}
			@Override
			public void onNothingSelected(AdapterView<?> arg0) {}
		});
		// 设置默认值
		spinner_seatObj.setVisibility(View.VISIBLE);
		dp_orderDate = (DatePicker)this.findViewById(R.id.dp_orderDate);
		ET_startTime = (EditText) findViewById(R.id.ET_startTime);
		ET_endTime = (EditText) findViewById(R.id.ET_endTime);
		ET_addTime = (EditText) findViewById(R.id.ET_addTime);
		spinner_userObj = (Spinner) findViewById(R.id.Spinner_userObj);
		// 获取所有的预约用户
		try {
			userInfoList = userInfoService.QueryUserInfo(null);
		} catch (Exception e1) { 
			e1.printStackTrace(); 
		}
		int userInfoCount = userInfoList.size();
		userObj_ShowText = new String[userInfoCount];
		for(int i=0;i<userInfoCount;i++) { 
			userObj_ShowText[i] = userInfoList.get(i).getName();
		}
		// 将可选内容与ArrayAdapter连接起来
		userObj_adapter = new ArrayAdapter<String>(this,android.R.layout.simple_spinner_item, userObj_ShowText);
		// 设置下拉列表的风格
		userObj_adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		// 将adapter 添加到spinner中
		spinner_userObj.setAdapter(userObj_adapter);
		// 添加事件Spinner事件监听
		spinner_userObj.setOnItemSelectedListener(new OnItemSelectedListener() {
			@Override
			public void onItemSelected(AdapterView<?> arg0, View arg1,int arg2, long arg3) {
				seatOrder.setUserObj(userInfoList.get(arg2).getUser_name()); 
			}
			@Override
			public void onNothingSelected(AdapterView<?> arg0) {}
		});
		// 设置默认值
		spinner_userObj.setVisibility(View.VISIBLE);
		ET_orderState = (EditText) findViewById(R.id.ET_orderState);
		ET_replyContent = (EditText) findViewById(R.id.ET_replyContent);
		ET_orderMemo = (EditText) findViewById(R.id.ET_orderMemo);
		btnAdd = (Button) findViewById(R.id.BtnAdd);
		/*单击添加座位预约按钮*/
		btnAdd.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				try {
					/*获取预约日期*/
					Date orderDate = new Date(dp_orderDate.getYear()-1900,dp_orderDate.getMonth(),dp_orderDate.getDayOfMonth());
					seatOrder.setOrderDate(new Timestamp(orderDate.getTime()));
					/*验证获取开始时间*/ 
					if(ET_startTime.getText().toString().equals("")) {
						Toast.makeText(SeatOrderAddActivity.this, "开始时间输入不能为空!", Toast.LENGTH_LONG).show();
						ET_startTime.setFocusable(true);
						ET_startTime.requestFocus();
						return;	
					}
					seatOrder.setStartTime(ET_startTime.getText().toString());
					/*验证获取结束时间*/ 
					if(ET_endTime.getText().toString().equals("")) {
						Toast.makeText(SeatOrderAddActivity.this, "结束时间输入不能为空!", Toast.LENGTH_LONG).show();
						ET_endTime.setFocusable(true);
						ET_endTime.requestFocus();
						return;	
					}
					seatOrder.setEndTime(ET_endTime.getText().toString());
					/*验证获取提交预约时间*/ 
					if(ET_addTime.getText().toString().equals("")) {
						Toast.makeText(SeatOrderAddActivity.this, "提交预约时间输入不能为空!", Toast.LENGTH_LONG).show();
						ET_addTime.setFocusable(true);
						ET_addTime.requestFocus();
						return;	
					}
					seatOrder.setAddTime(ET_addTime.getText().toString());
					/*验证获取预约状态*/ 
					if(ET_orderState.getText().toString().equals("")) {
						Toast.makeText(SeatOrderAddActivity.this, "预约状态输入不能为空!", Toast.LENGTH_LONG).show();
						ET_orderState.setFocusable(true);
						ET_orderState.requestFocus();
						return;	
					}
					seatOrder.setOrderState(ET_orderState.getText().toString());
					/*验证获取管理回复*/ 
					if(ET_replyContent.getText().toString().equals("")) {
						Toast.makeText(SeatOrderAddActivity.this, "管理回复输入不能为空!", Toast.LENGTH_LONG).show();
						ET_replyContent.setFocusable(true);
						ET_replyContent.requestFocus();
						return;	
					}
					seatOrder.setReplyContent(ET_replyContent.getText().toString());
					/*验证获取预约备注*/ 
					if(ET_orderMemo.getText().toString().equals("")) {
						Toast.makeText(SeatOrderAddActivity.this, "预约备注输入不能为空!", Toast.LENGTH_LONG).show();
						ET_orderMemo.setFocusable(true);
						ET_orderMemo.requestFocus();
						return;	
					}
					seatOrder.setOrderMemo(ET_orderMemo.getText().toString());
					/*调用业务逻辑层上传座位预约信息*/
					SeatOrderAddActivity.this.setTitle("正在上传座位预约信息，稍等...");
					String result = seatOrderService.AddSeatOrder(seatOrder);
					Toast.makeText(getApplicationContext(), result, 1).show(); 
					Intent intent = getIntent();
					setResult(RESULT_OK,intent);
					finish();
				} catch (Exception e) {}
			}
		});
	}

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);
	}
}
