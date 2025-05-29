public class Test {
    public static void main(String[] args) throws Exception {
        UnitTest.setConfig(UnitTest.Verbose.DETAILED);
        UnitTest.test(new Test(), UnitTest.setupMethod(Test.class, "add", int.class, int.class), new Object[][]{{1, 1}, {2, 3}}, new Object[]{2, 5});
        System.out.println("");
        System.out.println("-----------------------------");
        UnitTest.test(UnitTest.setupMethod(Test.class, "mold", String.class, String.class), new Object[][]{{"1", "1"}, {"2", "3"}}, new Object[]{"11", "23"});
        System.out.println();
        System.out.println("-------test with super-------");
        UnitTest.test(new Test(), UnitTest.setupMethod(Test.class, "add", int.class, int.class), new Object[][]{{1, 1}, {2, 3}},   new Test(), UnitTest.setupMethod(Test.class, "add", int.class, int.class));
    }

    public int add(int a, int b) {
        return a + b;
    }

    public static String mold(String a, String b) {
        return a + b;
    }
}
