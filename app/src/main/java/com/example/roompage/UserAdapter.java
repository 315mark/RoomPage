package com.example.roompage;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import com.example.roompage.databinding.ItemUserBinding;
import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.databinding.ViewDataBinding;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.RecyclerView;

// 若是使用paging 则需使用 ViewModel 以及 room进行配合使用
public class UserAdapter extends BaseBindingAdapter<User, ItemUserBinding> {

    public UserAdapter(Context context) {
        super(context);
    }

    @Override
    protected int getLayoutResId(int viewType) {
        return R.layout.item_user;
    }

    @Override
    protected void onBindItem(ItemUserBinding binding, User user){
        binding.setModel(user);
        binding.executePendingBindings();
    }

    private static final DiffUtil.ItemCallback<User> diffCallback = new DiffUtil.ItemCallback<User>() {
        @Override
        public boolean areItemsTheSame(@NonNull User oldItem, @NonNull User newItem) {
            return oldItem.age == newItem.age;
        }

        @Override
        public boolean areContentsTheSame(@NonNull User oldItem, @NonNull User newItem) {
            return oldItem.name.equals(newItem.name);
        }
    };
}
