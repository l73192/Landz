package com.landz.landz.ui.search;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ListView;
import com.google.gson.Gson;
import com.landz.R;
import com.landz.framwork.base.BaseActivity;
import com.landz.framwork.http.HttpHelper;
import com.landz.framwork.http.HttpRequestAsyncTask;
import com.landz.framwork.utils.IntentUtils;
import com.landz.landz.entity.SearchBean;
import com.landz.landz.entity.SearchResultBean;
import com.landz.landz.ui.MainActivity;
import java.util.List;
/**
 * 作者/日期: admin on 2016/8/3.
 * 描述:搜索
 */
public class SearchActivity extends BaseActivity {
    private EditText et_search;
    private ListView lv_search;
    private SearchAdapter searchAdapter;
    /**
     * type=1 地图过来的 type=2,二手楼盘过来的 type=3为买卖 type=4 租赁列表过来 type=5 首页过来
     */
    private int type;
    private List<SearchBean>searchBeen;

    @Override
    public int getContentViewId() {
        return R.layout.activity_search;
    }

    @Override
    public void beforeInitView() {
        type = getIntent().getIntExtra("type",0);
    }

    @Override
    public void initView() {
        et_search = findViewByIdNoCast(R.id.et_search);
        lv_search = findViewByIdNoCast(R.id.lv_search);
    }

    @Override
    public void initData() {
        searchAdapter = new SearchAdapter(this);
        switch (type){
            case 5:
                et_search.setHint("请输入楼盘名称或房源特征…");
                break;
        }
        et_search.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                String content = et_search.getText().toString().trim();//trim()去掉首尾空格
                if (!TextUtils.isEmpty(content))
                    getData(content);
                else {
                    searchBeen.clear();
                    searchAdapter.setResult(searchBeen);
                    searchAdapter.notifyDataSetChanged();
                }
            }
        });
        lv_search.setAdapter(searchAdapter);
        lv_search.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                String resblockName = "";
                String circleTypeCode = "";
                SearchBean searchBean = (SearchBean) searchAdapter.getItem(i);
                if ("0".equals(searchBean.type) || "1".equals(searchBean.type))
                    resblockName = searchBean.name;
                else
                    circleTypeCode = searchBean.id;
                Bundle bundle = new Bundle();
                bundle.putString("resblockName",resblockName);
                bundle.putString("circleTypeCode",circleTypeCode);
                IntentUtils.openActivity(SearchActivity.this, MainActivity.class,bundle);
            }
        });
    }

    /**
     * 获取搜索数据
     */
    public void getData(String content){
        HttpHelper.getSearchDatas(this, content, "0", new HttpRequestAsyncTask.CallBack() {
            @Override
            public void OnSuccess(String result) {
                dismissSoftKeyboard(SearchActivity.this);
                SearchResultBean resultBean = new Gson().fromJson(result,SearchResultBean.class);
                searchBeen = resultBean.result;
                searchAdapter.setResult(searchBeen);
                searchAdapter.notifyDataSetChanged();
            }

            @Override
            public void OnFailed(String errMsg) {
                dismissSoftKeyboard(SearchActivity.this);
            }
        });
    }

    @Override
    public void onClick(View view) {

    }

}
