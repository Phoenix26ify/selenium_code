Feature: Search functionality

  Scenario: Perform a search on a webpage
    Given I open the browser and navigate to "https://www.bing.com"
    When I enter "Selenium Automation" in the search bar
    Then I should see search results related to "Selenium Automation"


