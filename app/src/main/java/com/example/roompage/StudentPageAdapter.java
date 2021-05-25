package com.example.roompage;

import android.annotation.SuppressLint;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.paging.PagedListAdapter;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.RecyclerView;

//继承Databing ViewHolder 需要改成下面这种写法  不然数据不会刷新  BaseListAdapter
public class StudentPageAdapter extends PagedListAdapter<Student, StudentPageAdapter.MyViewHolder> {

    private OnItemClickListener<Student> listener;

    public StudentPageAdapter() {
        super(mDiffCallback);
    }

    /*
    @Override
    protected int getLayoutResId(int viewType) {
        return R.layout.module_room_cell;
    }

    @Override
    protected void onBindItem(ModuleRoomCellBinding binding, Student item) {
        LogUtils.i(" Adapter " +item.toString());
        binding.setModel(item);
        binding.executePendingBindings();
    }*/

    private static final DiffUtil.ItemCallback<Student> mDiffCallback = new DiffUtil.ItemCallback<Student>() {
        @Override
        public boolean areItemsTheSame(@NonNull Student oldItem, @NonNull Student newItem) {
            return oldItem.id == newItem.id;
        }

        @Override
        public boolean areContentsTheSame(@NonNull Student oldItem, @NonNull Student newItem) {
            return oldItem.studentNumber == newItem.studentNumber;
        }
    };

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view = inflater.inflate(R.layout.module_room_cell, parent, false);
        return new MyViewHolder(view);
    }

    @Override
    @SuppressLint("SetTextI18n")
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        Student student = getItem(position);
        if (student == null) {
            holder.textView.setText("loading");
        } else {
            holder.textView.setText(String.valueOf(student.studentNumber));
        }
        listener.onItemClick(getItem(position));
    }

    static class MyViewHolder extends RecyclerView.ViewHolder {
        TextView textView;

        MyViewHolder(@NonNull View itemView) {
            super(itemView);
            textView = itemView.findViewById(R.id.textView);
        }
    }

    public void setOnItemClickListener(OnItemClickListener<Student> listener) {
        this.listener = listener;
    }

    public interface OnItemClickListener<Student> {
        void onItemClick(Student item);
    }
}
