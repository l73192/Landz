package com.landz.landz.ui.search;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;
import com.landz.R;
import com.landz.landz.entity.SearchBean;
import java.util.List;
/**
 * 作者/日期: admin on 2016/8/3.
 * 描述:搜索适配器
 */
public class SearchAdapter extends BaseAdapter {
    private Context context;
    private List<SearchBean> result;

    public SearchAdapter(Context context) {
        this.context = context;
    }

    public void setResult(List<SearchBean> result) {
        this.result = result;
    }

    @Override
    public int getCount() {
        return result == null ? 0 : result.size();
    }

    @Override
    public Object getItem(int i) {
        return result == null ? null : result.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View convertView, ViewGroup viewGroup) {
        ViewHolder viewHolder = null;
        if (convertView == null){
            convertView = View.inflate(context, R.layout.item_search,null);
            viewHolder = new ViewHolder();
            viewHolder.tv_search_content = (TextView) convertView.findViewById(R.id.tv_search_content);
            convertView.setTag(viewHolder);
        }else
            viewHolder = (ViewHolder) convertView.getTag();
        if (result.get(i) != null)
            viewHolder.tv_search_content.setText(result.get(i).name);
        return convertView;
    }

    private class ViewHolder {
        TextView tv_search_content;
    }
}
