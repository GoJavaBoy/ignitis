package lt.ignitis.task.service;

import lt.ignitis.task.model.Post;
import lt.ignitis.task.repository.PostRepository;
import lt.ignitis.task.repository.UserRepository;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import java.util.List;

import static lt.ignitis.task.util.ValidationUtil.checkNotFoundWithId;

@Service
public class PostService {
    private final PostRepository postRepository;
    private final UserRepository userRepository;


    public PostService(PostRepository postRepository, UserRepository userRepository) {
        this.postRepository = postRepository;
        this.userRepository = userRepository;
    }

    public Post get(int id, int userId) {
        return checkNotFoundWithId(postRepository.findById(id)
                .filter(post -> post.getUser().getId() == userId)
                .orElse(null), id);
    }

    public void delete(int id, int userId) {
        checkNotFoundWithId(postRepository.delete(id, userId) != 0, id);
    }

    public void deleteByPostId(int id) {
        checkNotFoundWithId(postRepository.deleteByPostId(id), id);
    }

    public List<Post> getAllByUserId(int userId) {
        return postRepository.getAllByUserId(userId);
    }

    public List<Post> getAll() {
        return postRepository.findAll();
    }

    public void update(Post post, int userId) {
        Assert.notNull(post, "post must not be null");
        checkNotFoundWithId(create(post, userId), post.id());
    }

    public Post create(Post post, int userId) {
        Assert.notNull(post, "post must not be null");
        if (!post.isNew() && get(post.getId(), userId) == null) {
            return null;
        }
        post.setUser(userRepository.getOne(userId));
        return postRepository.save(post);
    }
}
