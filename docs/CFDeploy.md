## Steps for Deployment:
### 1) Install Cloud foundry command line interface (CF cli)
follow [instructions](https://docs.cloudfoundry.org/cf-cli/install-go-cli.html)
### 2) run `sbt dist` on project root. This must create project jar file in `/target/universal/`
### 3) run `cf login` on root directory. Provide your credentials for given API endpoint when prompted.
![image11](Resources/11.%20CF%20Login.png)
### 4) run `cf push <APP-NAME> -p <ARTIFACT> -b <BUILDPACK>`
	APP-NAME: name of jar
	ARTIFACT: path of jar
	BUILDPACK: https://github.com/cloudfoundry/java-buildpack.git
![image12](Resources/12.%20CF%20Push.png)
### 5) On successful deployment, CF starts your app and you can use the Application Route created to test the apis.
![image13](Resources/13.%20App%20deployed.png)
![image14](Resources/14.%20App%20Route.png)

### Note: `cf logs <APP_NAME> --recent` provides the logs for your recently deployed app, you may use it for debugging in case of any issues in deployment. 
