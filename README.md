# Kirk - pragmatic UI test automation

[![Build Status](https://travis-ci.org/SergeyPirogov/kirk.svg?branch=master)](https://travis-ci.org/SergeyPirogov/kirk) [![codecov](https://codecov.io/gh/SergeyPirogov/kirk/branch/master/graph/badge.svg)](https://codecov.io/gh/SergeyPirogov/kirk) [![Maven Central](https://img.shields.io/maven-central/v/com.automation-remarks/kirk.svg)]()

- No Page Factory
- No @FindBy
- Pragmatic DSL
- Informative error messages

Simple script example:

```java
 @Test fun testCanLogin() {
   drive {
      to("http://localhost:8086")
      element("#inputEmail3").setValue("admin")
      element("#inputPassword3").setValue("admin")
      element("#parent > button").click()
      element("a.navbar-brand").shouldHave(text("Video service"))
   }
}
```

[DOCUMENTATION](http://automation-remarks.com/kirk/)

Contributions:

- Fork project
- Create feature branch like feature/<name>
- Cover your feature with tests
- Create pull request

**Pull request tests should be green to be merged and docs be updated**
