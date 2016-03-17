package myobject;

import myobject.impl.MyObjectVect;
import myobject.impl.MyObject;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Iterator;
import messif.objects.LocalAbstractObject;
import messif.objects.util.StreamGenericAbstractObjectIterator;

/**
 * Testing class for new objects.
 * @author xbatko
 */
public class Main {

    /**
     * Creates a data file for {@link MyObject} and {@link MyObjectVect}
     * and then reads the data and computes the distances to a given object.
     * @param args no arguments are expected
     * @throws IOException if there was an error writing/reading the data file
     */
    public static void main(String[] args) throws IOException {
        writeRandomMyObjects("myobject.data", 10);

        readDataGetDistance(
                new StreamGenericAbstractObjectIterator<MyObject>(MyObject.class, "myobject.data"),
                new MyObject(0.5f)
        );

        System.out.println("==============================================================================");

        writeRandomMyObjectVects("myobjectvect.data", 10);

        readDataGetDistance(
                new StreamGenericAbstractObjectIterator<MyObjectVect>(MyObjectVect.class, "myobjectvect.data"),
                new MyObjectVect(0.5f, 0.5f, 0.5f)
        );
    }

    /**
     * Writes {@code count} randomly generated {@link MyObject}s
     * to the given {@code file}.
     * @param file the file into which to write the data
     * @param count number of objects to generate
     * @throws IOException if there was a problem writing to file
     */
    public static void writeRandomMyObjects(String file, int count) throws IOException {
        FileOutputStream out = new FileOutputStream(file);
        for (int i = 1; i <= count; i++) {
            MyObject obj = new MyObject(
                    Integer.toString(i),
                    (float)Math.random()
            );
            obj.write(out);
        }
        out.close();
    }

    /**
     * Writes {@code count} randomly generated {@link MyObjectVect}s
     * to the given {@code file}.
     * @param file the file into which to write the data
     * @param count number of objects to generate
     * @throws IOException if there was a problem writing to file
     */
    public static void writeRandomMyObjectVects(String file, int count) throws IOException {
        FileOutputStream out = new FileOutputStream(file);
        for (int i = 1; i <= count; i++) {
            MyObjectVect obj = new MyObjectVect(
                    Integer.toString(i),
                    (float)Math.random(), (float)Math.random(), (float)Math.random()
            );
            obj.write(out);
        }
        out.close();
    }

    /**
     * Read each object from the iterator and compute its distance to the given {@code distObject}.
     * @param iterator the iterator over all objects
     * @param distObj the object against which to get the distance
     */
    public static void readDataGetDistance(Iterator<? extends LocalAbstractObject> iterator, LocalAbstractObject distObj) {
        while (iterator.hasNext()) {
            // Read next object from the iterator
            LocalAbstractObject obj = iterator.next();
            // Write the result to the output
            System.out.println(
                    "Distance of " + obj + " to " + distObj + " = " +
                    distObj.getDistance(obj)
            );
        }
    }

}
