package com.hmigl.insurancepackage.domain;

import com.hmigl.insurancepackage.web.UserInformation;

public enum Score {
  AUTO {
    @Override
    public String calculateScore(UserInformation userInformation, int baseScore) {
      String score;

      baseScore -= AUTO.deductRiskPointBasedOnAge(userInformation.age());

      baseScore -= AUTO.deductRiskPointBasedOnIncome(userInformation.income());

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

      baseScore -= DISABILITY.deductRiskPointBasedOnAge(userInformation.age());

      baseScore -= DISABILITY.deductRiskPointBasedOnIncome(userInformation.income());

      baseScore += DISABILITY.addRiskPointBasedOnDependents(userInformation.dependents());

      if (userInformation.house() != null
          && userInformation.house().ownershipStatus().equals("mortgaged")) baseScore += 1;

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

      baseScore -= HOME.deductRiskPointBasedOnAge(userInformation.age());

      baseScore -= HOME.deductRiskPointBasedOnIncome(userInformation.income());

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

      baseScore -= LIFE.deductRiskPointBasedOnAge(userInformation.age());

      baseScore -= LIFE.deductRiskPointBasedOnIncome(userInformation.income());

      baseScore += LIFE.addRiskPointBasedOnDependents(userInformation.dependents());

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

  private int deductRiskPointBasedOnAge(int age) {
    if (age < 30) return 2;
    if (age <= 40) return 1;
    return 0;
  }

  private int deductRiskPointBasedOnIncome(int income) {
    if (income > 200_000) return 1;
    return 0;
  }

  private int addRiskPointBasedOnDependents(int dependents) {
    if (dependents > 0) return 1;
    return 0;
  }
}
