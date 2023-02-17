package com.hmigl.insurancepackage.web;

import jakarta.validation.constraints.NotBlank;

public record RiskProfile(
    @NotBlank String auto,
    @NotBlank String disability,
    @NotBlank String home,
    @NotBlank String life) {
}
