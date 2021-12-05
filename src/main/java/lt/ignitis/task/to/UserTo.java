package lt.ignitis.task.to;

import lt.ignitis.task.HasId;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import java.io.Serial;
import java.io.Serializable;

public class UserTo implements HasId, Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    private Integer id;

    @Email
    @NotBlank
    @Size(max = 30)
    private String email;

    @NotBlank
    @Size(min = 5, max = 100)
    private String password;

    public UserTo() {
    }

    public UserTo(Integer id, String email, String password) {
        this.id = id;
        this.email = email;
        this.password = password;
    }

    @Override
    public Integer getId() {
        return id;
    }

    @Override
    public void setId(Integer id) {
        this.id = id;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    @Override
    public String toString() {
        return "UserTo{" +
                "id=" + id +
                ", email='" + email + '\'' +
                '}';
    }
}
