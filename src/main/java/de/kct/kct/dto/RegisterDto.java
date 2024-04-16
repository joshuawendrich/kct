package de.kct.kct.dto;

import java.util.Set;

public record RegisterDto(String email, String password, Set<String> kostenstellen) {
}
