package com.mobileclient.activity;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;

import java.sql.Timestamp;
import java.util.Date;
import java.util.List;

import com.mobileclient.util.HttpUtil;
import com.mobileclient.util.ImageService;
import com.mobileclient.domain.Room;
import com.mobileclient.service.RoomService;
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
import android.widget.TextView;
import android.widget.ImageView;
import android.widget.ImageView.ScaleType;
import android.widget.Spinner;
import android.widget.Toast;

public class RoomEditActivity extends Activity {
	// 声明确定添加按钮
	private Button btnUpdate;
	// 声明阅览室idTextView
	private TextView TV_roomId;
	// 声明阅览室名称输入框
	private EditText ET_roomName;
	// 声明阅览室照片图片框控件
	private ImageView iv_roomPhoto;
	private Button btn_roomPhoto;
	protected int REQ_CODE_SELECT_IMAGE_roomPhoto = 1;
	private int REQ_CODE_CAMERA_roomPhoto = 2;
	// 声明阅览室位置输入框
	private EditText ET_roomPlace;
	// 声明总座位数输入框
	private EditText ET_seatNum;
	protected String carmera_path;
	/*要保存的阅览室信息*/
	Room room = new Room();
	/*阅览室管理业务逻辑层*/
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
		setContentView(R.layout.room_edit); 
		ImageView search = (ImageView) this.findViewById(R.id.search);
		search.setVisibility(View.GONE);
		TextView title = (TextView) this.findViewById(R.id.title);
		title.setText("编辑阅览室信息");
		ImageView back = (ImageView) this.findViewById(R.id.back_btn);
		back.setOnClickListener(new OnClickListener(){ 
			@Override
			public void onClick(View arg0) {
				finish();
			}
		});
		TV_roomId = (TextView) findViewById(R.id.TV_roomId);
		ET_roomName = (EditText) findViewById(R.id.ET_roomName);
		iv_roomPhoto = (ImageView) findViewById(R.id.iv_roomPhoto);
		/*单击图片显示控件时进行图片的选择*/
		iv_roomPhoto.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				Intent intent = new Intent();
				intent.setClass(RoomEditActivity.this,photoListActivity.class);
				startActivityForResult(intent,REQ_CODE_SELECT_IMAGE_roomPhoto);
			}
		});
		btn_roomPhoto = (Button) findViewById(R.id.btn_roomPhoto);
		btn_roomPhoto.setOnClickListener(new OnClickListener() { 
			@Override
			public void onClick(View arg0) {
				Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE); 
				carmera_path = HttpUtil.FILE_PATH + "/carmera_roomPhoto.bmp";
				File out = new File(carmera_path); 
				intent.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(out)); 
				startActivityForResult(intent, REQ_CODE_CAMERA_roomPhoto);  
			}
		});
		ET_roomPlace = (EditText) findViewById(R.id.ET_roomPlace);
		ET_seatNum = (EditText) findViewById(R.id.ET_seatNum);
		btnUpdate = (Button) findViewById(R.id.BtnUpdate);
		Bundle extras = this.getIntent().getExtras();
		roomId = extras.getInt("roomId");
		/*单击修改阅览室按钮*/
		btnUpdate.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				try {
					/*验证获取阅览室名称*/ 
					if(ET_roomName.getText().toString().equals("")) {
						Toast.makeText(RoomEditActivity.this, "阅览室名称输入不能为空!", Toast.LENGTH_LONG).show();
						ET_roomName.setFocusable(true);
						ET_roomName.requestFocus();
						return;	
					}
					room.setRoomName(ET_roomName.getText().toString());
					if (!room.getRoomPhoto().startsWith("upload/")) {
						//如果图片地址不为空，说明用户选择了图片，这时需要连接服务器上传图片
						RoomEditActivity.this.setTitle("正在上传图片，稍等...");
						String roomPhoto = HttpUtil.uploadFile(room.getRoomPhoto());
						RoomEditActivity.this.setTitle("图片上传完毕！");
						room.setRoomPhoto(roomPhoto);
					} 
					/*验证获取阅览室位置*/ 
					if(ET_roomPlace.getText().toString().equals("")) {
						Toast.makeText(RoomEditActivity.this, "阅览室位置输入不能为空!", Toast.LENGTH_LONG).show();
						ET_roomPlace.setFocusable(true);
						ET_roomPlace.requestFocus();
						return;	
					}
					room.setRoomPlace(ET_roomPlace.getText().toString());
					/*验证获取总座位数*/ 
					if(ET_seatNum.getText().toString().equals("")) {
						Toast.makeText(RoomEditActivity.this, "总座位数输入不能为空!", Toast.LENGTH_LONG).show();
						ET_seatNum.setFocusable(true);
						ET_seatNum.requestFocus();
						return;	
					}
					room.setSeatNum(Integer.parseInt(ET_seatNum.getText().toString()));
					/*调用业务逻辑层上传阅览室信息*/
					RoomEditActivity.this.setTitle("正在更新阅览室信息，稍等...");
					String result = roomService.UpdateRoom(room);
					Toast.makeText(getApplicationContext(), result, 1).show(); 
					Intent intent = getIntent();
					setResult(RESULT_OK,intent);
					finish();
				} catch (Exception e) {}
			}
		});
		initViewData();
	}

	/* 初始化显示编辑界面的数据 */
	private void initViewData() {
	    room = roomService.GetRoom(roomId);
		this.TV_roomId.setText(roomId+"");
		this.ET_roomName.setText(room.getRoomName());
		byte[] roomPhoto_data = null;
		try {
			// 获取图片数据
			roomPhoto_data = ImageService.getImage(HttpUtil.BASE_URL + room.getRoomPhoto());
			Bitmap roomPhoto = BitmapFactory.decodeByteArray(roomPhoto_data, 0, roomPhoto_data.length);
			this.iv_roomPhoto.setImageBitmap(roomPhoto);
		} catch (Exception e) {
			e.printStackTrace();
		}
		this.ET_roomPlace.setText(room.getRoomPlace());
		this.ET_seatNum.setText(room.getSeatNum() + "");
	}
	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);
		if (requestCode == REQ_CODE_CAMERA_roomPhoto  && resultCode == Activity.RESULT_OK) {
			carmera_path = HttpUtil.FILE_PATH + "/carmera_roomPhoto.bmp"; 
			BitmapFactory.Options opts = new BitmapFactory.Options();
			opts.inJustDecodeBounds = true;
			BitmapFactory.decodeFile(carmera_path, opts); 
			opts.inSampleSize = photoListActivity.computeSampleSize(opts, -1, 300*300);
			opts.inJustDecodeBounds = false;
			try {
				Bitmap booImageBm = BitmapFactory.decodeFile(carmera_path, opts);
				String jpgFileName = "carmera_roomPhoto.jpg";
				String jpgFilePath =  HttpUtil.FILE_PATH + "/" + jpgFileName;
				try {
					FileOutputStream jpgOutputStream = new FileOutputStream(jpgFilePath);
					booImageBm.compress(Bitmap.CompressFormat.JPEG, 30, jpgOutputStream);// 把数据写入文件 
					File bmpFile = new File(carmera_path);
					bmpFile.delete();
				} catch (FileNotFoundException e) {
					e.printStackTrace();
				} 
				this.iv_roomPhoto.setImageBitmap(booImageBm);
				this.iv_roomPhoto.setScaleType(ScaleType.FIT_CENTER);
				this.room.setRoomPhoto(jpgFileName);
			} catch (OutOfMemoryError err) {  }
		}

		if(requestCode == REQ_CODE_SELECT_IMAGE_roomPhoto && resultCode == Activity.RESULT_OK) {
			Bundle bundle = data.getExtras();
			String filename =  bundle.getString("fileName");
			String filepath = HttpUtil.FILE_PATH + "/" + filename;
			BitmapFactory.Options opts = new BitmapFactory.Options();
			opts.inJustDecodeBounds = true; 
			BitmapFactory.decodeFile(filepath, opts); 
			opts.inSampleSize = photoListActivity.computeSampleSize(opts, -1, 128*128);
			opts.inJustDecodeBounds = false; 
			try { 
				Bitmap bm = BitmapFactory.decodeFile(filepath, opts);
				this.iv_roomPhoto.setImageBitmap(bm); 
				this.iv_roomPhoto.setScaleType(ScaleType.FIT_CENTER); 
			} catch (OutOfMemoryError err) {  } 
			room.setRoomPhoto(filename); 
		}
	}
}
