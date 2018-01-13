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
    @Length(min = 6, max = 6)
    @JsonView(View.Public.class)
    private String postalcode;

    @NotEmpty
    @Length(min = 3, max = 100)
    @JsonView(View.Public.class)
    private String city;

    @NotEmpty
    @Length(min = 4, max = 100)
    @JsonView(View.Public.class)
    private String street;

    @NotEmpty
    @Length(min = 1, max = 10)
    @JsonView(View.Public.class)
    private String streetnumber;

    @JsonView(View.Public.class)
    private String role;

    public User(){

    }


    public User(Long id, String email, String fullName, String postalcode, String city, String street, String streetnumber, String role) {
        this.id = id;
        this.email = email;
        this.fullName = fullName;
        this.postalcode = postalcode;
        this.city = city;
        this.street = street;
        this.streetnumber = streetnumber;
        this.role = role;
    }

    public User(long id, String email, String password, String fullName, String postalcode, String city, String street, String streetnumber, String role) {
        this.id = id;
        this.email = email;
        this.password = password;
        this.fullName = fullName;
        this.postalcode = postalcode;
        this.city = city;
        this.street = street;
        this.streetnumber = streetnumber;
        this.role = role;
    }

    public long getId(){
        return id;
    }

    public void setId(Long id){
        this.id = id;
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

    public String getPostalcode() {
        return postalcode;
    }

    public void setPostalcode(String postalcode) {
        this.postalcode = postalcode;
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

    public String getStreet() {
        return street;
    }

    public void setStreet(String street) {
        this.street = street;
    }

    public boolean equals(User user)
    {
        return email.equals(user.getEmail());
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
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
