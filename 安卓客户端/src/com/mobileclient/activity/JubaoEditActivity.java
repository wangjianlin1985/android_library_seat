package com.mobileclient.activity;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;

import java.sql.Timestamp;
import java.util.Date;
import java.util.List;

import com.mobileclient.util.HttpUtil;
import com.mobileclient.util.ImageService;
import com.mobileclient.domain.Jubao;
import com.mobileclient.service.JubaoService;
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
import android.widget.TextView;
import android.widget.ImageView;
import android.widget.ImageView.ScaleType;
import android.widget.Spinner;
import android.widget.Toast;

public class JubaoEditActivity extends Activity {
	// 声明确定添加按钮
	private Button btnUpdate;
	// 声明举报idTextView
	private TextView TV_jubaoId;
	// 声明举报标题输入框
	private EditText ET_title;
	// 声明举报内容输入框
	private EditText ET_content;
	// 声明举报用户下拉框
	private Spinner spinner_userObj;
	private ArrayAdapter<String> userObj_adapter;
	private static  String[] userObj_ShowText  = null;
	private List<UserInfo> userInfoList = null;
	/*举报用户管理业务逻辑层*/
	private UserInfoService userInfoService = new UserInfoService();
	// 声明举报时间输入框
	private EditText ET_jubaoTime;
	// 声明管理回复输入框
	private EditText ET_replyContent;
	protected String carmera_path;
	/*要保存的举报信息*/
	Jubao jubao = new Jubao();
	/*举报管理业务逻辑层*/
	private JubaoService jubaoService = new JubaoService();

	private int jubaoId;
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		//去除title
		requestWindowFeature(Window.FEATURE_NO_TITLE); 
		//去掉Activity上面的状态栏
		getWindow().setFlags(WindowManager.LayoutParams. FLAG_FULLSCREEN , WindowManager.LayoutParams. FLAG_FULLSCREEN);
		// 设置当前Activity界面布局
		setContentView(R.layout.jubao_edit); 
		ImageView search = (ImageView) this.findViewById(R.id.search);
		search.setVisibility(View.GONE);
		TextView title = (TextView) this.findViewById(R.id.title);
		title.setText("编辑举报信息");
		ImageView back = (ImageView) this.findViewById(R.id.back_btn);
		back.setOnClickListener(new OnClickListener(){ 
			@Override
			public void onClick(View arg0) {
				finish();
			}
		});
		TV_jubaoId = (TextView) findViewById(R.id.TV_jubaoId);
		ET_title = (EditText) findViewById(R.id.ET_title);
		ET_content = (EditText) findViewById(R.id.ET_content);
		spinner_userObj = (Spinner) findViewById(R.id.Spinner_userObj);
		// 获取所有的举报用户
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
		// 设置图书类别下拉列表的风格
		userObj_adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		// 将adapter 添加到spinner中
		spinner_userObj.setAdapter(userObj_adapter);
		// 添加事件Spinner事件监听
		spinner_userObj.setOnItemSelectedListener(new OnItemSelectedListener() {
			@Override
			public void onItemSelected(AdapterView<?> arg0, View arg1,int arg2, long arg3) {
				jubao.setUserObj(userInfoList.get(arg2).getUser_name()); 
			}
			@Override
			public void onNothingSelected(AdapterView<?> arg0) {}
		});
		// 设置默认值
		spinner_userObj.setVisibility(View.VISIBLE);
		ET_jubaoTime = (EditText) findViewById(R.id.ET_jubaoTime);
		ET_replyContent = (EditText) findViewById(R.id.ET_replyContent);
		btnUpdate = (Button) findViewById(R.id.BtnUpdate);
		Bundle extras = this.getIntent().getExtras();
		jubaoId = extras.getInt("jubaoId");
		/*单击修改举报按钮*/
		btnUpdate.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				try {
					/*验证获取举报标题*/ 
					if(ET_title.getText().toString().equals("")) {
						Toast.makeText(JubaoEditActivity.this, "举报标题输入不能为空!", Toast.LENGTH_LONG).show();
						ET_title.setFocusable(true);
						ET_title.requestFocus();
						return;	
					}
					jubao.setTitle(ET_title.getText().toString());
					/*验证获取举报内容*/ 
					if(ET_content.getText().toString().equals("")) {
						Toast.makeText(JubaoEditActivity.this, "举报内容输入不能为空!", Toast.LENGTH_LONG).show();
						ET_content.setFocusable(true);
						ET_content.requestFocus();
						return;	
					}
					jubao.setContent(ET_content.getText().toString());
					/*验证获取举报时间*/ 
					if(ET_jubaoTime.getText().toString().equals("")) {
						Toast.makeText(JubaoEditActivity.this, "举报时间输入不能为空!", Toast.LENGTH_LONG).show();
						ET_jubaoTime.setFocusable(true);
						ET_jubaoTime.requestFocus();
						return;	
					}
					jubao.setJubaoTime(ET_jubaoTime.getText().toString());
					/*验证获取管理回复*/ 
					if(ET_replyContent.getText().toString().equals("")) {
						Toast.makeText(JubaoEditActivity.this, "管理回复输入不能为空!", Toast.LENGTH_LONG).show();
						ET_replyContent.setFocusable(true);
						ET_replyContent.requestFocus();
						return;	
					}
					jubao.setReplyContent(ET_replyContent.getText().toString());
					/*调用业务逻辑层上传举报信息*/
					JubaoEditActivity.this.setTitle("正在更新举报信息，稍等...");
					String result = jubaoService.UpdateJubao(jubao);
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
	    jubao = jubaoService.GetJubao(jubaoId);
		this.TV_jubaoId.setText(jubaoId+"");
		this.ET_title.setText(jubao.getTitle());
		this.ET_content.setText(jubao.getContent());
		for (int i = 0; i < userInfoList.size(); i++) {
			if (jubao.getUserObj().equals(userInfoList.get(i).getUser_name())) {
				this.spinner_userObj.setSelection(i);
				break;
			}
		}
		this.ET_jubaoTime.setText(jubao.getJubaoTime());
		this.ET_replyContent.setText(jubao.getReplyContent());
	}
	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);
	}
}
