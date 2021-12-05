package lt.ignitis.task.web.post;

import lt.ignitis.task.model.Post;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping(value = AdminPostController.REST_URL, produces = MediaType.APPLICATION_JSON_VALUE)
public class AdminPostController extends AbstractPostController {
    static final String REST_URL = "/admin/posts";

    @Override
    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteByPostId(@PathVariable int id) {
        super.deleteByPostId(id);
    }

    @Override
    @GetMapping("/{id}")
    public Post get(@PathVariable int id) {
        return super.get(id);
    }

    @Override
    @GetMapping("/byuser/{userId}")
    public List<Post> getAllByUserId(@PathVariable int userId) {
        return super.getAllByUserId(userId);
    }

    @GetMapping()
    public List<Post> getAll() {
        return super.getAll();
    }

    @Override
    @PutMapping(value = "/{id}", consumes = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void update(@RequestBody Post post, @PathVariable int id) {
        super.update(post, id);
    }

    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Post> createWithLocation(@RequestBody Post post) {
        Post created = super.create(post);

        URI uriOfNewResource = ServletUriComponentsBuilder.fromCurrentContextPath()
                .path(REST_URL + "/{id}")
                .buildAndExpand(created.getId()).toUri();

        return ResponseEntity.created(uriOfNewResource).body(created);
    }

}
