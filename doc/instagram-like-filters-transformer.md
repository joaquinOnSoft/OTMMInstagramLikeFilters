# OTMM transformer to apply Instagram-like filters

An OTMM transformers to create a preview for each filter

> Transformers are service plugins that enable content to be transformed to various formats and packages. 
> Transformers can be categorized as Metadata or Asset Content based on the whether they operate on metadata or content

## Logs
 
 You need to add these categories to the `log4j.xml` configuration file, which is usually located at `C:\Apps\MediaManagement_TomEE\conf`, in order to see the logs written by the transformers:
 
 ```xml
	<Logger name="com.opentext.otmm.sc.api" level="DEBUG" additivity="false">
		<AppenderRef ref="CONSOLE"/>
		<AppenderRef ref="FILE"/>
	</Logger>
	<!-- Abstract class transformers -->
	<Logger name="com.opentext.otmm.sc.imgfilters" level="DEBUG" additivity="false">
		<AppenderRef ref="CONSOLE"/>
		<AppenderRef ref="FILE"/>
	</Logger>
	<Logger name="com.opentext.otmm.sc.transformers" level="DEBUG" additivity="false">
		<AppenderRef ref="CONSOLE"/>
		<AppenderRef ref="FILE"/>
	</Logger>		
 ```
 
## Deploy the transformer JAR file
 1. Copy the `core` and `transformer` jar file, e.g. `OTMMInstagramLikeFilters-core-21.02.02.jar` and `OTMMInstagramLikeFilters-transformer-21.02.02.jar`, in the `plugin` directory of your OTMM instance.
```
 C:\Apps\MediaManagement\plugins\OTMMInstagramLikeFilters-core-{version}.jar
 C:\Apps\MediaManagement\plugins\OTMMInstagramLikeFilters-transformer-{version}.jar
``` 
 2. As administrator open a DOS window and navigate to
```
 c:\Apps\MediaManagement\install\ant
``` 
 3. Enter the following command: `ant deploy-customizations`, make sure
there’s no errors and that the build is successful.

![ant deploy-customizations](/images/ant-deploy-customizations.png)

![ant deploy-customizations](/images/ant-deploy-customizations-successful.png)


 4. Restart the **OpenText Media Manager** Windows **service**.
 
 ![Restart OpenText Media Manager services](/images/restart-otmm-service.png)
 
## Register transformer with OTMM
 1. In Chrome navigate to Media Management Administration:
    * URL: http://YOUR_OTMM_URL/teams
    * Username: tsuper
    * Password: YOU_PASSWORD
 > The Media Management Administration dashboard opens.
 2. In the Transformers section, select **Transformers**
 3. Click the **New Transformer** button.
 4. In the New transformer page enter the following information and click
**Save**.
    * **Transformer ID**: **INSTAGRAM-LIKE.TRANSFORMER.FILTERS** (Arbitrary value)
    * **Name**: **Instagram-like filters** (Arbitrary value)
    * **Description**: Create a preview of the asset for each filter available
    * **Implementation class**: `com.opentext.otmm.sc.transformers.ImageMagickFiltersTransformer` (Class created in the previous labs)
    * **Type**: **Import** selected
    * **Enabled**: Selected.
 > Make sure that you get a message that indicates that the transformer was
successfully added.

![Instagram-like transformer](/images/instagram-like-transformer.png)

 5. In the Transformers Details page select the **Instances** tab and click
**New Instance**.

![Instagram-like transformer new instance](/images/instagram-like-transformer-new-instance.png)

 6. In the Add new instance page enter the following information and click
**Save**.
    * **Id**: INSTAGRAM-LIKE.TRANSFORMER.FILTERS.DEFAULT	
    * **Name**: Instagram-like filters transformer default
    * **Description**: INSTAGRAM-LIKE.TRANSFORMER.FILTERS default instance

![Instagram-like transformer instance](/images/instagram-like-transformer-instance.png)

 7. Click on **Attributes**  tab in the **Transformer details** page
 8. Click on **New Attribute** button
 9. Set the following values:
      * **Name**: IMAGEMAGICK_HOME_PATH	
      * **Description**: Imagemagick home path (including a '\' at the end	
      * **Default value**: C:\Apps\AdaptiveMediaDelivery\ImageMagick\	
      * **Attribute option**: 	
      * **Data Type**: 	Character
      * **Display Mode**: Editable
      * **Display: Text String
      * **Required: 

![Transformer - Add New Attribute](/images/transformer-add-new-attribute.png)

  10. Click on **Save** button	
  11. Repeat steps 7 to 9 for the attribute **OUTPUT_PATH**
      * **Name**: OUTPUT_PATH	
      * **Description**: Output path used to create image filters previews	
      * **Default value**: C:\Apps\MediaManagement\data\cs\working_area\filters
 
![Transformer - Attributes](/images/transformer-details-attributes.png)

> Import transformers need to be associated with a mime type and Export
> transformers to a content type. The same class can be associated with both
> mime types and content types, and also multiple transformers can be
> associated to these types. This would allow you to perform multiple
> transformations when importing a mp4 for example.

## Associate the Import transformer with a mime type
   1. In Media Management Administration navigate to **Content delivery > Mime Types**. 
   The Mime types page displays.
   2. Use Search box of the Mime types page to search for **png**.
   A list of Mime type containing **png** displays.
   3. Click the **View button** for the **image/png** mime type.
   The Mime types details page opens.
   4. In the Mime types details page select Import Transformers.
   5. Click Add Transformer.
   6. In the new row, scroll all the way to the end and select **INSTAGRAM-LIKE.TRANSFORMER.FILTERS**
   7. Click Save.
   
![Transformers associated with mp4 mime type](/images/transformer-associated-with-mime-type.png)

This means that every time that an `image/png` file is imported our `INSTAGRAM-LIKE.TRANSFORMER.FILTERS` transformer
will be executed.

```
See 'Developing for OpenText Media Management 16.5' tutorial for further details.

(4-0804-165-00_-_Developing_for_OpenText_Media_Management_16.5_OT.pdf) 
```

## Required .jar files

> This section is only included to know the original location of the .jar files used in the project.

Import the indicated set of files to the indicated project folders:

**Set 1**
1. From: **C:\Apps\MediaManagement\jars**
 - commons-beanutils-1.9.4.jar
 - commons-codec-1.5.jar
 - commons-httpclient-3.1.jar
 - commons-io-2.6.jar
 - commons-lang-2.4.jar
 - commons-logging-1.2.jar
 - jdom.jar
 - TEAMS-common.jar
 - TEAMS-sdk.jar
 - TEAMS-server.jar
 
2. To project folder: **jars**

**Set 2**
1. From: **C:\Apps\MediaManagement_TomEE\lib**
 - jackson-annotations-2.11.2.jar
 - jackson-core-2.11.2.jar
 - jackson-databind-2.11.2.jar
 
2. To project folder: **jars**

**Set 3**
1. From: **C:\Apps\MediaManagement\deploy\artesia**
 - otmm-server-ext-api.jar
2. To project folder: **lib**

