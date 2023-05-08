package com.mobileclient.activity;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import com.mobileclient.util.HttpUtil;

import java.sql.Timestamp;
import java.util.Date;
import java.util.List;

import com.mobileclient.domain.Seat;
import com.mobileclient.service.SeatService;
import com.mobileclient.domain.Room;
import com.mobileclient.service.RoomService;
import com.mobileclient.domain.SeatState;
import com.mobileclient.service.SeatStateService;
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
public class SeatAddActivity extends Activity {
	// 声明确定添加按钮
	private Button btnAdd;
	// 声明所在阅览室下拉框
	private Spinner spinner_roomObj;
	private ArrayAdapter<String> roomObj_adapter;
	private static  String[] roomObj_ShowText  = null;
	private List<Room> roomList = null;
	/*所在阅览室管理业务逻辑层*/
	private RoomService roomService = new RoomService();
	// 声明座位编号输入框
	private EditText ET_seatCode;
	// 声明当前状态下拉框
	private Spinner spinner_seatStateObj;
	private ArrayAdapter<String> seatStateObj_adapter;
	private static  String[] seatStateObj_ShowText  = null;
	private List<SeatState> seatStateList = null;
	/*当前状态管理业务逻辑层*/
	private SeatStateService seatStateService = new SeatStateService();
	protected String carmera_path;
	/*要保存的座位信息*/
	Seat seat = new Seat();
	/*座位管理业务逻辑层*/
	private SeatService seatService = new SeatService();

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		//去除title
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		//去掉Activity上面的状态栏
		getWindow().setFlags(WindowManager.LayoutParams. FLAG_FULLSCREEN , WindowManager.LayoutParams. FLAG_FULLSCREEN); 
		// 设置当前Activity界面布局
		setContentView(R.layout.seat_add); 
		ImageView search = (ImageView) this.findViewById(R.id.search);
		search.setVisibility(View.GONE);
		TextView title = (TextView) this.findViewById(R.id.title);
		title.setText("添加座位");
		ImageView back = (ImageView) this.findViewById(R.id.back_btn);
		back.setOnClickListener(new OnClickListener(){ 
			@Override
			public void onClick(View arg0) {
				finish();
			}
		});
		spinner_roomObj = (Spinner) findViewById(R.id.Spinner_roomObj);
		// 获取所有的所在阅览室
		try {
			roomList = roomService.QueryRoom(null);
		} catch (Exception e1) { 
			e1.printStackTrace(); 
		}
		int roomCount = roomList.size();
		roomObj_ShowText = new String[roomCount];
		for(int i=0;i<roomCount;i++) { 
			roomObj_ShowText[i] = roomList.get(i).getRoomName();
		}
		// 将可选内容与ArrayAdapter连接起来
		roomObj_adapter = new ArrayAdapter<String>(this,android.R.layout.simple_spinner_item, roomObj_ShowText);
		// 设置下拉列表的风格
		roomObj_adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		// 将adapter 添加到spinner中
		spinner_roomObj.setAdapter(roomObj_adapter);
		// 添加事件Spinner事件监听
		spinner_roomObj.setOnItemSelectedListener(new OnItemSelectedListener() {
			@Override
			public void onItemSelected(AdapterView<?> arg0, View arg1,int arg2, long arg3) {
				seat.setRoomObj(roomList.get(arg2).getRoomId()); 
			}
			@Override
			public void onNothingSelected(AdapterView<?> arg0) {}
		});
		// 设置默认值
		spinner_roomObj.setVisibility(View.VISIBLE);
		ET_seatCode = (EditText) findViewById(R.id.ET_seatCode);
		spinner_seatStateObj = (Spinner) findViewById(R.id.Spinner_seatStateObj);
		// 获取所有的当前状态
		try {
			seatStateList = seatStateService.QuerySeatState(null);
		} catch (Exception e1) { 
			e1.printStackTrace(); 
		}
		int seatStateCount = seatStateList.size();
		seatStateObj_ShowText = new String[seatStateCount];
		for(int i=0;i<seatStateCount;i++) { 
			seatStateObj_ShowText[i] = seatStateList.get(i).getStateName();
		}
		// 将可选内容与ArrayAdapter连接起来
		seatStateObj_adapter = new ArrayAdapter<String>(this,android.R.layout.simple_spinner_item, seatStateObj_ShowText);
		// 设置下拉列表的风格
		seatStateObj_adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		// 将adapter 添加到spinner中
		spinner_seatStateObj.setAdapter(seatStateObj_adapter);
		// 添加事件Spinner事件监听
		spinner_seatStateObj.setOnItemSelectedListener(new OnItemSelectedListener() {
			@Override
			public void onItemSelected(AdapterView<?> arg0, View arg1,int arg2, long arg3) {
				seat.setSeatStateObj(seatStateList.get(arg2).getStateId()); 
			}
			@Override
			public void onNothingSelected(AdapterView<?> arg0) {}
		});
		// 设置默认值
		spinner_seatStateObj.setVisibility(View.VISIBLE);
		btnAdd = (Button) findViewById(R.id.BtnAdd);
		/*单击添加座位按钮*/
		btnAdd.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				try {
					/*验证获取座位编号*/ 
					if(ET_seatCode.getText().toString().equals("")) {
						Toast.makeText(SeatAddActivity.this, "座位编号输入不能为空!", Toast.LENGTH_LONG).show();
						ET_seatCode.setFocusable(true);
						ET_seatCode.requestFocus();
						return;	
					}
					seat.setSeatCode(ET_seatCode.getText().toString());
					/*调用业务逻辑层上传座位信息*/
					SeatAddActivity.this.setTitle("正在上传座位信息，稍等...");
					String result = seatService.AddSeat(seat);
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
