package com.hmigl.insurancepackage.domain;

import com.hmigl.insurancepackage.web.UserInformation;

public enum Score {
  AUTO {
    @Override
    public String calculateScore(UserInformation userInformation, int baseScore) {
      String score;

      if (userInformation.age() < 30) baseScore -= 2;
      if (userInformation.age() >= 30 && userInformation.age() <= 40) baseScore -= 1;

      if (userInformation.income() >= 200_000) baseScore -= 1;

      if (userInformation.vehicle() != null
          && userInformation.vehicle().year() >= userInformation.vehicle().year() - 5)
        baseScore += 1;

      score = AUTO.getScore(baseScore);

      if (userInformation.vehicle() == null) score = "ineligible";

      return score;
    }
  },
  DISABILITY {
    @Override
    public String calculateScore(UserInformation userInformation, int baseScore) {
      String score;

      if (userInformation.age() < 30) baseScore -= 2;
      if (userInformation.age() >= 30 && userInformation.age() <= 40) baseScore -= 1;

      if (userInformation.income() >= 200_000) baseScore -= 1;

      if (userInformation.house() != null
          && userInformation.house().ownershipStatus().equals("mortgaged")) baseScore += 1;

      if (userInformation.dependents() > 0) baseScore += 1;

      if (userInformation.maritalStatus().equals("married")) baseScore -= 1;

      score = DISABILITY.getScore(baseScore);

      if (userInformation.income() == 0 || userInformation.age() >= 60) score = "ineligible";

      return score;
    }
  },
  HOME {
    @Override
    public String calculateScore(UserInformation userInformation, int baseScore) {
      String score;

      if (userInformation.age() < 30) baseScore -= 2;
      if (userInformation.age() >= 30 && userInformation.age() <= 40) baseScore -= 1;

      if (userInformation.income() >= 200_000) baseScore -= 1;

      if (userInformation.house() != null
          && userInformation.house().ownershipStatus().equals("mortgaged")) baseScore += 1;

      score = HOME.getScore(baseScore);

      if (userInformation.house() == null) score = "ineligible";

      return score;
    }
  },
  LIFE {
    @Override
    public String calculateScore(UserInformation userInformation, int baseScore) {
      String score;

      if (userInformation.age() < 30) baseScore -= 2;
      if (userInformation.age() >= 30 && userInformation.age() <= 40) baseScore -= 1;

      if (userInformation.income() >= 200_000) baseScore -= 1;

      if (userInformation.dependents() > 0) baseScore += 1;

      if (userInformation.maritalStatus().equals("married")) baseScore += 1;

      score = LIFE.getScore(baseScore);

      if (userInformation.age() >= 60) score = "ineligible";

      return score;
    }
  };

  public abstract String calculateScore(UserInformation userInformation, int baseScore);

  private String getScore(int baseScore) {
    if (baseScore <= 0) return "economic";
    if (baseScore <= 2) return "regular";
    return "responsible";
  }
}
