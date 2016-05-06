/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package web;

import java.util.Iterator;
import java.util.NoSuchElementException;
import messif.algorithms.Algorithm;
import messif.algorithms.AlgorithmMethodException;
import messif.objects.LocalAbstractObject;
import messif.objects.util.RankedAbstractObject;
import messif.operations.query.GetObjectByLocatorOperation;
import messif.operations.query.KNNQueryOperation;

/**
 * Bean class that provides support for searching the algorithm.
 * @author xbatko
 */
public class SearchExecutorBean {
    /** Indexing algorithm that provides the search */
    private static Algorithm algorithm;
    static {
        // Initialization of the algorithm from serialized form
        try {
            algorithm = Algorithm.restoreFromFile("C:\\images-collection.ser");
        } catch (Exception e) {
            throw new InternalError(e.toString());
        }
    }

    /** Query object for this bean */
    private LocalAbstractObject queryObject;
    /** Number of results to retrieve */
    private int resultCount = 10;

    /**
     * Returns the current query object for this bean's search method.
     * @return the current query object
     */
    public LocalAbstractObject getQueryObject() {
        return queryObject;
    }

    /**
     * Set the query object for this bean's search method.
     * @param queryObject the new query object
     */
    public void setQueryObject(LocalAbstractObject queryObject) {
        this.queryObject = queryObject;
    }

    /**
     * Searches for the given imageId locator in the algorithm to set the query object.
     * If there is no object with the given imageId, the
     * {@link NoSuchElementException} is thrown.
     *
     * @param imageId the image locator to search for
     * @throws AlgorithmMethodException if there was an error searching in the index
     * @throws NoSuchMethodException if the {@link GetObjectByLocatorOperation} is not supported by the algorithm
     * @throws NoSuchElementException if there was no object with the given {@code imageId}
     */
    public void setImageId(String imageId) throws AlgorithmMethodException, NoSuchMethodException, NoSuchElementException {
        if (imageId != null && !imageId.isEmpty()) {
            GetObjectByLocatorOperation queryObjectOperation = new GetObjectByLocatorOperation(imageId);
            queryObjectOperation = algorithm.executeOperation(queryObjectOperation);
            setQueryObject((LocalAbstractObject)queryObjectOperation.getAnswerObjects().next());
        }
    }

    /**
     * Returns the number of results returned by this bean's search method.
     * @return the number of results
     */
    public int getResultCount() {
        return resultCount;
    }

    /**
     * Set the number of results returned by this bean's search method.
     * @param resultCount the new number of results
     */
    public void setResultCount(int resultCount) {
        this.resultCount = resultCount;
    }

    /**
     * Perform the kNN search on the algorithm and return results.
     * @return iterator with the query results
     * @throws IllegalStateException if the search has failed
     */
    public Iterator<RankedAbstractObject> getResults() throws IllegalStateException {
        if (queryObject == null)
            return null;

        try {
            return algorithm.executeOperation(new KNNQueryOperation(queryObject, resultCount)).getAnswer();
        } catch (AlgorithmMethodException e) {
            throw new IllegalStateException("There was an error processing the KNN operation", e);
        } catch (NoSuchMethodException e) {
            throw new IllegalStateException("Cannot search, the operation is not supported by " + algorithm.getName(), e);
        }
    }
}
