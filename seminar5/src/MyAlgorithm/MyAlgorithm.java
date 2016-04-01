/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package MyAlgorithm;

import java.io.IOException;
import messif.algorithms.Algorithm;
import messif.objects.LocalAbstractObject;
import messif.objects.util.StreamGenericAbstractObjectIterator;
import messif.operations.QueryOperation;

/**
 *
 * @author msulc
 */
public class MyAlgorithm extends Algorithm{

    private final String fileName;
    private final Class<? extends LocalAbstractObject> objectClass;
    
    @AlgorithmConstructor(
        description = "sequential searching over a file",
        arguments = {
            "file with the data",
            "class of the objects in the file"
        })
    public MyAlgorithm(String fileName, Class<? extends LocalAbstractObject> objectClass){
        // TODO code application logic here
    
        super("My Algorithm for searching over file");
        
        this.fileName = fileName;
        this.objectClass = objectClass;
    }
    
    public void executeSearch(QueryOperation<?> operation) throws IOException {
        StreamGenericAbstractObjectIterator<LocalAbstractObject> iter=
            new StreamGenericAbstractObjectIterator<>(objectClass, fileName);
        operation.evaluate(iter);
        
    }
}
