package cn.luchaoqun.campuscalendar.view;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.support.design.widget.NavigationView;
import android.support.design.widget.Snackbar;
import android.support.v4.widget.DrawerLayout;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.TextView;

import com.facebook.drawee.backends.pipeline.Fresco;
import com.jude.easyrecyclerview.EasyRecyclerView;
import com.jude.easyrecyclerview.adapter.RecyclerArrayAdapter;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import cn.luchaoqun.campuscalendar.R;
import cn.luchaoqun.campuscalendar.adapter.JobAdapter;
import cn.luchaoqun.campuscalendar.adapter.JobViewHolder;
import cn.luchaoqun.campuscalendar.commen.ACache;
import cn.luchaoqun.campuscalendar.commen.BaseActivity;
import cn.luchaoqun.campuscalendar.contants.HomeContants;
import cn.luchaoqun.campuscalendar.contants.RestContants;
import cn.luchaoqun.campuscalendar.model.Collection;
import cn.luchaoqun.campuscalendar.model.CollectionRelation;
import cn.luchaoqun.campuscalendar.model.CollectionRequest;
import cn.luchaoqun.campuscalendar.model.Job;
import cn.luchaoqun.campuscalendar.model.JobModel;
import cn.luchaoqun.campuscalendar.model.Like;
import cn.luchaoqun.campuscalendar.model.User;
import cn.luchaoqun.campuscalendar.model.UserReturn;
import cn.luchaoqun.campuscalendar.rest.RestClient;
import retrofit.Callback;
import retrofit.RetrofitError;
import retrofit.client.Response;

/**
 * Create by Luchaoqun on 2015/8/7 13:30
 *
 * @author Luchaoqun
 */

public class HomeActivity extends BaseActivity implements Toolbar.OnMenuItemClickListener,SearchView.OnQueryTextListener,
        NavigationView.OnNavigationItemSelectedListener,RecyclerArrayAdapter.OnLoadMoreListener,
        SwipeRefreshLayout.OnRefreshListener, JobViewHolder.OnItemClickListener {

    @Bind(R.id.home_toolbar)
    Toolbar homeToolbar;
    @Bind(R.id.home_drawer)
    DrawerLayout homeDrawer;
    @Bind(R.id.home_nav)
    NavigationView homeNav;
    @Bind(R.id.home_recycler)
    EasyRecyclerView homeRecycler;
    @Bind(R.id.home_main)
    FrameLayout homeMain;
    @Bind(R.id.home_drawer_username)
    TextView usernameTxt;

    private ActionBarDrawerToggle mDrawerToggle;
    private JobAdapter adapter;
    private List<Job> jobs = new ArrayList<>();

    private boolean isCollection = false;
    private int page = 0;
    private ACache aCache;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Fresco.initialize(this);
        aCache = ACache.get(this);
        if(aCache.getAsString("token") != null) {
            setContentView(R.layout.activity_home);

            ButterKnife.bind(this);

            setStatusForKITKAT(homeMain);//设置API 19沉浸式效果
            setDrawerAndToolbar();

            initEvent();

            initRecyclerView();//初始化列表
            getJobsFromRest();//第一次请求
        } else {
            Intent registerIntent = new Intent(this,RegisterActivity.class);
            startActivity(registerIntent);
        }
    }

    private void initRecyclerView() {
        homeRecycler.setLayoutManager(new LinearLayoutManager(this));
        homeRecycler.setAdapterWithProgress(adapter = new JobAdapter(this));
        adapter.setMore(R.layout.view_more, this);
        adapter.setNoMore(R.layout.view_nomore);
        adapter.setOnItemClickListener(this);
    }

    /**
     * item点击事件
     * @param view
     */
    @Override
    public void OnItemClick(View view,Job job) {
        Intent jobDetailIntent = new Intent(HomeActivity.this, JobDetailActivity.class);
        int position = adapter.getPosition(job);
        jobDetailIntent.putExtra("id",jobs.get(position).getObjectId());
        jobDetailIntent.putExtra("company",jobs.get(position).getCompany());
        startActivity(jobDetailIntent);
    }

    @Override
    public void onLoadMore() {
        if(!isCollection) {
            getJobsFromRest();
        }
    }

    @Override
    public void onRefresh() {
        if(isCollection) {
            getLikes();
        } else {
            page=0;
            getJobsFromRest();
        }
    }

    /**
     * 请求网络数据
     */
    public void getJobsFromRest(){
        RestClient.getInstance().getJobs(RestContants.LIST_LIMIT, RestContants.LIST_LIMIT * page, RestContants.LIST_ORDER, new Callback<JobModel>() {
            @Override
            public void success(JobModel jobModel, Response response) {
                page++;
                if (page == 1) {
                    jobs.clear();
                    adapter.clear();
                }
                jobs.addAll(jobModel.getResults());
                if (jobModel.getResults().isEmpty()) adapter.stopMore();
                adapter.addAll(jobModel.getResults());
            }

            @Override
            public void failure(RetrofitError error) {
                Log.e("error", error.getMessage()+"");
            }
        });
    }

    private void initEvent() {
        homeToolbar.setOnMenuItemClickListener(this);
        homeNav.setNavigationItemSelectedListener(this);
        homeRecycler.setRefreshListener(this);
    }

    /**
     * init Toolbar and DrawerNav
     */
    private void setDrawerAndToolbar() {
        homeToolbar.setTitle(R.string.app_name);
        homeToolbar.setTitleTextColor(Color.WHITE);
        setSupportActionBar(homeToolbar);

        getSupportActionBar().setHomeButtonEnabled(true); //设置返回键可用
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        homeDrawer.setDrawerLockMode(DrawerLayout.LOCK_MODE_UNLOCKED);
        mDrawerToggle = new ActionBarDrawerToggle(this, homeDrawer, homeToolbar, R.string.app_name, R.string.app_name);
        mDrawerToggle.syncState();
        homeDrawer.setDrawerListener(mDrawerToggle);
        usernameTxt.setText(aCache.getAsString("username"));
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_home, menu);
        MenuItem searchItem = menu.findItem(R.id.action_search);
        SearchView searchView = (SearchView) searchItem.getActionView();
        searchView.setOnQueryTextListener(this);// 为该SearchView组件设置事件监听器
        searchView.setQueryHint("请输入搜索内容");// 设置该SearchView内默认显示的提示文本
        return true;
    }

    @Override
    public boolean onQueryTextSubmit(String query) {
        adapter.clear();
        Iterator it = jobs.iterator();
        while (it.hasNext()) {
            Job item = (Job)it.next();
            if(item.getCompany().contains(query) || item.getDeadline().contains(query)) {
                adapter.add(item);
            }
        }
        return false;
    }

    @Override
    public boolean onQueryTextChange(String newText) {
        adapter.clear();
        Iterator it = jobs.iterator();
        while (it.hasNext()) {
            Job item = (Job)it.next();
            if(item.getCompany().contains(newText) || item.getDeadline().contains(newText)) {
                adapter.add(item);
            }
        }
        return false;
    }

    //阶段性弃用
    @Override
    public boolean onMenuItemClick(MenuItem menuItem) {
        switch (menuItem.getItemId()) {
            case R.id.action_search:
                break;
        }
        return true;
    }

    /**
     * 抽屉菜单点击事件
     * @param menuItem
     * @return
     */
    @Override
    public boolean onNavigationItemSelected(MenuItem menuItem) {
        menuItem.setChecked(true);
        homeDrawer.closeDrawers();
        switch (menuItem.getItemId()){
            case R.id.nav_home:
                homeToolbar.setTitle(HomeContants.NAV_CAMPUS_CALENDAR);
                isCollection = false;
                page=0;
                getJobsFromRest();
                break;
            case R.id.nav_collection:
                homeToolbar.setTitle(HomeContants.NAV_COLLECTION);
                isCollection = true;
                getLikes();
                break;
            case R.id.nav_exit:
                finish();
                break;
            case R.id.nav_feedback:
                Intent feedbackIntent = new Intent(HomeActivity.this, FeedbackActivity.class);
                startActivity(feedbackIntent);
                break;
            case R.id.nav_about:
                Intent aboutIntent = new Intent(HomeActivity.this, AboutActivity.class);
                startActivity(aboutIntent);
                break;
            default:
                break;
        }
        return true;
    }

    private void getLikes() {
        String username = aCache.getAsString("username");
        String like = "{\"likes\":\"" + username + "\"}";
        RestClient.getInstance().getJobLikes(like, new Callback<JobModel>() {
            @Override
            public void success(JobModel jobModel, Response response) {
                jobs.clear();
                jobs = jobModel.getResults();
                adapter.clear();
                adapter.addAll(jobs);
                adapter.stopMore();
                Log.w("----------->", "收藏请求成功");
            }

            @Override
            public void failure(RetrofitError error) {
                Snackbar.make(homeRecycler, "收藏请求失败，发条反馈给作者吧#_#", Snackbar.LENGTH_SHORT).show();
                Log.w("----------->",error.getMessage() + "收藏请求失败");
            }
        });
    }

    /**
     * 暂时弃用
     */
    private void getCollectionsFromRest() {
        String objectId = aCache.getAsString("objectId");
        Collection collection = new Collection();
        collection.setObjectId(objectId);
        CollectionRelation collectionRelation = new CollectionRelation();
        collectionRelation.setObject(collection);
        CollectionRequest collectionRequest = new CollectionRequest();
        collectionRequest.set$relatedTo(collectionRelation);
        RestClient.getInstance().getUser(collectionRequest, new Callback<UserReturn>() {
            @Override
            public void success(UserReturn user, Response response) {
                jobs.clear();
                jobs = user.getJobs();
                adapter.clear();
                adapter.addAll(jobs);
            }

            @Override
            public void failure(RetrofitError retrofitError) {

            }
        });
    }
}
