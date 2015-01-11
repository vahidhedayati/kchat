kchat
==============

Grails web project built on Grails 2.4.4

Is inbuilt with Bootstrap and has its own mechanism to produce a near enough  kickstart-with-bootstrap theme as the site container.

It also uses apache-shiro as an authentication mechanism where shiro authentication has been modified to query first multiple Active Directory/LDAP servers that can be configured and mainted in provided example kchatConfig.groovy that should really be externalised.

Shiro modified to keep user logged in for a lengthy period, found (configured in the same config file and shiro bean override and kchatFilters.groovy)

User details from AD retained in local DB and reused for chat user etc...

so two examples 1 for external config locatin and serverurl that you could add to your tomcat setenv.sh

```

/*
* This is the most important configuration required for websocket calls
* in my current version the hostname is being defined by tomcat start up setenv.sh
* In my tomcat setenv.sh I have
* HOSTNAME=$(hostname)
* JAVA_OPTS="$JAVA_OPTS -DSERVERURL=$HOSTNAME"
*
* Now as per below the hostname is getting set to this value
* if not defined wschat will default it localhost:8080
*
* Configuration location for external config - called in Config.groovy
* JAVA_OPTS="$JAVA_OPTS -DCONFIGLOC=conf/"
* now it will look for {tomcatbase}/conf/kchat/kchatConfig.groovy
*/
````
