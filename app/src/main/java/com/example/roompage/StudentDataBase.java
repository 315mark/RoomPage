package com.example.roompage;

import android.content.Context;

import androidx.annotation.NonNull;
import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.room.migration.Migration;
import androidx.sqlite.db.SupportSQLiteDatabase;

// exportSchema 这个字段添加避免room 运行报错
@Database(entities = {Student.class},version = 1, exportSchema = false)
public abstract class StudentDataBase extends RoomDatabase {

    private static volatile StudentDataBase dataBase;

    public static StudentDataBase getInstance(Context context) {
        if (dataBase == null) {
            dataBase = Room.databaseBuilder(context, StudentDataBase.class ,"student_database")
                    .allowMainThreadQueries()  //放在主线程执行  看需求  数据量大 会anr 卡顿
//                    .fallbackToDestructiveMigration()   数据库强制升级  删除原先数据清空
//                    .addMigrations(migration_1_2,migration_2_3,migration_3_4)   //此处就是进行数据库升级
                    .build();
        }
        return dataBase;
    }


    //数据库 bean 类 进行升级删除字段  手机程序对应版本1 修改升级版本2
    static final Migration migration_1_2 = new Migration(1,2) {
        @Override
        public void migrate(@NonNull SupportSQLiteDatabase database) {
            // 为旧表添加新的字段
            database.execSQL("ALTER TABLE user ADD age INTEGER Default 0 not null ");
        }
    };

    static final Migration migration_2_3 = new Migration(1,2) {
        @Override
        public void migrate(@NonNull SupportSQLiteDatabase database) {
            //创建新的数据表
            database.execSQL("CREATE TABLE IF NOT EXISTS `book` (`id` INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL, `name` TEXT, `address` TEXT)");
        }
    };

    static final Migration migration_3_4 = new Migration(1,2) {
        @Override
        public void migrate(@NonNull SupportSQLiteDatabase database) {
            //创建一张符合我们要求的临时表temp_Student
            //将数据从旧表Student拷贝至临时表temp_Student
            //删除旧表Student
            //将临时表temp_Student重命名为Student
            database.execSQL("CREATE TABLE temp_Student (" +
                    "id INTEGER PRIMARY KEY NOT NULL," +
                    "name TEXT," +
                    "age TEXT)");
            database.execSQL("INSERT INTO temp_Student (id, name, age) " +
                    "SELECT id, name, age FROM Student");
            database.execSQL("DROP TABLE Student");
            database.execSQL("ALTER TABLE temp_Student RENAME TO Student");
        }
    };

    public abstract StudentDao getStudentDao();


}
