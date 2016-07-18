package www.chendanfeng.com.fragment;

import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import www.chendanfeng.com.adapter.NewsListAdapter;
import www.chendanfeng.com.boishixuan.R;
import www.chendanfeng.com.network.model.NewsResponse;
import www.chendanfeng.com.xrecyclerview.ProgressStyle;
import www.chendanfeng.com.xrecyclerview.XRecyclerView;

/**
 * Created by Administrator on 2016/7/16 0016.
 */
public class SystemNewsFragment extends BaseFragment{
    @Bind(R.id.systemNewsRecyclerView)
    XRecyclerView mSystemNewsRecyclerView;
    private NewsListAdapter mNewsListAdapter;
    private View mView;
    private List<NewsResponse> mNewsResponseList;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        this.mView = inflater.inflate(R.layout.fragment_system_news, container, false);
        ButterKnife.bind(this, this.mView);
        return this.mView;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initRecyclerView();
    }
    private void initRecyclerView() {
        this.mNewsResponseList = new ArrayList<>();
        NewsResponse newsResponse = new NewsResponse();
        newsResponse.newsContent= "尊敬的张小三用户，您在2016-07-12  17:19:00的时候萨达的防晒霜的撒旦王琪琪";
        newsResponse.newsTitle = "系统消息：购买理财消息";
        newsResponse.newsTime = "2016-07-12 17:30:00";
        this.mNewsResponseList.add(newsResponse);

        newsResponse = new NewsResponse();
        newsResponse.newsContent= "尊敬的张小三用户，您在2016-07-12  17:19:00的时候萨达的防晒霜的撒旦王琪琪";
        newsResponse.newsTitle = "系统消息：购买理财消息";
        newsResponse.newsTime = "2016-07-12 17:30:00";
        this.mNewsResponseList.add(newsResponse);

        newsResponse = new NewsResponse();
        newsResponse.newsContent= "尊敬的张小三用户，您在2016-07-12  17:19:00的时候萨达的防晒霜的撒旦王琪琪";
        newsResponse.newsTitle = "系统消息：购买理财消息";
        newsResponse.newsTime = "2016-07-12 17:30:00";
        this.mNewsResponseList.add(newsResponse);


        this.mNewsListAdapter = new NewsListAdapter(getActivity(),this.mNewsResponseList,1);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity());
        this.mSystemNewsRecyclerView.setLayoutManager(linearLayoutManager);
        this.mSystemNewsRecyclerView.setRefreshProgressStyle(ProgressStyle.BallSpinFadeLoader);
        this.mSystemNewsRecyclerView.setLoadingMoreProgressStyle(ProgressStyle.Pacman);
        this.mSystemNewsRecyclerView.setArrowImageView(R.drawable.icon_font_down_grey);


        this.mSystemNewsRecyclerView.setLoadingListener(new XRecyclerView.LoadingListener() {
            @Override
            public void onRefresh() {
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        //TODO
                        SystemNewsFragment.this.mSystemNewsRecyclerView.refreshComplete();
                    }
                },3000);
            }

            @Override
            public void onLoadMore() {
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        //TODO
                        SystemNewsFragment.this.mSystemNewsRecyclerView.loadMoreComplete();
                    }
                },3000);

            }
        });
        this.mSystemNewsRecyclerView.setAdapter(this.mNewsListAdapter);
    }
}