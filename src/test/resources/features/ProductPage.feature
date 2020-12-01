@tmp
Feature: Buy items from Eshop

  Scenario: Buy a whitemug and logout
    Given I navigate to 'UK' homepage
    When I add Capgemini whitemug in the cart
    And I click the checkout button
    And I try to login to eshop from checkout page with "eshopuser1@sogeti.com" and "Pass@word1"
    When I click the paynow button
    Then I can see the thank you message
    When I click logout link from dropdown
    And I successfully log out of Eshop

  Scenario: Buy two reports from Eshop and logout
    Given I navigate to 'UK' homepage
    When I add report in the cart
    And I select qty as two in the cart
    And I click the update button
    And I click the checkout button
    And I try to login to eshop from checkout page with "eshopuser2@sogeti.com" and "Pass@word1"
    When I click the paynow button
    Then I can see the thank you message
    When I click logout link from dropdown
    And I successfully log out of Eshop
