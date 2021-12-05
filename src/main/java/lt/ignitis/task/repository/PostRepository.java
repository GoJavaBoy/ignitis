package lt.ignitis.task.repository;


import lt.ignitis.task.model.Post;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Transactional(readOnly = true)
public interface PostRepository extends JpaRepository<Post, Integer> {

    @Modifying
    @Transactional
    @Query("DELETE FROM Post p WHERE p.id=:id AND p.user.id=:userId")
    int delete(@Param("id") int id, @Param("userId") int userId);

    @Modifying
    @Transactional
    @Query("DELETE FROM Post p WHERE p.id=:id")
    int deleteByPostId(@Param("id") int id);

    @Query("SELECT p FROM Post p WHERE p.user.id=:userId")
    List<Post> getAllByUserId(@Param("userId") int userId);
}
