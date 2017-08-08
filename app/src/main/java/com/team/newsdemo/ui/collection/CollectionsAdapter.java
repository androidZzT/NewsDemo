package com.team.newsdemo.ui.collection;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.team.newsdemo.R;
import com.team.newsdemo.model.NewsResponse;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Android_ZzT on 17/8/9.
 */

public class CollectionsAdapter extends RecyclerView.Adapter<CollectionsAdapter.CollectionViewHolder> {


    public interface OnItemClickListener {

        void onItemClick(View view, int position);

    }

    private List<NewsResponse.New> data;

    private OnItemClickListener mListener;

    public CollectionsAdapter(List<NewsResponse.New> data) {
        this.data = data;
    }

    @Override
    public CollectionViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View itemView = inflater.inflate(R.layout.item_news_common, parent, false);
        return new CollectionViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(CollectionViewHolder holder, final int position) {
        NewsResponse.New n = data.get(position);
        holder.tvTitle.setText(n.getTitle());
        holder.tvCategory.setText(n.getCategory());

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

    public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
        mListener = onItemClickListener;
    }

    public void setData(List<NewsResponse.New> data) {
        this.data = data;
        notifyDataSetChanged();
    }

    static class CollectionViewHolder extends RecyclerView.ViewHolder {

        View root;
        @BindView(R.id.tv_title)
        TextView tvTitle;
        @BindView(R.id.tv_category)
        TextView tvCategory;
        @BindView(R.id.iv_icon)
        ImageView ivIcon;

        public CollectionViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
            ivIcon.setVisibility(View.GONE);
            root = itemView;
        }
    }

}
