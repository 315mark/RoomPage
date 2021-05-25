package com.example.roompage;

import android.os.Bundle;

import com.blankj.utilcode.util.LogUtils;

import java.util.Arrays;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

public class MainActivity extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        RecyclerView recyclerView = (RecyclerView) findViewById(R.id.recycler);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        UserAdapter adapter = new UserAdapter(this);
        recyclerView.setAdapter(adapter);

        UserInfoAdapter adapter1 = new UserInfoAdapter();
        recyclerView.setAdapter(adapter1);

        adapter.getItems().add(new User("张三", 18));
        adapter.getItems().add(new User("李四", 28));
        adapter.getItems().add(new User("王五", 38));
        adapter.getItems().add(new User("赵勾", 38));
        adapter.getItems().add(new User("王炸", 38));
        adapter.getItems().add(new User("飞机", 38));
        adapter.getItems().add(new User("毛台", 38));
        adapter.getItems().add(new User("白给", 38));
        adapter.getItems().add(new User("花生", 38));

        User[] users = new User[10];
        for (int i =0; i<10;i++){
            User user = new User();
            user.age= i;
            user.name = "童鞋" +i ;
            users[i] = user;
        }

//        adapter1.getItems().addAll(Arrays.asList(users));

        adapter.setItemClickListener(new BaseBindingAdapter.OnItemClickListener<User>() {
            @Override
            public void onItemClick(User item) {
                LogUtils.d("进入点击跳转界面 ");

            }
        });

       /* Uri data = getIntent().getData();
        String param = data.getQueryParameter("goodsId");//获取指定key下的参数
        String param2 = data.getQueryParameter("userId");//获取指定key下的参数*/
    }
}