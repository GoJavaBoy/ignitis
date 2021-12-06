package lt.ignitis.task.web.post;

import lt.ignitis.task.PostTestData;
import lt.ignitis.task.model.Post;
import lt.ignitis.task.service.PostService;
import lt.ignitis.task.util.exception.NotFoundException;
import lt.ignitis.task.web.AbstractControllerTest;
import lt.ignitis.task.web.json.JsonUtil;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import static lt.ignitis.task.PostTestData.*;
import static lt.ignitis.task.TestUtil.readFromJson;
import static lt.ignitis.task.TestUtil.userHttpBasic;
import static lt.ignitis.task.UserTestData.*;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

class AdminPostControllerTest extends AbstractControllerTest {

    private static final String REST_URL = AdminPostController.REST_URL + '/';

    @Autowired
    private PostService postService;

    @Test
    void deleteByPostId() throws Exception {
        perform(MockMvcRequestBuilders.delete(REST_URL + ADMIN_POST_ID)
                .with(userHttpBasic(admin)))
                .andDo(print())
                .andExpect(status().isNoContent());
        assertThrows(NotFoundException.class, () -> postService.get(ADMIN_POST_ID, ADMIN_ID));
    }

    @Test
    void get() throws Exception {
        perform(MockMvcRequestBuilders.get(REST_URL + ADMIN_POST_ID)
                .with(userHttpBasic(admin)))
                .andExpect(status().isOk())
                .andDo(print())
                // https://jira.spring.io/browse/SPR-14472
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(POST_MATCHER.contentJson(adminPost1));
    }

    @Test
    void getAllByUserId() throws Exception {
        perform(MockMvcRequestBuilders.get(REST_URL + "byuser/" + USER_ID)
                .with(userHttpBasic(admin)))
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(POST_MATCHER.contentJson(post1, post2, post3));
    }

    @Test
    void getAll() throws Exception {
        perform(MockMvcRequestBuilders.get(REST_URL)
                .with(userHttpBasic(admin)))
                .andExpect(status().isOk())
                .andDo(print())
                // https://jira.spring.io/browse/SPR-14472
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(POST_MATCHER.contentJson(post1, post2, post3, post4, post5, post6, post7, adminPost1, adminPost2));
    }

    @Test
    void update() throws Exception {
        Post updatedPost = PostTestData.getUpdated();
        updatedPost.setId(ADMIN_POST_ID);
        perform(MockMvcRequestBuilders.put(REST_URL + ADMIN_POST_ID).contentType(MediaType.APPLICATION_JSON)
                .with(userHttpBasic(admin))
                .content(JsonUtil.writeValue(updatedPost)))
                .andDo(print())
                .andExpect(status().isNoContent());

        POST_MATCHER.assertMatch(postService.get(ADMIN_POST_ID, ADMIN_ID), updatedPost);
    }

    @Test
    void createWithLocation() throws Exception {
        Post newPost = PostTestData.getNew();
        ResultActions action = perform(MockMvcRequestBuilders.post(REST_URL)
                .contentType(MediaType.APPLICATION_JSON)
                .with(userHttpBasic(admin))
                .content(JsonUtil.writeValue(newPost)))
                .andExpect(status().isCreated());

        Post created = readFromJson(action, Post.class);
        int newId = created.id();
        newPost.setId(newId);
        POST_MATCHER.assertMatch(created, newPost);
        POST_MATCHER.assertMatch(postService.get(newId, ADMIN_ID), newPost);
    }
}