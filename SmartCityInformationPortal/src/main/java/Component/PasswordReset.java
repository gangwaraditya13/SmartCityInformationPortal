package Component;

import lombok.Data;

@Data
public class PasswordReset {
    private String oldPassword;
    private String newPassword;
}
