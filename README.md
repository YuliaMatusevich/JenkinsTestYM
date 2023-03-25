# JenkinsQA_05 training project for automation testing

---
* The project at hand is focused on testing the functionality of Jenkins, a popular automation server widely used in software development.
Over 300 tests have been written for this project, with the aim of thoroughly examining the various features and capabilities of Jenkins.

## Java | Selenium | TestNG | Allure Report

---
* To ensure an organized and efficient approach to the testing process, the project has been structured using the Page Object Model (POM).
This design pattern allows for easy maintenance and scalability of the test framework, ensuring that any future changes or additions can be easily integrated into the existing structure.
####
* In order to provide comprehensive and insightful reports on the test results, the project utilizes Allure Report.
####
* To run the project locally, you must install [Maven](https://maven.apache.org/download.cgi), [Java 11](https://docs.aws.amazon.com/corretto/latest/corretto-11-ug/downloads-list.html) and [Jenkins](https://www.jenkins.io/download/)  on the local machine. 
Install [IntellijIDEA](https://www.jetbrains.com/idea/download/#section=windows) for ease of use.
####
* Go to the resources package and copy the file local.properties.TEMPLATE.
Paste it into the resources package and rename the new file to local.properties .
In the username and password fields enter the "admin" and password from the jenkins settings respectively
####
* After installation, a command can be used in the terminal to <b>run the tests</b> locally: `mvn clean test`
####
* Generate local <b>allure report</b>: `mvn allure:serve`
####
* Check the last allure report on CI: [Allure Report](https://redroverschool.github.io/JenkinsQA_05/index.html)

---
![](https://img.shields.io/badge/java-version%2011-blue?style=flat-square)
![](https://img.shields.io/badge/selenium-v.4.5.3-red?style=flat-square)
![](https://img.shields.io/badge/testng-v.7.6.1-success?style=flat-square)
![](https://img.shields.io/badge/allure-v.2.20.1-yellow?style=flat-square)