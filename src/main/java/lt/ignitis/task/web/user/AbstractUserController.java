package lt.ignitis.task.web.user;

import lt.ignitis.task.model.User;
import lt.ignitis.task.service.UserService;
import lt.ignitis.task.to.UserTo;
import lt.ignitis.task.util.UserUtil;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

import static lt.ignitis.task.util.ValidationUtil.assureIdConsistent;
import static lt.ignitis.task.util.ValidationUtil.checkNew;

public abstract class AbstractUserController {

    @Autowired
    private UserService service;

    public List<User> getAll() {
        return service.getAll();
    }

    public User get(int id) {
        return service.get(id);
    }

    public User create(User user) {
        checkNew(user);
        return service.create(user);
    }

    public User create(UserTo userTo) {
        checkNew(userTo);
        return service.create(UserUtil.createNewFromTo(userTo));
    }

    public void delete(int id) {
        service.delete(id);
    }

    public void update(User user, int id) {
        assureIdConsistent(user, id);
        service.update(user);
    }

    public void update(UserTo userTo, int id) {
        assureIdConsistent(userTo, id);
        service.update(userTo);
    }

    public User getByMail(String email) {
        return service.getByEmail(email);
    }
}
