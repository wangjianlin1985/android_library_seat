package com.mobileclient.activity;
import java.sql.Timestamp;
import java.util.Date;
import java.util.List;
import com.mobileclient.domain.SeatOrder;
import com.mobileclient.domain.Seat;
import com.mobileclient.service.SeatService;
import com.mobileclient.domain.UserInfo;
import com.mobileclient.service.UserInfoService;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;

import android.widget.ImageView;
import android.widget.TextView;
public class SeatOrderQueryActivity extends Activity {
	// 声明查询按钮
	private Button btnQuery;
	// 声明预约座位下拉框
	private Spinner spinner_seatObj;
	private ArrayAdapter<String> seatObj_adapter;
	private static  String[] seatObj_ShowText  = null;
	private List<Seat> seatList = null; 
	/*座位管理业务逻辑层*/
	private SeatService seatService = new SeatService();
	// 预约日期控件
	private DatePicker dp_orderDate;
	private CheckBox cb_orderDate;
	// 声明提交预约时间输入框
	private EditText ET_addTime;
	// 声明预约用户下拉框
	private Spinner spinner_userObj;
	private ArrayAdapter<String> userObj_adapter;
	private static  String[] userObj_ShowText  = null;
	private List<UserInfo> userInfoList = null; 
	/*用户管理业务逻辑层*/
	private UserInfoService userInfoService = new UserInfoService();
	// 声明预约状态输入框
	private EditText ET_orderState;
	/*查询过滤条件保存到这个对象中*/
	private SeatOrder queryConditionSeatOrder = new SeatOrder();

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		//去除title 
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		//去掉Activity上面的状态栏
		getWindow().setFlags(WindowManager.LayoutParams. FLAG_FULLSCREEN , WindowManager.LayoutParams. FLAG_FULLSCREEN);
		// 设置当前Activity界面布局
		setContentView(R.layout.seatorder_query);
		ImageView search = (ImageView) this.findViewById(R.id.search);
		search.setVisibility(View.GONE);
		TextView title = (TextView) this.findViewById(R.id.title);
		title.setText("设置座位预约查询条件");
		ImageView back_btn = (ImageView) this.findViewById(R.id.back_btn);
		back_btn.setOnClickListener(new android.view.View.OnClickListener(){
			@Override
			public void onClick(View arg0) {
				finish();
			}
		});
		btnQuery = (Button) findViewById(R.id.btnQuery);
		spinner_seatObj = (Spinner) findViewById(R.id.Spinner_seatObj);
		// 获取所有的座位
		try {
			seatList = seatService.QuerySeat(null);
		} catch (Exception e1) {
			e1.printStackTrace();
		}
		int seatCount = seatList.size();
		seatObj_ShowText = new String[seatCount+1];
		seatObj_ShowText[0] = "不限制";
		for(int i=1;i<=seatCount;i++) { 
			seatObj_ShowText[i] = seatList.get(i-1).getSeatCode();
		} 
		// 将可选内容与ArrayAdapter连接起来
		seatObj_adapter = new ArrayAdapter<String>(this,android.R.layout.simple_spinner_item, seatObj_ShowText);
		// 设置预约座位下拉列表的风格
		seatObj_adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		// 将adapter 添加到spinner中
		spinner_seatObj.setAdapter(seatObj_adapter);
		// 添加事件Spinner事件监听
		spinner_seatObj.setOnItemSelectedListener(new OnItemSelectedListener() {
			@Override
			public void onItemSelected(AdapterView<?> arg0, View arg1,int arg2, long arg3) {
				if(arg2 != 0)
					queryConditionSeatOrder.setSeatObj(seatList.get(arg2-1).getSeatId()); 
				else
					queryConditionSeatOrder.setSeatObj(0);
			}
			@Override
			public void onNothingSelected(AdapterView<?> arg0) {}
		});
		// 设置默认值
		spinner_seatObj.setVisibility(View.VISIBLE);
		dp_orderDate = (DatePicker) findViewById(R.id.dp_orderDate);
		cb_orderDate = (CheckBox) findViewById(R.id.cb_orderDate);
		ET_addTime = (EditText) findViewById(R.id.ET_addTime);
		spinner_userObj = (Spinner) findViewById(R.id.Spinner_userObj);
		// 获取所有的用户
		try {
			userInfoList = userInfoService.QueryUserInfo(null);
		} catch (Exception e1) {
			e1.printStackTrace();
		}
		int userInfoCount = userInfoList.size();
		userObj_ShowText = new String[userInfoCount+1];
		userObj_ShowText[0] = "不限制";
		for(int i=1;i<=userInfoCount;i++) { 
			userObj_ShowText[i] = userInfoList.get(i-1).getName();
		} 
		// 将可选内容与ArrayAdapter连接起来
		userObj_adapter = new ArrayAdapter<String>(this,android.R.layout.simple_spinner_item, userObj_ShowText);
		// 设置预约用户下拉列表的风格
		userObj_adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		// 将adapter 添加到spinner中
		spinner_userObj.setAdapter(userObj_adapter);
		// 添加事件Spinner事件监听
		spinner_userObj.setOnItemSelectedListener(new OnItemSelectedListener() {
			@Override
			public void onItemSelected(AdapterView<?> arg0, View arg1,int arg2, long arg3) {
				if(arg2 != 0)
					queryConditionSeatOrder.setUserObj(userInfoList.get(arg2-1).getUser_name()); 
				else
					queryConditionSeatOrder.setUserObj("");
			}
			@Override
			public void onNothingSelected(AdapterView<?> arg0) {}
		});
		// 设置默认值
		spinner_userObj.setVisibility(View.VISIBLE);
		ET_orderState = (EditText) findViewById(R.id.ET_orderState);
		/*单击查询按钮*/
		btnQuery.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				try {
					/*获取查询参数*/
					if(cb_orderDate.isChecked()) {
						/*获取预约日期*/
						Date orderDate = new Date(dp_orderDate.getYear()-1900,dp_orderDate.getMonth(),dp_orderDate.getDayOfMonth());
						queryConditionSeatOrder.setOrderDate(new Timestamp(orderDate.getTime()));
					} else {
						queryConditionSeatOrder.setOrderDate(null);
					} 
					queryConditionSeatOrder.setAddTime(ET_addTime.getText().toString());
					queryConditionSeatOrder.setOrderState(ET_orderState.getText().toString());
					Intent intent = getIntent();
					//这里使用bundle绷带来传输数据
					Bundle bundle =new Bundle();
					//传输的内容仍然是键值对的形式
					bundle.putSerializable("queryConditionSeatOrder", queryConditionSeatOrder);
					intent.putExtras(bundle);
					setResult(RESULT_OK,intent);
					finish();
				} catch (Exception e) {}
			}
			});
	}
}
