# Infection
[![Build Status](https://travis-ci.org/RamV13/Infection.svg?branch=master)](https://travis-ci.org/RamV13/Infection)

An implementation of a model for user versioning focused on managing new feature rollouts while preserving the invariant that all pairs should ideally be on the same version.

### UI Inspiration
- Graph color scheme is based off Kkan Academy knowledge map

### How to Run/Use
- TODO recommended parameters (+ strict limited infection fails beyond 15 15 3 because it is slow)
- TODO UI usage (+ TOTAL vs. LIMITED vs. STRICT)
- TODO Clicking node/Execute => single upgrade
- All arrows are directed from coaches to students

### Performance
- Disable assertion VM flag (-ea) for increased performance because class invariant checks (for total infection) can be expensive

### Design Choices
##### General
- Used [GraphStream](http://graphstream-project.org/) to visualize the model of the user graph
- Used Java Swing because the graphing library supports swing (with more time UI/UX could be much more refined)
- Implemented Model-View-Controller design pattern to separate the various concerns of the application

##### Performance
- Used a LinkedList vs. ArrayList depending on the usage in the code because ArrayList's have the additional CPU overhead of resizing. So LinkedList's were appropriate when lookup by index was not required.
- Used a HashMap to map nodes in the graph (View) to the users (Model) because of the *O*(1) lookup time
- Used a UUID (practically unique although not guaranteed) as the key to identify users in the Map

##### Specification
- For limited infection, one of the decisions was that it would be better to have a coach on a higher version than a student in order to address the concern mentioned in the project specification that younger individuals cannot understand the concept of versioning and would not like to see different versions of a site. Thus, infecting students was prioritized over infecting coaches.
- Additionally, infection is not counted for users that are already on the version being upgraded to (ex. if there exists a graph of 4 users where one of the users is already on the next version, then applying limited infection (strict or not) will result in all of the users on the same version.

### Build Process
- Used [Travis CI](https://travis-ci.org/) for continuous integration
- Used branch protection to enforce status checks (with [Travis CI](https://travis-ci.org/)) before merging other branches into **master**
- Also used branch protection to prevent force pushes from Git

### Code Style
- 80 character limit
- Complete Javadoc documentation
