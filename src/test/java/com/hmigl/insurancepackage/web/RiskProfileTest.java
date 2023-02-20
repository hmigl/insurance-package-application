package com.hmigl.insurancepackage.web;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class RiskProfileTest {

  @Test
  @DisplayName("No income should be ineligible for disability")
  void shouldBeIneligibleForDisability() {
    var userInformation =
        new UserInformation(
            35,
            2,
            new UserInformation.House("owned"),
            0,
            "married",
            List.of(0, 1, 0),
            new UserInformation.Vehicle(2018));
    var riskProfile = new RiskProfile("regular", "ineligible", "economic", "regular");

    assertEquals(riskProfile, RiskProfile.fromUserInformation(userInformation));
  }

  @Test
  @DisplayName("No vehicle should be ineligible for auto")
  void shouldBeIneligibleForAuto() {
    var userInformation =
        new UserInformation(
            35, 2, new UserInformation.House("owned"), 0, "married", List.of(0, 1, 0), null);
    var riskProfile = new RiskProfile("ineligible", "ineligible", "economic", "regular");

    assertEquals(riskProfile, RiskProfile.fromUserInformation(userInformation));
  }

  @Test
  @DisplayName("No house should be ineligible for home")
  void shouldBeIneligibleForHome() {
    var userInformation = new UserInformation(35, 2, null, 0, "married", List.of(0, 1, 0), null);
    var riskProfile = new RiskProfile("ineligible", "ineligible", "ineligible", "regular");

    assertEquals(riskProfile, RiskProfile.fromUserInformation(userInformation));
  }
}
