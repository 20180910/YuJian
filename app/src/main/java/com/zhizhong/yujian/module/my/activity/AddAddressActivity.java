package com.zhizhong.yujian.module.my.activity;

import android.text.TextUtils;
import android.view.View;
import android.widget.TextView;

import com.bigkoo.pickerview.OptionsPickerView;
import com.github.androidtools.PhoneUtils;
import com.github.fastshape.MyEditText;
import com.github.rxbus.rxjava.MyFlowableSubscriber;
import com.github.rxbus.rxjava.Rx;
import com.library.base.BaseObj;
import com.suke.widget.SwitchButton;
import com.zhizhong.yujian.IntentParam;
import com.zhizhong.yujian.R;
import com.zhizhong.yujian.base.BaseActivity;
import com.zhizhong.yujian.base.MyCallBack;
import com.zhizhong.yujian.module.my.network.ApiRequest;
import com.zhizhong.yujian.module.my.network.response.AddressObj;
import com.zhizhong.yujian.network.NetApiRequest;
import com.zhizhong.yujian.network.response.CityObj;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.OnClick;
import io.reactivex.FlowableEmitter;
import io.reactivex.annotations.NonNull;

public class AddAddressActivity extends BaseActivity {
    @BindView(R.id.et_editaddress_name)
    MyEditText et_editaddress_name;
    @BindView(R.id.et_editaddress_phone)
    MyEditText et_editaddress_phone;
    @BindView(R.id.tv_editaddress_area)
    TextView tv_editaddress_area;
    @BindView(R.id.et_editaddress_detail)
    MyEditText et_editaddress_detail;
    @BindView(R.id.sb_address_default)
    SwitchButton sb_address_default;
    String province,city,area;
    private AddressObj addressObj;

    @Override
    protected int getContentView() {
        setAppTitle("添加新地址");
        return R.layout.add_address_act;
    }

    @Override
    protected void initView() {
        addressObj = (AddressObj) getIntent().getSerializableExtra(IntentParam.addressBean);
        if(addressObj!=null){
            province=addressObj.getProvince();
            city=addressObj.getCity();
            area=addressObj.getArea();
            sb_address_default.setChecked(addressObj.getIs_default()==1?true:false);
            et_editaddress_name.setText(addressObj.getRecipient());
            et_editaddress_phone.setText(addressObj.getPhone());
            tv_editaddress_area.setText(province+city+area);
            et_editaddress_detail.setText(addressObj.getShipping_address_details());
        }
    }

    @Override
    protected void initData() {
        getAllArea(false);
    }
    private List<String> options1Items = new ArrayList<>();
    private List<List<String>> options2Items = new ArrayList<>();
    private List<List<List<String>>> options3Items = new ArrayList<>();
    private void getAllArea(final boolean isShow) {
        if (isShow&&notEmpty(options1Items)) {
            showArea();
        } else {
            Rx.start(new MyFlowableSubscriber<List<CityObj>>() {
                @Override
                public void subscribe(@NonNull final FlowableEmitter<List<CityObj>> emitter) {
                    Map<String, String> map = new HashMap<String, String>();
                    map.put("rnd", getRnd());
                    map.put("sign", getSign(map));
                    NetApiRequest.getAllArea(map, new MyCallBack<List<CityObj>>(mContext) {
                        @Override
                        public void onSuccess(List<CityObj> list, int errorCode, String msg) {
                            cityList = list;
                            emitter.onNext(list);
                            for (int i = 0; i < cityList.size(); i++) {
                                options1Items.add(cityList.get(i).getTitle());

                                List<String> item2 = new ArrayList<>();
                                List<List<String>> item3 = new ArrayList<>();

                                for (int j = 0; j < cityList.get(i).getPca_list().size(); j++) {
                                    item2.add(cityList.get(i).getPca_list().get(j).getTitle());
                                    List<String> stringList = new ArrayList<>();
                                    for (int k = 0; k < cityList.get(i).getPca_list().get(j).getPca_list().size(); k++) {
                                        stringList.add(cityList.get(i).getPca_list().get(j).getPca_list().get(k).getTitle());
                                    }
                                    item3.add(stringList);
                                }
                                options2Items.add(item2);
                                options3Items.add(item3);
                            }
                            emitter.onComplete();
                        }

                        @Override
                        public void onError(Throwable e) {
                            super.onError(e);
                            emitter.onError(e);
                        }
                    });
                }

                @Override
                public void onNext(List<CityObj> list) {
                    if (isShow) {
                        showArea();
                    }
                }
            });
        }
    }
    private List<CityObj> cityList;
    private void showArea() {

        PhoneUtils.hiddenKeyBoard(mContext);

        //条件选择器
        OptionsPickerView pvOptions = new OptionsPickerView.Builder(mContext, new OptionsPickerView.OnOptionsSelectListener() {


            @Override
            public void onOptionsSelect(int options1, int option2, int options3, View v) {
                Log("===" + options1 + "===" + option2 + "===" + options3);
                 province = cityList.get(options1).getTitle();
                 city = cityList.get(options1).getPca_list().get(option2).getTitle();
                 area = cityList.get(options1).getPca_list().get(option2).getPca_list().get(options3).getTitle();

                tv_editaddress_area.setText( province+""+city+""+area );
            }
        }).build();
        pvOptions.setPicker(options1Items, options2Items, options3Items);

        pvOptions.show();

    }
    @OnClick({  R.id.tv_editaddress_commit,R.id.tv_editaddress_area})
    public void onViewClick(View view) {
        switch (view.getId()) {
            case R.id.tv_editaddress_area:
                getAllArea(true);
                break;
            case R.id.tv_editaddress_commit:
                String name = getSStr(et_editaddress_name);
                String phone =getSStr(et_editaddress_phone);
                String area =getSStr(tv_editaddress_area);
                String detail =getSStr(et_editaddress_detail);
                if(TextUtils.isEmpty(name)){
                    showMsg("请输入收货人");
                    return;
                }else if(TextUtils.isEmpty(phone)){
                    showMsg("请输入联系电话");
                    return;
                }else if(TextUtils.isEmpty(area)){
                    showMsg("请选择省市区");
                    return;
                }else if(TextUtils.isEmpty(detail)){
                    showMsg("请输入详细地址");
                    return;
                }
                addAddress(name,phone,detail);
                break;
        }
    }

    private void addAddress(String name, String phone, String detail) {

        showLoading();
        Map<String,String> map=new HashMap<String,String>();
        map.put("user_id",getUserId());
        map.put("address_id",addressObj==null?"0":addressObj.getAddress_id());
        map.put("recipient",name);
        map.put("phone",phone);
        map.put("shipping_address_detail",detail);
        map.put("is_default",sb_address_default.isChecked()?"1":"0");
        map.put("province",province);
        map.put("city",city);
        map.put("area",area);
        map.put("sign",getSign(map));
        ApiRequest.addAddress(map, new MyCallBack<BaseObj>(mContext) {
            @Override
            public void onSuccess(BaseObj obj, int errorCode, String msg) {
                showMsg(msg);
                setResult(RESULT_OK);
                finish();
            }
        });
    }


}
