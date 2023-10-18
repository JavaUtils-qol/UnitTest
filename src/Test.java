import java.lang.reflect.Method;

public class Test {
    public static void main(String[] args) throws Exception {
        Test test = new Test();

        Method add = Test.class.getMethod("add", int.class, int.class);
        Method mold = Test.class.getMethod("mold", String.class, String.class);
        UnitTest.test(test, add, new Object[][]{{1, 1}, {2, 3}}, new Object[]{2, 5});
        UnitTest.test(test, mold, new Object[][]{{"1", "1"}, {"2", "3"}}, new Object[]{"11", "23"});
    }

    public int add(int a, int b){
        return a + b;
    }

    public String mold(String a, String b){
        return a + b;
    }
}
