import java.util.List;
import java.util.Random;
import com.google.caliper.Param;
import com.google.caliper.SimpleBenchmark;
import com.google.common.base.Supplier;
import com.google.common.collect.Lists;

/**
 * Compares appending to a LinkedList to appending to an ArrayList.
 * 
 * Intuitively, I would have said LinkedList was more efficient (ArrayList needs to copy the whole table from time to
 * time, even though the cost is amortized). However, it seems to be the opposite. Upon further research, I've found
 * explanations by Stephen Colebourne (see references).
 * The allocation / destruction of the node objects seems to outweigh array allocation and copy (which is handled
 * natively).
 * 
 * Sample results on my macbook pro:
 * <pre>
 *     0% Scenario{vm=java, trial=0, benchmark=AppendToLinkedList, appendCount=100000} 4323056,29 ns; ?=732353,68 ns @ 10 trials
    50% Scenario{vm=java, trial=0, benchmark=AppendToArrayList, appendCount=100000} 1760429,08 ns; ?=63597,29 ns @ 10 trials

             benchmark   ms linear runtime
    AppendToLinkedList 4,32 ==============================
     AppendToArrayList 1,76 ============

 * </pre>
 * 
 * @see <a href="http://blog.xebia.fr/2011/02/17/java-collection-performance">
 *        an article about Java collections (French)
 *      </a>
 * @see <a href="http://commons.apache.org/collections/api-3.2/org/apache/commons/collections/list/TreeList.html">
 *        Javadoc for TreeList in Commons-collections (also contains benchmark results) 
 *      </a>
 * @see <a href="http://download.oracle.com/javase/6/docs/api/java/util/LinkedList.html">
 *        Javadoc for LinkedList
 *      </a>
 * @see <a href="http://download.oracle.com/javase/6/docs/api/java/util/ArrayList.html">
 *        Javadoc for ArrayList
 *      </a>
 * @see <a href="http://chaoticjava.com/posts/linkedlist-vs-arraylist/#comment-175">
 *        Stephen's comment (1)
 *      </a>
 * @see <a href="http://chaoticjava.com/posts/linkedlist-vs-arraylist/#comment-182">
 *        Stephen's comment (2)
 *      </a>
 */
public class ListAppendBenchmark extends SimpleBenchmark {
  
  @Param int appendCount;
  private Integer[] elements;
  
  private Supplier<List<Integer>> linkedListBuilder = new Supplier<List<Integer>>() {
    @Override public List<Integer> get() { return Lists.newLinkedList(); }
  };
  
  private Supplier<List<Integer>> arrayListBuilder = new Supplier<List<Integer>>() {
    @Override public List<Integer> get() { return Lists.newArrayList(); }
  };
  
  @Override protected void setUp() throws Exception {
    elements = new Integer[appendCount];
    Random random = new Random();
    for (int i = 0; i < appendCount; i++) {
      elements[i] = Integer.valueOf(random.nextInt());
    }
  }
  
  public int timeAppendToLinkedList(int reps) {
    return genericTimeAppend(linkedListBuilder, reps);
  }
  
  public int timeAppendToArrayList(int reps) {
    return genericTimeAppend(arrayListBuilder, reps);
  }
  
  private int genericTimeAppend(Supplier<List<Integer>> builder, int reps) {
    int sum = 0;
    for (int i = 0; i < reps; i++) {
      List<Integer> list = builder.get();
      for (Integer e : elements) {
        list.add(e);
      }
      sum += list.get(0);
    }
    return sum;
  }
}
