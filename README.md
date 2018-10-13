# Mandelbreaker CLI
Mandelbreaker is a CLI that splits a bigger image up into smaller parts,
distributes the calculations needed for the smaller parts on one or more
servers running mandelbreaker-server and then reassembles the parts as a .pgm picture.

#Build
The project is built using maven:
~~~
    mvn clean install
~~~
Will create a runnable jar in the target directory of the project.

#Usage
The following program arguments are mandatory:
* -mincr: Minimum value of real part of C across total picture
* -maxcr: Maximum value of real part of C across total picture
* -mincim: Minumum value of imaginary part of C across total picture
* -maxcim: maximum value of imaginary part of C across total picture
* -px: Subimage width
* -py: Subimage height
* -x: Total picture width
* -y: Total picture height
* -i: Maximum number of iterations to perform
* -s: A list of server:port over which to distribute work

Example: 
~~~
     java -jar mandelbreaker-1.0.jar -mincr <double> -maxcr <double> -mincim <double> 
     -maxcim <double> -px <int> -py <int> -x <int> -y <int> -i <int> -s <string...>
~~~

##Output
The resulting reassembled picture will be output as a.pgm. Please save it as something else before running again if you 
would like to save the result of a run.