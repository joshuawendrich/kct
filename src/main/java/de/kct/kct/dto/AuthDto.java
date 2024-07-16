package de.kct.kct.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;
import java.util.Set;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class AuthDto {
    private String accessToken;
    private Set<String> kostenstellen;
    private List<String> organisationseinheiten;
}
