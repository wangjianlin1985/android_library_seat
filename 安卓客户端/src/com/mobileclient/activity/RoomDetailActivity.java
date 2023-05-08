package com.mobileclient.activity;

import java.util.Date;
import com.mobileclient.domain.Room;
import com.mobileclient.service.RoomService;
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
public class RoomDetailActivity extends Activity {
	// 声明返回按钮
	private Button btnReturn;
	// 声明阅览室id控件
	private TextView TV_roomId;
	// 声明阅览室名称控件
	private TextView TV_roomName;
	// 声明阅览室照片图片框
	private ImageView iv_roomPhoto;
	// 声明阅览室位置控件
	private TextView TV_roomPlace;
	// 声明总座位数控件
	private TextView TV_seatNum;
	/* 要保存的阅览室信息 */
	Room room = new Room(); 
	/* 阅览室管理业务逻辑层 */
	private RoomService roomService = new RoomService();
	private int roomId;
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		//去除title 
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		//去掉Activity上面的状态栏
		getWindow().setFlags(WindowManager.LayoutParams. FLAG_FULLSCREEN , WindowManager.LayoutParams. FLAG_FULLSCREEN); 
		// 设置当前Activity界面布局
		setContentView(R.layout.room_detail);
		ImageView search = (ImageView) this.findViewById(R.id.search);
		search.setVisibility(View.GONE);
		TextView title = (TextView) this.findViewById(R.id.title);
		title.setText("查看阅览室详情");
		ImageView back = (ImageView) this.findViewById(R.id.back_btn);
		back.setOnClickListener(new OnClickListener(){ 
			@Override
			public void onClick(View arg0) {
				finish();
			}
		});
		// 通过findViewById方法实例化组件
		btnReturn = (Button) findViewById(R.id.btnReturn);
		TV_roomId = (TextView) findViewById(R.id.TV_roomId);
		TV_roomName = (TextView) findViewById(R.id.TV_roomName);
		iv_roomPhoto = (ImageView) findViewById(R.id.iv_roomPhoto); 
		TV_roomPlace = (TextView) findViewById(R.id.TV_roomPlace);
		TV_seatNum = (TextView) findViewById(R.id.TV_seatNum);
		Bundle extras = this.getIntent().getExtras();
		roomId = extras.getInt("roomId");
		btnReturn.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				RoomDetailActivity.this.finish();
			}
		}); 
		initViewData();
	}
	/* 初始化显示详情界面的数据 */
	private void initViewData() {
	    room = roomService.GetRoom(roomId); 
		this.TV_roomId.setText(room.getRoomId() + "");
		this.TV_roomName.setText(room.getRoomName());
		byte[] roomPhoto_data = null;
		try {
			// 获取图片数据
			roomPhoto_data = ImageService.getImage(HttpUtil.BASE_URL + room.getRoomPhoto());
			Bitmap roomPhoto = BitmapFactory.decodeByteArray(roomPhoto_data, 0,roomPhoto_data.length);
			this.iv_roomPhoto.setImageBitmap(roomPhoto);
		} catch (Exception e) {
			e.printStackTrace();
		}
		this.TV_roomPlace.setText(room.getRoomPlace());
		this.TV_seatNum.setText(room.getSeatNum() + "");
	} 
}
