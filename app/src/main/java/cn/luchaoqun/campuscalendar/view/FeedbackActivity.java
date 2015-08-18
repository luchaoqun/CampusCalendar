package cn.luchaoqun.campuscalendar.view;

import android.graphics.Color;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.EditText;

import butterknife.Bind;
import butterknife.ButterKnife;
import cn.luchaoqun.campuscalendar.R;
import cn.luchaoqun.campuscalendar.commen.BaseActivity;
import cn.luchaoqun.campuscalendar.model.Feedback;
import cn.luchaoqun.campuscalendar.rest.RestClient;
import retrofit.Callback;
import retrofit.RetrofitError;
import retrofit.client.Response;

/**
 * Created by Administrator on 2015/8/10.
 */
public class FeedbackActivity extends BaseActivity implements Toolbar.OnMenuItemClickListener {
    @Bind(R.id.feedback_toolbar)
    Toolbar feedbackToolbar;
    @Bind(R.id.feedback_content)
    EditText feedbackContent;
    @Bind(R.id.feedback_connect)
    EditText feedbackConnect;

    private String content;
    private String connect;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_feedback);
        ButterKnife.bind(this);

        setToolbar();
        initEvent();
    }

    private void initEvent() {
        feedbackToolbar.setOnMenuItemClickListener(this);
    }

    private void setToolbar() {
        feedbackToolbar.setTitle("意见反馈");
        feedbackToolbar.setTitleTextColor(Color.WHITE);
        setSupportActionBar(feedbackToolbar);
        getSupportActionBar().setHomeButtonEnabled(true); //设置返回键可用
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_feedback, menu);
        return true;
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

    @Override
    public boolean onMenuItemClick(MenuItem menuItem) {
        switch (menuItem.getItemId()) {
            case R.id.feedback_commit:
                content = feedbackContent.getText().toString();
                connect = feedbackConnect.getText().toString();
                if(content.length() == 0){
                    Snackbar.make(feedbackContent,"请填写反馈内容^_^",Snackbar.LENGTH_SHORT).show();
                } else if(connect.length() == 0){
                    Snackbar.make(feedbackContent,"请填写联系方式^_^",Snackbar.LENGTH_SHORT).show();
                } else {
                    Feedback feedback = new Feedback();
                    feedback.setContent(content);
                    feedback.setConnect(connect);

                    RestClient.getInstance().postFeedback(feedback, new Callback<Object>() {
                        @Override
                        public void success(Object o, Response response) {
                            Snackbar.make(feedbackContent,"反馈成功^_^",Snackbar.LENGTH_SHORT).show();
                        }

                        @Override
                        public void failure(RetrofitError error) {
                            Snackbar.make(feedbackContent,"反馈失败，这是为什么呢#_#",Snackbar.LENGTH_SHORT).show();
                        }
                    });
                }

                break;
        }
        return true;
    }
}
