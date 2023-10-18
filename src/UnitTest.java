import java.lang.reflect.Method;

public class UnitTest {
    public static boolean test(Object obj, Method function, Object[][] parametersList, Object[] resultList) throws Exception{
        boolean allTestsPassed = true;
        try {
            int i = 0;
            while(i != parametersList.length){
                long startTime = System.nanoTime();
                Object result = function.invoke(obj, parametersList[i]);
                long elapsedTime = System.nanoTime() - startTime;
                if(result.equals(resultList[i])){
                    System.out.println("Scenario " + "\033[1;97m" + (i+1) + "\033[0m" + ": " + "\033[1;32m" + "passed" + "\033[0m" + " in: " + elapsedTime + "ms");
                }
                else{
                    System.out.println("Scenario " + "\033[1;97m" + (i+1) + "\033[0m" + ": " + "\033[1;31m" + "failed" + "\033[0m" + " (Expected Return: " + resultList[i] + ", Actual Return: " + result + ") in: " + elapsedTime + "ms");
                    allTestsPassed = false;
                }
                // System.out.println("Result: " + result + " | Expected Result: " + resultList[i] + " | Same: " + result.equals(resultList[i]));
                i++;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return allTestsPassed;
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
