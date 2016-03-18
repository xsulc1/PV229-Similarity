/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package seminar4;

import java.util.Iterator;
import messif.algorithms.impl.SequentialScan;
import messif.buckets.CapacityFullException;
import messif.objects.LocalAbstractObject;
import messif.objects.util.AggregationFunction;
import messif.objects.util.RankedAbstractObject;
import messif.operations.data.InsertOperation;
import messif.operations.query.AggregationFunctionQueryOperation;
import messif.operations.query.KNNQueryOperation;
import myobject.impl.MyMetaObject;
import myobject.impl.MyMultiQueryOperation;
import myobject.impl.MyObject;
import myobject.impl.MyObjectVect;

/**
 *
 * @author msulc
 */
public class Seminar4{
    public static LocalAbstractObject createRandomObject(String locator) {
        return new MyMetaObject(locator,
            new MyObject((float)Math.random()),
            new MyObjectVect((float)Math.random() + 1, (float)Math.random()+2)
        );
    }
    /**
     * @param args the command line arguments
     * @throws java.lang.Exception
     */
public static void main(String[] args) throws Exception {
        // TODO code application logic here
        SequentialScan alg = new SequentialScan() ;
        
        for (int i=0; i < 5; i++) {
                    
            InsertOperation op =    
                new InsertOperation (new MyMetaObject("1", new MyObject(1.0f),new MyObjectVect(2,3)));
            
            System.out.println(op);
    //vzdalenost jako sumu obou vzdalenostnich casti - number, vector    
            op = alg.executeOperation(op);
            if (!op.wasSuccessful())
                System.out.println(op.getErrorCode());
    
        }
        System.out.println(alg);
        
        LocalAbstractObject queryObject = createRandomObject("q");
        
        KNNQueryOperation queryOp = new KNNQueryOperation (queryObject, 3);
        queryOp = alg.executeOperation(queryOp);
        
        
        Iterator<RankedAbstractObject> answer = queryOp.getAnswer();
        while (answer.hasNext()) {
            System.out.println(answer.next());
        }
        
        System.out.println(" ---- aggregate results ----");
        
        AggregationFunctionQueryOperation aggOp = new AggregationFunctionQueryOperation (
                queryObject, 3,
        
                AggregationFunction.valueOf(" 2* number + 2 * vector"  )
        );
        
        aggOp = alg.executeOperation(aggOp);
        Iterator<RankedAbstractObject> aggAnswer = aggOp.getAnswer();
        while (aggAnswer.hasNext()) {
            System.out.println(aggAnswer.next());
        }
        
        System.out.println(" ---- multi query results ----");
        LocalAbstractObject queryObject2 = createRandomObject("q2");
        MyMultiQueryOperation multiOp = new MyMultiQueryOperation(queryObject, queryObject2, 3);
        
        multiOp = alg.executeOperation(multiOp);
        Iterator<RankedAbstractObject> multiAnswer = multiOp.getAnswer();
        while(multiAnswer.hasNext()){
            System.out.println(multiAnswer.next());
        }
    }
}
