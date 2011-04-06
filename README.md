A couple of Java microbenchmarks.

I'm aware that writing a good benchmark is hard (see [Brian Goetz' article](http://www.ibm.com/developerworks/java/library/j-jtp02225.html) and [the Caliper documentation](http://code.google.com/p/caliper/wiki/JavaMicrobenchmarks)). My goal is just to have a rough estimate of "how does solution A compare to solution B?". Don't take any result for granted, real-world profiling (if possible) should always be preferred.

I've used [Caliper](http://code.google.com/p/caliper) to avoid common pitfalls. If you still see a flaw in my procedure, I'll be happy to hear your comments.

To run (Maven required):

    lib/install-libs
    mvn compile exec:exec

The class to run is specified in the arguments to `exec-maven-plugin` in `pom.xml`:

    <argument>AppendBenchmark</argument>
    <argument>-DappendCount=100000</argument>

You can specify multiple comma-separated values for the test arguments:

    <argument>-DappendCount=10,100,1000</argument>

