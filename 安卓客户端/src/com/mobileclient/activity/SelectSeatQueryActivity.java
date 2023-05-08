package com.mobileclient.activity;
import java.sql.Timestamp;
import java.util.Date;
import java.util.List;
import com.mobileclient.domain.SelectSeat;
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
public class SelectSeatQueryActivity extends Activity {
	// 声明查询按钮
	private Button btnQuery;
	// 声明座位编号下拉框
	private Spinner spinner_seatObj;
	private ArrayAdapter<String> seatObj_adapter;
	private static  String[] seatObj_ShowText  = null;
	private List<Seat> seatList = null; 
	/*座位管理业务逻辑层*/
	private SeatService seatService = new SeatService();
	// 声明选座用户下拉框
	private Spinner spinner_userObj;
	private ArrayAdapter<String> userObj_adapter;
	private static  String[] userObj_ShowText  = null;
	private List<UserInfo> userInfoList = null; 
	/*用户管理业务逻辑层*/
	private UserInfoService userInfoService = new UserInfoService();
	// 声明选座开始时间输入框
	private EditText ET_startTime;
	// 声明选座结束时间输入框
	private EditText ET_endTime;
	// 声明选座状态输入框
	private EditText ET_seatState;
	/*查询过滤条件保存到这个对象中*/
	private SelectSeat queryConditionSelectSeat = new SelectSeat();

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		//去除title 
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		//去掉Activity上面的状态栏
		getWindow().setFlags(WindowManager.LayoutParams. FLAG_FULLSCREEN , WindowManager.LayoutParams. FLAG_FULLSCREEN);
		// 设置当前Activity界面布局
		setContentView(R.layout.selectseat_query);
		ImageView search = (ImageView) this.findViewById(R.id.search);
		search.setVisibility(View.GONE);
		TextView title = (TextView) this.findViewById(R.id.title);
		title.setText("设置选座查询条件");
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
		// 设置座位编号下拉列表的风格
		seatObj_adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		// 将adapter 添加到spinner中
		spinner_seatObj.setAdapter(seatObj_adapter);
		// 添加事件Spinner事件监听
		spinner_seatObj.setOnItemSelectedListener(new OnItemSelectedListener() {
			@Override
			public void onItemSelected(AdapterView<?> arg0, View arg1,int arg2, long arg3) {
				if(arg2 != 0)
					queryConditionSelectSeat.setSeatObj(seatList.get(arg2-1).getSeatId()); 
				else
					queryConditionSelectSeat.setSeatObj(0);
			}
			@Override
			public void onNothingSelected(AdapterView<?> arg0) {}
		});
		// 设置默认值
		spinner_seatObj.setVisibility(View.VISIBLE);
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
		// 设置选座用户下拉列表的风格
		userObj_adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		// 将adapter 添加到spinner中
		spinner_userObj.setAdapter(userObj_adapter);
		// 添加事件Spinner事件监听
		spinner_userObj.setOnItemSelectedListener(new OnItemSelectedListener() {
			@Override
			public void onItemSelected(AdapterView<?> arg0, View arg1,int arg2, long arg3) {
				if(arg2 != 0)
					queryConditionSelectSeat.setUserObj(userInfoList.get(arg2-1).getUser_name()); 
				else
					queryConditionSelectSeat.setUserObj("");
			}
			@Override
			public void onNothingSelected(AdapterView<?> arg0) {}
		});
		// 设置默认值
		spinner_userObj.setVisibility(View.VISIBLE);
		ET_startTime = (EditText) findViewById(R.id.ET_startTime);
		ET_endTime = (EditText) findViewById(R.id.ET_endTime);
		ET_seatState = (EditText) findViewById(R.id.ET_seatState);
		/*单击查询按钮*/
		btnQuery.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				try {
					/*获取查询参数*/
					queryConditionSelectSeat.setStartTime(ET_startTime.getText().toString());
					queryConditionSelectSeat.setEndTime(ET_endTime.getText().toString());
					queryConditionSelectSeat.setSeatState(ET_seatState.getText().toString());
					Intent intent = getIntent();
					//这里使用bundle绷带来传输数据
					Bundle bundle =new Bundle();
					//传输的内容仍然是键值对的形式
					bundle.putSerializable("queryConditionSelectSeat", queryConditionSelectSeat);
					intent.putExtras(bundle);
					setResult(RESULT_OK,intent);
					finish();
				} catch (Exception e) {}
			}
			});
	}
}
