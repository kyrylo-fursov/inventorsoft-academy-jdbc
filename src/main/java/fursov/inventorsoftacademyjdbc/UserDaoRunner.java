package fursov.inventorsoftacademyjdbc;

import fursov.inventorsoftacademyjdbc.dao.UserDao;
import fursov.inventorsoftacademyjdbc.domain.Role;
import fursov.inventorsoftacademyjdbc.domain.User;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class UserDaoRunner implements CommandLineRunner {
    private final UserDao userDao;

    public UserDaoRunner(UserDao userDao) {
        this.userDao = userDao;
    }

    @Override
    public void run(String... args) throws Exception {
        User user1 = new User(1L, "john@gmail.com", "1234", "John", Role.USER);
        User user2 = new User(2L, "max@gmail.com", "1234", "Max", Role.USER);
        User user3 = new User(2L, "ann@gmail.com", "1234", "Ann", Role.ADMIN);
        User user4 = new User(8L, "kyrylo@gmail.com", "1234", "Kyrylo", Role.ADMIN);

        userDao.addUser(user1);
        userDao.addUser(user2);
        userDao.addUser(user3);
        userDao.addUser(user4);
        userDao.deleteUser(2L);
        userDao.addUser(user3);

        List<User> users = userDao.getAllUsers();
        System.out.println(users);
        userDao.updateUser(2L, new User(2L, "updated", "updated", "updated", Role.USER));
        userDao.updateUser(3L, new User(2L, "updated2", "updated", "updated", Role.USER));
        System.out.println(userDao.getUserByEmail("234"));
        System.out.println(userDao.getUserByEmail("john@gmail.com"));
        System.out.println(userDao.getUserById(2));
        System.out.println(userDao.getUserById(3));
    }
}

