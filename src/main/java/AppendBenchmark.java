import java.util.List;
import java.util.Random;
import com.google.caliper.Param;
import com.google.caliper.SimpleBenchmark;
import com.google.common.base.Supplier;
import com.google.common.collect.Lists;

public class AppendBenchmark extends SimpleBenchmark {
  
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
