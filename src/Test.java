public class Test {
    public static void main(String[] args) throws Exception {
        Test test = new Test();
        
        UnitTest.test(test, UnitTest.setupMethod(Test.class, "add", int.class, int.class), new Object[][]{{1, 1}, {2, 3}}, new Object[]{2, 5});
        UnitTest.test(test, UnitTest.setupMethod(Test.class, "mold", String.class, String.class), new Object[][]{{"1", "1"}, {"2", "3"}}, new Object[]{"11", "23"});
    }

    public int add(int a, int b){
        return a + b;
    }

    public String mold(String a, String b){
        return a + b;
    }
}
