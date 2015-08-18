package cn.luchaoqun.campuscalendar.view;

import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;

import butterknife.Bind;
import butterknife.ButterKnife;
import cn.luchaoqun.campuscalendar.R;
import cn.luchaoqun.campuscalendar.commen.BaseActivity;

/**
 * Created by Administrator on 2015/8/11.
 */
public class AboutActivity extends BaseActivity {
    @Bind(R.id.about_toolbar)
    Toolbar aboutToolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_about);

        ButterKnife.bind(this);
        setToolbar();
    }

    private void setToolbar() {
        aboutToolbar.setTitle("关于");
        aboutToolbar.setTitleTextColor(Color.WHITE);
        setSupportActionBar(aboutToolbar);
        getSupportActionBar().setHomeButtonEnabled(true); //设置返回键可用
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
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
}
