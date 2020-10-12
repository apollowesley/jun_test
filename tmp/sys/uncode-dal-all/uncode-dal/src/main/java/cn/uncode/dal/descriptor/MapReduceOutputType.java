package cn.uncode.dal.descriptor;

public enum MapReduceOutputType {
    /**
     * Save the job output to a collection, replacing its previous content
     */
    REPLACE,
    /**
     * Merge the job output with the existing contents of outputTarget collection
     */
    MERGE,
    /**
     * Reduce the job output with the existing contents of outputTarget collection
     */
    REDUCE,
    /**
     * Return results inline, no result is written to the DB server
     */
    INLINE

}
