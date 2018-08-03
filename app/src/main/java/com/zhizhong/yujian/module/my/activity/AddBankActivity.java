package com.zhizhong.yujian.module.my.activity;

import android.support.v4.content.ContextCompat;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.bigkoo.pickerview.OptionsPickerView;
import com.github.androidtools.PhoneUtils;
import com.github.androidtools.inter.MyOnClickListener;
import com.github.mydialog.MySimpleDialog;
import com.library.base.BaseObj;
import com.library.base.view.richedit.TheEditor;
import com.zhizhong.yujian.BuildConfig;
import com.zhizhong.yujian.R;
import com.zhizhong.yujian.base.BaseActivity;
import com.zhizhong.yujian.base.GlideUtils;
import com.zhizhong.yujian.base.MyCallBack;
import com.zhizhong.yujian.module.my.network.ApiRequest;
import com.zhizhong.yujian.module.my.network.response.BankListObj;
import com.zhizhong.yujian.module.my.network.response.KaiHuHangObj;
import com.zhizhong.yujian.view.MyEditText;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.OnClick;

public class AddBankActivity extends BaseActivity {
    @BindView(R.id.iv_add_bank_icon)
    ImageView iv_add_bank_icon;
    @BindView(R.id.tv_add_bank_name)
    TextView tv_add_bank_name;
    @BindView(R.id.et_add_bank_zhihang)
    MyEditText et_add_bank_zhihang;
    @BindView(R.id.et_add_bank_card)
    MyEditText et_add_bank_card;
    @BindView(R.id.et_add_bank_username)
    MyEditText et_add_bank_username;


    private String bankId;
    @Override
    protected int getContentView() {
        setAppTitle("添加银行卡");
        return R.layout.add_bank_act;
    }

    @Override
    protected void initView() {

    }

    @Override
    protected void initData() {

    }
    @OnClick({R.id.tv_add_bank_name, R.id.tv_add_bank_save,R.id.ib_add_bank_shuoming,R.id.app_title})
    public void onViewClick(View view) {
        switch (view.getId()) {
            case R.id.app_title:
                if(BuildConfig.DEBUG){
                    et_add_bank_card.setText("6217880800008464499");
                }
                break;
            case R.id.ib_add_bank_shuoming:
                showShuoMing();
                break;
            case R.id.tv_add_bank_name:
                getBankList();
                break;
            case R.id.tv_add_bank_save:

                if(TextUtils.isEmpty(bankId)){
                    showMsg("请选择银行");
                    return;
                }else if(TextUtils.isEmpty(getSStr(et_add_bank_zhihang))){
                    showMsg("请输入支行名");
                    return;
                }else if(TextUtils.isEmpty(getSStr(et_add_bank_card))){
                    showMsg("请输入银行卡号");
                    return;
                }else if(TextUtils.isEmpty(getSStr(et_add_bank_username))){
                    showMsg("请输入姓名");
                    return;
                }
                save(getSStr(et_add_bank_zhihang),getSStr(et_add_bank_card),getSStr(et_add_bank_username));
                break;
        }
    }

    private void showShuoMing() {
        showLoading();
        Map<String,String>map=new HashMap<String,String>();
        map.put("rnd",getRnd());
        map.put("sign",getSign(map));
        ApiRequest.getKaiHuHangShuoMing(map, new MyCallBack<KaiHuHangObj>(mContext) {
            @Override
            public void onSuccess(KaiHuHangObj obj, int errorCode, String msg) {

                final MySimpleDialog dialog=new MySimpleDialog(mContext);
                dialog.setWidth(PhoneUtils.getScreenWidth(mContext)*3/5);
                View view = getLayoutInflater().inflate(R.layout.add_bank_msg_popu, null);

                TextView tv_add_bank_chaxun_title = view.findViewById(R.id.tv_add_bank_chaxun_title);
                tv_add_bank_chaxun_title.setText(obj.getTitle());
                TheEditor te_add_bank_chaxun_content = view.findViewById(R.id.te_add_bank_chaxun_content);
                te_add_bank_chaxun_content.setInputEnabled(false);
                te_add_bank_chaxun_content.setEditorFontSize(13);
                te_add_bank_chaxun_content.setEditorFontColor(ContextCompat.getColor(mContext,R.color.gray_66));
                te_add_bank_chaxun_content.setHtml(obj.getContent());
                view.findViewById(R.id.tv_add_bank_chaxun_known).setOnClickListener(new MyOnClickListener() {
                    @Override
                    protected void onNoDoubleClick(View view) {
                        dialog.dismiss();
                    }
                });
                dialog.setCanceledOnTouchOutside(true);
                dialog.setContentView(view);
                dialog.show();
            }
        });
    }

    private void save(String zhihang, String card, String username) {
        showLoading();
        Map<String,String>map=new HashMap<String,String>();
        map.put("user_id",getUserId());
        map.put("realname",username);
        map.put("bank_card_num",card);
        map.put("opening_bank",zhihang);
        map.put("bank_id",bankId);
        map.put("sign",getSign(map));
        ApiRequest.addBankAccount(map, new MyCallBack<BaseObj>(mContext) {
            @Override
            public void onSuccess(BaseObj obj, int errorCode, String msg) {
                showMsg(msg);
                setResult(RESULT_OK);
                finish();
            }
        });

    }

    private void getBankList() {
        showLoading();
        Map<String,String> map=new HashMap<String,String>();
        map.put("rnd",getRnd());
        map.put("sign",getSign(map));
        ApiRequest.getBankList(map, new MyCallBack<List<BankListObj>>(mContext) {
            @Override
            public void onSuccess(List<BankListObj> list, int errorCode, String msg) {
                showBank(list);
            }
        });

    }

    private void showBank(final List<BankListObj> list) {
        List<String>strList=new ArrayList<>();
        for (int i = 0; i < list.size(); i++) {
            strList.add(list.get(i).getBank_name());
        }
        OptionsPickerView pickerView=new OptionsPickerView.Builder(mContext, new OptionsPickerView.OnOptionsSelectListener() {
            @Override
            public void onOptionsSelect(int options1, int options2, int options3, View v) {
                BankListObj bankListObj = list.get(options1);
                GlideUtils.into(mContext,bankListObj.getImage_url(),iv_add_bank_icon);
                iv_add_bank_icon.setVisibility(View.VISIBLE);
                tv_add_bank_name.setText(bankListObj.getBank_name());
                bankId =bankListObj.getBank_id();
            }
        }).build();
        pickerView.setPicker(strList);
        pickerView.show();

    }
}
