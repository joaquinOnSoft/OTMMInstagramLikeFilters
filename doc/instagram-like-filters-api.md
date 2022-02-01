# OTMM REST API extension to apply Instagram-like filters

## Pre-requisites

> OTMM REST API framework is based on Jersey REST API Framework that supports [JAX-RS 2.0](https://www.oracle.com/technical-resources/articles/java/jaxrs20.html) specification. 


## Steps to deploy a custom REST API

1. Package the REST API(s) as a jar for e.g., **OTMMInstagramLikeFilters-api-21.02.02.jar** and deploy it under the right location:

| Server       | Location                                             |
|--------------|------------------------------------------------------|
| JBOSS server | <TEAMS_HOME>\ear\artesia.ear\restapi.war\WEB-INF\lib | 
| TOMEE server | <TEAMS_HOME>\ear\artesia\restapi\WEB-INF\lib         | 

> **NOTE:** In case of Upgrade to a next version of OTMM, you would have to re-deploy this extension.

2. Register your custom REST API Resource package as JAX-RS root resource in the `Tresource` file, located under `<TEAMS_HOME>/data/cs/global`, by adding/updating the `EXTENSION_PACKAGES` under `[RESTAPI\CONFIG]`. In our project, the custom REST API class name is **com.opentext.otmm.sc.api.Filters**, so, the configuration in the settings should be

```
[RESTAPI\CONFIG]
"EXTENSION_PACKAGES"="com.opentext.otmm.sc.api"
```

Once we have updated the **Tresource** file, it should look like this:

![Tresource file](/images/tresource-otmm-config.png)

> **NOTE:** The property holds a comma separated value and so if there are different packages you need to enter it as comma separated values.

3. Restart OTMM

## OTMM REST API Limitations
Currently only the Request and Response classes as defined by OTMM REST APIs are supported and the bindings are pre-defined. If your API has different request and response, some of the configurations might not work and you might have to change. Please see the bindings defined in the MOXyContextResolver class in otmm-rest-representations.jar

## Required .jar files
Here is the list of other jar files required to use Media Management REST API Java client:

   * TEAMS-sdk.jar
   * TEAMS-common.jar
   * ot-transfer-scheme.jar
   * commons-lang-2.4.jar
   * commons-logging-1.2.jar
   * commons-io-2.8.0.jar
   * commons-beanutils-1.9.4.jar 

The TEAMS-sdk.jar,ot-transfer-scheme.jar and TEAMS-common.jar are located in deploy/artesia directory and all apache commons jars are located in deploy/commons directory in Media Management installation.
   