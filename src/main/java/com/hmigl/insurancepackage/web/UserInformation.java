package com.hmigl.insurancepackage.web;

import jakarta.validation.Valid;
import jakarta.validation.constraints.*;

import java.util.List;

public record UserInformation(
    @NotNull @PositiveOrZero Integer age,
    @NotNull @PositiveOrZero Integer dependents,
    @Valid House house,
    @NotNull @PositiveOrZero Integer income,
    @NotBlank @Pattern(regexp = "married|single") String maritalStatus,
    @Size(min = 3, max = 3) List<Boolean> riskQuestions,
    @Valid Vehicle vehicle) {

  protected record House(@Pattern(regexp = "owned|mortgaged") String ownershipStatus) {}

  protected record Vehicle(@Positive Integer year) {}
}
