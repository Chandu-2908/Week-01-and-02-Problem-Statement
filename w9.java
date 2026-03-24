import java.util.*;

public class TransactionAnalyzer {

    // Transaction class
    static class Transaction {
        int id;
        int amount;
        String merchant;
        String account;
        long time; // milliseconds

        Transaction(int id, int amount, String merchant, String account, long time) {
            this.id = id;
            this.amount = amount;
            this.merchant = merchant;
            this.account = account;
            this.time = time;
        }
    }

    List<Transaction> transactions = new ArrayList<>();


    // Add transaction
    public void addTransaction(Transaction t) {
        transactions.add(t);
    }


    // Classic Two Sum
    public List<String> findTwoSum(int target) {

        HashMap<Integer, Transaction> map = new HashMap<>();
        List<String> result = new ArrayList<>();

        for (Transaction t : transactions) {

            int complement = target - t.amount;

            if (map.containsKey(complement)) {

                Transaction prev = map.get(complement);

                result.add("(" + prev.id + "," + t.id + ")");
            }

            map.put(t.amount, t);
        }

        return result;
    }


    // Two Sum within time window
    public List<String> findTwoSumTimeWindow(int target, long windowMillis) {

        HashMap<Integer, Transaction> map = new HashMap<>();
        List<String> result = new ArrayList<>();

        for (Transaction t : transactions) {

            int complement = target - t.amount;

            if (map.containsKey(complement)) {

                Transaction prev = map.get(complement);

                if (Math.abs(t.time - prev.time) <= windowMillis) {

                    result.add("(" + prev.id + "," + t.id + ")");
                }
            }

            map.put(t.amount, t);
        }

        return result;
    }


    // Duplicate detection
    public void detectDuplicates() {

        HashMap<String, List<Transaction>> map = new HashMap<>();

        for (Transaction t : transactions) {

            String key = t.amount + "_" + t.merchant;

            map.putIfAbsent(key, new ArrayList<>());

            map.get(key).add(t);
        }

        for (String key : map.keySet()) {

            List<Transaction> list = map.get(key);

            if (list.size() > 1) {

                System.out.print("Duplicate transaction detected: ");

                for (Transaction t : list) {
                    System.out.print("ID:" + t.id + " ");
                }

                System.out.println();
            }
        }
    }


    // K-Sum
    public void findKSum(int k, int target) {

        List<Integer> nums = new ArrayList<>();

        for (Transaction t : transactions)
            nums.add(t.amount);

        List<List<Integer>> result = new ArrayList<>();

        kSumHelper(nums, k, target, 0, new ArrayList<>(), result);

        for (List<Integer> r : result)
            System.out.println(r);
    }


    private void kSumHelper(List<Integer> nums, int k, int target,
                            int start, List<Integer> current,
                            List<List<Integer>> result) {

        if (k == 0 && target == 0) {

            result.add(new ArrayList<>(current));
            return;
        }

        if (k == 0)
            return;

        for (int i = start; i < nums.size(); i++) {

            current.add(nums.get(i));

            kSumHelper(nums, k - 1, target - nums.get(i),
                    i + 1, current, result);

            current.remove(current.size() - 1);
        }
    }


    // Main Simulation
    public static void main(String[] args) {

        TransactionAnalyzer system = new TransactionAnalyzer();

        system.addTransaction(new Transaction(1,500,"StoreA","acc1",1000));
        system.addTransaction(new Transaction(2,300,"StoreB","acc2",2000));
        system.addTransaction(new Transaction(3,200,"StoreC","acc3",2500));
        system.addTransaction(new Transaction(4,500,"StoreA","acc4",3000));

        System.out.println("Two Sum (target=500):");
        System.out.println(system.findTwoSum(500));

        System.out.println("\nTwo Sum within 1 hour:");
        System.out.println(system.findTwoSumTimeWindow(500,3600000));

        System.out.println("\nDuplicate Detection:");
        system.detectDuplicates();

        System.out.println("\nK Sum (k=3 target=1000):");
        system.findKSum(3,1000);
    }
}