package com.team.newsdemo.ui.news;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.team.newsdemo.R;
import com.team.newsdemo.model.NewsResponse;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Android_ZzT on 17/8/8.
 */

public class NewsAdapter extends RecyclerView.Adapter<NewsAdapter.NewsViewHolder> {

    public interface OnItemClickListener {

        void onItemClick(View view, int position);

    }

    private static final int TYPE_BANNER = 0;
    private static final int TYPE_NORMAL = 1;

    private List<NewsResponse.New> data;

    private OnItemClickListener mListener;

    public NewsAdapter(List<NewsResponse.New> data) {
        this.data = data;
    }

    @Override
    public NewsViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View itemView = inflater.inflate(R.layout.item_news_common, parent, false);
        return new NewsViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(NewsViewHolder holder, final int position) {
        NewsResponse.New n = data.get(position);
        if (getItemViewType(position) == TYPE_BANNER) {
            int height = holder.ivIcon.getContext().getResources().getDimensionPixelSize(R.dimen.height_128);
            holder.tvLayout.setVisibility(View.GONE);
            RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, height *3 / 2);
            holder.ivIcon.setLayoutParams(params);
            holder.ivIcon.setScaleType(ImageView.ScaleType.FIT_XY);
        } else {
            holder.tvTitle.setText(n.getTitle());
            holder.tvCategory.setText(n.getCategory());
        }
        Glide.with(holder.ivIcon.getContext())
                .load(n.getThumb_url())
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .crossFade(300)
                .placeholder(R.color._bkgd__gray_light)
                .into(holder.ivIcon);

        holder.root.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mListener != null) {
                    mListener.onItemClick(v, position);
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return data == null ? 0 : data.size();
    }

    @Override
    public int getItemViewType(int position) {
        return data.get(position).getId() == 1 ? TYPE_BANNER : TYPE_NORMAL;
    }

    public void setData(List<NewsResponse.New> data) {
        this.data = data;
        notifyDataSetChanged();
    }

    public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
        mListener = onItemClickListener;
    }

    static class NewsViewHolder extends RecyclerView.ViewHolder {

        View root;
        @BindView(R.id.ll_layout)
        View tvLayout;
        @BindView(R.id.iv_icon)
        ImageView ivIcon;
        @BindView(R.id.tv_title)
        TextView tvTitle;
        @BindView(R.id.tv_category)
        TextView tvCategory;

        public NewsViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
            root = itemView;
        }
    }
}
