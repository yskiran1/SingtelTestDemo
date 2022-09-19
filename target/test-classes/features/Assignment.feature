Feature: SingTel Demo project
  I want to use this feature file for SingTel demo testing purpose

  Scenario: TC001 verify user is able to create todo list
    Given user has launched the url
    When user clicks on what needs to be done text box
    And user creates todo list
      | Task1 |
      | Task2 |
      | Task3 |
      | Task4 |
      | Task5 |

  Scenario: TC002 verify user is able to verify the created todo list
    Given user has launched the url
    Then verify the item is created successfully
      | Task1 |
      | Task2 |
      | Task3 |
      | Task4 |
      | Task5 |
    When user marks the one task as completed
    Then user clicks on completed button and verify the list
    When user clicks on all button and verify the list
    Then user clicks on active button and verify the list
    When user clicks on all button and verify the list


  Scenario: TC003 verify user is able to delete and verify the count
    Given user has launched the url
    When user deletes two items form list
    Then verify one item is still there in the list

