package com.zhizhong.yujian.module.auction.network.response;

import com.library.base.BaseObj;

import java.math.BigDecimal;
import java.util.List;

public class PaiMaiGoodsDetailObj extends BaseObj {
    /**
     * goods_id : 2
     * goods_name : 御见翡翠本命佛项链吊坠缅甸天然弥勒佛玉吊坠男女款笑肚玉天然佛公生日礼物
     * goods_image : http://121.40.186.118:10011/upload/goods.png
     * goods_video :
     * img_list : ["http://121.40.186.118:10011/upload/goods.png","http://121.40.186.118:10011/upload/goods.png","http://121.40.186.118:10011/upload/goods.png"]
     * raise_price : 100
     * clap_price : 1200
     * sales_volume : 2027
     * stock : 50
     * tixing : 0
     * chujia_num : 0
     * pinglun_list : []
     * chujia_list : []
     * product_parameter_list : [{"parameter_name":"品牌:","parameter_value":"御见"},{"parameter_name":"材质:","parameter_value":"翡翠"},{"parameter_name":"尺寸:","parameter_value":"15×11mm"},{"parameter_name":"毛重:","parameter_value":"154g"},{"parameter_name":"产地:","parameter_value":"缅甸"}]
     * goods_details_list : ["http://121.40.186.118:10011/upload/goods.png","http://121.40.186.118:10011/upload/goods.png","http://121.40.186.118:10011/upload/goods.png","http://121.40.186.118:10011/upload/goods.png","http://121.40.186.118:10011/upload/goods.png","http://121.40.186.118:10011/upload/goods.png"]
     * tuijian_list : [{"goods_id":1,"goods_image":"http://121.40.186.118:10011/upload/goods.png","goods_name":"御见翡翠本命佛项链吊坠缅甸天然弥勒佛玉吊坠男女款笑肚玉天然佛公生日礼物","type":1,"chujia_num":0,"dangqian_price":800000,"begin_time":946656000,"end_time":946656000},{"goods_id":13,"goods_image":"http://121.40.186.118:10011/upload/goods.png","goods_name":"御见翡翠本命佛项链吊坠缅甸天然弥勒佛玉吊坠男女款笑肚玉天然佛公生日礼物","type":1,"chujia_num":0,"dangqian_price":800000,"begin_time":946656000,"end_time":946656000},{"goods_id":16,"goods_image":"http://121.40.186.118:10011/upload/goods.png","goods_name":"御见翡翠本命佛项链吊坠缅甸天然弥勒佛玉吊坠男女款笑肚玉天然佛公生日礼物","type":1,"chujia_num":0,"dangqian_price":800000,"begin_time":946656000,"end_time":946656000},{"goods_id":18,"goods_image":"http://121.40.186.118:10011/upload/goods.png","goods_name":"御见翡翠本命佛项链吊坠缅甸天然弥勒佛玉吊坠男女款笑肚玉天然佛公生日礼物","type":1,"chujia_num":0,"dangqian_price":800000,"begin_time":946656000,"end_time":946656000}]
     * is_collect : 0
     * is_baozhengjin : 0
     * begin_time : 1533088800
     * end_time : 1533895200
     */

    private String goods_id;
    private String goods_name;
    private String goods_image;
    private String goods_video;
    private BigDecimal raise_price;
    private BigDecimal clap_price;
    private String sales_volume;//围观人数
    private int stock;//库存
    private int type;
    private String tixing;
    private String chujia_num;
    private int is_collect;
    private int is_baozhengjin;//保证金是否额度达标(1是 0否)
    private long begin_time;
    private long end_time;
    private List<String> img_list;
    private List<?> pinglun_list;
    private List<?> chujia_list;
    private List<ProductParameterListBean> product_parameter_list;
    private List<String> goods_details_list;
    private List<PaiMaiGoodsObj> tuijian_list;

    private int multiple=1000;

    public String getGoods_id() {
        return goods_id;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public void setGoods_id(String goods_id) {
        this.goods_id = goods_id;
    }

    public String getGoods_name() {
        return goods_name;
    }

    public void setGoods_name(String goods_name) {
        this.goods_name = goods_name;
    }

    public String getGoods_image() {
        return goods_image;
    }

    public void setGoods_image(String goods_image) {
        this.goods_image = goods_image;
    }

    public String getGoods_video() {
        return goods_video;
    }

    public void setGoods_video(String goods_video) {
        this.goods_video = goods_video;
    }

    public BigDecimal getRaise_price() {
        return raise_price;
    }

    public void setRaise_price(BigDecimal raise_price) {
        this.raise_price = raise_price;
    }

    public BigDecimal getClap_price() {
        return clap_price;
    }

    public void setClap_price(BigDecimal clap_price) {
        this.clap_price = clap_price;
    }

    public String getSales_volume() {
        return sales_volume;
    }

    public void setSales_volume(String sales_volume) {
        this.sales_volume = sales_volume;
    }

    public int getStock() {
        return stock;
    }

    public void setStock(int stock) {
        this.stock = stock;
    }

    public String getTixing() {
        return tixing;
    }

    public void setTixing(String tixing) {
        this.tixing = tixing;
    }

    public String getChujia_num() {
        return chujia_num;
    }

    public void setChujia_num(String chujia_num) {
        this.chujia_num = chujia_num;
    }

    public int getIs_collect() {
        return is_collect;
    }

    public void setIs_collect(int is_collect) {
        this.is_collect = is_collect;
    }

    public int getIs_baozhengjin() {
        return is_baozhengjin;
    }

    public void setIs_baozhengjin(int is_baozhengjin) {
        this.is_baozhengjin = is_baozhengjin;
    }

    public long getBegin_time() {
        return begin_time*multiple;
    }

    public void setBegin_time(long begin_time) {
        this.begin_time = begin_time;
    }

    public long getEnd_time() {
        return end_time*multiple;
    }

    public void setEnd_time(long end_time) {
        this.end_time = end_time;
    }

    public List<String> getImg_list() {
        return img_list;
    }

    public void setImg_list(List<String> img_list) {
        this.img_list = img_list;
    }

    public List<?> getPinglun_list() {
        return pinglun_list;
    }

    public void setPinglun_list(List<?> pinglun_list) {
        this.pinglun_list = pinglun_list;
    }

    public List<?> getChujia_list() {
        return chujia_list;
    }

    public void setChujia_list(List<?> chujia_list) {
        this.chujia_list = chujia_list;
    }

    public List<ProductParameterListBean> getProduct_parameter_list() {
        return product_parameter_list;
    }

    public void setProduct_parameter_list(List<ProductParameterListBean> product_parameter_list) {
        this.product_parameter_list = product_parameter_list;
    }

    public List<String> getGoods_details_list() {
        return goods_details_list;
    }

    public void setGoods_details_list(List<String> goods_details_list) {
        this.goods_details_list = goods_details_list;
    }

    public List<PaiMaiGoodsObj> getTuijian_list() {
        return tuijian_list;
    }

    public void setTuijian_list(List<PaiMaiGoodsObj> tuijian_list) {
        this.tuijian_list = tuijian_list;
    }

    public static class ProductParameterListBean {
        /**
         * parameter_name : 品牌:
         * parameter_value : 御见
         */

        private String parameter_name;
        private String parameter_value;

        public String getParameter_name() {
            return parameter_name;
        }

        public void setParameter_name(String parameter_name) {
            this.parameter_name = parameter_name;
        }

        public String getParameter_value() {
            return parameter_value;
        }

        public void setParameter_value(String parameter_value) {
            this.parameter_value = parameter_value;
        }
    }
}
