I wrote this after reading [this article (french)](http://blog.xebia.fr/2011/02/17/java-collection-performance).

It cites benchmark results from Commons-Collections' [TreeList](http://commons.apache.org/collections/api-3.2/org/apache/commons/collections/list/TreeList.html) javadoc, which indicate that the append (`add()` at end) operation is roughly equivalent for [LinkedList](http://download.oracle.com/javase/6/docs/api/java/util/LinkedList.html) and [ArrayList](http://download.oracle.com/javase/6/docs/api/java/util/ArrayList.html)).

I found this counter-intuitive (`ArrayList` needs to copy the whole table from time to time, even though the cost is amortized), but this benchmark seems to confirm it. Upon further research, I've found more explanations by Stephen Colebourne [here](http://chaoticjava.com/posts/linkedlist-vs-arraylist/#comment-175) and [here](http://chaoticjava.com/posts/linkedlist-vs-arraylist/#comment-182).

I'm aware that writing a good Java benchmark is hard (see [Brian Goetz' article](http://www.ibm.com/developerworks/java/library/j-jtp02225.html) and [the Caliper documentation](http://code.google.com/p/caliper/wiki/JavaMicrobenchmarks)). I've used [Caliper](http://code.google.com/p/caliper) to avoid common pitfalls. If you still see a flaw in my procedure, I'll be happy to hear your comments.

To run (Maven required):

    lib/install-libs
    mvn exec:exec

To change the sample data size (number of appends), edit this line in `pom.xml`:

    <argument>-DappendCount=100000</argument>

You can specify multiple comma-separated sizes: `10,100,1000`.

Here is the output I get for 100,000 appends:

    0% Scenario{vm=java, trial=0, benchmark=AppendToLinkedList, appendCount=100000} 4323056,29 ns; ?=732353,68 ns @ 10 trials
    50% Scenario{vm=java, trial=0, benchmark=AppendToArrayList, appendCount=100000} 1760429,08 ns; ?=63597,29 ns @ 10 trials

             benchmark   ms linear runtime
    AppendToLinkedList 4,32 ==============================
     AppendToArrayList 1,76 ============
