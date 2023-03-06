package com.xuhuawei.linkedadapter;

import android.view.View;
import android.widget.TextView;

import com.xuhuawei.linkedadapter.base.AdapterBehavior;
import com.xuhuawei.linkedadapter.base.BaseViewHolder;

/*
 * User: xuhuawei
 * Date: 2023/2/17
 * Desc:
 */
public class LinkedViewHolder extends BaseViewHolder<LinkedBean> {
    private TextView textView;

    public LinkedViewHolder(AdapterBehavior behavior, View itemView) {
        super(behavior, itemView);
    }

    @Override
    protected void findViewByIds() {
        textView = findViewById(R.id.textView);
    }

    @Override
    protected void onBindView(int position, LinkedBean bean) {
        textView.setText("Positon:"+bean.position);
    }


}
