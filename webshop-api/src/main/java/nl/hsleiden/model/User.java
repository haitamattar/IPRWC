package nl.hsleiden.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonView;
import nl.hsleiden.View;
import org.hibernate.validator.constraints.Email;
import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.NotEmpty;

import javax.security.auth.Subject;
import java.security.Principal;

public class User implements Principal {

    @NotEmpty
    @JsonView(View.Public.class)
    private long id = 0;

    @NotEmpty
    @Email
    @JsonView(View.Public.class)
    private String email;

    @NotEmpty
    @Length(min = 8)
    @JsonView(View.Private.class)
    private String password;

    @NotEmpty
    @Length(min = 3, max = 100)
    @JsonView(View.Public.class)
    private String fullName;

    @NotEmpty
    @Length(min = 6, max = 7)
    @JsonView(View.Public.class)
    private String postcode;

    @NotEmpty
    @Length(min = 1, max = 10)
    @JsonView(View.Public.class)
    private String streetnumber;

    @JsonView(View.Public.class)
    private String role;


    public User(String email, String fullName, String postcode, String streetnumber, String role) {
        this.email = email;
        this.fullName = fullName;
        this.postcode = postcode;
        this.streetnumber = streetnumber;
        this.role = role;
    }

    public User(long id, String email, String password, String fullName, String postcode, String streetnumber, String role) {
        this.id = id;
        this.email = email;
        this.password = password;
        this.fullName = fullName;
        this.postcode = postcode;
        this.streetnumber = streetnumber;
        this.role = role;
    }

    public long getId(){
        return id;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public String getPostcode() {
        return postcode;
    }

    public void setPostcode(String postcode) {
        this.postcode = postcode;
    }

    public String getStreetnumber() {
        return streetnumber;
    }

    public void setStreetnumber(String streetnumber) {
        this.streetnumber = streetnumber;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }


    public boolean equals(User user)
    {
        return email.equals(user.getEmail());
    }

    @Override
    @JsonIgnore
    public String getName() {
        return email;
    }

    @Override
    public boolean implies(Subject subject) {
        return false;
    }
}
