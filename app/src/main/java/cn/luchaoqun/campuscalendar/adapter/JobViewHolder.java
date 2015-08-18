package cn.luchaoqun.campuscalendar.adapter;

import android.net.Uri;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.balysv.materialripple.MaterialRippleLayout;
import com.facebook.drawee.view.SimpleDraweeView;
import com.jude.easyrecyclerview.adapter.BaseViewHolder;

import cn.luchaoqun.campuscalendar.R;
import cn.luchaoqun.campuscalendar.model.Job;

/**
 * Created by Administrator on 2015/8/9.
 */
public class JobViewHolder extends BaseViewHolder<Job> {

    private SimpleDraweeView jobIcon;
    private TextView jobCompany;
    private TextView jobDeadline;
    private MaterialRippleLayout materialRippleLayout;

    private OnItemClickListener onItemClickListener = null;
    public interface OnItemClickListener{
        public void OnItemClick(View view, Job job);
    }

    public JobViewHolder(ViewGroup parent,OnItemClickListener onItemClickListener) {
        super(parent, R.layout.item_job);
        jobIcon = $(R.id.job_icon);
        jobCompany = $(R.id.job_company);
        jobDeadline = $(R.id.job_deadline);
        materialRippleLayout = $(R.id.ripple);
        this.onItemClickListener = onItemClickListener;
    }

    public void setData(final Job job) {
        jobCompany.setText(job.getCompany());
        jobDeadline.setText(job.getDeadline());
        jobIcon.setImageURI(Uri.parse(job.getIcon()));
        materialRippleLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onItemClickListener.OnItemClick(view, job);
            }
        });
    }
}
