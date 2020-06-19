package com.example.datn_2020.model.db;

        import androidx.room.Database;
        import androidx.room.RoomDatabase;

        import com.example.datn_2020.model.dao.UserDAO;
        import com.example.datn_2020.model.entity.User;

@Database(entities = {User.class},version = 2,exportSchema = false)
public abstract class AppDatabase extends RoomDatabase {
    public abstract UserDAO userDAO();
}
