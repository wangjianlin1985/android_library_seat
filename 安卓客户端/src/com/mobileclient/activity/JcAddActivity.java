package com.mobileclient.activity;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import com.mobileclient.util.HttpUtil;

import java.sql.Timestamp;
import java.util.Date;
import java.util.List;

import com.mobileclient.domain.Jc;
import com.mobileclient.service.JcService;
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
public class JcAddActivity extends Activity {
	// 声明确定添加按钮
	private Button btnAdd;
	// 声明奖惩类型输入框
	private EditText ET_jcType;
	// 声明奖惩标题输入框
	private EditText ET_title;
	// 声明奖惩内容输入框
	private EditText ET_content;
	// 声明奖惩用户下拉框
	private Spinner spinner_userObj;
	private ArrayAdapter<String> userObj_adapter;
	private static  String[] userObj_ShowText  = null;
	private List<UserInfo> userInfoList = null;
	/*奖惩用户管理业务逻辑层*/
	private UserInfoService userInfoService = new UserInfoService();
	// 声明信用分数输入框
	private EditText ET_creditScore;
	// 声明奖惩时间输入框
	private EditText ET_jcTime;
	protected String carmera_path;
	/*要保存的奖惩信息*/
	Jc jc = new Jc();
	/*奖惩管理业务逻辑层*/
	private JcService jcService = new JcService();

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		//去除title
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		//去掉Activity上面的状态栏
		getWindow().setFlags(WindowManager.LayoutParams. FLAG_FULLSCREEN , WindowManager.LayoutParams. FLAG_FULLSCREEN); 
		// 设置当前Activity界面布局
		setContentView(R.layout.jc_add); 
		ImageView search = (ImageView) this.findViewById(R.id.search);
		search.setVisibility(View.GONE);
		TextView title = (TextView) this.findViewById(R.id.title);
		title.setText("添加奖惩");
		ImageView back = (ImageView) this.findViewById(R.id.back_btn);
		back.setOnClickListener(new OnClickListener(){ 
			@Override
			public void onClick(View arg0) {
				finish();
			}
		});
		ET_jcType = (EditText) findViewById(R.id.ET_jcType);
		ET_title = (EditText) findViewById(R.id.ET_title);
		ET_content = (EditText) findViewById(R.id.ET_content);
		spinner_userObj = (Spinner) findViewById(R.id.Spinner_userObj);
		// 获取所有的奖惩用户
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
				jc.setUserObj(userInfoList.get(arg2).getUser_name()); 
			}
			@Override
			public void onNothingSelected(AdapterView<?> arg0) {}
		});
		// 设置默认值
		spinner_userObj.setVisibility(View.VISIBLE);
		ET_creditScore = (EditText) findViewById(R.id.ET_creditScore);
		ET_jcTime = (EditText) findViewById(R.id.ET_jcTime);
		btnAdd = (Button) findViewById(R.id.BtnAdd);
		/*单击添加奖惩按钮*/
		btnAdd.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				try {
					/*验证获取奖惩类型*/ 
					if(ET_jcType.getText().toString().equals("")) {
						Toast.makeText(JcAddActivity.this, "奖惩类型输入不能为空!", Toast.LENGTH_LONG).show();
						ET_jcType.setFocusable(true);
						ET_jcType.requestFocus();
						return;	
					}
					jc.setJcType(ET_jcType.getText().toString());
					/*验证获取奖惩标题*/ 
					if(ET_title.getText().toString().equals("")) {
						Toast.makeText(JcAddActivity.this, "奖惩标题输入不能为空!", Toast.LENGTH_LONG).show();
						ET_title.setFocusable(true);
						ET_title.requestFocus();
						return;	
					}
					jc.setTitle(ET_title.getText().toString());
					/*验证获取奖惩内容*/ 
					if(ET_content.getText().toString().equals("")) {
						Toast.makeText(JcAddActivity.this, "奖惩内容输入不能为空!", Toast.LENGTH_LONG).show();
						ET_content.setFocusable(true);
						ET_content.requestFocus();
						return;	
					}
					jc.setContent(ET_content.getText().toString());
					/*验证获取信用分数*/ 
					if(ET_creditScore.getText().toString().equals("")) {
						Toast.makeText(JcAddActivity.this, "信用分数输入不能为空!", Toast.LENGTH_LONG).show();
						ET_creditScore.setFocusable(true);
						ET_creditScore.requestFocus();
						return;	
					}
					jc.setCreditScore(Float.parseFloat(ET_creditScore.getText().toString()));
					/*验证获取奖惩时间*/ 
					if(ET_jcTime.getText().toString().equals("")) {
						Toast.makeText(JcAddActivity.this, "奖惩时间输入不能为空!", Toast.LENGTH_LONG).show();
						ET_jcTime.setFocusable(true);
						ET_jcTime.requestFocus();
						return;	
					}
					jc.setJcTime(ET_jcTime.getText().toString());
					/*调用业务逻辑层上传奖惩信息*/
					JcAddActivity.this.setTitle("正在上传奖惩信息，稍等...");
					String result = jcService.AddJc(jc);
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
