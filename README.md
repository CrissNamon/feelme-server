## FeelMe Server
[![Build Status](https://app.travis-ci.com/CrissNamon/feelme-server.svg?branch=main)](https://app.travis-ci.com/CrissNamon/feelme-server)
[![codecov](https://codecov.io/gh/CrissNamon/feelme-server/branch/main/graph/badge.svg?token=Q6MM2CY23E)](https://codecov.io/gh/CrissNamon/feelme-server)
<br>
FeelMe Wear is an android app, which lets two people to connect their WearOS watches and send vibrations to them.
<br>
<p>Android app project: coming soon</p>

Server uses:
+ Spring Boot (Web, Data JPA)
+ PostgreSQL (with Flyway migrations) for data storage
+ Firebase Cloud Messaging to send data to user devices
+ Testcontainers with embedded postgresql for integration tests
+ TravisCI for tests and javadoc generation
+ JaCoCo and codecov.io for code coverage

<p>
<b>Server contains integration tests with embedded postgresql, which requires Docker to be installed</b>
</p>
<p>
To run in <b>Docker</b> container use <code>docker-compose up --build</code>
</p>
