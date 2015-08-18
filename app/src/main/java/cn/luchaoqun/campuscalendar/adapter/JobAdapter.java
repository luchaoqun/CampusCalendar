package cn.luchaoqun.campuscalendar.adapter;

import android.content.Context;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.jude.easyrecyclerview.adapter.BaseViewHolder;
import com.jude.easyrecyclerview.adapter.RecyclerArrayAdapter;

import cn.luchaoqun.campuscalendar.model.Job;

/**
 * Created by Administrator on 2015/8/9.
 */
public class JobAdapter extends RecyclerArrayAdapter<Job> {

    private JobViewHolder jobViewHolder;
    private JobViewHolder.OnItemClickListener onItemClickListener = null;


    public void setOnItemClickListener(JobViewHolder.OnItemClickListener onItemClickListener){
        this.onItemClickListener = onItemClickListener;
    }

    public JobAdapter (Context context) {
        super(context);
    }

    @Override
    public BaseViewHolder OnCreateViewHolder(ViewGroup parent, int viewType) {
        jobViewHolder = new JobViewHolder(parent,onItemClickListener);
        return jobViewHolder;
    }
}
