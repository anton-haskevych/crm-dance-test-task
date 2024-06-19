package com.example.demo.DTO.users;

import com.example.demo.security.user.Position;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UserBasicInfoDTO {
    private Long id;
    private String firstName;
    private String lastName;
    private Position position;
    private String email;
}
