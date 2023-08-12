package tn.iit.compte.app.dto;

import lombok.*;
import tn.iit.compte.app.model.User;


@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class UserDto {
    @EqualsAndHashCode.Include
    private String id;
    private String firstName;
    private String lastName;
    private String phoneNumber;
    private String email;
    public UserDto(User user){
        this.id = user.getId();
        this.firstName = user.getFirstName();
        this.lastName = user.getLastName();
        this.phoneNumber = user.getPhoneNumber();
        this.email = user.getEmail();
    }

}
