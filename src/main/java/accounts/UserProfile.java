package accounts;

import jakarta.persistence.*;
import org.hibernate.annotations.NaturalId;

@Entity
@Table(name="users")
public class UserProfile {

    @Id
    @Column(name="id")
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    private long id;

    @NaturalId
    @Column(name="login", unique=true, nullable=false)
    private String login;

    @Column(name="password", nullable=false)
    private String pass;

    @Column(name="email", unique=true, nullable=false)
    private String email;

    public UserProfile(String login, String pass, String email) {
        this.login = login;
        this.pass = pass;
        this.email = email;
    }

    public UserProfile() {}

    public String getLogin() {
        return login;
    }

    public String getPass() {
        return pass;
    }

    public String getEmail() {
        return email;
    }
}
