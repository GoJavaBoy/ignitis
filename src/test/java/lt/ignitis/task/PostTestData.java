package lt.ignitis.task;

import lt.ignitis.task.model.Post;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import static lt.ignitis.task.model.AbstractBaseEntity.START_SEQ;

public class PostTestData {
    public static final TestMatcher<Post> POST_MATCHER = TestMatcher.usingIgnoringFieldsComparator(Post.class, "user");

    public static final int NOT_FOUND = 10;
    public static final int POST1_ID = START_SEQ + 4;
    public static final int ADMIN_POST_ID = START_SEQ + 11;

    public static final Post post1 = new Post(POST1_ID, "User Post_1", "Text for post 1");
    public static final Post post2 = new Post(POST1_ID + 1, "User Post_2", "Text for post 2");
    public static final Post post3 = new Post(POST1_ID + 2, "User Post_3", "Text for post 3");
    public static final Post post4 = new Post(POST1_ID + 3, "User 1 Post_1", "Text for post 1");
    public static final Post post5 = new Post(POST1_ID + 4, "User 1 Post_2", "Text for post 2");
    public static final Post post6 = new Post(POST1_ID + 5, "User 2 Post_1", "Text for post 1");
    public static final Post post7 = new Post(POST1_ID + 6, "User 2 Post_2", "Text for post 2");
    public static final Post adminPost1 = new Post(ADMIN_POST_ID, "Admin Post_1", "Text for admin post 1");
    public static final Post adminPost2 = new Post(ADMIN_POST_ID + 1, "Admin Post_2", "Text for admin post 2");

    public static final List<Post> userPosts = List.of(post1, post2, post3);
    public static final List<Post> user1Posts = List.of(post4, post5);
    public static final List<Post> user2Posts = List.of(post6, post7);
    public static final List<Post> adminPosts = List.of(adminPost1, adminPost2);

    public static final List<Post> allPosts = List.of(post1, post2, post3, post4, post5, post6, post7, adminPost1, adminPost2);

    public static Post getNew() {
        return new Post(null, "new post TITLE", "new post TEXT");
    }

    public static Post getUpdated() {
        return new Post(POST1_ID, post1.getTitle()+" Updated", "updated Text");
    }
}
