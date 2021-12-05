package lt.ignitis.task.service;

import lt.ignitis.task.model.Post;
import lt.ignitis.task.util.exception.NotFoundException;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

import static lt.ignitis.task.PostTestData.*;
import static lt.ignitis.task.UserTestData.NOT_FOUND;
import static lt.ignitis.task.UserTestData.USER_ID;
import static org.junit.jupiter.api.Assertions.assertThrows;

class PostServiceTest extends AbstractServiceTest {

    @Autowired
    protected PostService service;

    @Test
    void get() {
        Post post = service.get(POST1_ID, USER_ID);
        POST_MATCHER.assertMatch(post, post1);
    }

    @Test
    void delete() {
        service.delete(POST1_ID, USER_ID);
        assertThrows(NotFoundException.class, () -> service.get(POST1_ID, USER_ID));
    }

    @Test
    void deleteByPostId() {
        service.deleteByPostId(POST1_ID + 1);
        assertThrows(NotFoundException.class, () -> service.get(POST1_ID + 1, USER_ID));
    }

    @Test
    void getAllByUserId() {
        List<Post> all = service.getAllByUserId(USER_ID);
        POST_MATCHER.assertMatch(all, userPosts);
    }

    @Test
    void getAll() {
        List<Post> all = service.getAll();
        POST_MATCHER.assertMatch(all, allPosts);
    }

    @Test
    void update() {
        Post updated = getUpdated();
        service.update(updated, USER_ID);
        POST_MATCHER.assertMatch(service.get(POST1_ID, USER_ID), getUpdated());
    }

    @Test
    void create() {
        Post created = service.create(getNew(), USER_ID);
        int newId = created.id();
        Post newPost = getNew();
        newPost.setId(newId);
        POST_MATCHER.assertMatch(created, newPost);
        POST_MATCHER.assertMatch(service.get(newId, USER_ID), newPost);
    }

    @Test
    void getNotFound() {
        assertThrows(NotFoundException.class, () -> service.get(NOT_FOUND, USER_ID));
    }

    @Test
    void deletedNotFound() {
        assertThrows(NotFoundException.class, () -> service.delete(NOT_FOUND, USER_ID));
    }
}