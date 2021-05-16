# EzMail (Easy Mail) <br> ![Test](https://github.com/DanJSG/EzMail/actions/workflows/maven.yml/badge.svg) 
Simple Java library for sending HTML templated emails. Built on top of the JavaMail API and Thymeleaf.

## Installation with Maven
Add the follow to the dependencies section of your pom.xml:

```xml
<dependency>
  <groupId>org.ezlibs</groupId>
  <artifactId>ezmail</artifactId>
  <version>1.0.1</version>
</dependency>
```

This library also depends on the Java Mail API, so ensure the following is also in your dependency list:

```xml
<dependency>
    <groupId>com.sun.mail</groupId>
    <artifactId>javax.mail</artifactId>
    <version>1.6.2</version>
</dependency>
```

Requires Java 8 or above.

## Quickstart

Clone the repository and open the project in the `/Quickstart` directory to get started. 
This shows how to configure your SMTP settings, create a templated email class and send an email.

If you have not already configured an SMTP server for development, [this support article](https://support.google.com/a/answer/176600?hl=en) shows how to configure your Gmail account to allow mail to be sent from an application.  

For more information on how to use the Thymeleaf HTML templating engine, refer [to their docs](https://www.thymeleaf.org/doc/tutorials/3.0/usingthymeleaf.html).

## License

This repository is available under the [MIT License](https://github.com/DanJSG/EzMail/blob/main/LICENSE).
