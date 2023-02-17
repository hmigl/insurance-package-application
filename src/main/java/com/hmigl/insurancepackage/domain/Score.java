package com.hmigl.insurancepackage.domain;

import com.hmigl.insurancepackage.web.UserInformation;

import java.time.Year;

public enum Score {
  AUTO {
    private String score;

    @Override
    public String calculateScore(UserInformation userInformation, int baseScore) {
      baseScore -= AUTO.deductRiskPointBasedOnAge(userInformation.age());
      baseScore -= AUTO.deductRiskPointBasedOnIncome(userInformation.income());
      if (userInformation.vehicle() != null
          && userInformation.vehicle().year() >= Year.now().getValue() - 5) baseScore += 1;

      score = AUTO.getScore(baseScore);
      if (userInformation.vehicle() == null) score = "ineligible";

      return score;
    }
  },
  DISABILITY {
    private String score;

    @Override
    public String calculateScore(UserInformation userInformation, int baseScore) {
      baseScore -= DISABILITY.deductRiskPointBasedOnAge(userInformation.age());
      baseScore -= DISABILITY.deductRiskPointBasedOnIncome(userInformation.income());
      baseScore += DISABILITY.addRiskPointBasedOnDependents(userInformation.dependents());
      baseScore -= DISABILITY.addOrDeductRiskPointBasedOnMaritalStatus(userInformation.maritalStatus());
      if (userInformation.house() != null
          && userInformation.house().ownershipStatus().equals("mortgaged")) baseScore += 1;

      score = DISABILITY.getScore(baseScore);
      if (userInformation.income() == 0 || userInformation.age() > 60) score = "ineligible";

      return score;
    }
  },
  HOME {
    private String score;

    @Override
    public String calculateScore(UserInformation userInformation, int baseScore) {
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
    private String score;

    @Override
    public String calculateScore(UserInformation userInformation, int baseScore) {
      baseScore -= LIFE.deductRiskPointBasedOnAge(userInformation.age());
      baseScore -= LIFE.deductRiskPointBasedOnIncome(userInformation.income());
      baseScore += LIFE.addRiskPointBasedOnDependents(userInformation.dependents());
      baseScore += LIFE.addOrDeductRiskPointBasedOnMaritalStatus(userInformation.maritalStatus());

      score = LIFE.getScore(baseScore);
      if (userInformation.age() > 60) score = "ineligible";

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

  private int addOrDeductRiskPointBasedOnMaritalStatus(String maritalStatus) {
    if (maritalStatus.equals("married")) return 1;
    return 0;
  }
}
