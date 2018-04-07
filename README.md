# Welcome to the AUI Demo Source!

## Quick links

* [Atlassian User Interface (AUI)][1]
* [Spring Framework][2]
* [Nsys Platform][3]

## Description

[AUI Demo](https://github.com/xboot-dev/aui-demo-webapp) is an example of AUI (Atlassian User Interface) implementation in a web application based on Spring framework.
The code is based on source code of [Nsys Portal](https://nsys.org).

<img src="doc/images/aui-demo-01.png" width="700" height="410" alt="AUI Demo Screenshot" />
<img src="doc/images/aui-demo-02.png" width="700" height="410" alt="AUI Demo Screenshot" />
<img src="doc/images/aui-demo-03.png" width="700" height="480" alt="AUI Demo Screenshot" />

## Quick start

 * cd "project root"
 * ./build.sh
 * ./run-portal.sh
 * vi target/aui-demo/logs/aui-demo-webapp.log
 * Browse to [http://localhost:8080](http://localhost:8080).

## Docker-CLI:

~~~~
$ docker run -it --rm -p 8080:8080 xboot/aui-demo
~~~~

> AUI Demo will be available at http://localhost:8080

## How to build image xboot/aui-demo

### Build the Image

~~~~
$ ./scripts/build-image.sh
~~~~

### Run the Image

~~~~
$ ./scripts/run-image.sh
~~~~

### Stop the Image

~~~~
$ ./scripts/stop-image.sh
~~~~

[1]: https://docs.atlassian.com/aui
[2]: https://projects.spring.io/spring-framework
[3]: https://nsys.org