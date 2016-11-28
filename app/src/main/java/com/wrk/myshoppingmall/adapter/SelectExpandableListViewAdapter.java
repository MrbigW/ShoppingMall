package com.wrk.myshoppingmall.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.wrk.myshoppingmall.R;

/**
 * Created by MrbigW on 2016/11/25.
 * weChat:1024057635
 * GitHub:MrbigW
 * Usage: -.-
 * -------------------=.=------------------------
 */

public class SelectExpandableListViewAdapter extends BaseExpandableListAdapter {
    private String[] parent_name = new String[]{"价格", "推荐主题", "类型"};
    private String[] child_price = new String[]{"不限", "15以下", "15-30", "30-50", "50-70", "70-100", "100以上"};
    private String[] child_theme = new String[]{"全部", "盗墓笔记", "FUNKO", "GSC", "古风原创", "剑侠情缘", "零食", "秦时明月", "全职高手", "长草颜文字"};
    private String[] child_type = new String[]{"全部", "古风", "和风", "lolita", "日常", "泳衣", "汉风", "胖次"};

    private Context mContext;


    public SelectExpandableListViewAdapter(Context context) {
        this.mContext = context;
    }

    // 获得父项的数目
    @Override
    public int getGroupCount() {
        return parent_name.length;
    }

    // 获得子项的数目
    @Override
    public int getChildrenCount(int groupPosition) {
        switch (groupPosition) {
            case 0:
                return child_price.length;
            case 1:
                return child_theme.length;
            case 2:
                return child_type.length;
        }
        return 0;
    }

    // 获得某个父项
    @Override
    public Object getGroup(int groupPosition) {
        return null;
    }

    // 获得某个子项
    @Override
    public Object getChild(int groupPosition, int childPosition) {
        return null;
    }

    // 获得父项的id
    @Override
    public long getGroupId(int groupPosition) {
        return 0;
    }

    // 获得子项的id
    @Override
    public long getChildId(int groupPosition, int childPosition) {
        return 0;
    }

    // 按函数的名字来理解应该是是否具有稳定的id，这个方法目前一直都是返回false，没有去改动过
    @Override
    public boolean hasStableIds() {
        return false;
    }

    // 获得父项显示的view
    @Override
    public View getGroupView(int groupPosition, boolean isExpanded, View convertView, ViewGroup parent) {
        if (convertView == null) {
            convertView = View.inflate(mContext, R.layout.select_parent_item, null);
        }

        TextView tv_drawer_name = (TextView) convertView.findViewById(R.id.tv_drawer_name);
        tv_drawer_name.setText(parent_name[groupPosition]);

        TextView tv_drawer_select = (TextView) convertView.findViewById(R.id.tv_drawer_select);
        switch (groupPosition) {
            case 0:
                tv_drawer_select.setText("不限");
                break;
            case 1:
                tv_drawer_select.setText("全部");
                break;
            case 2:
                tv_drawer_select.setText("全部");
                break;
        }

        return convertView;
    }

    // 获得子项显示的view
    @Override
    public View getChildView(int groupPosition, int childPosition, boolean isLastChild, View convertView, ViewGroup parent) {
        convertView = View.inflate(mContext, R.layout.select_child_item, null);
        TextView tv_selct_name = (TextView) convertView.findViewById(R.id.tv_selct_name);
        ImageView iv_drawer_select = (ImageView) convertView.findViewById(R.id.iv_drawer_select);
        switch (groupPosition) {
            case 0:
                tv_selct_name.setText(child_price[childPosition]);
                return convertView;
            case 1:
                tv_selct_name.setText(child_theme[childPosition]);
                return convertView;
            case 2:
                tv_selct_name.setText(child_type[childPosition]);
                return convertView;
        }

        return convertView;
    }

    //  子项是否可选中，如果需要设置子项的点击事件，需要返回true
    @Override
    public boolean isChildSelectable(int groupPosition, int childPosition) {
        return true;
    }


}









