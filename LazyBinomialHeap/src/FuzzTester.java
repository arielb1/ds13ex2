import java.util.Date;
import java.util.Random;

public class FuzzTester {
    int m_run_length;
    String m_id;
    Random m_rand;

    public FuzzTester(int run_length, Random rand) {
        m_run_length = run_length;
        m_rand = rand;
    }

    public String setId(String id) { m_id = id; }

    public void fuzz() {
        int i=0;
        try {
            Thread.sleep(1000);
        } catch(Exception e) {
            System.out.println(m_id + ": failed at command #" + i);
            throw;
        }
    }

    public static void main(String[] args) {
        if(args.length == 0) {
            Syster.err.println("usage: FuzzTester RUNLENGTH NRUNS SEED");
        }

        int run_length = Integer.parseInt(args[0]);
        int count_runs = Integer.parseInt(args[1]);
        int seed = Long.parseLong(seed);
        
        FuzzTester fuzzer = new FuzzTester(run_length, new Random(seed));

        System.err.println("Fuzzing with seed " + seed + ": doing "			   + count_runs + " runs each of length " + run_length);

        for(int i=0; i<count_runs; i++) {
            String id = "Seed " + seed + "Run " + i;
            fuzzer.setId(id);

            Date before = new Date();
            fuzzer.fuzz();
            Date after = new Date();

            System.out.println(id + ": succeeded. Took " +
			       (after.getTime() - before.getTime()) + "ms");
        }
    }
}