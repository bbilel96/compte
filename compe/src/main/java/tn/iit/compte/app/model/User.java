package tn.iit.compte.app.model;

import lombok.*;
import org.hibernate.annotations.GenericGenerator;
import tn.iit.compte.app.config.generate.StringPrefixSequenceGenerator;
import tn.iit.compte.app.request.UserRequest;

import javax.persistence.*;
import java.io.Serial;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Table(name = "t_user")
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class User implements Serializable {
    @Serial
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "user_sequence")
    @GenericGenerator(name = "user_sequence",
            strategy = "tn.iit.compte.app.config.generate.StringPrefixSequenceGenerator",
            parameters = {
                    @org.hibernate.annotations.Parameter(name = StringPrefixSequenceGenerator.INCREMENT_PARAM, value = "50"),
                    @org.hibernate.annotations.Parameter(name = StringPrefixSequenceGenerator.VALUE_PREFIX_PARAMETER, value = "U_"),
                    @org.hibernate.annotations.Parameter(name = StringPrefixSequenceGenerator.NUMBER_FORMAT_DEFAULT, value = "%05d"),


            })
    @EqualsAndHashCode.Include
    private String id;
    @Column(nullable = false, length = 20)
    private String firstName;
    @Column(nullable = false, length = 20)
    private String lastName;
    @Column(nullable = false, unique = true, length = 8)
    private String phoneNumber;
    @Column(nullable = false, unique = true, length = 50)
    private String email;
    @Column(nullable = false)
    private String password;
    @Column(nullable = false)
    @OneToMany(
            mappedBy = "user",
            cascade = {CascadeType.PERSIST, CascadeType.REMOVE}
    )
    @ToString.Exclude
    private Set<Compte> comptes = new HashSet<>();

    public User(UserRequest userRequest) {
        this.firstName = userRequest.getFirstName();
        this.lastName = userRequest.getLastName();
        this.phoneNumber = userRequest.getPhoneNumber();
        this.email = userRequest.getEmail();
        this.password = userRequest.getPassword();
    }

    public void update(User user) {
        firstName = user.firstName;
        lastName = user.lastName;
        password = user.password;
    }
}
