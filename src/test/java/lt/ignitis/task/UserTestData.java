package lt.ignitis.task;

import lt.ignitis.task.model.Role;
import lt.ignitis.task.model.User;
import lt.ignitis.task.web.json.JsonUtil;
import org.assertj.core.api.Assertions;

import java.util.Collections;
import java.util.Date;

import static lt.ignitis.task.PostTestData.*;
import static lt.ignitis.task.model.AbstractBaseEntity.START_SEQ;

public class UserTestData {
    public static final TestMatcher<User> USER_MATCHER = TestMatcher.usingIgnoringFieldsComparator(User.class, "registered", "posts", "password");
    public static TestMatcher<User> USER_WITH_POSTS_MATCHER =
            TestMatcher.usingAssertions(User.class,
//     No need use ignoringAllOverriddenEquals, see https://assertj.github.io/doc/#breaking-changes
                    (a, e) -> Assertions.assertThat(a).usingRecursiveComparison()
                            .ignoringFields("registered", "posts.user", "password").isEqualTo(e),
                    (a, e) -> {
                        throw new UnsupportedOperationException();
                    });

    public static final int USER_ID = START_SEQ;
    public static final int ADMIN_ID = START_SEQ + 3;
    public static final int NOT_FOUND = 10;

    public static final User user = new User(USER_ID,"user@gmail.com", "password", Role.USER);
    public static final User user1 = new User(USER_ID + 1,"user1@gmail.com", "password", Role.USER);
    public static final User user2 = new User(USER_ID + 2,"user2@gmail.com", "password", Role.USER);
    public static final User admin = new User(ADMIN_ID,"admin@gmail.com", "admin", Role.ADMIN, Role.USER);

    static {
        user.setPosts(userPosts);
        user1.setPosts(user1Posts);
        user2.setPosts(user2Posts);
        admin.setPosts(adminPosts);
    }

    public static User getNew() {
        return new User(null, "new@gmail.com", "newPass", new Date(), Collections.singleton(Role.USER));
    }

    public static User getUpdated() {
        User updated = new User(user);
        updated.setEmail("update@gmail.com");
        updated.setPassword("newPass");
        updated.setRoles(Collections.singletonList(Role.ADMIN));
        return updated;
    }

    public static String jsonWithPassword(Object user, String passw) {
        return JsonUtil.writeAdditionProps(user, "password", passw);
    }
}
