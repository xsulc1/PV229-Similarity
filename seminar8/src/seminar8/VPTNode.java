/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package seminar8;

import java.util.Iterator;
import messif.buckets.BucketDispatcher;
import messif.buckets.BucketStorageException;
import messif.buckets.CapacityFullException;
import messif.buckets.LocalBucket;
import messif.objects.LocalAbstractObject;
import messif.objects.util.RankedAbstractObject;
import messif.objects.util.RankedSortedCollection;
import messif.operations.query.RangeQueryOperation;
import messif.pivotselection.AbstractPivotChooser;
import messif.pivotselection.RandomPivotChooser;

/**
 *
 * @author msulc
 */
public class VPTNode {
    private LocalAbstractObject pivot;
    private float distance;
    private VPTNode leftSubtree;
    private VPTNode rightSubtree;
    
    private LocalBucket bucket;
    
    public VPTNode(LocalBucket bucket) {
        this.bucket = bucket;
    }
    
    public void insert(LocalAbstractObject obj, BucketDispatcher bucketDispatcher) throws BucketStorageException{
        if (bucket != null) {
            try {
                bucket.addObject(obj);
            } catch (CapacityFullException e) {
                split(bucketDispatcher);
                insert(obj, bucketDispatcher);
                    
                    }    
        } else {
                float dist = obj.getDistance(pivot);
                if (dist <= distance)
                    leftSubtree.insert(obj, bucketDispatcher);
                else
                    rightSubtree.insert(obj, bucketDispatcher);
                }
    }
        
    public void split (BucketDispatcher bucketDispatcher) throws BucketStorageException {
        AbstractPivotChooser pivotChooser = new RandomPivotChooser();
        pivotChooser.registerSampleProvider(bucket);
        pivot = pivotChooser.getNextPivot();
        RankedSortedCollection col = new RankedSortedCollection(pivot, bucket);
        
        leftSubtree = new VPTNode(bucketDispatcher.createBucket());
        rightSubtree = new VPTNode(bucketDispatcher.createBucket());
        
        Iterator<RankedAbstractObject> iterator = col.iterator();
        int count = 0;
        while (iterator.hasNext()) {
            RankedAbstractObject rankedObject = iterator.next();
            count++;
            if (count < col.size() / 2 ) {
                leftSubtree.bucket.addObject((LocalAbstractObject)rankedObject.getObject());
                
            } else {
                if (count == col.size() / 2)
                    distance = rankedObject.getDistance();
                rightSubtree.bucket.addObject((LocalAbstractObject)rankedObject.getObject());
            }
        }
        
        bucketDispatcher.removeBucket(bucket.getBucketID(), true);
        bucket = null;
    }
    
    public void insert(){
        
    }
    
    public void insertBulk(BulkInsertOperation op) {
        
    }
            
    
    public void range (RangeQueryOperation op) {
        if (bucket != null) {
            bucket.processQuery(op);
        } else {
            float pivotDist = op.getQueryObject().getDistance(pivot);
            if (pivotDist - op.getRadius() <= distance) {
                leftSubtree.range(op);
            }
            if (pivotDist + op.getRadius() >= distance) {
            }
        }    
    }
}

!== similart to vantage point 3 but u will have to use second partitiong method u will need 2 pivots instead
of pivot and the radius u will have to adjust the split method and insertion etc too...left subtree objects closer to left pivot
and vice versa vantage point 3 / adjust attributes and decision algorithm for insertions / compute distance between object and
both pivots if smaller left other right subtree
I have two pivots, division line, i have query object with radius for the range query,
i need to decide whater the distance betweem pivot and query is smaller than one half of distance between pivots ill go to
the left if its bigger i go to the right if its in the middle i will go for both
