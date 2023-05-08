package com.mobileclient.activity;
import java.sql.Timestamp;
import java.util.Date;
import java.util.List;
import com.mobileclient.domain.UserInfo;
import com.mobileclient.domain.UserType;
import com.mobileclient.service.UserTypeService;
import com.mobileclient.domain.ClassInfo;
import com.mobileclient.service.ClassInfoService;

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
public class UserInfoQueryActivity extends Activity {
	// 声明查询按钮
	private Button btnQuery;
	// 声明用户名输入框
	private EditText ET_user_name;
	// 声明用户类型下拉框
	private Spinner spinner_userTypeObj;
	private ArrayAdapter<String> userTypeObj_adapter;
	private static  String[] userTypeObj_ShowText  = null;
	private List<UserType> userTypeList = null; 
	/*用户类型管理业务逻辑层*/
	private UserTypeService userTypeService = new UserTypeService();
	// 声明所在班级下拉框
	private Spinner spinner_classObj;
	private ArrayAdapter<String> classObj_adapter;
	private static  String[] classObj_ShowText  = null;
	private List<ClassInfo> classInfoList = null; 
	/*班级管理业务逻辑层*/
	private ClassInfoService classInfoService = new ClassInfoService();
	// 声明姓名输入框
	private EditText ET_name;
	// 出生日期控件
	private DatePicker dp_birthDate;
	private CheckBox cb_birthDate;
	// 声明联系电话输入框
	private EditText ET_telephone;
	// 声明是否黑名单输入框
	private EditText ET_blackFlag;
	/*查询过滤条件保存到这个对象中*/
	private UserInfo queryConditionUserInfo = new UserInfo();

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		//去除title 
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		//去掉Activity上面的状态栏
		getWindow().setFlags(WindowManager.LayoutParams. FLAG_FULLSCREEN , WindowManager.LayoutParams. FLAG_FULLSCREEN);
		// 设置当前Activity界面布局
		setContentView(R.layout.userinfo_query);
		ImageView search = (ImageView) this.findViewById(R.id.search);
		search.setVisibility(View.GONE);
		TextView title = (TextView) this.findViewById(R.id.title);
		title.setText("设置用户查询条件");
		ImageView back_btn = (ImageView) this.findViewById(R.id.back_btn);
		back_btn.setOnClickListener(new android.view.View.OnClickListener(){
			@Override
			public void onClick(View arg0) {
				finish();
			}
		});
		btnQuery = (Button) findViewById(R.id.btnQuery);
		ET_user_name = (EditText) findViewById(R.id.ET_user_name);
		spinner_userTypeObj = (Spinner) findViewById(R.id.Spinner_userTypeObj);
		// 获取所有的用户类型
		try {
			userTypeList = userTypeService.QueryUserType(null);
		} catch (Exception e1) {
			e1.printStackTrace();
		}
		int userTypeCount = userTypeList.size();
		userTypeObj_ShowText = new String[userTypeCount+1];
		userTypeObj_ShowText[0] = "不限制";
		for(int i=1;i<=userTypeCount;i++) { 
			userTypeObj_ShowText[i] = userTypeList.get(i-1).getUserTypeName();
		} 
		// 将可选内容与ArrayAdapter连接起来
		userTypeObj_adapter = new ArrayAdapter<String>(this,android.R.layout.simple_spinner_item, userTypeObj_ShowText);
		// 设置用户类型下拉列表的风格
		userTypeObj_adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		// 将adapter 添加到spinner中
		spinner_userTypeObj.setAdapter(userTypeObj_adapter);
		// 添加事件Spinner事件监听
		spinner_userTypeObj.setOnItemSelectedListener(new OnItemSelectedListener() {
			@Override
			public void onItemSelected(AdapterView<?> arg0, View arg1,int arg2, long arg3) {
				if(arg2 != 0)
					queryConditionUserInfo.setUserTypeObj(userTypeList.get(arg2-1).getUserTypeId()); 
				else
					queryConditionUserInfo.setUserTypeObj(0);
			}
			@Override
			public void onNothingSelected(AdapterView<?> arg0) {}
		});
		// 设置默认值
		spinner_userTypeObj.setVisibility(View.VISIBLE);
		spinner_classObj = (Spinner) findViewById(R.id.Spinner_classObj);
		// 获取所有的班级
		try {
			classInfoList = classInfoService.QueryClassInfo(null);
		} catch (Exception e1) {
			e1.printStackTrace();
		}
		int classInfoCount = classInfoList.size();
		classObj_ShowText = new String[classInfoCount+1];
		classObj_ShowText[0] = "不限制";
		for(int i=1;i<=classInfoCount;i++) { 
			classObj_ShowText[i] = classInfoList.get(i-1).getClassName();
		} 
		// 将可选内容与ArrayAdapter连接起来
		classObj_adapter = new ArrayAdapter<String>(this,android.R.layout.simple_spinner_item, classObj_ShowText);
		// 设置所在班级下拉列表的风格
		classObj_adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		// 将adapter 添加到spinner中
		spinner_classObj.setAdapter(classObj_adapter);
		// 添加事件Spinner事件监听
		spinner_classObj.setOnItemSelectedListener(new OnItemSelectedListener() {
			@Override
			public void onItemSelected(AdapterView<?> arg0, View arg1,int arg2, long arg3) {
				if(arg2 != 0)
					queryConditionUserInfo.setClassObj(classInfoList.get(arg2-1).getClassNo()); 
				else
					queryConditionUserInfo.setClassObj("");
			}
			@Override
			public void onNothingSelected(AdapterView<?> arg0) {}
		});
		// 设置默认值
		spinner_classObj.setVisibility(View.VISIBLE);
		ET_name = (EditText) findViewById(R.id.ET_name);
		dp_birthDate = (DatePicker) findViewById(R.id.dp_birthDate);
		cb_birthDate = (CheckBox) findViewById(R.id.cb_birthDate);
		ET_telephone = (EditText) findViewById(R.id.ET_telephone);
		ET_blackFlag = (EditText) findViewById(R.id.ET_blackFlag);
		/*单击查询按钮*/
		btnQuery.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				try {
					/*获取查询参数*/
					queryConditionUserInfo.setUser_name(ET_user_name.getText().toString());
					queryConditionUserInfo.setName(ET_name.getText().toString());
					if(cb_birthDate.isChecked()) {
						/*获取出生日期*/
						Date birthDate = new Date(dp_birthDate.getYear()-1900,dp_birthDate.getMonth(),dp_birthDate.getDayOfMonth());
						queryConditionUserInfo.setBirthDate(new Timestamp(birthDate.getTime()));
					} else {
						queryConditionUserInfo.setBirthDate(null);
					} 
					queryConditionUserInfo.setTelephone(ET_telephone.getText().toString());
					queryConditionUserInfo.setBlackFlag(ET_blackFlag.getText().toString());
					Intent intent = getIntent();
					//这里使用bundle绷带来传输数据
					Bundle bundle =new Bundle();
					//传输的内容仍然是键值对的形式
					bundle.putSerializable("queryConditionUserInfo", queryConditionUserInfo);
					intent.putExtras(bundle);
					setResult(RESULT_OK,intent);
					finish();
				} catch (Exception e) {}
			}
			});
	}
}
