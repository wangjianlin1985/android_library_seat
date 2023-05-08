package com.mobileclient.util;

import java.util.List;  
import java.util.Map;

import com.mobileclient.service.RoomService;
import com.mobileclient.service.SeatStateService;
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

public class SeatSimpleAdapter extends SimpleAdapter { 
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

    public SeatSimpleAdapter(Context context, List<? extends Map<String, ?>> data, int resource, String[] from, int[] to,ListView listView) { 
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
	  if (convertView == null) convertView = mInflater.inflate(R.layout.seat_list_item, null);
	  convertView.setTag("listViewTAG" + position);
	  holder = new ViewHolder(); 
	  /*绑定该view各个控件*/
	  holder.tv_seatId = (TextView)convertView.findViewById(R.id.tv_seatId);
	  holder.tv_roomObj = (TextView)convertView.findViewById(R.id.tv_roomObj);
	  holder.tv_seatCode = (TextView)convertView.findViewById(R.id.tv_seatCode);
	  holder.tv_seatStateObj = (TextView)convertView.findViewById(R.id.tv_seatStateObj);
	  /*设置各个控件的展示内容*/
	  holder.tv_seatId.setText("座位id：" + mData.get(position).get("seatId").toString());
	  holder.tv_roomObj.setText("所在阅览室：" + (new RoomService()).GetRoom(Integer.parseInt(mData.get(position).get("roomObj").toString())).getRoomName());
	  holder.tv_seatCode.setText("座位编号：" + mData.get(position).get("seatCode").toString());
	  holder.tv_seatStateObj.setText("当前状态：" + (new SeatStateService()).GetSeatState(Integer.parseInt(mData.get(position).get("seatStateObj").toString())).getStateName());
	  /*返回修改好的view*/
	  return convertView; 
    } 

    static class ViewHolder{ 
    	TextView tv_seatId;
    	TextView tv_roomObj;
    	TextView tv_seatCode;
    	TextView tv_seatStateObj;
    }
} 
