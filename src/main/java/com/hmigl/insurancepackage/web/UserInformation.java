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
    @Size(min = 3, max = 3) List<Integer> riskQuestions,
    @Valid Vehicle vehicle) {

  public UserInformation {
    for (int e : riskQuestions)
      if (e != 0 && e != 1) throw new IllegalArgumentException("answers must be either 0 or 1");
  }

  public record House(@Pattern(regexp = "owned|mortgaged") String ownershipStatus) {}

  public record Vehicle(@Positive Integer year) {}

  public int baseScore() {
    return riskQuestions.stream().mapToInt(Integer::intValue).sum();
  }
}
