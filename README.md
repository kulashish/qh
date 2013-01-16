1. Steps to configure and deploy the application
1.a. Extract meta.war
1.b. Open WEB-INF/web.xml and look for <param-name>root_path</param-name>. Change the corresponding param value to a valid path in the file system.
1.c. Repackage the meta.war and deploy it in a web container. We have tested it with tomcat7
OR
1.a. Deploy meta.war in a web container. We tested with tomcat7. In tomcat7 the applcation is deployed in webapps/. 
1.b. Browse to webapps/meta/WEB-INF/. Open web.xml and look for <param-name>root_path</param-name>. Change the corresponding param value to a valid path in the file system.
1.c. Restart the web container.

2. Steps to use the application
2.a. Once deployed, the application is available at http://<host_IP>:<port>/meta/confusion.jsf
2.b. Use the upload files option to upload the two sample arff files available in 'files' (in the distribution)
2.c. These files should now be available in the "Train file" and "Holdout file" dropdown. Select "10K-train-random.arff" as the train file and "10K-test-random.arff" as the holdout file. Click on Update.
2.d. This will invoke model training and the confusion matrix will be updated.
2.e. Change the FPs from 70 to 65 and click on Submit. This will invoke the underlying iterative training. Observe as the confusion matrix and the chart get updated if the user intention is met. 
2.f. You could play around with the FP count. In the case when the user intention cannot be met, a message is shown to intimate this to the user.
