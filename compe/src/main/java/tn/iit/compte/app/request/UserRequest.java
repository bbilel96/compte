package tn.iit.compte.app.request;

import lombok.*;
import org.hibernate.validator.constraints.Length;
import tn.iit.compte.app.request.action.Create;
import tn.iit.compte.app.request.action.Update;

import javax.validation.constraints.*;
import java.util.HashSet;
import java.util.Set;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class UserRequest {
    @EqualsAndHashCode.Include

    private String id;


    @NotNull(message = "First name is required", groups = {Create.class, Update.class})
    @NotEmpty(message = "First name is required", groups = {Create.class, Update.class})
    @Length( max = 20, message = "First name must contain maximum 20 characters.", groups = {Create.class, Update.class})
    private String firstName;
    @NotEmpty(message = "Last name is required", groups = {Create.class, Update.class})
    @NotNull(message = "Last name is required", groups = {Create.class, Update.class})
    @Length( max = 20, message = "Last name must contain maximum 20 characters.", groups = {Create.class, Update.class})
    private String lastName;

    @Pattern(regexp = "\\d{8}", message = "Phone number must contain 8 numbers.", groups = {Create.class})
    @NotNull(message = "Phone number is required.", groups = {Create.class})
    private String phoneNumber;
    @Email(message = "Email is not valid.", groups = {Create.class})
    @Length(max = 50, message = "Email must contain maximum 50 characters.", groups = {Create.class})
    @NotNull(message = "Email is required.", groups = {Create.class})
    @NotEmpty(message = "Email is required.", groups = {Create.class})

    private String email;
    @NotNull(message = "Password is required.", groups = {Create.class, Update.class})
    @NotEmpty(message = "Password is required.", groups = {Create.class, Update.class})
    private String password;
    private Set<CompteRequest> comptes = new HashSet<>();

}
