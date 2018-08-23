package com.zhizhong.yujian.module.home.activity;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.Display;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;

import com.github.androidtools.PhoneUtils;
import com.github.androidtools.SPUtils;
import com.github.androidtools.inter.MyOnClickListener;
import com.github.baseclass.adapter.MyRecyclerViewHolder;
import com.google.gson.Gson;
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
import com.zhizhong.yujian.network.NetApiRequest;
import com.zhizhong.yujian.view.TextMsgInputDialog;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;

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

    MyAdapter adapter;

    private String liveAddress;
    private String liveId;
    private String groupId;
    private TXLivePlayer mLivePlayer;
    private boolean loginResult;
    private TIMConversation conversation;
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
        String title = getIntent().getStringExtra(IntentParam.title);
        setAppTitle(title);
        if(TextUtils.isEmpty(liveAddress)){
            showMsg("该地址无法观看直播");
            finish();
        }


        loginIM();

        adapter=new MyAdapter<ChatBean>(mContext,R.layout.live_room_chat_item,pageSize) {
            @Override
            public void bindData(MyRecyclerViewHolder holder, int position, ChatBean bean) {
                holder.setText(R.id.tv_live_room_chat_name,bean.name);
                holder.setText(R.id.tv_live_room_chat_content,bean.content);
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
                }
            }
            @Override
            public void onNetStatus(Bundle bundle) {
            }
        });

        showContent();
    }

    protected void onViewClick(View v) {
        switch (v.getId()){
                /*if(TextUtils.isEmpty(getSStr(et_live_room_content))){
                    showMsg("请输入文字内容");
                    return;
                }
                send(getSStr(et_live_room_content));*/
        }
    }

    private void send(String content) {
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
        chatBean.content=content;
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
        mView.performLongClick();
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
                            }
                            adapter.getList().add(chatBean);
                            adapter.notifyDataSetChanged();
                            rv_live_room_chat.scrollToPosition(adapter.getItemCount()-1);
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
