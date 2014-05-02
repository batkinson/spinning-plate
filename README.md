# Spinning Plate

This is a simple little http server that allows you to upload xml content, with
accompanying CSS and other media files, and dynamically generate PDF files using
the flying saucer library.

## To Build It

With Maven 3 installed, building the project is the normal mantra:

> mvn install

The project is built as a jar under the target directory.

## To Configure It

The application needs a storage directory to place the uploaded files into.
Rather than bake this into the application, it is configured by looking for a
properties file in the user's home directory. The file is:

> Under Windows: c:\Users\<username>\.spinning-plate.properties

> Under Linux/Unix: /home/<username>/.spinning-plate.properties

The only setting required is storage.dir, which should point to the desired
storage location like so:

> storage.dir=/home/brent/.spinning-plate-storage

## To Run It

Running the application is as simple as running the main application class. The
built jar makes this as simple as double-clicking it under windows, or under
Linux/Unix:

> java -jar <spinning-plate-jar>

This will start the server on TCP port 8080.

## To Use It

Using the application is as simple as getting/putting/deleting files using the
appropriate HTTP methods. Using curl, an example:

> curl -X PUT --data @afile.xml http://localhost:8080/input.xml

> curl -X PUT --data @astyle.css http://localhost:8080/style.css

> curl http://localhost:8080/input.pdf # Generates the pdf for input.xml

