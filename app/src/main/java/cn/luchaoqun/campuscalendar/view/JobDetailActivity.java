package cn.luchaoqun.campuscalendar.view;

import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.support.design.widget.Snackbar;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.race604.flyrefresh.FlyRefreshLayout;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import cn.luchaoqun.campuscalendar.R;
import cn.luchaoqun.campuscalendar.commen.ACache;
import cn.luchaoqun.campuscalendar.commen.BaseActivity;
import cn.luchaoqun.campuscalendar.contants.JobContants;
import cn.luchaoqun.campuscalendar.model.Collection;
import cn.luchaoqun.campuscalendar.model.CollectionObject;
import cn.luchaoqun.campuscalendar.model.Job;
import cn.luchaoqun.campuscalendar.model.JobItem;
import cn.luchaoqun.campuscalendar.model.Like;
import cn.luchaoqun.campuscalendar.model.Likes;
import cn.luchaoqun.campuscalendar.model.User;
import cn.luchaoqun.campuscalendar.rest.RestClient;
import retrofit.Callback;
import retrofit.RetrofitError;
import retrofit.client.Response;

/**
 * Created by Administrator on 2015/8/11.
 */
public class JobDetailActivity extends BaseActivity implements FlyRefreshLayout.OnPullRefreshListener {

    @Bind(R.id.job_detail_list)
    RecyclerView jobDetailList;
    @Bind(R.id.fly_layout)
    FlyRefreshLayout flyLayout;
    @Bind(R.id.job_toolbar)
    Toolbar job_toolbar;

    private Handler mHandler = new Handler();;
    private LinearLayoutManager mLayoutManager;
    private ItemAdapter itemAdapter;
    private String objectId,company;
    private List<JobItem> jobItems = new ArrayList<>();

    private ACache aCache;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_job_detail);
        objectId = getIntent().getStringExtra("id");
        company = getIntent().getStringExtra("company");
        ButterKnife.bind(this);

        initToolbar();
        initView();
        getJobData();//请求数据
    }

    private void initView() {
        ;
        flyLayout.setOnPullRefreshListener(this);

        mLayoutManager = new LinearLayoutManager(this);
        jobDetailList.setLayoutManager(mLayoutManager);
        itemAdapter = new ItemAdapter(this);

        jobDetailList.setAdapter(itemAdapter);
    }

    private void initToolbar() {
        job_toolbar.setTitle(company);
        job_toolbar.setTitleTextColor(Color.WHITE);
        setSupportActionBar(job_toolbar);
        getSupportActionBar().setHomeButtonEnabled(true); //设置返回键可用
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

    private void getJobData(){
        RestClient.getInstance().getJobById(objectId, new Callback<Job>() {
            @Override
            public void success(Job job, Response response) {
                jobItems = turnToList(job);//转换数据
                itemAdapter.notifyDataSetChanged();

                Snackbar.make(flyLayout,"下拉即可收藏本条校招信息~",Snackbar.LENGTH_SHORT).show();
            }

            @Override
            public void failure(RetrofitError error) {

                Log.e("", "----------------------->" + error.getMessage());
            }
        });
    }

    private List<JobItem> turnToList(Job job) {

        if(job.getDeadline() != null) {
            JobItem jobItem = new JobItem();
            jobItem.setTitle(JobContants.DEADLINE);
            jobItem.setContent(job.getDeadline());
            jobItems.add(jobItem);
        }
        if(job.getWebsite() != null) {
            JobItem jobItem = new JobItem();
            jobItem.setTitle(JobContants.WEBSITE);
            jobItem.setContent(job.getWebsite());
            jobItems.add(jobItem);
        }
        if(job.getPlace() != null) {
            JobItem jobItem = new JobItem();
            jobItem.setTitle(JobContants.PLACE);
            jobItem.setContent(job.getPlace());
            jobItems.add(jobItem);
        }
        if(job.getPosition() != null) {
            JobItem jobItem = new JobItem();
            jobItem.setTitle(JobContants.POSITION);
            jobItem.setContent(job.getPosition());
            jobItems.add(jobItem);
        }
        if(job.getContent() != null) {
            JobItem jobItem = new JobItem();
            jobItem.setTitle(JobContants.CONTENT);
            jobItem.setContent(job.getContent());
            jobItems.add(jobItem);
        }

        return jobItems;
    }

    @Override
    public void onRefresh(FlyRefreshLayout flyRefreshLayout) {
        mHandler.postDelayed(new Runnable() {
            @Override
            public void run() {
                flyLayout.onRefreshFinish();
            }
        }, 1000);
    }

    @Override
    public void onRefreshAnimationEnd(FlyRefreshLayout flyRefreshLayout) {
        putLike();
    }

    private void putLike() {
        aCache = ACache.get(this);
        String username = aCache.getAsString("username");

        Likes likes = new Likes();
        likes.getObjects().add(username);
        Like like = new Like();
        like.setLikes(likes);

        RestClient.getInstance().putLike(objectId, like, new Callback<Object>() {
            @Override
            public void success(Object o, Response response) {
                Snackbar.make(flyLayout,"收藏成功^_^", Snackbar.LENGTH_SHORT).show();
            }

            @Override
            public void failure(RetrofitError error) {
                Snackbar.make(flyLayout, "并没有收藏成功，这逗比功能是在逗我？",Snackbar.LENGTH_SHORT).show();
                Log.e("--------->",error.getMessage() + "");
            }
        });
    }

    /**
     * 收藏--------------->弃用
     */
    private void putCollectionToRest() {
        Collection collection = new Collection();
        collection.setObjectId(objectId);
        CollectionObject collectionObject = new CollectionObject();
        collectionObject.getCollections().add(collection);
        User user = new User();
        user.setCollections(collectionObject);
        aCache = ACache.get(this);

        RestClient.getInstance().putCollection(aCache.getAsString("token"), aCache.getAsString("objectId"), user, new Callback<Object>() {
            @Override
            public void success(Object o, Response response) {
                Snackbar.make(flyLayout,"收藏成功^_^", Snackbar.LENGTH_SHORT).show();
            }

            @Override
            public void failure(RetrofitError retrofitError) {
                Log.e("--------->",retrofitError.getMessage() + "");
            }
        });
    }

    /***
     * 左侧返回按钮监听事件
     * @param item
     * @return
     */
    @Override
    public boolean onOptionsItemSelected(MenuItem item)
    {
        if(item.getItemId() == android.R.id.home)
        {
            finish();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    private class ItemAdapter extends RecyclerView.Adapter<ItemViewHolder> {

        private LayoutInflater mInflater;

        public ItemAdapter(Context context) {
            mInflater = LayoutInflater.from(context);
        }

        @Override
        public ItemViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
            View view = mInflater.inflate(R.layout.item_job_detail, viewGroup, false);
            return new ItemViewHolder(view);
        }

        @Override
        public void onBindViewHolder(ItemViewHolder itemViewHolder, int i) {
            final JobItem item = jobItems.get(i);
            itemViewHolder.title.setText(item.getTitle());
            itemViewHolder.content.setText(item.getContent());
        }

        @Override
        public int getItemCount() {
            return jobItems.size();
        }
    }

    private static class ItemViewHolder extends RecyclerView.ViewHolder {

        TextView title;
        TextView content;

        public ItemViewHolder(View itemView) {
            super(itemView);
            title = (TextView) itemView.findViewById(R.id.item_title);
            content = (TextView) itemView.findViewById(R.id.item_content);
        }

    }
}
