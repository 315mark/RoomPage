package com.example.roompage;

import com.example.roompage.databinding.ModuleRoomCellBinding;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.DiffUtil;

public class UserInfoAdapter extends BaseListAdapter<Student, ModuleRoomCellBinding>{

    public UserInfoAdapter() {
        super(mDiffCallback);
    }

    @Override
    protected int getLayoutResId(int viewType) {
        return R.layout.module_room_cell;
    }

    @Override
    protected void onBindItem(ModuleRoomCellBinding binding, Student item) {
        binding.setModel(item);
        binding.executePendingBindings();
    }

/*  @Override
    protected void onBindItem(ItemUserBinding binding, Student item) {
        binding.setModel(item);
        binding.executePendingBindings();
    }*/

   final static DiffUtil.ItemCallback<Student> mDiffCallback = new DiffUtil.ItemCallback<Student>() {
       @Override
       public boolean areItemsTheSame(@NonNull Student oldItem, @NonNull Student newItem) {
           return oldItem.id == newItem.id;
       }

       @Override
       public boolean areContentsTheSame(@NonNull Student oldItem, @NonNull Student newItem) {
           return oldItem.studentNumber == newItem.studentNumber;
       }
   };
}
