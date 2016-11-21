# redpesa-reporting
Reporting Module for RedPesa

# Prerequisites
1. JDK 1.8 +. Tested on build 1.8.0_77-b03
2. Wildfly v. 10.0.0. Tested on v.10.1.0 as well
3. Mysql database. The following data sources must exist;
	- 
	-
4. Gradle v 2.13 
5. Maven v 3.2.1
6. Checkout the projet `redpesa` from my public repo and install it in your local maven repo. It's a dependency on this project.



#Build instructions.
1. Clone the project git@github.com:timomwa/redpesa.git 
2. Navigate to the folder `redpesa/ejb` and install the project to your local maven repository by running;
	```
	gradle install
	```	
3. Navigate back to this project (`redpesa-reporting`)
4. Install the sub project `ejb-reporting` in your local maven repository. 
	To do this, navigate to the 'ejb-reporting' folder and run'
	
	```
	gradle install
	```	
5. Navigate back to `redpesa-reporting` and generate the ear file;

	```
	gradle clean ear
	```
	
6. Deploy to Wildfly version 10.0.0
