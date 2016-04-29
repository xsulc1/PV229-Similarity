/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package seminar8;

import messif.algorithms.Algorithm;
import messif.buckets.BucketDispatcher;
import messif.buckets.BucketStorageException;
import messif.buckets.impl.MemoryStorageBucket;
import messif.operations.data.InsertOperation;
import messif.operations.query.RangeQueryOperation;

/**
 *
 * @author msulc
 */
public class VPTAlgorithm extends Algorithm {
    private final BucketDispatcher bucketDispatcher;
    private VPTNode root;
    /**
     * @param args the command line arguments
     * @throws messif.buckets.BucketStorageException
     */
    public VPTAlgorithm() throws BucketStorageException {
        super("VPT index");
        this.bucketDispatcher = new BucketDispatcher(
                Integer.MAX_VALUE,
                4,
                4,
                0,
                false,
                MemoryStorageBucket.class
        );
        this.root = new VPTNode(this.bucketDispatcher.createBucket());
        // TODO code application logic here
    }
    
    public void insert(InsertOperation op) throws BucketStorageException {
        root.insert(op.getInsertedObject(), bucketDispatcher);
    }
    
    public void range(RangeQueryOperation op) {
        root.range(op);
        op.endOperation();
    }
    
}
