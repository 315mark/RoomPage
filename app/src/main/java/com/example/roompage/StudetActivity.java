package com.example.roompage;

import android.os.Bundle;

import com.blankj.utilcode.util.LogUtils;

import com.example.roompage.StudentPageAdapter.OnItemClickListener;
import com.example.roompage.databinding.ModuleRoomPageActivityBinding;

import java.util.Arrays;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.LiveData;
import androidx.paging.LivePagedListBuilder;
import androidx.paging.PagedList;
import androidx.paging.PagedList.Callback;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;

public class StudetActivity extends AppCompatActivity {

    protected ModuleRoomPageActivityBinding databind;
    StudentDao mDao ;
    StudentDataBase mDataBase ;
    StudentPageAdapter mAdapter ;
    LiveData<PagedList<Student>> mLiveData ;
    Executor executor = Executors.newSingleThreadExecutor();

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        databind = DataBindingUtil.setContentView(this, getLayout());
        databind.executePendingBindings();
        databind.setLifecycleOwner(this);

        mAdapter = new StudentPageAdapter();

        databind.recyclerView.setLayoutManager(new LinearLayoutManager(this));
        databind.recyclerView.addItemDecoration(new DividerItemDecoration(this,DividerItemDecoration.VERTICAL));
        databind.recyclerView.setAdapter(mAdapter);

        mDao = StudentDataBase.getInstance(this).getStudentDao();
        mLiveData = new LivePagedListBuilder<>(mDao.getAllStudents(),20).build();
        mLiveData.observe(this, (PagedList<Student> students) ->{
            mAdapter.submitList(students);
            LogUtils.d(" 数据新增后是否还会执行.... " + students.toString());
            students.addWeakCallback(null, new Callback(){
                @Override
                public void onChanged(int position, int count) {
                    LogUtils.d("onChanged" + position );
                }

                @Override
                public void onInserted(int position, int count){
                }

                @Override
                public void onRemoved(int position, int count){
                }
            });
        });

        mAdapter.setOnItemClickListener(new OnItemClickListener<Student>() {
            @Override
            public void onItemClick(Student item) {
                LogUtils.i(" item 点击事件监听 ");
//                mDao.insertStudents(item);
            }
        });

        databind.buttonPopulate.setOnClickListener(view -> {
            Student[] students = new Student[100];
            for (int i = 0; i < students.length; i++){
                Student student = new Student();
                student.studentNumber= i;
                students[i] = student;
            }
            LogUtils.e(" 数据... " + Arrays.toString(students));
            executor.execute(() -> {
                mDao.insertStudents(students);
            });
        });

        databind.buttonClear.setOnClickListener(view -> {
           executor.execute(() -> {
               mDao.clearAll();
           });
        });
    }

    private int getLayout() {
        return R.layout.module_room_page_activity ;
    }
}

