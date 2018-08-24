package com.zhizhong.yujian.module.live.fragment;

import android.content.Intent;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.github.androidtools.PhoneUtils;
import com.github.androidtools.inter.MyOnClickListener;
import com.github.baseclass.adapter.MyRecyclerViewHolder;
import com.github.fastshape.MyTextView;
import com.library.base.BaseObj;
import com.zhizhong.yujian.IntentParam;
import com.zhizhong.yujian.R;
import com.zhizhong.yujian.adapter.MyAdapter;
import com.zhizhong.yujian.base.BaseFragment;
import com.zhizhong.yujian.base.MyCallBack;
import com.zhizhong.yujian.base.SpaceItemDecoration;
import com.zhizhong.yujian.module.home.activity.LiveRoomActivity;
import com.zhizhong.yujian.module.home.network.response.LiveObj;
import com.zhizhong.yujian.module.live.network.ApiRequest;
import com.zhizhong.yujian.network.NetApiRequest;
import com.zhizhong.yujian.network.response.LiveStatusObj;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;

public class LiveFragment extends BaseFragment {


    @BindView(R.id.iv_home_live)
    ImageView iv_home_live;
    @BindView(R.id.tv_live_room_flag)
    MyTextView tv_live_room_flag;
    @BindView(R.id.tv_live_room_peoplenum)
    MyTextView tv_live_room_peoplenum;
    @BindView(R.id.tv_live_room_name)
    MyTextView tv_live_room_name;
    @BindView(R.id.rv_live_room)
    RecyclerView rv_live_room;


    @BindView(R.id.iv_live_room2)
    ImageView iv_live_room2;
    @BindView(R.id.tv_live_room_flag2)
    MyTextView tv_live_room_flag2;
    @BindView(R.id.tv_live_room_peoplenum2)
    MyTextView tv_live_room_peoplenum2;
    @BindView(R.id.tv_live_room_name2)
    MyTextView tv_live_room_name2;

    @BindView(R.id.rv_live_room2)
    RecyclerView rv_live_room2;

    MyAdapter adapter;
    MyAdapter adapter2;

    @Override
    protected int getContentView() {
        return R.layout.live_frag;
    }

    @Override
    protected void initView() {
        adapter = new MyAdapter<LiveObj>(mContext, R.layout.live_room_item, pageSize) {
            @Override
            public void bindData(MyRecyclerViewHolder holder, int position, final LiveObj bean) {
                ImageView iv_live_room=holder.getImageView(R.id.iv_live_room);
                TextView tv_live_room_flag=holder.getTextView(R.id.tv_live_room_flag);
                TextView tv_live_room_name=holder.getTextView(R.id.tv_live_room_name);
                TextView tv_home_live_peoplenum=holder.getTextView(R.id.tv_home_live_peoplenum);

                if(TextUtils.isEmpty(bean.getChannel_name())){
                    iv_live_room.setOnClickListener(new MyOnClickListener() {
                        @Override
                        protected void onNoDoubleClick(View view) {
                            showMsg("该直播间未开播");
                        }
                    });
                    tv_live_room_flag.setText("未开播");
                    tv_live_room_name.setText("相玉直播间");
                    tv_home_live_peoplenum.setText("0人观看");
                }else{
                    iv_live_room.setOnClickListener(new MyOnClickListener() {
                        @Override
                        protected void onNoDoubleClick(View view) {
                            goLive(bean.getChannel_name(),bean.getChannel_address(),bean.getLive_user_id(),bean.getChannel_id());
                        }
                    });
                    tv_live_room_flag.setText("直播中");
                    tv_home_live_peoplenum.setText(bean.getLive_num()+"人观看");
                    tv_live_room_name.setText(bean.getChannel_name());
                }
            }
        };


        rv_live_room.setNestedScrollingEnabled(false);
        rv_live_room.setLayoutManager(new GridLayoutManager(mContext, 2));
        rv_live_room.addItemDecoration(new SpaceItemDecoration(PhoneUtils.dip2px(mContext,5)));
        rv_live_room.setAdapter(adapter);


        adapter2 = new MyAdapter<LiveObj>(mContext, R.layout.live_room_item, pageSize) {
            @Override
            public void bindData(MyRecyclerViewHolder holder, int position,final LiveObj bean) {
                ImageView iv_live_room=holder.getImageView(R.id.iv_live_room);
                TextView tv_live_room_flag=holder.getTextView(R.id.tv_live_room_flag);
                TextView tv_live_room_name=holder.getTextView(R.id.tv_live_room_name);
                TextView tv_home_live_peoplenum=holder.getTextView(R.id.tv_home_live_peoplenum);

                if(TextUtils.isEmpty(bean.getChannel_name())){
                    iv_live_room.setOnClickListener(new MyOnClickListener() {
                        @Override
                        protected void onNoDoubleClick(View view) {
                            showMsg("该直播间未开播");
                        }
                    });
                    tv_live_room_flag.setText("未开播");
                    tv_live_room_name.setText("相玉直播间");
                    tv_home_live_peoplenum.setText("0人观看");
                }else{
                    iv_live_room.setOnClickListener(new MyOnClickListener() {
                        @Override
                        protected void onNoDoubleClick(View view) {
                            goLive(bean.getChannel_name(),bean.getChannel_address(),bean.getLive_user_id(),bean.getChannel_id());
                        }
                    });
                    tv_live_room_flag.setText("直播中");
                    tv_live_room_name.setText(bean.getChannel_name());
                    tv_home_live_peoplenum.setText(bean.getLive_num()+"人观看");
                }
            }
        };

        rv_live_room2.setNestedScrollingEnabled(false);
        rv_live_room2.setLayoutManager(new GridLayoutManager(mContext, 2));
        rv_live_room2.addItemDecoration(new SpaceItemDecoration(PhoneUtils.dip2px(mContext,5)));
        rv_live_room2.setAdapter(adapter2);

    }

    @Override
    protected void initData() {
        showProgress();
        getData(1,false);
    }

    @Override
    protected void onMyReStart() {
        super.onMyReStart();
        getData(1,false);
    }

    @Override
    protected void getData(int page, boolean isLoad) {
        super.getData(page, isLoad);
        Map<String,String> map=new HashMap<String,String>();
        map.put("status","1");
        map.put("pagesize","10");
        map.put("page",page+"");
        map.put("sign",getSign(map));
        ApiRequest.getLiveList(map, new MyCallBack<List<LiveObj>>(mContext,pl_load,pcfl) {
            @Override
            public void onSuccess(final List<LiveObj> list, int errorCode, String msg) {
                if(notEmpty(list)){
                    tv_live_room_flag.setText("直播中");
//                    tv_live_room_peoplenum.setText(list.get(0).getChannel_name());
                    tv_live_room_name.setText(list.get(0).getChannel_name());
                    iv_home_live.setOnClickListener(new MyOnClickListener() {
                        @Override
                        protected void onNoDoubleClick(View view) {
                            goLive(list.get(0).getChannel_name(),list.get(0).getChannel_address(),list.get(0).getLive_user_id(),list.get(0).getChannel_id());
                        }
                    });
                    if(list.size()<=5){
                        tv_live_room_flag2.setText("未开播");
                        tv_live_room_name2.setText("相玉直播间");
                        iv_live_room2.setOnClickListener(new MyOnClickListener() {
                            @Override
                            protected void onNoDoubleClick(View view) {
                                showMsg("该直播间未开播");
                            }
                        });
                    }else{
                        tv_live_room_flag2.setText("直播中");
                        tv_live_room_name2.setText(list.get(5).getChannel_name());
                        iv_live_room2.setOnClickListener(new MyOnClickListener() {
                            @Override
                            protected void onNoDoubleClick(View view) {
                                goLive(list.get(5).getChannel_name(),list.get(5).getChannel_address(),list.get(5).getLive_user_id(),list.get(5).getChannel_id());
                            }
                        });
                    }
                }else {
                    tv_live_room_flag.setText("未开播");
                    tv_live_room_name.setText("相玉直播间");
                    iv_home_live.setOnClickListener(new MyOnClickListener() {
                        @Override
                        protected void onNoDoubleClick(View view) {
                            showMsg("该直播间未开播");
                        }
                    });
                    tv_live_room_flag2.setText("未开播");
                    tv_live_room_name2.setText("相玉直播间");
                    iv_live_room2.setOnClickListener(new MyOnClickListener() {
                        @Override
                        protected void onNoDoubleClick(View view) {
                            showMsg("该直播间未开播");
                        }
                    });
                }
                for (int i = 0; i < 10; i++) {
                    list.add(new LiveObj());
                }
                List<LiveObj> liveObjs = list.subList(1, 5);
                List<LiveObj> liveObjs1 = list.subList(6, 10);
                adapter.setList(liveObjs,true);
                adapter2.setList(liveObjs1,true);
            }
        });
    }
    public void setLiveRoomPeopleNum(String liveId) {
        Map<String,String>map=new HashMap<String,String>();
        map.put("user_id",getHXName());
        map.put("type","1");
        map.put("channel_id",liveId);
        map.put("sign",getSign(map));
        NetApiRequest.setLiveRoomPeopleNum(map, new MyCallBack<BaseObj>(mContext) {
            @Override
            public void onSuccess(BaseObj obj, int errorCode, String msg) {

            }
        });
    }
    private void goLive(final String title,final String channel_address,final String liveId,final String groupId) {
        showLoading();
        Map<String,String>map=new HashMap<String,String>();
        map.put("channel_id",groupId);
        map.put("sign",getSign(map));
        NetApiRequest.getLiveStatus(map, new MyCallBack<List<LiveStatusObj>>(mContext) {
            @Override
            public void onSuccess(List<LiveStatusObj> list, int errorCode, String msg) {
                if(notEmpty(list)){
                    LiveStatusObj liveObj = list.get(0);
                    if(liveObj.getStatus()==1){
                        setLiveRoomPeopleNum(groupId);
                        Intent intent = new Intent();
                        intent.putExtra(IntentParam.title,title);
                        intent.putExtra(IntentParam.liveId,liveId);//主播id
                        intent.putExtra(IntentParam.groupId,groupId);//直播码，群组id
                        intent.putExtra(IntentParam.liveAddress,channel_address);
                        STActivity(intent, LiveRoomActivity.class);
                    }else{
                        showMsg("直播已关闭");
                        getData(1,false);
                    }

                }else{
                    showMsg("该直播不存在");
                    getData(1,false);
                }
            }
            @Override
            public void onError(Throwable e, boolean hiddenMsg) {
                super.onError(e, true);
                showMsg("直播已关闭");
                getData(1,false);
            }
        });

    }
    @Override
    protected void onViewClick(View v) {

    }
}
