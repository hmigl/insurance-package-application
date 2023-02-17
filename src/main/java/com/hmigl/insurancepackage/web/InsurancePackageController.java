package com.hmigl.insurancepackage.web;

import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/insurance-packages")
public class InsurancePackageController {
  @PostMapping
  public ResponseEntity<?> processInsuranceNeeds(@RequestBody @Valid UserInformation userInformation) {
    return ResponseEntity.ok(userInformation.toString());
  }
}
