/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package seminar4;

import messif.algorithms.impl.SequentialScan;
import messif.buckets.CapacityFullException;
import messif.objects.LocalAbstractObject;
import messif.operations.data.InsertOperation;
import myobject.impl.MyMetaObject;
import myobject.impl.MyObject;
import myobject.impl.MyObjectVect;

/**
 *
 * @author msulc
 */
public class Seminar4 {
    public static LocalAbstractObject createRandomObject(String locator) {
        return new MyMetaObject(locator,
            new MyObject((float)Math.random()),
            new MyObjectVect((float)Math.random() + 1, (float)Math.random()+2)
        );
    }
    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) throws Exception {
        // TODO code application logic here
        SequentialScan alg = new SequentialScan() ;
        
        for (int i=0; i < 5; i++) {
                    
            InsertOperation op =    
                new InsertOperation (new MyMetaObject("1", new MyObject(1.0f),new MyObjectVect(2,3)));
        
            op = alg.executeOperation(op);
            if (!op.wasSuccessful())
                System.out.println(op.getErrorCode());
    
        }
        System.out.println(alg);
    }
}
