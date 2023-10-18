import java.lang.reflect.Method;

public class UnitTest {
    public static boolean test(Object obj, Method function, Object[][] parametersList, Object[] resultList) throws Exception{
        try {
            int i = 0;
            while(i != parametersList.length){
                long startTime = System.nanoTime();
                Object result = function.invoke(obj, parametersList[i]);
                long elapsedTime = System.nanoTime() - startTime;
                System.out.println("Scenario " + (i+1) + ": " + (result.equals(resultList[i]) ? "\033[1;32m" : "\033[1;31m") + result.equals(resultList[i]) + "\033[0m" + " in: " + elapsedTime + "ms");
                // System.out.println("Result: " + result + " | Expected Result: " + resultList[i] + " | Same: " + result.equals(resultList[i]));
                i++;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false; // to modify
    }

    public static Method setupMethod(Class objClass, String methodName, Class... paramsClass){
        try {
            return objClass.getMethod(methodName, paramsClass);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
}
