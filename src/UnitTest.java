import java.lang.reflect.Method;

public class UnitTest {
    public static boolean test(Object obj, Method function, Object[][] parametersList, Object[] resultList) throws Exception{
        try {
            int i = 0;
            while(i != parametersList.length){
                Object result = function.invoke(obj, parametersList[i]);
                System.out.println("Result: " + result + " | Expected Result: " + resultList[i] + " | Same: " + result.equals(resultList[i]));
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
