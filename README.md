# Infection
[![Build Status](https://travis-ci.org/RamV13/Infection.svg?branch=master)](https://travis-ci.org/RamV13/Infection)

An implementation of a model for user versioning focused on managing new feature rollouts while preserving the invariant that all pairs should ideally be on the same version.

### UI Inspiration
- Graph color scheme was based off the Khan Academy knowledge map

### Usage
##### How to run
- Download the JAR by selecting `Infection-1.0.0.jar` in the project directory above and then selecting `View Raw`
- Use the command `java -jar Infection-1.0.0.jar <MINIMUM_USERS> <MAXIMUM_STUDENTS> <LEVELS>`

      `<MINIMUM_USERS>`: the minimum number of users in the user base
      
      `<MAXIMUM_STUDENTS>`: the maximum number of students per user
      
      `<LEVELS>`: the maximum number of levels of coach-student relationships
      
- Recommended parameters (because of the UI limitation):

      `<MINIMUM_USERS>`: <= 5
      
      `<MAXIMUM_STUDENTS>`: <= 5
      
      `<LEVELS>`: <= 3
  
  The model itself can support much higher values (with the exception of the strict limited infection extension which is slow and cannot function beyond 15, 15, and 3 for the above parameters)
  
###### Note: this application was developed on Java 8

##### How to use
- Each node in the graph represents a user and the number enclosed is the version number of that user
- Clicking a node upgrades the version number by 1 starting from that user.
- For limited infection, the number of users to infect must be specified in the text field.
- For strict limited infection, clicking a node does not upgrade the version number. Rather, the `Execute` button upgrades the version number, because the user to start from is decided by the application depending on the specified number of users to infect.
- In order to toggle between the different types of infection, just click the button that displays the current infection type (i.e. the button that starts with the text `Total Infection`)
- Note: all arrows in the graph are directed from coaches to students

### Performance
- Disable the assertion Java VM flag (-ea) for increased performance because class invariant checks (for total infection) can be expensive

### Design Choices
##### General
- Used [GraphStream](http://graphstream-project.org/) to visualize the model of the user graph
- Used Java Swing because the graphing library supports swing (with more time UI/UX could be much more refined)
- Implemented Model-View-Controller design pattern to separate the various concerns of the application

##### Performance
- Used a LinkedList vs. ArrayList depending on the usage in the code because ArrayList's have the additional CPU overhead of resizing. So LinkedList's were appropriate when lookup by index was not required.
- Used a HashMap to map nodes in the graph (View) to the users (Model) because of the *O*(1) lookup time
- Used a UUID (practically unique although not guaranteed) as the key to identify users in the Map
- One drawback is that all node labels (the version numbers) in the graph are redrawn any time an infection is performed because a mechanism for keeping track of which nodes were changed was not implemented (this could simply be done by the `User` object notifying an observer any time the version number changes where the observer then informs the graph to redraw the node label)

##### Specification
- For limited infection, one of the decisions was that it would be better to have a coach on a higher version than a student in order to address the concern mentioned in the project specification that younger individuals cannot understand the concept of versioning and would not like to see different versions of a site. Thus, infecting students was prioritized over infecting coaches.
- Additionally, infection is not counted for users that are already on the version being upgraded to (ex. if there exists a graph of 4 users where one of the users is already on the next version, then applying limited infection (strict or not) will result in all of the users on the same version.

### Build Process
- Used [Travis CI](https://travis-ci.org/) for continuous integration
- Used Github's branch protection to enforce status checks (with [Travis CI](https://travis-ci.org/)) before merging other branches into **master**
- Also used branch protection to prevent force pushes from Git

### Code Style
- 80 character limit
- Complete Javadoc documentation
