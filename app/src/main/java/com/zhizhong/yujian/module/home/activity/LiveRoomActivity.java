package com.zhizhong.yujian.module.home.activity;

import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.Display;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.TextView;

import com.github.androidtools.PhoneUtils;
import com.github.androidtools.SPUtils;
import com.github.androidtools.inter.MyOnClickListener;
import com.github.baseclass.adapter.MyRecyclerViewHolder;
import com.github.fastshape.FlowLayout;
import com.github.fastshape.MyTextView;
import com.github.mydialog.MyDialog;
import com.github.mydialog.MySimpleDialog;
import com.google.gson.Gson;
import com.library.base.BaseObj;
import com.tencent.imsdk.TIMCallBack;
import com.tencent.imsdk.TIMConversation;
import com.tencent.imsdk.TIMConversationType;
import com.tencent.imsdk.TIMElem;
import com.tencent.imsdk.TIMElemType;
import com.tencent.imsdk.TIMFriendshipManager;
import com.tencent.imsdk.TIMGroupManager;
import com.tencent.imsdk.TIMManager;
import com.tencent.imsdk.TIMMessage;
import com.tencent.imsdk.TIMMessageListener;
import com.tencent.imsdk.TIMTextElem;
import com.tencent.imsdk.TIMValueCallBack;
import com.tencent.rtmp.ITXLivePlayListener;
import com.tencent.rtmp.TXLiveConstants;
import com.tencent.rtmp.TXLivePlayer;
import com.tencent.rtmp.ui.TXCloudVideoView;
import com.zhizhong.yujian.AppXml;
import com.zhizhong.yujian.IntentParam;
import com.zhizhong.yujian.R;
import com.zhizhong.yujian.adapter.MyAdapter;
import com.zhizhong.yujian.base.BaseActivity;
import com.zhizhong.yujian.base.MyCallBack;
import com.zhizhong.yujian.module.home.bean.ChatBean;
import com.zhizhong.yujian.module.my.activity.LoginActivity;
import com.zhizhong.yujian.network.NetApiRequest;
import com.zhizhong.yujian.network.response.LiveRoomPeopleNumObj;
import com.zhizhong.yujian.view.TextMsgInputDialog;

import org.reactivestreams.Subscription;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import butterknife.BindView;
import butterknife.OnClick;
import io.reactivex.Flowable;
import io.reactivex.FlowableSubscriber;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.annotations.NonNull;
import io.reactivex.schedulers.Schedulers;

public class LiveRoomActivity extends BaseActivity {
    @BindView(R.id.video_view)
    TXCloudVideoView mView;
//    @BindView(R.id.et_live_room_content)
//    EditText et_live_room_content;

//    @BindView(R.id.tv_live_room_send)
//    MyTextView tv_live_room_send;

    @BindView(R.id.rv_live_room_chat)
    RecyclerView rv_live_room_chat;

    @BindView(R.id.bt_live_room_send)
    Button bt_live_room_send;

    @BindView(R.id.tv_live_room_people_num)
    TextView tv_live_room_people_num;

    List<Double>priceList=new ArrayList<>();

    MyAdapter adapter;

    private String liveAddress;
    private String liveId;//主播id
    private String groupId;//直播码id，群组id
    private TXLivePlayer mLivePlayer;
    private boolean loginResult;
    private TIMConversation conversation;
    private String title;
    private MySimpleDialog dialog;
    //    private String nickName;

    @Override
    protected int getContentView() {
        return R.layout.live_room_act;
    }

    @Override
    protected void initView() {
        showProgress();
        liveId = getIntent().getStringExtra(IntentParam.liveId);
        groupId = getIntent().getStringExtra(IntentParam.groupId);
        liveAddress = getIntent().getStringExtra(IntentParam.liveAddress);
        title = getIntent().getStringExtra(IntentParam.title);
        setAppTitle(title);
        if(TextUtils.isEmpty(liveAddress)){
            showMsg("该地址无法观看直播");
            finish();
        }


        loginIM();


        adapter=new MyAdapter<ChatBean>(mContext,R.layout.live_room_chat_item,pageSize) {
            @Override
            public void bindData(MyRecyclerViewHolder holder, int position, ChatBean bean) {
                TextView tv_live_room_chat_name = holder.getTextView(R.id.tv_live_room_chat_name);
                TextView tv_live_room_chat_content = holder.getTextView(R.id.tv_live_room_chat_content);
                if(TextUtils.isEmpty(bean.daShang)){
                    tv_live_room_chat_name.setText(bean.name);
                    tv_live_room_chat_content.setText(bean.content);
                    tv_live_room_chat_content.setVisibility(View.VISIBLE);
                }else{
                    tv_live_room_chat_name.setText(bean.name+"  "+bean.daShang);
                    tv_live_room_chat_content.setText("");
                    tv_live_room_chat_content.setVisibility(View.GONE);
                }
            }
        };
        List<ChatBean>list=new ArrayList<>();
        adapter.setList(list);
        rv_live_room_chat.setLayoutManager(new LinearLayoutManager(mContext));
        rv_live_room_chat.addItemDecoration(getItemDivider(PhoneUtils.dip2px(mContext,3), R.color.transparent) );
        rv_live_room_chat.setAdapter(adapter);

        bt_live_room_send.setOnClickListener(new MyOnClickListener() {
            @Override
            protected void onNoDoubleClick(View view) {
                showInputMsgDialog();
            }
        });

        intervalRequest();
    }
    Subscription subscription;
    private void intervalRequest() {
        Flowable.interval(30, TimeUnit.SECONDS)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new FlowableSubscriber<Long>() {
                    @Override
                    public void onSubscribe(@NonNull Subscription s) {
                        subscription = s;
                        s.request(Long.MAX_VALUE);
                    }
                    @Override
                    public void onNext(Long aLong) {
                        getPeopleNum();
                    }
                    @Override
                    public void onError(Throwable t) {
                    }
                    @Override
                    public void onComplete() {
                    }
                });
        addSubscription(subscription);
    }

    public void getPeopleNum(){
        Map<String,String>map=new HashMap<String,String>();
        map.put("channel_id",groupId);
        map.put("sign",getSign(map));
        NetApiRequest.getLiveRoomPeopleNum(map, new MyCallBack<LiveRoomPeopleNumObj>(mContext) {
            @Override
            public void onSuccess(LiveRoomPeopleNumObj obj, int errorCode, String msg) {
                tv_live_room_people_num.setText(obj.getNum()+"人观看");
            }
        });

    }

    private TextMsgInputDialog mTextMsgInputDialog;
    private void showInputMsgDialog() {
        if(mTextMsgInputDialog==null){
            mTextMsgInputDialog = new TextMsgInputDialog(this, R.style.InputDialog);
            mTextMsgInputDialog.setmOnTextSendListener(new TextMsgInputDialog.OnTextSendListener() {
                @Override
                public void onTextSend(String msg, boolean tanmuOpen) {
                    if(TextUtils.isEmpty(msg)){
                        showMsg("请输入文字内容");
                        return;
                    }
                    send(msg);
                }
            });
        }
        WindowManager windowManager = getWindowManager();
        Display display = windowManager.getDefaultDisplay();
        WindowManager.LayoutParams lp = mTextMsgInputDialog.getWindow().getAttributes();

        lp.width = (display.getWidth()); //设置宽度
        mTextMsgInputDialog.getWindow().setAttributes(lp);
        mTextMsgInputDialog.setCancelable(true);
        mTextMsgInputDialog.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_VISIBLE);
        mTextMsgInputDialog.show();
    }
    @Override
    protected void initData() {
        //创建 player 对象
        mLivePlayer = new TXLivePlayer(this);
        //关键 player 对象与界面 view
        mLivePlayer.setPlayerView(mView);
        mLivePlayer.startPlay(liveAddress, TXLivePlayer.PLAY_TYPE_LIVE_FLV); //推荐 FLV
        mLivePlayer.setPlayListener(new ITXLivePlayListener() {
            @Override
            public void onPlayEvent(int event, Bundle bundle) {
                if (event == TXLiveConstants.PLAY_ERR_NET_DISCONNECT) {
                    showMsg("直播断连");
                    finish();
//                    roomListenerCallback.onDebugLog("[AnswerRoom] 拉流失败：网络断开");
//                    roomListenerCallback.onError(-1, "网络断开，拉流失败");
                }else if(event == TXLiveConstants.PLAY_EVT_PLAY_END){
                    showMsg("直播结束");
                    finish();
                }else if(event == TXLiveConstants.PLAY_EVT_CONNECT_SUCC){
                    Log("===PLAY_EVT_CONNECT_SUCC"+event);
                    setLiveRoomPeopleNum(groupId);
                    showContent();
                    getPeopleNum();
                }
            }
            @Override
            public void onNetStatus(Bundle bundle) {
            }
        });

//        showContent();
    }
    @OnClick({R.id.tv_live_room_shang})
    protected void onViewClick(View v) {
        switch (v.getId()){
               case R.id.tv_live_room_shang:
                   if(noLogin()){
                       STActivity(LoginActivity.class);
                   }else{
                       priceList.clear();
                       priceList.add(500.0);
                       priceList.add(200.0);
                       priceList.add(100.0);
                       priceList.add(50.0);
                       priceList.add(20.0);
                       priceList.add(10.0);
                       priceList.add(5.0);
                       priceList.add(2.0);
                       priceList.add(1.0);
                       priceList.add(0.5);
                       priceList.add(0.2);
                       priceList.add(0.1);
                       showDialog();
                   }
                   break;
        }
    }

    private void showDialog() {
        dialog = new MySimpleDialog(mContext);
        View dialogView = getLayoutInflater().inflate(R.layout.live_room_popu, null);
        FlowLayout fl_live_room=dialogView.findViewById(R.id.fl_live_room);
        fl_live_room.removeAllViews();
        for (int i = 0; i < priceList.size(); i++) {
            final MyTextView myTextView=new MyTextView(mContext);
            myTextView.setText("¥"+priceList.get(i));
            FlowLayout.LayoutParams layoutParams = new FlowLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
            layoutParams.height=PhoneUtils.dip2px(mContext,30);
            layoutParams.setMargins(0,0,PhoneUtils.dip2px(mContext,10),PhoneUtils.dip2px(mContext,10));
            myTextView.setLayoutParams(layoutParams);
            myTextView.setPadding(PhoneUtils.dip2px(mContext,15),0,PhoneUtils.dip2px(mContext,15),0);
            myTextView.getViewHelper().setSolidColor(ContextCompat.getColor(mContext,R.color.c_press)).setRadius(PhoneUtils.dip2px(mContext,15));
            myTextView.setGravity(Gravity.CENTER);
            myTextView.setTextColor(ContextCompat.getColor(mContext,R.color.green));
            myTextView.complete();
            myTextView.setTag(priceList.get(i));
            myTextView.setOnClickListener(new MyOnClickListener() {
                @Override
                protected void onNoDoubleClick(View view) {
                    MyDialog.Builder mDialog=new MyDialog.Builder(mContext);
                    mDialog.setMessage("确认打赏"+myTextView.getTag()+"元?");
                    mDialog.setNegativeButton(new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.dismiss();
                        }
                    });
                    mDialog.setPositiveButton(new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.dismiss();
                            daShang((double)myTextView.getTag());
                        }
                    });
                    mDialog.create().show();
                }
            });
            fl_live_room.addView(myTextView);
        }
        dialog.setFullWidth();
        dialog.setGravity(Gravity.BOTTOM);
        dialog.setContentView(dialogView);
        dialog.show();
    }
    private void daShang(final double price) {
        showLoading();
        Map<String,String>map=new HashMap<String,String>();
        map.put("user_id",getUserId());
        map.put("live_user_id",liveId);
        map.put("amount",price+"");
        map.put("sign",getSign(map));
        NetApiRequest.liveRoomDaShang(map, new MyCallBack<BaseObj>(mContext) {
            @Override
            public void onSuccess(BaseObj obj, int errorCode, String msg) {
                if(dialog!=null){
                    dialog.dismiss();
                }
                send("打赏¥"+price+"元",true);
            }
        });

    }

    private void send(String content) {
        send(content,false);
    }
    private void send(String content,boolean isDaShang) {
        if(conversation==null){
            //获取群聊会话
            //会话类型：群组
            //群组 ID
            conversation = TIMManager.getInstance().getConversation(
                    TIMConversationType.Group,      //会话类型：群组
                    groupId);
            //构造一条消息
        }

        TIMMessage msg = new TIMMessage();
//添加文本内容
        TIMTextElem elem = new TIMTextElem();
        final ChatBean chatBean=new ChatBean();
        chatBean.groupId=groupId;
        if(isDaShang){
            chatBean.daShang=content;
        }else{
            chatBean.content=content;
        }
        Calendar instance = Calendar.getInstance();
        chatBean.name=getNickName() +"  "+ instance.get(Calendar.HOUR_OF_DAY)+":"+instance.get(Calendar.MINUTE);
        String json = new Gson().toJson(chatBean);
        Log("==="+json);
        elem.setText(json);
//将elem添加到消息
        if(msg.addElement(elem) != 0) {
            Log("@@@addElement failed");
            return;
        }
//发送消息@
        conversation.sendMessage(msg, new TIMValueCallBack<TIMMessage>() {//发送消息回调
            @Override
            public void onError(int code, String desc) {//发送消息失败
                //错误码 code 和错误描述 desc，可用于定位请求失败原因
                //错误码 code 含义请参见错误码表
                Log("@@@send message failed. code: " + code + " errmsg: " + desc);
                showMsg("发送失败");
            }
            @Override
            public void onSuccess(TIMMessage msg) {//发送消息成功
                adapter.getList().add(chatBean);
                adapter.notifyDataSetChanged();
                rv_live_room_chat.scrollToPosition(adapter.getItemCount()-1);
                Log("@@@SendMsg ok");
//                et_live_room_content.setText(null);
            }
        });

    }

    @Override
    public void onDestroy() {
        setLiveRoomPeopleNum();
        super.onDestroy();
        mLivePlayer.stopPlay(true); // true 代表清除最后一帧画面
        mView.onDestroy();
    }
    public String  getNickName(){
        String nickName;
        if(noLogin()){
            nickName= "游客" + getRnd();
        }else{
            nickName = SPUtils.getString(mContext, AppXml.nick_name, "游客" + getRnd());
        }
        if(TextUtils.isEmpty(nickName)){
            nickName ="游客" + getRnd();
        }
        Log("==="+ nickName);
        return nickName;
    }
    public void loginIM(){
        Map<String,String> map=new HashMap<String,String>();
        Log("11==="+getHXName());
        map.put("user_name",getHXName());
        map.put("sign",getSign(map));
        NetApiRequest.getUserSig(map, new MyCallBack<String>(mContext) {
            @Override
            public void onSuccess(String userSig, int errorCode, String msg) {
                Log("==="+userSig);
                addMessageListener();
                String name="88885502";
                String uisg="eJxlj0FPgzAAhe-8CsLZSClU0cSTwEI6wjaYZl4Ig0KqW8G2IM3if9exJTbxXb8v7*WdDNM0rXyZ3ZZV1Q1MFlL1xDIfTQtYN3*w72ldlLJwef0PkqmnnBRlIwmfoYMQggDoDq0Jk7ShV8P-DUIAaoaoP4p55lLhAeDAOwiQrtB2hkm4fY7XwSGOErwQ9v0geBAisXnPgtx*Xb2MZLnAbZhWe5xiRg5iHbfZBOTwGewi2EXY3vhcqkxMKmfjTjbHvQpSRZOv7dtKJE-apKRHcv3ker774ENPoyPhgnZsFiBwkANdcI5lfBs-tfReZA__";
                TIMManager.getInstance().login(getHXName(),userSig, new TIMCallBack() {
                    @Override
                    public void onError(int code, String desc) {
                        loginResult=false;
                        //错误码 code 和错误描述 desc，可用于定位请求失败原因
                        //错误码 code 列表请参见错误码表
                        Log("==="+"login failed. code: " + code + " errmsg: " + desc);
                        dismissLoading();
                        showMsg("登录聊天室失败");
                    }
                    @Override
                    public void onSuccess() {
                        loginResult = true;
                        setNickName();
                        //构造一条消息
                        Log("==="+"login succ");
                    }
                });
            }
            @Override
            public void onError(Throwable e, boolean hiddenMsg) {
                super.onError(e, true);
                dismissLoading();
                showMsg("登录聊天室失败");
            }
        });
    }

    private void setNickName() {
        //初始化参数，修改昵称为“cat”
        TIMFriendshipManager.ModifyUserProfileParam param = new TIMFriendshipManager.ModifyUserProfileParam();
        param.setNickname(getNickName());

        TIMFriendshipManager.getInstance().modifyProfile(param, new TIMCallBack() {
            @Override
            public void onError(int code, String desc) {
                //错误码 code 和错误描述 desc，可用于定位请求失败原因
                //错误码 code 列表请参见错误码表
                Log( "modifyProfile failed: " + code + " desc" + desc);
                joinGroup();
            }
            @Override
            public void onSuccess() {
                Log("setNickName-onSuccess");
                joinGroup();
            }
        });
    }

    private void joinGroup() {
        Log("==="+groupId);
        TIMGroupManager.getInstance().applyJoinGroup(groupId, "申请加群", new TIMCallBack() {
            public void onError(int code, String desc) {
                //接口返回了错误码 code 和错误描述 desc，可用于原因
                //错误码 code 列表请参见错误码表
                Log("disconnected"+code);
            }
            public void onSuccess() {
                Log("join group");
            }
        });
    }

    private void addMessageListener() {
        TIMManager.getInstance().addMessageListener(new TIMMessageListener() {//消息监听器
            @Override
            public boolean onNewMessages(List<TIMMessage> msgs) {//收到新消息
                //消息的内容解析请参考消息收发文档中的消息解析说明
                for (int k = 0; k < msgs.size(); k++) {
                    TIMMessage timMessage = msgs.get(k);
                    for(int i = 0; i < timMessage.getElementCount(); ++i) {
                        TIMElem elem = timMessage.getElement(i);
                        //获取当前元素的类型
                        TIMElemType elemType = elem.getType();

                        TIMElem nextElement = timMessage.getElement(i);
                        if (elemType == TIMElemType.Text) {
                            TIMTextElem textElem = (TIMTextElem) nextElement;
                            String text = textElem.getText();
                            ChatBean chatBean;
                            try{
                                chatBean = new Gson().fromJson(text, ChatBean.class);
                            }catch (Exception e){
                                chatBean=new ChatBean();
                                Calendar instance = Calendar.getInstance();
                                chatBean.name="系统消息"+"  "+ instance.get(Calendar.HOUR_OF_DAY)+":"+instance.get(Calendar.MINUTE);;
                                chatBean.content=text;
                                chatBean.groupId=groupId;
                            }
                            if(groupId.equals(chatBean.groupId)){
                                adapter.getList().add(chatBean);
                                adapter.notifyDataSetChanged();
                                rv_live_room_chat.scrollToPosition(adapter.getItemCount()-1);
                            }
                            Log("==="+chatBean.content);
                            //处理文本消息
                        } else if (elemType == TIMElemType.Image) {
                            //处理图片消息
                        }//...处理更多消息
                    }
                }
                return false; //返回true将终止回调链，不再调用下一个新消息监听器
            }
        });
    }
}
