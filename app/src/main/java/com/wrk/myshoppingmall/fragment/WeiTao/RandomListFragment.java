package com.wrk.myshoppingmall.fragment.WeiTao;

import android.graphics.Color;
import android.view.View;
import android.widget.TextView;

import com.alibaba.fastjson.JSON;
import com.wrk.myshoppingmall.R;
import com.wrk.myshoppingmall.bean.TagBean;
import com.wrk.myshoppingmall.common.BaseFragment;
import com.wrk.myshoppingmall.ui.randomLayout.StellarMap;
import com.wrk.myshoppingmall.utils.Constants;
import com.wrk.myshoppingmall.utils.ToastUtil;
import com.wrk.myshoppingmall.utils.UIUtils;

import java.util.List;
import java.util.Random;

import butterknife.BindView;

/**
 * Created by MrbigW on 2016/11/26.
 * weChat:1024057635
 * GitHub:MrbigW
 * Usage: -.-
 * -------------------=.=------------------------
 */
public class RandomListFragment extends BaseFragment {
    @BindView(R.id.sm_random)
    StellarMap smRandom;

    private List<TagBean.ResultBean> result;

    private List<TagBean.ResultBean> firDatas;
    private List<TagBean.ResultBean> senDatas;
    private List<TagBean.ResultBean> thirdData;
    private Random mRandom;


    @Override
    protected String getUrl() {
        return Constants.TAG_URL;
    }

    @Override
    protected void initData(String content) {
        processData(content);

        firDatas = result.subList(0, result.size() / 3);
        senDatas = result.subList(result.size() / 3, result.size() * 2 / 3);
        thirdData = result.subList(result.size() * 2 / 3, result.size());


        initStellarMap();
    }

    private void initStellarMap() {
        smRandom.setAdapter(new RandomAdapter());
        smRandom.setRegularity(11, 11);
        smRandom.setGroup(0, true);
    }


    @Override
    public int getLayoutId() {
        return R.layout.fragment_random;
    }

    @Override
    protected void initTitle() {

    }

    private void processData(String json) {
        TagBean tagBean = JSON.parseObject(json, TagBean.class);
        result = tagBean.getResult();
    }

    private class RandomAdapter implements StellarMap.Adapter {
        // 返回显示的组数
        @Override
        public int getGroupCount() {
            return 3;
        }

        // 返回数量
        @Override
        public int getCount(int group) {
            switch (group) {
                case 0:
                    return firDatas.size();
                case 1:
                    return senDatas.size();
                case 2:
                    return thirdData.size();
            }
            return firDatas.size();
        }

        /**
         * 返回指定组的指定位置上的View
         *
         * @param group：组
         * @param position            ：对于妹子数据来讲，position都从0开始
         * @param convertView：返回的view
         * @return
         */
        @Override
        public View getView(int group, int position, View convertView) {
            mRandom = new Random();

            final TextView textView = new TextView(getActivity());

            switch (group) {
                case 0:
                    textView.setText(firDatas.get(position).getName());
                    break;
                case 1:
                    textView.setText(senDatas.get(position).getName());
                    break;
                case 2:
                    textView.setText(thirdData.get(position).getName());
                    break;
            }

            // 随机的三色
            int red = mRandom.nextInt(200);
            int green = mRandom.nextInt(200);
            int blue = mRandom.nextInt(200);
            textView.setTextColor(Color.rgb(red, green, blue));

            textView.setTextSize(mRandom.nextInt(UIUtils.dp2px(10)) + UIUtils.dp2px(5));

            textView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    ToastUtil.showToast(getActivity(), textView.getText().toString());
                }
            });

            return textView;
        }

        @Override
        public int getNextGroupOnPan(int group, float degree) {
            return 0;
        }

        @Override
        public int getNextGroupOnZoom(int group, boolean isZoomIn) {
            if (group == 0) {
                return 1;
            } else if (group == 1) {
                return 2;
            } else if (group == 2) {
                return 0;
            }
            return 0;
        }
    }
}
