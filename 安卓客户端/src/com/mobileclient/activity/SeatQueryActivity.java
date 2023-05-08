package com.mobileclient.activity;
import java.sql.Timestamp;
import java.util.Date;
import java.util.List;
import com.mobileclient.domain.Seat;
import com.mobileclient.domain.Room;
import com.mobileclient.service.RoomService;
import com.mobileclient.domain.SeatState;
import com.mobileclient.service.SeatStateService;

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
public class SeatQueryActivity extends Activity {
	// 声明查询按钮
	private Button btnQuery;
	// 声明所在阅览室下拉框
	private Spinner spinner_roomObj;
	private ArrayAdapter<String> roomObj_adapter;
	private static  String[] roomObj_ShowText  = null;
	private List<Room> roomList = null; 
	/*阅览室管理业务逻辑层*/
	private RoomService roomService = new RoomService();
	// 声明座位编号输入框
	private EditText ET_seatCode;
	// 声明当前状态下拉框
	private Spinner spinner_seatStateObj;
	private ArrayAdapter<String> seatStateObj_adapter;
	private static  String[] seatStateObj_ShowText  = null;
	private List<SeatState> seatStateList = null; 
	/*座位状态管理业务逻辑层*/
	private SeatStateService seatStateService = new SeatStateService();
	/*查询过滤条件保存到这个对象中*/
	private Seat queryConditionSeat = new Seat();

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		//去除title 
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		//去掉Activity上面的状态栏
		getWindow().setFlags(WindowManager.LayoutParams. FLAG_FULLSCREEN , WindowManager.LayoutParams. FLAG_FULLSCREEN);
		// 设置当前Activity界面布局
		setContentView(R.layout.seat_query);
		ImageView search = (ImageView) this.findViewById(R.id.search);
		search.setVisibility(View.GONE);
		TextView title = (TextView) this.findViewById(R.id.title);
		title.setText("设置座位查询条件");
		ImageView back_btn = (ImageView) this.findViewById(R.id.back_btn);
		back_btn.setOnClickListener(new android.view.View.OnClickListener(){
			@Override
			public void onClick(View arg0) {
				finish();
			}
		});
		btnQuery = (Button) findViewById(R.id.btnQuery);
		spinner_roomObj = (Spinner) findViewById(R.id.Spinner_roomObj);
		// 获取所有的阅览室
		try {
			roomList = roomService.QueryRoom(null);
		} catch (Exception e1) {
			e1.printStackTrace();
		}
		int roomCount = roomList.size();
		roomObj_ShowText = new String[roomCount+1];
		roomObj_ShowText[0] = "不限制";
		for(int i=1;i<=roomCount;i++) { 
			roomObj_ShowText[i] = roomList.get(i-1).getRoomName();
		} 
		// 将可选内容与ArrayAdapter连接起来
		roomObj_adapter = new ArrayAdapter<String>(this,android.R.layout.simple_spinner_item, roomObj_ShowText);
		// 设置所在阅览室下拉列表的风格
		roomObj_adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		// 将adapter 添加到spinner中
		spinner_roomObj.setAdapter(roomObj_adapter);
		// 添加事件Spinner事件监听
		spinner_roomObj.setOnItemSelectedListener(new OnItemSelectedListener() {
			@Override
			public void onItemSelected(AdapterView<?> arg0, View arg1,int arg2, long arg3) {
				if(arg2 != 0)
					queryConditionSeat.setRoomObj(roomList.get(arg2-1).getRoomId()); 
				else
					queryConditionSeat.setRoomObj(0);
			}
			@Override
			public void onNothingSelected(AdapterView<?> arg0) {}
		});
		// 设置默认值
		spinner_roomObj.setVisibility(View.VISIBLE);
		ET_seatCode = (EditText) findViewById(R.id.ET_seatCode);
		spinner_seatStateObj = (Spinner) findViewById(R.id.Spinner_seatStateObj);
		// 获取所有的座位状态
		try {
			seatStateList = seatStateService.QuerySeatState(null);
		} catch (Exception e1) {
			e1.printStackTrace();
		}
		int seatStateCount = seatStateList.size();
		seatStateObj_ShowText = new String[seatStateCount+1];
		seatStateObj_ShowText[0] = "不限制";
		for(int i=1;i<=seatStateCount;i++) { 
			seatStateObj_ShowText[i] = seatStateList.get(i-1).getStateName();
		} 
		// 将可选内容与ArrayAdapter连接起来
		seatStateObj_adapter = new ArrayAdapter<String>(this,android.R.layout.simple_spinner_item, seatStateObj_ShowText);
		// 设置当前状态下拉列表的风格
		seatStateObj_adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		// 将adapter 添加到spinner中
		spinner_seatStateObj.setAdapter(seatStateObj_adapter);
		// 添加事件Spinner事件监听
		spinner_seatStateObj.setOnItemSelectedListener(new OnItemSelectedListener() {
			@Override
			public void onItemSelected(AdapterView<?> arg0, View arg1,int arg2, long arg3) {
				if(arg2 != 0)
					queryConditionSeat.setSeatStateObj(seatStateList.get(arg2-1).getStateId()); 
				else
					queryConditionSeat.setSeatStateObj(0);
			}
			@Override
			public void onNothingSelected(AdapterView<?> arg0) {}
		});
		// 设置默认值
		spinner_seatStateObj.setVisibility(View.VISIBLE);
		/*单击查询按钮*/
		btnQuery.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				try {
					/*获取查询参数*/
					queryConditionSeat.setSeatCode(ET_seatCode.getText().toString());
					Intent intent = getIntent();
					//这里使用bundle绷带来传输数据
					Bundle bundle =new Bundle();
					//传输的内容仍然是键值对的形式
					bundle.putSerializable("queryConditionSeat", queryConditionSeat);
					intent.putExtras(bundle);
					setResult(RESULT_OK,intent);
					finish();
				} catch (Exception e) {}
			}
			});
	}
}
