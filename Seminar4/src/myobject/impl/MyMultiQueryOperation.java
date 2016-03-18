package myobject.impl;


import messif.objects.LocalAbstractObject;
import messif.objects.util.AbstractObjectIterator;
import messif.operations.AbstractOperation;
import messif.operations.RankingQueryOperation;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author msulc
 */

@AbstractOperation.OperationName("multi-query object KNN operation")
public class MyMultiQueryOperation extends RankingQueryOperation {
    
    private final LocalAbstractObject query1;
    private final LocalAbstractObject query2;

    
    @OperationConstructor({"first query object", "second query object","number of nearest neigbors"})
    public MyMultiQueryOperation(LocalAbstractObject query1, LocalAbstractObject query2, int k) {
        
        super(k);
        this.query1 = query1;
        this.query2 = query2;
    }
    
    

    @Override
    protected boolean dataEqualsImpl(AbstractOperation operation) {
        if (!(operation instanceof MyMultiQueryOperation))
            return false;
        MyMultiQueryOperation castOperation = (MyMultiQueryOperation)operation;
        if (!query1.dataEquals(castOperation.query1))
            return false;
        if (!query2.dataEquals(castOperation.query2))
            return false;
        return true;
    }

    @Override
    public int dataHashCode() {
        return query1.dataHashCode() + query2.dataHashCode();
    }
    
    @Override
    public int evaluate(AbstractObjectIterator<? extends LocalAbstractObject> objects) {
        
        int count = 0;
        
        while (objects.hasNext()) {
            LocalAbstractObject object = objects.next();
            float dist1 = query1.getDistance(object);
            float dist2 = query2.getDistance(object);
            
            float resutDist = dist1 <= dist2 ? dist1 : dist2; 
            
            if (addToAnswer(object, resutDist, null) != null);
                count++;
        }
        return count;
    }
    
}
