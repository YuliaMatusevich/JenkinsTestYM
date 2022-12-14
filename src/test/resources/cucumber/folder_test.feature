# Created by Vadym Koroliuk at 12/14/2022
Feature: Folder Test
  # Enter feature description here

  Scenario: Create new Folder
    Given Go to home page
    When Click on link New Item
    And Enter an item name
    And Select Folder from list and click Ok
    And click Save
    Then Result: folder was created
    # Enter steps here