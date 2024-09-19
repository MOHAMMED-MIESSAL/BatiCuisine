package repository.interfaces;

import model.User;
import java.util.List;

public interface UserRepositoryInterface {
    void addUser(User user);
    User getUserById(int id);
    List<User> getAllUsers();
    void updateUser(User user);
    void deleteUser(int id);

}
