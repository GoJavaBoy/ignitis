package lt.ignitis.task.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

@Entity
@Table(name = "posts", uniqueConstraints = {@UniqueConstraint(columnNames = {"user_id", "date_time"}, name = "posts_unique_user_datetime_idx")})
public class Post extends AbstractBaseEntity {

    @Column(name = "title", nullable = false)
    @NotBlank
    @Size(min = 2, max = 120)
    private String title;

    @Column(name = "text", nullable = false)
    @NotBlank
    @Size(min = 2, max = 120)
    private String text;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    @OnDelete(action = OnDeleteAction.CASCADE)
    @JsonBackReference
    private User user;

    public Post() {
    }

    public Post(Integer id, @NotBlank @Size(min = 2, max = 120) String title, @NotBlank @Size(min = 2, max = 120) String text) {
        super(id);
        this.title = title;
        this.text = text;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    @Override
    public String toString() {
        return "Post{" +
                "id=" + id +
                ", title='" + title + '\'' +
                ", text='" + text + '\'' +
                ", user=" + user +
                '}';
    }
}
