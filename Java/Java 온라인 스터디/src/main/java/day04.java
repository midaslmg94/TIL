public class day04 {
    public static void main(String[] args) {
        int start = 2_110_000_000;
        int end = 2_100_000_000;
        // int mid = (start + end) / 2;  오버플로우 난다.
        int mid2 = start + (end - start) / 2; // 중간값 구할 때 오버플로우 안나게 하는 방법 1
        int mid3 = (start + end ) >>> 1; // 중간값 구할 때 오버플로우 안나게 하는 방법 2
        System.out.println(mid2);
        

    }
}
