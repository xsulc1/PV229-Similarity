/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package seminar8;

import java.util.Iterator;
import messif.algorithms.Algorithm;
import messif.objects.LocalAbstractObject;
import messif.objects.impl.ObjectFloatVectorL2;
import messif.objects.util.RankedAbstractObject;
import messif.operations.data.InsertOperation;
import messif.operations.query.RangeQueryOperation;

/**
 *
 * @author msulc
 */
public class test {
    
    
    public static LocalAbstractObject createRandomObject(){
        return new ObjectFloatVectorL2(3, 0, 1);
    }
    
    public static void main(String[] args) throws Exception {
        Algorithm index = new VPTAlgorithm();
        
        for(int i = 0; i < 10; i++) {
            index.executeOperation (new InsertOperation(CreateRandomObject()));
            
        }
        
        RandomQueryOperation queryOper = new RangeQueryOperation(
            createRandomObject(),
            2
        );
        
        queryOper = index.executeOperation(queryOper);
        Iterator<RankedAbstractObject> it = queryOper.getAnswer();
        while (it.hasNext()) {
            System.out.println(it);
        }
    }
}
