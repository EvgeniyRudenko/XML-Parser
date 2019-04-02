# XML-Parser
The application loads two XML files, compares entities and saves the result in a separate file named *output.txt*

## Installing
Simply clone the repository with the command  
```git clone https://github.com/EvgeniyRudenko/XML-Parser.git```

## Getting Started
To run the application go to the folder where *parser.jar* is located and run  
```java -jar parser.jar pathToFile1 pathToFile2 order```  
The 3-rd parameter *order* is optional and used to sort the result by name or used memory  
If you pass **0** no sorting will be applied, **1** for sorting by name and **2** is for sorting by memory  
All other arguments passed will be ignored
