@tmp
Feature: Buy a white Mug from Eshop and logout

  Scenario: Adding an Item to Basket
    Given I navigate to 'UK' homepage
    When I add Capgemini whitemug in the cart
    And I click the checkout button
    And I try to login to eshop from checkout page with "eshopuser1@sogeti.com" and "Pass@word1"
    When I click the paynow button
    Then I can see the thank you message
    When I click logout link from dropdown
    And I successfully log out of Eshop