This benchmark seems to show that [ArrayList](http://download.oracle.com/javase/6/docs/api/java/util/ArrayList.html) is faster than [LinkedList](http://download.oracle.com/javase/6/docs/api/java/util/LinkedList.html) for appends (intuitevely, I would have guessed the opposite).

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

Writing representative microbenchmarks is hard (read [this](http://code.google.com/p/caliper/wiki/JavaMicrobenchmarks)); you shouldn't do it &mdash; and neither should I. I've used [Caliper](http://code.google.com/p/caliper) to avoid common pitfalls. If you still see a flaw in my procedure, feel free to educate me.

