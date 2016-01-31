# Infection
[![Build Status](https://travis-ci.org/RamV13/Infection.svg?branch=master)](https://travis-ci.org/RamV13/Infection)

An implementation of a model for user versioning focused on managing new feature rollouts while preserving the invariant that all pairs should be on the same version.

Code Style
- 80 character limit
- Javadoc documentation 

UI Inspiration
- Graph color scheme is based off Kkan Academy knowledge map

Performance
- Disable assertion VM flag for increased performance because class invariant checks can be expensive

Choices
- Swing because graphing library only supports swing (with more time UI/UX could be much more refined)
- Observer pattern for effecting View updates from the Model data changes

- LinkedList vs ArrayList
- HashMap
- UUID (practically unique although not guaranteed)

TODO
package-info.java (for base and user)
Javadoc
JAR download (+fix maven export path)

TODO
- Rather have one student with one version higher rather than one student one version lower (add why?)

TODO UI usage (+ TOTAL vs. LIMITED vs. STRICT)

TODO recommended parameters

TODO arrow direction convention (coach -> student)

Credit [GraphStream](http://graphstream-project.org/) (graphing library)
