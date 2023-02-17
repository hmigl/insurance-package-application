package com.hmigl.insurancepackage.web;

import com.hmigl.insurancepackage.domain.Score;

public record RiskProfile(String auto, String disability, String home, String life) {

  public static RiskProfile fromUserInformation(UserInformation userInformation) {
    int baseScore = userInformation.baseScore();

    return new RiskProfile(
        Score.AUTO.calculateScore(userInformation, baseScore),
        Score.DISABILITY.calculateScore(userInformation, baseScore),
        Score.HOME.calculateScore(userInformation, baseScore),
        Score.LIFE.calculateScore(userInformation, baseScore));
  }
}
