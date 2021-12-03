# kotlinwebscraper

This app was developed as a test for a Software Engineer position.

###Rules
This is my implementation to solve the following problem: 

Given an url to a webpage, you need to find all links inside it. 
The system must be able to: 
- find all links inside a given url
- save all links found in a database (SQL or NoSql)
- allow list the data saved for each url

The code must:
- have unit tests
- be easy to setup and run using docker
- be deployed on a cloud environment (also using docker)

### The solution

For this project, I have used two different technologies and two different approaches: 

- To handle transactions with the database, I have choosen to create a rest api that allows you to create, update, delete and find a link.
- As a sample application that would use the api, inside the [front directory of this repo](https://github.com/ksetoue/kotlinwebscraper/tree/main/front), you can find a Vue app that provides a simple frontend to insert, update, delete and list the results of the api. 

### Architecture
The system consists of two applications, as mentioned above, and the figure bellow attempts to describe how they both interact with each other.

<img alt="project&#39;s architecture" src="https://imgur.com/a/aMs1lv7"/>