package com.landz.landz.ui.online.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.landz.R;
import com.landz.framwork.Constants;
import com.landz.framwork.image.ImageLoader;
import com.landz.framwork.utils.StringUtils;
import com.landz.landz.entity.HouseArrBean;
import com.landz.landz.entity.HouseOneArrBean;

import java.util.List;

/**
 * 作者/日期: admin on 2016/8/4.
 * 描述:在售豪宅适配器
 */
public class OnlineHouseAdapter extends BaseAdapter {
    private Context context;
    private List<Object>totalList;
    private int type_01=0;//houseArr
    private int type_02=1;//houseOneArr
    private ImageLoader imageLoader;//图片缓存优化

    public OnlineHouseAdapter(Context context) {
        this.context = context;
        imageLoader = new ImageLoader();
    }

    public void setTotalList(List<Object> totalList) {
        this.totalList = totalList;
    }

    @Override
    public int getCount() {
        return totalList==null?0:totalList.size();
    }

    @Override
    public Object getItem(int i) {
        return totalList == null?null:totalList.get(i);
    }

    @Override
    public int getItemViewType(int position) {
        if (totalList.get(position) instanceof HouseArrBean)
            return type_01;
        else
            return type_02;
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public int getViewTypeCount() {
        return 2;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup viewGroup) {
        ViewHolder viewHolder = null;
        ViewHolder2 viewHolder2 = null;
        int type = getItemViewType(position);
        if (convertView == null){
            if (type == type_01){
                viewHolder = new ViewHolder();
                convertView = View.inflate(context, R.layout.item_online_da,null);
                viewHolder.img_online_twopic = (ImageView) convertView.findViewById(R.id.img_online_twopic);
                viewHolder.ll_bottom_label = (LinearLayout) convertView.findViewById(R.id.ll_bottom_label);
                viewHolder.tv_detail_tese1 = (TextView) convertView.findViewById(R.id.tv_detail_tese1);
                viewHolder.tv_detail_tese2 = (TextView) convertView.findViewById(R.id.tv_detail_tese2);
                viewHolder.tv_detail_tese3 = (TextView) convertView.findViewById(R.id.tv_detail_tese3);
                viewHolder.tv_online_diduan = (TextView) convertView.findViewById(R.id.tv_online_diduan);
                viewHolder.tv_online_jianjie = (TextView) convertView.findViewById(R.id.tv_online_jianjie);
                viewHolder.tv_online_jushi = (TextView) convertView.findViewById(R.id.tv_online_jushi);
                viewHolder.tv_online_zongjia = (TextView) convertView.findViewById(R.id.tv_online_zongjia);
                convertView.setTag(viewHolder);
            }
            if (type == type_02){
                viewHolder2 = new ViewHolder2();
                convertView = View.inflate(context, R.layout.item_online_xiao,null);
                viewHolder2.img_online_onepic = (ImageView) convertView.findViewById(R.id.img_online_onepic);
                viewHolder2.tv_online_name = (TextView) convertView.findViewById(R.id.tv_online_name);
                viewHolder2.tv_online_onemoney = (TextView) convertView.findViewById(R.id.tv_online_onemoney);
                viewHolder2.tv_online_type = (TextView) convertView.findViewById(R.id.tv_online_type);
                viewHolder2.tv_online_xinxi = (TextView) convertView.findViewById(R.id.tv_online_xinxi);
                convertView.setTag(viewHolder2);
            }
        }else {
            if (type ==type_01)
                viewHolder = (ViewHolder) convertView.getTag();
            if (type == type_02)
                viewHolder2 = (ViewHolder2) convertView.getTag();
        }

        if (type == type_01){
            HouseArrBean houseArrBean = (HouseArrBean) totalList.get(position);
            viewHolder.img_online_twopic.setImageResource(R.mipmap.defult_twopic);
            viewHolder.tv_online_diduan.setText(houseArrBean.resblockName + "  "
                    + houseArrBean.circleTypeName);
            viewHolder.tv_online_jianjie.setText(houseArrBean.salesTrait);
            viewHolder.tv_online_jushi.setText(houseArrBean.bedroomAmount + "室"
                    + houseArrBean.parlorAmount + "厅" + "  " + StringUtils.doubleFormat(houseArrBean.buildSize) + "㎡");;
            viewHolder.tv_online_zongjia.setText(StringUtils
                    .doubleFormat(houseArrBean.totalPrices) + "万");
            imageLoader.displayImg(houseArrBean.titleImg + Constants.IMG_URL_SUFFIX_ONLINE_LIST_TWO,viewHolder.img_online_twopic);
            showLabel(houseArrBean.houseLabel,viewHolder);
        }else if (type == type_02) {//
            HouseOneArrBean oneModel = (HouseOneArrBean) totalList.get(position);
            viewHolder2.tv_online_onemoney.setText(StringUtils
                    .doubleFormat(oneModel.totalprBegin) + "-" + StringUtils.doubleFormat(oneModel.totalprEnd) + "万");
            viewHolder2.tv_online_type.setText(oneModel.resblockType);
            viewHolder2.tv_online_name.setText(oneModel.resblockOneName);
            viewHolder2.tv_online_xinxi.setText(oneModel.bedroomAmount + "室"
                    + oneModel.parlorAmount + "厅" + "  " + StringUtils.doubleFormat(oneModel.buildSize) + "㎡  "
                    + StringUtils.doubleFormat(oneModel.unitprBegin) + "-" + StringUtils.doubleFormat(oneModel.unitprEnd) + "万/㎡");
            viewHolder2.img_online_onepic.setImageResource(R.mipmap.defult_onepic);
            imageLoader.displayImg(oneModel.titlepicImg
                            + Constants.IMG_URL_SUFFIX_ONLINE_LIST_ONE,
                    viewHolder2.img_online_onepic);
        }
        return convertView;
    }
    /**
     * houseArr显示
     */
    public class ViewHolder{
        ImageView img_online_twopic;
        TextView tv_online_jianjie;
        TextView tv_online_diduan;
        TextView tv_online_jushi;
        TextView tv_online_zongjia;
        LinearLayout ll_bottom_label;
        TextView tv_detail_tese1;
        TextView tv_detail_tese2;
        TextView tv_detail_tese3;
    }

    /**
     * houseOneArr显示
     */
    public class ViewHolder2{
        ImageView img_online_onepic;
        TextView tv_online_name;
        TextView tv_online_type;
        TextView tv_online_xinxi;
        TextView tv_online_onemoney;
    }

    /**
     * 显示标签内容
     */
    private void showLabel(String label, ViewHolder holder) {
        if (!TextUtils.isEmpty(label)) {
            String[] arr = label.split(" ");
            if (arr.length >= 3) {
                holder.tv_detail_tese1.setText(arr[0]);
                holder.tv_detail_tese1.setVisibility(View.VISIBLE);
                holder.tv_detail_tese2.setText(arr[1]);
                holder.tv_detail_tese2.setVisibility(View.VISIBLE);
                holder.tv_detail_tese3.setText(arr[2]);
                holder.tv_detail_tese3.setVisibility(View.VISIBLE);
            } else if (arr.length == 2) {
                holder.tv_detail_tese1.setText(arr[0]);
                holder.tv_detail_tese1.setVisibility(View.VISIBLE);
                holder.tv_detail_tese2.setText(arr[1]);
                holder.tv_detail_tese2.setVisibility(View.VISIBLE);
            } else if (arr.length == 1) {
                holder.tv_detail_tese1.setText(arr[0]);
                holder.tv_detail_tese1.setVisibility(View.VISIBLE);
            }
        }
    }


}
