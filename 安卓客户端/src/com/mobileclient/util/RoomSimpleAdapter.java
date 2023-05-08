package com.mobileclient.util;

import java.util.List;  
import java.util.Map;

import com.mobileclient.activity.R;
import com.mobileclient.imgCache.ImageLoadListener;
import com.mobileclient.imgCache.ListViewOnScrollListener;
import com.mobileclient.imgCache.SyncImageLoader;
import android.content.Context;
import android.view.LayoutInflater; 
import android.view.View;
import android.view.ViewGroup;  
import android.widget.ImageView; 
import android.widget.ListView;
import android.widget.SimpleAdapter; 
import android.widget.TextView; 

public class RoomSimpleAdapter extends SimpleAdapter { 
	/*需要绑定的控件资源id*/
    private int[] mTo;
    /*map集合关键字数组*/
    private String[] mFrom;
/*需要绑定的数据*/
    private List<? extends Map<String, ?>> mData; 

    private LayoutInflater mInflater;
    Context context = null;

    private ListView mListView;
    //图片异步缓存加载类,带内存缓存和文件缓存
    private SyncImageLoader syncImageLoader;

    public RoomSimpleAdapter(Context context, List<? extends Map<String, ?>> data, int resource, String[] from, int[] to,ListView listView) { 
        super(context, data, resource, from, to); 
        mTo = to; 
        mFrom = from; 
        mData = data;
        mInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        this.context= context;
        mListView = listView; 
        syncImageLoader = SyncImageLoader.getInstance();
        ListViewOnScrollListener onScrollListener = new ListViewOnScrollListener(syncImageLoader,listView,getCount());
        mListView.setOnScrollListener(onScrollListener);
    } 

  public View getView(int position, View convertView, ViewGroup parent) { 
	  ViewHolder holder = null;
	  ///*第一次装载这个view时=null,就新建一个调用inflate渲染一个view*/
	  if (convertView == null) convertView = mInflater.inflate(R.layout.room_list_item, null);
	  convertView.setTag("listViewTAG" + position);
	  holder = new ViewHolder(); 
	  /*绑定该view各个控件*/
	  holder.tv_roomId = (TextView)convertView.findViewById(R.id.tv_roomId);
	  holder.tv_roomName = (TextView)convertView.findViewById(R.id.tv_roomName);
	  holder.iv_roomPhoto = (ImageView)convertView.findViewById(R.id.iv_roomPhoto);
	  holder.tv_roomPlace = (TextView)convertView.findViewById(R.id.tv_roomPlace);
	  holder.tv_seatNum = (TextView)convertView.findViewById(R.id.tv_seatNum);
	  /*设置各个控件的展示内容*/
	  holder.tv_roomId.setText("阅览室id：" + mData.get(position).get("roomId").toString());
	  holder.tv_roomName.setText("阅览室名称：" + mData.get(position).get("roomName").toString());
	  holder.iv_roomPhoto.setImageResource(R.drawable.default_photo);
	  ImageLoadListener roomPhotoLoadListener = new ImageLoadListener(mListView,R.id.iv_roomPhoto);
	  syncImageLoader.loadImage(position,(String)mData.get(position).get("roomPhoto"),roomPhotoLoadListener);  
	  holder.tv_roomPlace.setText("阅览室位置：" + mData.get(position).get("roomPlace").toString());
	  holder.tv_seatNum.setText("总座位数：" + mData.get(position).get("seatNum").toString());
	  /*返回修改好的view*/
	  return convertView; 
    } 

    static class ViewHolder{ 
    	TextView tv_roomId;
    	TextView tv_roomName;
    	ImageView iv_roomPhoto;
    	TextView tv_roomPlace;
    	TextView tv_seatNum;
    }
} 
