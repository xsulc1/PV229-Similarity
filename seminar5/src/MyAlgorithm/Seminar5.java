/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package MyAlgorithm;

import java.util.Iterator;
import messif.objects.LocalAbstractObject;
import messif.objects.impl.MetaObjectMap;
import messif.objects.util.AggregationFunction;
import messif.objects.util.RankedAbstractObject;
import messif.objects.util.StreamGenericAbstractObjectIterator;
import messif.operations.query.AggregationFunctionQueryOperation;
import messif.statistics.OperationStatistics;
import messif.statistics.StatisticCounter;
import messif.statistics.StatisticTimer;
import messif.statistics.Statistics;

/**
 *
 * @author msulc
 */
public class Seminar5 {
    
    public static void main (String[] args) throws Exception {
        MyAlgorithm alg = new MyAlgorithm("C:\\Users\\msulc\\Dropbox\\1PROJEKTY\\seminar5\\metadata", MetaObjectMap.class);
        
        LocalAbstractObject query =
            new StreamGenericAbstractObjectIterator<> (
                    MetaObjectMap.class, "C:\\Users\\msulc\\Dropbox\\1PROJEKTY\\seminar5\\metadata"
        ).next();
        
        for (int i=0; i <4; i++) {
            
            OperationStatistics localStats = OperationStatistics.getLocalThreadStatistics();
            localStats.registerBoundStat("DistanceComputations");
        
            AggregationFunctionQueryOperation knnQuery = alg.executeOperation(
                    new AggregationFunctionQueryOperation(query, 10,
                        AggregationFunction.valueOf("ColorStructureType + ScalableColorType")
                        )
            );



            Iterator<RankedAbstractObject> answer = knnQuery.getAnswer();
            while (answer.hasNext()) {
                System.out.println(answer.next());
            }


            StatisticCounter distComp = StatisticCounter.getStatistics("DistanceComputations");
            System.out.println(distComp);

            Iterator<Statistics<?>> allStatistics = Statistics.getAllStatistics();
            while (allStatistics.hasNext())
                System.out.println(allStatistics.next());

            System.out.println(localStats);
            localStats.resetStatistics();
        }    
     }
}
