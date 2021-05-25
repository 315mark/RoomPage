package com.example.roompage;

import android.view.LayoutInflater;
import android.view.ViewGroup;
import com.blankj.utilcode.util.LogUtils;
import androidx.annotation.LayoutRes;
import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.databinding.ObservableArrayList;
import androidx.databinding.ViewDataBinding;
import androidx.paging.PagedListAdapter;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.RecyclerView;

public abstract class BaseListAdapter<M, VDB extends ViewDataBinding> extends PagedListAdapter<M, RecyclerView.ViewHolder>
        implements IListChangeCallback<ObservableArrayList<M>> {

    protected ObservableArrayList<M> mItems;
    private final ListChangedCallbackProxy mItemsChangeCallback;
    protected OnItemClickListener<M> mItemClickListener;

    public BaseListAdapter(DiffUtil.ItemCallback<M> mDiffCallback){
        super(mDiffCallback);
        this.mItems = new ObservableArrayList<>();
        this.mItemsChangeCallback = new ListChangedCallbackProxy(this);
    }

    public ObservableArrayList<M> getItems(){
        return mItems;
    }

    @Override
    public int getItemCount() {
        return this.mItems.size();
    }

    protected abstract @LayoutRes int getLayoutResId(int viewType);

    protected abstract void onBindItem(VDB binding, M item);

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LogUtils.d(" onCreateViewHolder " +viewType);
        VDB binding = DataBindingUtil.inflate(LayoutInflater.from(parent.getContext()), this.getLayoutResId(viewType), parent, false);
        return new BaseBindingViewHolder<VDB>(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        VDB binding = DataBindingUtil.getBinding(holder.itemView);
        this.onBindItem(binding, this.mItems.get(position));
        binding.getRoot().setOnClickListener(view -> {
            mItemClickListener.onItemClick(mItems.get(position));
        });
    }

    @Override
    public void onAttachedToRecyclerView(@NonNull RecyclerView recyclerView) {
        super.onAttachedToRecyclerView(recyclerView);
        this.mItems.addOnListChangedCallback(mItemsChangeCallback);
    }

    @Override
    public void onDetachedFromRecyclerView(@NonNull RecyclerView recyclerView) {
        super.onDetachedFromRecyclerView(recyclerView);
        this.mItems.removeOnListChangedCallback(mItemsChangeCallback);
    }

    public void onRefreshCompleted(){

    }

    public void onLoadMoreCompleted(){

    }

    public void setItemClickListener(OnItemClickListener<M> listener) {
        this.mItemClickListener = listener;
    }

    //region 处理数据集变化
    @Override
    public void onChanged(ObservableArrayList<M> sender) {
        notifyDataSetChanged();
    }

    @Override
    public void onItemRangeChanged(ObservableArrayList<M> sender, int positionStart, int itemCount) {
        notifyItemRangeChanged(positionStart, itemCount);
    }

    @Override
    public void onItemRangeInserted(ObservableArrayList<M> sender, int positionStart, int itemCount) {
        notifyItemRangeInserted(positionStart, itemCount);
        notifyItemRangeChanged(positionStart + itemCount, mItems.size() - positionStart - itemCount);
    }

    @Override
    public void onItemRangeMoved(ObservableArrayList<M> sender, int fromPosition, int toPosition, int itemCount) {
        notifyDataSetChanged();
    }

    @Override
    public void onItemRangeRemoved(ObservableArrayList<M> sender, int positionStart, int itemCount) {
        notifyItemRangeRemoved(positionStart, itemCount);
        notifyItemRangeChanged(positionStart, mItems.size() - positionStart);
    }

    //endregion

    class ListChangedCallbackProxy extends ObservableArrayList.OnListChangedCallback<ObservableArrayList<M>> {
        private final IListChangeCallback<ObservableArrayList<M>> mBase;

        ListChangedCallbackProxy(IListChangeCallback<ObservableArrayList<M>> base) {
            this.mBase = base;
        }

        @Override
        public void onChanged(ObservableArrayList<M> sender) {
            mBase.onChanged(sender);
        }

        @Override
        public void onItemRangeChanged(ObservableArrayList<M> sender, int positionStart, int itemCount) {
            mBase.onItemRangeChanged(sender, positionStart, itemCount);
        }

        @Override
        public void onItemRangeInserted(ObservableArrayList<M> sender, int positionStart, int itemCount) {
            mBase.onItemRangeInserted(sender, positionStart, itemCount);
        }

        @Override
        public void onItemRangeMoved(ObservableArrayList<M> sender, int fromPosition, int toPosition, int itemCount) {
            mBase.onItemRangeMoved(sender, fromPosition, toPosition, itemCount);
        }

        @Override
        public void onItemRangeRemoved(ObservableArrayList<M> sender, int positionStart, int itemCount) {
            mBase.onItemRangeRemoved(sender, positionStart, itemCount);
        }
    }

    static class BaseBindingViewHolder<VDB extends ViewDataBinding> extends RecyclerView.ViewHolder {
        VDB dataBinding;
        public BaseBindingViewHolder(VDB itemView) {
            super(itemView.getRoot());
            LogUtils.i(" BaseBindingViewHolder 这个方法 " );
            dataBinding = itemView ;
        }
    }

    public interface OnItemClickListener<M> {
        void onItemClick(M item);
    }
}