package com.mobileclient.activity;

import java.util.Date;
import com.mobileclient.domain.Jubao;
import com.mobileclient.service.JubaoService;
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
public class JubaoDetailActivity extends Activity {
	// 声明返回按钮
	private Button btnReturn;
	// 声明举报id控件
	private TextView TV_jubaoId;
	// 声明举报标题控件
	private TextView TV_title;
	// 声明举报内容控件
	private TextView TV_content;
	// 声明举报用户控件
	private TextView TV_userObj;
	// 声明举报时间控件
	private TextView TV_jubaoTime;
	// 声明管理回复控件
	private TextView TV_replyContent;
	/* 要保存的举报信息 */
	Jubao jubao = new Jubao(); 
	/* 举报管理业务逻辑层 */
	private JubaoService jubaoService = new JubaoService();
	private UserInfoService userInfoService = new UserInfoService();
	private int jubaoId;
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		//去除title 
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		//去掉Activity上面的状态栏
		getWindow().setFlags(WindowManager.LayoutParams. FLAG_FULLSCREEN , WindowManager.LayoutParams. FLAG_FULLSCREEN); 
		// 设置当前Activity界面布局
		setContentView(R.layout.jubao_detail);
		ImageView search = (ImageView) this.findViewById(R.id.search);
		search.setVisibility(View.GONE);
		TextView title = (TextView) this.findViewById(R.id.title);
		title.setText("查看举报详情");
		ImageView back = (ImageView) this.findViewById(R.id.back_btn);
		back.setOnClickListener(new OnClickListener(){ 
			@Override
			public void onClick(View arg0) {
				finish();
			}
		});
		// 通过findViewById方法实例化组件
		btnReturn = (Button) findViewById(R.id.btnReturn);
		TV_jubaoId = (TextView) findViewById(R.id.TV_jubaoId);
		TV_title = (TextView) findViewById(R.id.TV_title);
		TV_content = (TextView) findViewById(R.id.TV_content);
		TV_userObj = (TextView) findViewById(R.id.TV_userObj);
		TV_jubaoTime = (TextView) findViewById(R.id.TV_jubaoTime);
		TV_replyContent = (TextView) findViewById(R.id.TV_replyContent);
		Bundle extras = this.getIntent().getExtras();
		jubaoId = extras.getInt("jubaoId");
		btnReturn.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				JubaoDetailActivity.this.finish();
			}
		}); 
		initViewData();
	}
	/* 初始化显示详情界面的数据 */
	private void initViewData() {
	    jubao = jubaoService.GetJubao(jubaoId); 
		this.TV_jubaoId.setText(jubao.getJubaoId() + "");
		this.TV_title.setText(jubao.getTitle());
		this.TV_content.setText(jubao.getContent());
		UserInfo userObj = userInfoService.GetUserInfo(jubao.getUserObj());
		this.TV_userObj.setText(userObj.getName());
		this.TV_jubaoTime.setText(jubao.getJubaoTime());
		this.TV_replyContent.setText(jubao.getReplyContent());
	} 
}
