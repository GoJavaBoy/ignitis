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

class ProfilePostControllerTest extends AbstractControllerTest {

    private static final String REST_URL = ProfilePostController.REST_URL + '/';

    @Autowired
    private PostService postService;

    @Test
    void get() throws Exception {
        perform(MockMvcRequestBuilders.get(REST_URL + POST1_ID)
                .with(userHttpBasic(user)))
                .andExpect(status().isOk())
                .andDo(print())
                // https://jira.spring.io/browse/SPR-14472
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(POST_MATCHER.contentJson(post1));
    }

    @Test
    void delete() throws Exception {
        perform(MockMvcRequestBuilders.delete(REST_URL + POST1_ID)
                .with(userHttpBasic(user)))
                .andDo(print())
                .andExpect(status().isNoContent());
        assertThrows(NotFoundException.class, () -> postService.get(POST1_ID, USER_ID));
    }

    @Test
    void getAllOwnPosts() throws Exception {
        perform(MockMvcRequestBuilders.get(REST_URL)
                .with(userHttpBasic(user)))
                .andExpect(status().isOk())
                .andDo(print())
                // https://jira.spring.io/browse/SPR-14472
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(POST_MATCHER.contentJson(post1, post2, post3));
    }

    @Test
    void update() throws Exception {
        Post updatedPost = PostTestData.getUpdated();
        perform(MockMvcRequestBuilders.put(REST_URL + POST1_ID).contentType(MediaType.APPLICATION_JSON)
                .with(userHttpBasic(user))
                .content(JsonUtil.writeValue(updatedPost)))
                .andDo(print())
                .andExpect(status().isNoContent());

        POST_MATCHER.assertMatch(postService.get(POST1_ID, USER_ID), updatedPost);
    }

    @Test
    void createWithLocation() throws Exception {
        Post newPost = PostTestData.getNew();
        ResultActions action = perform(MockMvcRequestBuilders.post(REST_URL)
                .contentType(MediaType.APPLICATION_JSON)
                .with(userHttpBasic(user))
                .content(JsonUtil.writeValue(newPost)))
                .andExpect(status().isCreated());

        Post created = readFromJson(action, Post.class);
        int newId = created.id();
        newPost.setId(newId);
        POST_MATCHER.assertMatch(created, newPost);
        POST_MATCHER.assertMatch(postService.get(newId, USER_ID), newPost);
    }
}