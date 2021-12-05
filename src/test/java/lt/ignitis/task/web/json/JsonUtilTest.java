package lt.ignitis.task.web.json;

import lt.ignitis.task.model.Post;
import org.junit.jupiter.api.Test;

import java.util.List;

import static lt.ignitis.task.PostTestData.*;

public class JsonUtilTest {
    @Test
    void readWriteValue() {
        String json = JsonUtil.writeValue(adminPost1);
        System.out.println(json);
        Post post = JsonUtil.readValue(json, Post.class);
        POST_MATCHER.assertMatch(post, adminPost1);
    }

    @Test
    void readWriteValues() {
        String json = JsonUtil.writeValue(adminPosts);
        System.out.println(json);
        List<Post> posts = JsonUtil.readValues(json, Post.class);
        POST_MATCHER.assertMatch(posts, adminPosts);
    }
}
