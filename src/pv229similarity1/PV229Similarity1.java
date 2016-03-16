/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pv229similarity1;

import java.util.Iterator;
import messif.algorithms.impl.SequentialScan;
import messif.objects.LocalAbstractObject;
import messif.objects.impl.ObjectStringEditDist;
import messif.objects.util.RankedAbstractObject;
import messif.objects.util.StreamGenericAbstractObjectIterator;
import messif.operations.AnswerType;
import messif.operations.data.BulkInsertOperation;
import messif.operations.query.KNNQueryOperation;

/**
 *
 * @author xsulc1
 */
public class PV229Similarity1 {

    /**
     * @param args the command line arguments
     * @throws java.lang.Exception
     */
    public static void main(String[] args) throws Exception {
        SequentialScan engine = new SequentialScan();
        // TODO code application logic here
        Iterator<LocalAbstractObject> dataIterator = new StreamGenericAbstractObjectIterator<>(
            ObjectStringEditDist.class,
            "\\C:\\Users\\msulc\\Dropbox\\1PROJEKTY\\PV229-Similarity\\sources\\text.txt"
        );
        
        BulkInsertOperation insertOper = new BulkInsertOperation(dataIterator);
        
        engine.executeOperation(insertOper);
        
        KNNQueryOperation query = new KNNQueryOperation (
            new ObjectStringEditDist("stanice"),
            10,
            AnswerType.ORIGINAL_OBJECTS
        );
        
        query = engine.executeOperation(query);
        
        Iterator<RankedAbstractObject> answer = query.getAnswer();
        while (answer.hasNext()) {
            RankedAbstractObject pair = answer.next();
            System.out.print(((ObjectStringEditDist)pair.getObject()).getStringData());
            System.out.print(": ");
            System.out.println(pair.getDistance());
        }
    }
    
}
