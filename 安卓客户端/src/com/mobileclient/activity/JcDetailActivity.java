package com.mobileclient.activity;

import java.util.Date;
import com.mobileclient.domain.Jc;
import com.mobileclient.service.JcService;
import com.mobileclient.domain.UserInfo;
import com.mobileclient.service.UserInfoService;
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
public class JcDetailActivity extends Activity {
	// 声明返回按钮
	private Button btnReturn;
	// 声明奖惩id控件
	private TextView TV_jcId;
	// 声明奖惩类型控件
	private TextView TV_jcType;
	// 声明奖惩标题控件
	private TextView TV_title;
	// 声明奖惩内容控件
	private TextView TV_content;
	// 声明奖惩用户控件
	private TextView TV_userObj;
	// 声明信用分数控件
	private TextView TV_creditScore;
	// 声明奖惩时间控件
	private TextView TV_jcTime;
	/* 要保存的奖惩信息 */
	Jc jc = new Jc(); 
	/* 奖惩管理业务逻辑层 */
	private JcService jcService = new JcService();
	private UserInfoService userInfoService = new UserInfoService();
	private int jcId;
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		//去除title 
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		//去掉Activity上面的状态栏
		getWindow().setFlags(WindowManager.LayoutParams. FLAG_FULLSCREEN , WindowManager.LayoutParams. FLAG_FULLSCREEN); 
		// 设置当前Activity界面布局
		setContentView(R.layout.jc_detail);
		ImageView search = (ImageView) this.findViewById(R.id.search);
		search.setVisibility(View.GONE);
		TextView title = (TextView) this.findViewById(R.id.title);
		title.setText("查看奖惩详情");
		ImageView back = (ImageView) this.findViewById(R.id.back_btn);
		back.setOnClickListener(new OnClickListener(){ 
			@Override
			public void onClick(View arg0) {
				finish();
			}
		});
		// 通过findViewById方法实例化组件
		btnReturn = (Button) findViewById(R.id.btnReturn);
		TV_jcId = (TextView) findViewById(R.id.TV_jcId);
		TV_jcType = (TextView) findViewById(R.id.TV_jcType);
		TV_title = (TextView) findViewById(R.id.TV_title);
		TV_content = (TextView) findViewById(R.id.TV_content);
		TV_userObj = (TextView) findViewById(R.id.TV_userObj);
		TV_creditScore = (TextView) findViewById(R.id.TV_creditScore);
		TV_jcTime = (TextView) findViewById(R.id.TV_jcTime);
		Bundle extras = this.getIntent().getExtras();
		jcId = extras.getInt("jcId");
		btnReturn.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				JcDetailActivity.this.finish();
			}
		}); 
		initViewData();
	}
	/* 初始化显示详情界面的数据 */
	private void initViewData() {
	    jc = jcService.GetJc(jcId); 
		this.TV_jcId.setText(jc.getJcId() + "");
		this.TV_jcType.setText(jc.getJcType());
		this.TV_title.setText(jc.getTitle());
		this.TV_content.setText(jc.getContent());
		UserInfo userObj = userInfoService.GetUserInfo(jc.getUserObj());
		this.TV_userObj.setText(userObj.getName());
		this.TV_creditScore.setText(jc.getCreditScore() + "");
		this.TV_jcTime.setText(jc.getJcTime());
	} 
}
