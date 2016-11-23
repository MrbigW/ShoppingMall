package com.wrk.myshoppingmall.adapter.home;

import android.content.Context;
import android.os.Handler;
import android.os.Message;
import android.support.v4.view.PagerAdapter;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.squareup.picasso.Picasso;
import com.wrk.myshoppingmall.R;
import com.wrk.myshoppingmall.bean.ResultBean;
import com.wrk.myshoppingmall.ui.VerticalViewPager;
import com.wrk.myshoppingmall.utils.Constants;
import com.wrk.myshoppingmall.utils.ToastUtil;
import com.youth.banner.Banner;
import com.youth.banner.BannerConfig;
import com.youth.banner.Transformer;
import com.youth.banner.listener.OnBannerClickListener;
import com.youth.banner.loader.ImageLoader;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

/**
 * Created by MrbigW on 2016/11/22.
 * weChat:1024057635
 * GitHub:MrbigW
 * Usage: -.-
 * -------------------=.=------------------------
 */

public class HomeRecycleAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    // 上下文
    private Context mContext;
    // 数据Bean对象
    private ResultBean mResultBean;

    private LayoutInflater mLayoutInflater;
    /**
     * 五种类型
     */
    // 横幅广告
    public static final int BANNER = 0;
    // 频道
    public static final int CHANNEL = 1;
    // 淘宝头条
    public static final int HEADLINE = 2;
    // 秒杀
    public static final int SECKILL = 3;
    // 推荐
    public static final int RECOMMEND = 4;
    // 热卖
    public static final int HOT = 5;

    // 当前类型
    public int currentType = BANNER;


    private VerticalViewPager vvp_headline;

    public HomeRecycleAdapter(Context context, ResultBean resultBean) {
        this.mContext = context;
        this.mResultBean = resultBean;
        mLayoutInflater = LayoutInflater.from(mContext);
    }

    @Override
    public int getItemCount() {
        return 7;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        if (viewType == BANNER) {
            return new BannerViewHolder(mLayoutInflater.inflate(R.layout.banner_viewpager, null));
        } else if (viewType == CHANNEL) {
            return new ChannelViewHolder(mLayoutInflater.inflate(R.layout.channel_item, null));
        } else if (viewType == HOT) {
            return new HotViewHolder(mLayoutInflater.inflate(R.layout.hot_item, null));
        } else if (viewType == HEADLINE) {
            return new HeadLineViewHolder(mLayoutInflater.inflate(R.layout.headline_item, null));
        } else if (viewType == SECKILL) {
            return new SecKillViewHolder(mLayoutInflater.inflate(R.layout.seckill_item, null));
        } else if (viewType == RECOMMEND) {
            return new RecommendViewHolder(mLayoutInflater.inflate(R.layout.recommend_item, null));
        }

        return null;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        if (getItemViewType(position) == BANNER) {
            BannerViewHolder bannerViewHolder = (BannerViewHolder) holder;
            bannerViewHolder.setData(mResultBean.getBanner_info());
        } else if (getItemViewType(position) == CHANNEL) {
            ChannelViewHolder channelViewHolder = (ChannelViewHolder) holder;
            channelViewHolder.setData(mResultBean.getChannel_info());
        } else if (getItemViewType(position) == HEADLINE) {
            HeadLineViewHolder headLineViewHolder = (HeadLineViewHolder) holder;
        } else if (getItemViewType(position) == SECKILL) {
            SecKillViewHolder seckillViewHolder = (SecKillViewHolder) holder;
            seckillViewHolder.setData(mResultBean.getSeckill_info());
        } else if (getItemViewType(position) == RECOMMEND) {
            RecommendViewHolder recommendViewHolder = (RecommendViewHolder) holder;
            recommendViewHolder.setData(mResultBean.getRecommend_info());
        } else if (getItemViewType(position) == HOT) {
            HotViewHolder hotViewHolder = (HotViewHolder) holder;
            hotViewHolder.setData(mResultBean.getHot_info());
        }
    }

    @Override
    public int getItemViewType(int position) {

        switch (position) {
            case BANNER:
                currentType = BANNER;
                break;
            case CHANNEL:
                currentType = CHANNEL;
                break;
            case HEADLINE:
                currentType = HEADLINE;
                break;
            case SECKILL:
                currentType = SECKILL;
                break;
            case RECOMMEND:
                currentType = RECOMMEND;
                break;
            case HOT:
                currentType = HOT;
                break;
        }

        return currentType;
    }

    // 热门
    class HotViewHolder extends RecyclerView.ViewHolder {

        private GridView gv_hot;
        private LinearLayout ll_hot_more;

        public HotViewHolder(View itemView) {
            super(itemView);
            gv_hot = (GridView) itemView.findViewById(R.id.gv_hot);
            ll_hot_more = (LinearLayout) itemView.findViewById(R.id.ll_hot_more);
        }

        public void setData(List<ResultBean.HotInfoBean> data) {
            gv_hot.setAdapter(new HotGridViewAdapter(mContext, data));

            // 设置每项的单击事件
            gv_hot.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    ToastUtil.showToast(mContext, position + "");
                }
            });

            // 更多的单击事件
            ll_hot_more.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    ToastUtil.showToast(mContext, "查看更多~");
                }
            });
        }
    }

    // 推荐
    class RecommendViewHolder extends RecyclerView.ViewHolder {

        private LinearLayout ll_recommend_more;
        private GridView gv_recommend;

        public RecommendViewHolder(View itemView) {
            super(itemView);
            gv_recommend = (GridView) itemView.findViewById(R.id.gv_recommend);
            ll_recommend_more = (LinearLayout) itemView.findViewById(R.id.ll_recommend_more);
        }

        public void setData(List<ResultBean.RecommendInfoBean> data) {
            gv_recommend.setAdapter(new RecommendGridViewAdapter(mContext, data));

            // 设置每项的单击事件
            gv_recommend.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    ToastUtil.showToast(mContext, position + "");
                }
            });

            // 更多的单击事件
            ll_recommend_more.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    ToastUtil.showToast(mContext, "查看更多~");
                }
            });
        }

    }

    private boolean isFirst = true;
    private TextView tvTime;
    private int dt;

    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            if (msg.what == 0) {
                dt = dt - 1000;
                SimpleDateFormat sd = new SimpleDateFormat("HH:mm:ss");
                tvTime.setText(sd.format(new Date(dt)));

                handler.removeMessages(0);
                handler.sendEmptyMessageDelayed(0, 1000);
                if (dt == 0) {
                    handler.removeMessages(0);
                }
            } else if (msg.what == 1) {
                if (vvp_headline != null) {
                    int item = (vvp_headline.getCurrentItem() + 1) % 3;
                    vvp_headline.setCurrentItem(item);
                    handler.sendEmptyMessageDelayed(1, 3000);
                }
            }

        }
    };

    // 秒杀
    class SecKillViewHolder extends RecyclerView.ViewHolder {
        private LinearLayout ll_seckill_more;
        private RecyclerView rv_seckill;
        private SeckillRecyclerViewAdapter mAdapter;

        public SecKillViewHolder(View itemView) {
            super(itemView);
            rv_seckill = (RecyclerView) itemView.findViewById(R.id.rv_seckill);
            ll_seckill_more = (LinearLayout) itemView.findViewById(R.id.ll_seckill_more);
            tvTime = (TextView) itemView.findViewById(R.id.tv_time_seckill);
        }

        public void setData(ResultBean.SeckillInfoBean data) {
            // 设置秒杀时间
            if (isFirst) {
                dt = (int) (Integer.parseInt(data.getEnd_time()) - System.currentTimeMillis());
                isFirst = false;
            }

            // 设置RecyclerView
            rv_seckill.setLayoutManager(new LinearLayoutManager(mContext, LinearLayoutManager.HORIZONTAL, false));
            // 设置适配器
            mAdapter = new SeckillRecyclerViewAdapter(mContext, data);
            rv_seckill.setAdapter(mAdapter);

            // 倒计时
            handler.sendEmptyMessageDelayed(0, 1000);

            // 点击事件
            mAdapter.setOnSeckillRecyclerView(new SeckillRecyclerViewAdapter.OnSeckillRecyclerView() {
                @Override
                public void onClick(int position) {
                    ToastUtil.showToast(mContext, position + "");
                }
            });

            // 更多的点击事件
            ll_seckill_more.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    ToastUtil.showToast(mContext, "查看更多~");
                }
            });

        }
    }


    // 头条
    class HeadLineViewHolder extends RecyclerView.ViewHolder {

        private ImageButton ib_headline;

        public HeadLineViewHolder(View itemView) {
            super(itemView);
            ib_headline = (ImageButton) itemView.findViewById(R.id.ib_headline);
            vvp_headline = (VerticalViewPager) itemView.findViewById(R.id.vvp_headline);

            vvp_headline.setAdapter(new HeadLinePagerAdapter());

            vvp_headline.setOnTouchListener(new View.OnTouchListener() {
                @Override
                public boolean onTouch(View v, MotionEvent event) {
                    return true;
                }
            });

            ib_headline.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    ToastUtil.showToast(mContext, "淘宝头条");
                }
            });

            handler.removeMessages(1);
            handler.sendEmptyMessageDelayed(1, 3000);

        }
    }

    // 频道
    class ChannelViewHolder extends RecyclerView.ViewHolder {

        private GridView gv_channel;

        public ChannelViewHolder(View itemView) {
            super(itemView);
            gv_channel = (GridView) itemView.findViewById(R.id.gv_channel);
        }

        public void setData(List<ResultBean.ChannelInfoBean> mChannelInfoBeen) {
            gv_channel.setAdapter(new ChannelAdapter(mContext, mChannelInfoBeen));

            // 单击事件
            gv_channel.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    if (position <= 8) {
                        ToastUtil.showToast(mContext, position + "");
                    }
                }
            });

        }

    }

    // 广告条
    class BannerViewHolder extends RecyclerView.ViewHolder {

        public Banner banner;

        public BannerViewHolder(View itemView) {
            super(itemView);
            banner = (Banner) itemView.findViewById(R.id.banner);
        }

        public void setData(List<ResultBean.BannerInfoBean> bannerInfoBeen) {
            banner.setBannerStyle(BannerConfig.NUM_INDICATOR_TITLE);
            // 设置图片加载器
            banner.setImageLoader(new PicassoImageLoader());
            // 设置图片集合
            List<String> imageUris = new ArrayList<>();
            for (int i = 0; i < bannerInfoBeen.size(); i++) {
                imageUris.add(Constants.Base_URl_IMAGE + bannerInfoBeen.get(i).getImage());
            }
            banner.setImages(imageUris);
            // 设置banner动画效果
            banner.setBannerAnimation(Transformer.Accordion);
            //设置标题集合（当banner样式有显示title时）
            String[] titles = new String[]{"尚硅谷新学员做客CCTV", "尚硅谷在线课堂震撼发布", "抱歉，是真的没座了"};
            banner.setBannerTitles(Arrays.asList(titles));
            //设置自动轮播，默认为true
            banner.isAutoPlay(true);
            //设置轮播时间
            banner.setDelayTime(3000);
            //设置指示器位置（当banner模式中有指示器时）
            banner.setIndicatorGravity(BannerConfig.RIGHT);
            //banner设置方法全部调用完毕时最后调用
            banner.start();
            //banner的点击事件
            banner.setOnBannerClickListener(new OnBannerClickListener() {
                @Override
                public void OnBannerClick(int position) {
                    ToastUtil.showToast(mContext, position + "");
                }
            });
        }
    }


    public class HeadLinePagerAdapter extends PagerAdapter {
        @Override
        public int getCount() {
            return 3;
        }

        @Override
        public Object instantiateItem(ViewGroup container, int position) {


            View view = View.inflate(mContext, R.layout.headline_content, null);

            TextView tv_headline_1 = (TextView) view.findViewById(R.id.tv_headline_1);
            TextView tv_headline_2 = (TextView) view.findViewById(R.id.tv_headline_2);

            switch (position) {
                case 0:
                    tv_headline_1.setText("美国当选总统特朗普宣布美国将退出TPP");
                    tv_headline_2.setText("暴雪打造全新第一人称新作 能否超越《守望先锋》？");
                    break;
                case 1:
                    tv_headline_1.setText("人工智能重大进展！全球首个光电子神经网络问世");
                    tv_headline_2.setText("中国禁止电视台播放韩国明星代言广告？外交部回应");
                    break;
                case 2:
                    tv_headline_1.setText("美军鹰派认为中美或随时开战：南海只是开胃菜");
                    tv_headline_2.setText("专家：中国需紧盯美俄关系新动向 要做及时改变");
                    break;
            }

            view.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    ToastUtil.showToast(mContext, "淘宝头条");
                }
            });

            container.addView(view);

            return view;
        }

        @Override
        public boolean isViewFromObject(View view, Object object) {
            return view == object;
        }

        @Override
        public void destroyItem(ViewGroup container, int position, Object object) {
//            super.destroyItem(container, position, object);
            container.removeView((View) object);
        }
    }

    /**
     * banner的图片加载器
     */
    public class PicassoImageLoader extends ImageLoader {
        @Override
        public void displayImage(Context context, Object path, ImageView imageView) {

            //Picasso 加载图片简单用法
            Picasso.with(context).load((String) path).into(imageView);

        }
    }

}




























