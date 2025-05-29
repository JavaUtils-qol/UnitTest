public class Test {
    public static void main(String[] args) throws Exception {
        UnitTest.setConfig(UnitTest.Verbose.DETAILED);
        UnitTest.test(new Test(), UnitTest.setupMethod(Test.class, "add", int.class, int.class), new Object[][]{{1, 1}, {2, 3}}, new Object[]{2, 5});
        System.out.println("");
        UnitTest.test(UnitTest.setupMethod(Test.class, "mold", String.class, String.class), new Object[][]{{"1", "1"}, {"2", "3"}}, new Object[]{"11", "23"});
        System.out.println();
        System.out.println("-------test with super-------");
        UnitTest.test(new Test(), UnitTest.setupMethod(Test.class, "add", int.class, int.class), new Object[][]{{1, 1}, {2, 3}},   new Test(), UnitTest.setupMethod(Test.class, "add", int.class, int.class));
        System.out.println();
        UnitTest.test(new Test(), UnitTest.setupMethod(Test.class, "isTrue", boolean.class), new Object[][] {{true}, {false}, {true}, {true}, {true}, {false}, {false}, {false}, {true}}, new Object[]{true, false, true, true, true, false, false, false, true});
    
        UnitTest.setConfig(UnitTest.Verbose.NONE);
        System.out.println();
        System.out.println("-------No Verbose-------");
        System.out.println(UnitTest.test(new Test(), UnitTest.setupMethod(Test.class, "add", int.class, int.class), new Object[][]{{1, 1}, {2, 3}}, new Object[]{2, 5}));
    }

    public int add(int a, int b) {
        return a + b;
    }

    public static String mold(String a, String b) {
        return a + b;
    }

    public boolean isTrue(boolean value) {
        return true;
    }
}
