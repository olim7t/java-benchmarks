import static info.piwai.funkyjfunctional.guava.FunkyGuava.*;
import info.piwai.funkyjfunctional.guava.Pred;
import java.util.List;
import com.google.caliper.Param;
import com.google.caliper.SimpleBenchmark;
import com.google.common.base.Predicate;
import com.google.common.collect.Iterables;
import com.google.common.collect.Lists;

/**
 * Compares a FunkyJFunctional filter with its pure Guava equivalent.
 * 
 * FunkyJFunctional provides a new way to write Guava SAM types (amongst other things), but it relies on reflection
 * and creates an new instance on each use (each invocation of the predicate in the example below). I was curious
 * to see the impact on performance.    
 * 
 * Sample results on my macbook pro:
 * <pre>
 * listSize        benchmark    us linear runtime
 *    10000  FilterWithGuava   116 =
 *    10000 FilterWithFunkyJ   482 =
 *   100000  FilterWithGuava  1406 =
 *   100000 FilterWithFunkyJ  5117 ==
 *  1000000  FilterWithGuava 13241 =======
 *  1000000 FilterWithFunkyJ 55185 ==============================
 * </pre>
 *
 * @see <a href="https://github.com/pyricau/FunkyJFunctional">
 *        FunkyJFunctional
 *      </a>
 */
public class FunkyJFilterBenchmark extends SimpleBenchmark {
  
  @Param int listSize;
  private List<Integer> ints;
  
  @Override protected void setUp() throws Exception {
    ints = Lists.newArrayListWithCapacity(listSize);
    for (int i = 0; i < listSize; i++) {
      ints.add(i);
    }
  }
  
  private static final Predicate<Integer> GUAVA_PREDICATE = new Predicate<Integer>() {
    @Override public boolean apply(Integer i) { return i % 2 == 0; }
  };
  
  private static class FUNKY_CLASS extends Pred<Integer> {{ r = t % 2 == 0; }};
  private static final Predicate<Integer> FUNKY_PREDICATE = withPred(FUNKY_CLASS.class);
  
  public int timeFilterWithGuava(int reps) {
    return genericTimeFilter(GUAVA_PREDICATE, reps);
  }
  
  public int timeFilterWithFunkyJ(int reps) {
    return genericTimeFilter(FUNKY_PREDICATE, reps);
  }
  
  private int genericTimeFilter(Predicate<Integer> p, int reps) {
  	int sum = 0;
    for (int i = 0; i < reps; i++) {
      Iterable<Integer> evenInts = Iterables.filter(ints, p);
      sum += Iterables.size(evenInts);
    }
    return sum;
  }
}
