Feature: Add Candidate API

  Scenario: Calling login API
    Given Login API is provided
    When User call login API
    Then a token will be generated

  Scenario: Calling Cookies API
    Given Token API is provided
    When User call token API
    Then Cookies will be generated

  Scenario: Calling Add Candidate API
    Given Cookies API is provided
    When User call Add Candidate API
    Then the candidate info will be added

  Scenario: Calling Delete Candidate API
    Given Cookies API2 is provided
    When User call Delete Candidate API
    Then the candidate info will be deleted