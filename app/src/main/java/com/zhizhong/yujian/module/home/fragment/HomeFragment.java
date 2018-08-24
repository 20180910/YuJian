package com.zhizhong.yujian.module.home.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.github.androidtools.PhoneUtils;
import com.github.androidtools.inter.MyOnClickListener;
import com.github.baseclass.BaseDividerGridItem;
import com.github.baseclass.adapter.MyRecyclerViewHolder;
import com.github.fastshape.MyTextView;
import com.github.rxbus.MyConsumer;
import com.library.base.view.MyRecyclerView;
import com.zhizhong.yujian.IntentParam;
import com.zhizhong.yujian.R;
import com.zhizhong.yujian.adapter.GoodsAdapter;
import com.zhizhong.yujian.adapter.MyAdapter;
import com.zhizhong.yujian.base.BaseFragment;
import com.zhizhong.yujian.base.GlideUtils;
import com.zhizhong.yujian.base.MyCallBack;
import com.zhizhong.yujian.event.RefreshLivePeopleEvent;
import com.zhizhong.yujian.module.home.activity.AllTuiJianGoodsActivity;
import com.zhizhong.yujian.module.home.activity.LiveRoomActivity;
import com.zhizhong.yujian.module.home.activity.ZiXunDetailActivity;
import com.zhizhong.yujian.module.home.network.ApiRequest;
import com.zhizhong.yujian.module.home.network.response.LiveObj;
import com.zhizhong.yujian.module.home.network.response.ZiXunObj;
import com.zhizhong.yujian.network.response.GoodsObj;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * Created by Administrator on 2018/6/20.
 */

public class HomeFragment extends BaseFragment {
    @BindView(R.id.tv_home_live_flag)
    MyTextView tv_home_live_flag;
    @BindView(R.id.iv_home_live)
    ImageView iv_home_live;
    @BindView(R.id.iv_home_live2)
    ImageView iv_home_live2;
    @BindView(R.id.iv_home_live3)
    ImageView iv_home_live3;

    @BindView(R.id.tv_home_live_name)
    TextView tv_home_live_name;
    @BindView(R.id.tv_home_live_name2)
    TextView tv_home_live_name2;
    @BindView(R.id.tv_home_live_name3)
    TextView tv_home_live_name3;

    @BindView(R.id.tv_home_live_peoplenum)
    TextView tv_home_live_peoplenum;
    @BindView(R.id.tv_home_live_peoplenum2)
    TextView tv_home_live_peoplenum2;
    @BindView(R.id.tv_home_live_peoplenum3)
    TextView tv_home_live_peoplenum3;

    @BindView(R.id.tv_home_live_flag2)
    TextView tv_home_live_flag2;

    @BindView(R.id.tv_home_live_flag3)
    TextView tv_home_live_flag3;

    @BindView(R.id.rv_home_tuijian_goods)
    RecyclerView rv_home_tuijian_goods;

    @BindView(R.id.rv_home_zixun)
    MyRecyclerView rv_home_zixun;

    MyAdapter ziXunAdapter;

    GoodsAdapter tuiJianAdapter;

    @Override
    protected int getContentView() {
        return R.layout.home_frag;
    }

    public static HomeFragment newInstance() {
        Bundle args = new Bundle();

        HomeFragment fragment = new HomeFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    protected void initView() {
        tuiJianAdapter=new GoodsAdapter(mContext,R.layout.tuijian_goods_item,pageSize);

        rv_home_tuijian_goods.setNestedScrollingEnabled(false);
        rv_home_tuijian_goods.setLayoutManager(new GridLayoutManager(mContext,2));
        rv_home_tuijian_goods.addItemDecoration(new BaseDividerGridItem(mContext, PhoneUtils.dip2px(mContext,10),R.color.white));
        rv_home_tuijian_goods.setAdapter(tuiJianAdapter);



        ziXunAdapter=new MyAdapter<ZiXunObj>(mContext,R.layout.zixun_item,pageSize) {
            @Override
            public void bindData(MyRecyclerViewHolder holder, int position, final ZiXunObj bean) {
                ImageView iv_zixun = holder.getImageView(R.id.iv_zixun);
                GlideUtils.into(mContext,bean.getImage_url(),iv_zixun);

                holder.setText(R.id.tv_zixun_title,bean.getTitle());
                holder.setText(R.id.tv_zixun_time,bean.getAdd_time());

                holder.itemView.setOnClickListener(new MyOnClickListener() {
                    @Override
                    protected void onNoDoubleClick(View view) {
                        Intent intent=new Intent();
                        intent.putExtra(IntentParam.id,bean.getInformation_id());
                        STActivity(intent,ZiXunDetailActivity.class);
                    }
                });
            }
        };
        ziXunAdapter.setOnLoadMoreListener(this);

        rv_home_zixun.setAdapter(ziXunAdapter);

    }

    @Override
    protected void initData() {
        showProgress();
        getLive();
        getTuiJian();
        getData(1,false);
    }

    @Override
    protected void initRxBus() {
        super.initRxBus();
        getEvent(RefreshLivePeopleEvent.class, new MyConsumer<RefreshLivePeopleEvent>() {
            @Override
            public void onAccept(RefreshLivePeopleEvent event) {
                getLive();
            }
        });
    }

    private void getLive() {
        Map<String,String>map=new HashMap<String,String>();
        map.put("pagesize","3");
        map.put("page","1");
        map.put("status","1");
        map.put("sign",getSign(map));
        ApiRequest.getLiveList(map, new MyCallBack<List<LiveObj>>(mContext) {
            @Override
            public void onSuccess(final List<LiveObj> list, int errorCode, String msg) {
                if(notEmpty(list)){
                    if(list.size()>=3){
                        tv_home_live_flag.setText("直播中");
                        tv_home_live_peoplenum.setText(list.get(0).getLive_num()+"人观看");
                        tv_home_live_flag2.setText("直播中");
                        tv_home_live_peoplenum2.setText(list.get(1).getLive_num()+"人观看");

                        tv_home_live_flag3.setText("直播中");
                        tv_home_live_peoplenum3.setText(list.get(2).getLive_num()+"人观看");

                        tv_home_live_name.setText(list.get(0).getChannel_name());
                        tv_home_live_name2.setText(list.get(1).getChannel_name());
                        tv_home_live_name3.setText(list.get(2).getChannel_name());
                        getLiveImg(iv_home_live,list.get(0).getChannel_address());
                        getLiveImg(iv_home_live2,list.get(1).getChannel_address());
                        getLiveImg(iv_home_live3,list.get(2).getChannel_address());
                        iv_home_live.setOnClickListener(new MyOnClickListener() {
                            @Override
                            protected void onNoDoubleClick(View view) {
                                goLive(list.get(0).getChannel_name(),list.get(0).getChannel_address(),list.get(0).getLive_user_id(),list.get(0).getChannel_id());
                            }
                        });
                        iv_home_live2.setOnClickListener(new MyOnClickListener() {
                            @Override
                            protected void onNoDoubleClick(View view) {
                                goLive(list.get(1).getChannel_name(),list.get(1).getChannel_address(),list.get(1).getLive_user_id(),list.get(1).getChannel_id());
                            }
                        });
                        iv_home_live3.setOnClickListener(new MyOnClickListener() {
                            @Override
                            protected void onNoDoubleClick(View view) {
                                goLive(list.get(2).getChannel_name(),list.get(2).getChannel_address(),list.get(2).getLive_user_id(),list.get(2).getChannel_id());
                            }
                        });

                    }else if(list.size()>=2){
                        tv_home_live_flag.setText("直播中");
                        tv_home_live_peoplenum.setText(list.get(0).getLive_num()+"人观看");
                        tv_home_live_flag2.setText("直播中");
                        tv_home_live_peoplenum2.setText(list.get(1).getLive_num()+"人观看");
                        tv_home_live_flag3.setText("未开播");
                        tv_home_live_peoplenum3.setText("0人观看");
                        tv_home_live_name.setText(list.get(0).getChannel_name());
                        tv_home_live_name2.setText(list.get(1).getChannel_name());
                        tv_home_live_name3.setText("相玉直播间");
                        getLiveImg(iv_home_live,list.get(0).getChannel_address());
                        getLiveImg(iv_home_live2,list.get(1).getChannel_address());
                        iv_home_live.setOnClickListener(new MyOnClickListener() {
                            @Override
                            protected void onNoDoubleClick(View view) {
                                goLive(list.get(0).getChannel_name(),list.get(0).getChannel_address(),list.get(0).getLive_user_id(),list.get(0).getChannel_id());
                            }
                        });
                        iv_home_live2.setOnClickListener(new MyOnClickListener() {
                            @Override
                            protected void onNoDoubleClick(View view) {
                                goLive(list.get(1).getChannel_name(),list.get(1).getChannel_address(),list.get(1).getLive_user_id(),list.get(1).getChannel_id());
                            }
                        });
                        iv_home_live3.setOnClickListener(new MyOnClickListener() {
                            @Override
                            protected void onNoDoubleClick(View view) {
                                showMsg("该直播间未开播");
                            }
                        });
                    }else{
                        getLiveImg(iv_home_live,list.get(0).getChannel_address());
                        iv_home_live.setOnClickListener(new MyOnClickListener() {
                            @Override
                            protected void onNoDoubleClick(View view) {
                                goLive(list.get(0).getChannel_name(),list.get(0).getChannel_address(),list.get(0).getLive_user_id(),list.get(0).getChannel_id());
                            }
                        });
                        iv_home_live2.setOnClickListener(new MyOnClickListener() {
                            @Override
                            protected void onNoDoubleClick(View view) {
                                showMsg("该直播间未开播");
                            }
                        });
                        iv_home_live3.setOnClickListener(new MyOnClickListener() {
                            @Override
                            protected void onNoDoubleClick(View view) {
                                showMsg("该直播间未开播");
                            }
                        });
                        tv_home_live_flag.setText("直播中");
                        tv_home_live_peoplenum.setText(list.get(0).getLive_num()+"人观看");
                        tv_home_live_name.setText(list.get(0).getChannel_name());
                        tv_home_live_name2.setText("相玉直播间");
                        tv_home_live_name3.setText("相玉直播间");
                        tv_home_live_flag2.setText("未开播");
                        tv_home_live_peoplenum2.setText("0人观看");

                        tv_home_live_flag3.setText("未开播");
                        tv_home_live_peoplenum3.setText("0人观看");
                    }
                }else{
                    tv_home_live_flag.setText("未开播");
                    tv_home_live_flag2.setText("未开播");
                    tv_home_live_flag3.setText("未开播");
                    tv_home_live_name.setText("相玉直播间");
                    tv_home_live_name2.setText("相玉直播间");
                    tv_home_live_name3.setText("相玉直播间");

                    tv_home_live_peoplenum.setText("0人观看");
                    tv_home_live_peoplenum2.setText("0人观看");
                    tv_home_live_peoplenum3.setText("0人观看");

                    iv_home_live.setOnClickListener(new MyOnClickListener() {
                        @Override
                        protected void onNoDoubleClick(View view) {
                            showMsg("该直播间未开播");
                        }
                    });
                    iv_home_live2.setOnClickListener(new MyOnClickListener() {
                        @Override
                        protected void onNoDoubleClick(View view) {
                            showMsg("该直播间未开播");
                        }
                    });
                    iv_home_live3.setOnClickListener(new MyOnClickListener() {
                        @Override
                        protected void onNoDoubleClick(View view) {
                            showMsg("该直播间未开播");
                        }
                    });
                }
            }
        });
    }

    private void getLiveImg(final ImageView iv_home_live, String liveAddress) {
        //创建 player 对象
        /*final TXLivePlayer mLivePlayer = new TXLivePlayer(getActivity());
        //关键 player 对象与界面 view
        mLivePlayer.startPlay(liveAddress, TXLivePlayer.PLAY_TYPE_LIVE_FLV); //推荐 FLV
        mLivePlayer.snapshot(new TXLivePlayer.ITXSnapshotListener() {
            @Override
            public void onSnapshot(Bitmap bitmap) {
                Glide.with(mContext).load(bitmap).asBitmap().encoder(new BitmapEncoder(Bitmap.CompressFormat.PNG,90)).error(R.drawable.live_bg).into(iv_home_live);
                mLivePlayer.stopPlay(true); // true 代表清除最后一帧画面
            }
        });*/
    }

    private void goLive(String title,String channel_address,String live_user_id,String groupId) {
        setLiveRoomPeopleNum(groupId);
        Intent intent = new Intent();
        intent.putExtra(IntentParam.title,title);
        intent.putExtra(IntentParam.liveId,live_user_id);
        intent.putExtra(IntentParam.groupId,groupId);
        intent.putExtra(IntentParam.liveAddress,channel_address);
        STActivity(intent, LiveRoomActivity.class);
    }

    @Override
    protected void getOtherData() {
        super.getOtherData();
        getLive();
        getTuiJian();
    }
    public void getTuiJian(){
        Map<String,String> map=new HashMap<String,String>();
        map.put("user_id",getUserId());
        map.put("sign",getSign(map));
        ApiRequest.getHomeTuiJian(map, new MyCallBack<List<GoodsObj>>(mContext) {
            @Override
            public void onSuccess(List<GoodsObj> list, int errorCode, String msg) {
                tuiJianAdapter.setList(list,true);
            }
        });
    }
    @Override
    protected void getData(int page,final boolean isLoad) {
        super.getData(page, isLoad);
        Map<String,String>map=new HashMap<String,String>();
        map.put("pagesize",pagesize+"");
        map.put("page",page+"");
        map.put("sign",getSign(map));
        ApiRequest.getHomeZiXun(map, new MyCallBack<List<ZiXunObj>>(mContext,pl_load,pcfl) {
            @Override
            public void onSuccess(List<ZiXunObj> list, int errorCode, String msg) {
                if(isLoad){
                    pageNum++;
                    ziXunAdapter.addList(list,true);
                }else{
                    pageNum=2;
                    ziXunAdapter.setList(list,true);
                }
            }
        });


    }

    @OnClick({R.id.tv_home_more, R.id.ib_home_top})
    public void onViewClick(View view) {
        switch (view.getId()) {
            case R.id.tv_home_more:
                STActivity(AllTuiJianGoodsActivity.class);
                break;
            case R.id.ib_home_top:
                nsv.scrollTo(0,0);
//                STActivity(TestAct.class);
                break;
        }
    }
}