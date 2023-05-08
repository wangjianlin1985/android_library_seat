package com.mobileclient.activity;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.view.animation.AnimationSet;
import android.view.animation.LayoutAnimationController;
import android.view.animation.RotateAnimation;
import android.view.animation.ScaleAnimation;
import android.view.animation.TranslateAnimation;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.TextView;

public class MainMenuActivity extends Activity {
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTitle("手机客户端-主界面");
        setContentView(R.layout.main_menu);
        GridView gridview = (GridView) findViewById(R.id.gridview);
        
        AnimationSet set = new AnimationSet(false);
        Animation animation = new AlphaAnimation(0,1);
        animation.setDuration(500);
        set.addAnimation(animation);
        
        animation = new TranslateAnimation(1, 13, 10, 50);
        animation.setDuration(300);
        set.addAnimation(animation);
        
        animation = new RotateAnimation(30,10);
        animation.setDuration(300);
        set.addAnimation(animation);
        
        animation = new ScaleAnimation(5,0,2,0);
        animation.setDuration(300);
        set.addAnimation(animation);
        
        LayoutAnimationController controller = new LayoutAnimationController(set, 1);
        
        gridview.setLayoutAnimation(controller);
        
        gridview.setAdapter(new ImageAdapter(this));
    }
    
    // 继承BaseAdapter
    public class ImageAdapter extends BaseAdapter {
    	
    	LayoutInflater inflater;
    	
    	// 上下文
        private Context mContext;
        
        // 图片资源数组
        private Integer[] mThumbIds = {
                R.drawable.operateicon,R.drawable.operateicon,R.drawable.operateicon,R.drawable.operateicon,R.drawable.operateicon,R.drawable.operateicon,R.drawable.operateicon,R.drawable.operateicon,R.drawable.operateicon,R.drawable.operateicon
        };
        private String[] menuString = {"班级管理","用户管理","用户类型管理","阅览室管理","座位管理","座位状态管理","座位预约管理","选座管理","举报管理","奖惩管理"};

        // 构造方法
        public ImageAdapter(Context c) {
            mContext = c;
            inflater = LayoutInflater.from(mContext);
        }
        // 组件个数
        public int getCount() {
            return mThumbIds.length;
        }
        // 当前组件
        public Object getItem(int position) {
            return null;
        }
        // 当前组件id
        public long getItemId(int position) {
            return 0;
        }
        // 获得当前视图
        public View getView(int position, View convertView, ViewGroup parent) { 
        	View view = inflater.inflate(R.layout.gv_item, null);
			TextView tv = (TextView) view.findViewById(R.id.gv_item_appname);
			ImageView iv = (ImageView) view.findViewById(R.id.gv_item_icon);  
			tv.setText(menuString[position]); 
			iv.setImageResource(mThumbIds[position]); 
			  switch (position) {
				case 0:
					// 班级管理监听器
					view.setOnClickListener(classInfoLinstener);
					break;
				case 1:
					// 用户管理监听器
					view.setOnClickListener(userInfoLinstener);
					break;
				case 2:
					// 用户类型管理监听器
					view.setOnClickListener(userTypeLinstener);
					break;
				case 3:
					// 阅览室管理监听器
					view.setOnClickListener(roomLinstener);
					break;
				case 4:
					// 座位管理监听器
					view.setOnClickListener(seatLinstener);
					break;
				case 5:
					// 座位状态管理监听器
					view.setOnClickListener(seatStateLinstener);
					break;
				case 6:
					// 座位预约管理监听器
					view.setOnClickListener(seatOrderLinstener);
					break;
				case 7:
					// 选座管理监听器
					view.setOnClickListener(selectSeatLinstener);
					break;
				case 8:
					// 举报管理监听器
					view.setOnClickListener(jubaoLinstener);
					break;
				case 9:
					// 奖惩管理监听器
					view.setOnClickListener(jcLinstener);
					break;

			 
				default:
					break;
				} 
			return view; 
        }
       
    }
    
    OnClickListener classInfoLinstener = new OnClickListener() {
		public void onClick(View v) {
			Intent intent = new Intent();
			// 启动班级管理Activity
			intent.setClass(MainMenuActivity.this, ClassInfoListActivity.class);
			startActivity(intent);
		}
	};
    OnClickListener userInfoLinstener = new OnClickListener() {
		public void onClick(View v) {
			Intent intent = new Intent();
			// 启动用户管理Activity
			intent.setClass(MainMenuActivity.this, UserInfoListActivity.class);
			startActivity(intent);
		}
	};
    OnClickListener userTypeLinstener = new OnClickListener() {
		public void onClick(View v) {
			Intent intent = new Intent();
			// 启动用户类型管理Activity
			intent.setClass(MainMenuActivity.this, UserTypeListActivity.class);
			startActivity(intent);
		}
	};
    OnClickListener roomLinstener = new OnClickListener() {
		public void onClick(View v) {
			Intent intent = new Intent();
			// 启动阅览室管理Activity
			intent.setClass(MainMenuActivity.this, RoomListActivity.class);
			startActivity(intent);
		}
	};
    OnClickListener seatLinstener = new OnClickListener() {
		public void onClick(View v) {
			Intent intent = new Intent();
			// 启动座位管理Activity
			intent.setClass(MainMenuActivity.this, SeatListActivity.class);
			startActivity(intent);
		}
	};
    OnClickListener seatStateLinstener = new OnClickListener() {
		public void onClick(View v) {
			Intent intent = new Intent();
			// 启动座位状态管理Activity
			intent.setClass(MainMenuActivity.this, SeatStateListActivity.class);
			startActivity(intent);
		}
	};
    OnClickListener seatOrderLinstener = new OnClickListener() {
		public void onClick(View v) {
			Intent intent = new Intent();
			// 启动座位预约管理Activity
			intent.setClass(MainMenuActivity.this, SeatOrderListActivity.class);
			startActivity(intent);
		}
	};
    OnClickListener selectSeatLinstener = new OnClickListener() {
		public void onClick(View v) {
			Intent intent = new Intent();
			// 启动选座管理Activity
			intent.setClass(MainMenuActivity.this, SelectSeatListActivity.class);
			startActivity(intent);
		}
	};
    OnClickListener jubaoLinstener = new OnClickListener() {
		public void onClick(View v) {
			Intent intent = new Intent();
			// 启动举报管理Activity
			intent.setClass(MainMenuActivity.this, JubaoListActivity.class);
			startActivity(intent);
		}
	};
    OnClickListener jcLinstener = new OnClickListener() {
		public void onClick(View v) {
			Intent intent = new Intent();
			// 启动奖惩管理Activity
			intent.setClass(MainMenuActivity.this, JcListActivity.class);
			startActivity(intent);
		}
	};


	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// TODO Auto-generated method stub
		menu.add(0, 1, 1, "重新登入");  
		menu.add(0, 2, 2, "退出"); 
		return super.onCreateOptionsMenu(menu); 
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// TODO Auto-generated method stub
		if (item.getItemId() == 1) {//重新登入 
			Intent intent = new Intent();
			intent.setClass(MainMenuActivity.this,
					LoginActivity.class);
			startActivity(intent);
		} else if (item.getItemId() == 2) {//退出
			System.exit(0);  
		} 
		return true; 
	}
}
