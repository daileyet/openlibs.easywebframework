# Easyweb: MVC framework :camel::boom:

Easyweb is a java web MVC framework.It is free under Apache 2 license. Easyweb is simple, pure and easy to understand. You can use this framework to do:
* Bind web request to a java method in controller class
* Use POJO as web Controller
* Support multi-type response of java method as web View
* Support annotation configuration
* Add security controller flexibly and quickly
* Enable/disable simple web monitor to view Controllers and Filters

## Getting start
#### Add dependency to pom.xml

```xml
<dependency>
  <groupId>com.openthinks</groupId>
  <artifactId>easyweb</artifactId>
  <version>1.2</version>
</dependency>
```
#### Configure project by annotationed POJO class
```java
/*
 * File name:com.openthinks.easywebexample.EasyWebConfigure
*/
@EasyConfigure
@ScanPackages({ "com.openthinks.easywebexample" })
@RequestSuffixs(".do,.htm")
public class EasyWebConfigure{}
```

#### Enable easyweb in web.xml

```xml
  ...
  <servlet>
    <servlet-name>easyweb</servlet-name>
    <servlet-class>com.openthinks.easyweb.EasyWebDispatcher</servlet-class>
    <load-on-startup>0</load-on-startup>
  </servlet>
  <servlet-mapping>
    <servlet-name>easyweb</servlet-name>
    <url-pattern>*.do</url-pattern>
  </servlet-mapping>
  <servlet-mapping>
    <servlet-name>easyweb</servlet-name>
    <url-pattern>*.htm</url-pattern>
  </servlet-mapping>
  <listener>
    <listener-class>com.openthinks.easyweb.context.WebContextLoadListener</listener-class>
  </listener>
  <context-param>
    <param-name>configureClassName</param-name>
    <param-value>com.openthinks.easywebexample.EasyWebConfigure</param-value>
  </context-param>
  ...
```

#### Create Controller with POJO class

```java
@Controller
public class HelloController {
	@Mapping("/index")
	public String index() {
		return "hello.jsp";
	}
}    
```

### Deploy app to web container and run
After deploy your web application to Servlet container(Tomcat/Resin)

Access by URL: 
http://localhost:8080/easywebexample/index.htm 
or 
http://localhost:8080/easywebexample/index.do
to get page which render by **hello.jsp**


*easywebexample* is app web root context.

## Documentation
You can continue with [quick start](https://daileyet.gitbooks.io/easyweb/content/chapter1.html) or refer to the [documentation](https://daileyet.gitbooks.io/easyweb/content/).
