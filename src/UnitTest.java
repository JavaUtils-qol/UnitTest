import java.lang.reflect.Method;

/**
 * This class aids with debugging by providing an easy way to test scenarios.
 */
public class UnitTest {
    /**
     * Contains the different precisions of unit test.
     */
    public static enum Config {
        Default,
        Advanced,
        No_Console
    }

    private static Config _config = Config.Default;
    
    /**
     * Will test the given method with the given parameters and will then compare the result with the given expected result.
     * 
     * @param obj - Object of the same class as the method
     * @param method (Method) - result of {@code setupMethod()}
     * @param parametersList (Object[][]) - array of parameters
     * @param resultList (Object[]) - array of expected results
     * @return <b>true/false</b> depending on whether all tests passed
     * @throws Exception not specific
     */
    public static boolean test(Object obj, Method method, Object[][] parametersList, Object[] resultList) throws Exception {
        boolean allTestsPassed = true;
        int i = 0;

        while (i != parametersList.length) {
            double startTime = System.nanoTime();
            Object result = method.invoke(obj, parametersList[i]);
            double elapsedTime = (double) Math.round(((System.nanoTime() - startTime) * 0.000001) * 1000d) / 1000d;

            switch (_config) {
                case Default:
                    if (!defaultMode(result, resultList[i], i + 1, elapsedTime))
                        allTestsPassed = false;
                    break;
                case Advanced:
                    if (!advancedMode(result, resultList[i], i + 1, elapsedTime))
                        allTestsPassed = false;
                    break;
                case No_Console:
                    if (result.equals(resultList[i]))
                        allTestsPassed = false;
                    break;
            }
            i++;
        }
        return allTestsPassed;
    }

    /**
     * Will test the given static method with the given parameters and will then compare the result with the given expected result.
     * 
     * @param method (Method) - result of {@code setupMethod()}
     * @param parametersList (Object[][]) - array of parameters
     * @param resultList (Object[]) - array of expected results
     * @return <b>true/false</b> depending on whether all tests passed
     * @throws Exception not specific
     */
    public static boolean test(Method method, Object[][] parametersList, Object[] resultList) throws Exception {
        boolean allTestsPassed = true;
        int i = 0;

        while (i != parametersList.length) {
            double startTime = System.nanoTime();
            Object result = method.invoke(null, parametersList[i]);
            double elapsedTime = (double) Math.round(((System.nanoTime() - startTime) * 0.000001) * 1000d) / 1000d;

            switch (_config) {
                case Default:
                    if (!defaultMode(result, resultList[i], i + 1, elapsedTime))
                        allTestsPassed = false;
                    break;
                case Advanced:
                    if (!advancedMode(result, resultList[i], i + 1, elapsedTime))
                        allTestsPassed = false;
                    break;
                case No_Console:
                    if (result.equals(resultList[i]))
                        allTestsPassed = false;
                    break;
            }
            i++;
        }
        return allTestsPassed;
    }
    
    /**
     * Used to replace the need to import {@code java.lang.reflec.Method} in the main class.
     * <br><br>
     * If you still want to use the import way you can do {@code objClass.getMethod(methodName, paramsClass)}
     * 
     * @param objClass (Class) - parameter format: {@code ClassName.class}
     * @param methodName - name of the method
     * @param paramsClass (Class) - parameter format: {@code primitive.class, Object.class}
     * @return <b>method</b> (Method) - method for {@code test()}
     */
    public static Method setupMethod(Class objClass, String methodName, Class... paramsClass) {
        try {
            return objClass.getMethod(methodName, paramsClass);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
    
    /**
     * Will set the {@code UnitTest} class console output to a certain degree of precision.
     * 
     * @param config (Config) - config to set
     */
    public static void setConfig(Config config) {
        _config = config;
    }
    
    /**
     * Will return the degree of precision of UnitTest.
     * 
     * @return <b>state</b> (Config) - {@code UnitTest} precision
     */
    public static Config getConfig() {
        return _config;
    }



    
    
    private UnitTest(){} // set constructor to private to stop object creation

    private static boolean advancedMode(Object result, Object expectedResult, int scenarioNum, double elapsedTime) {
        if (result.equals(expectedResult)) {
            System.out.println("Scenario " + "\033[1;97m" + (scenarioNum) + "\033[0m" + ": " + "\033[1;32m" + "passed"
                    + "\033[0m" + " in: " + elapsedTime + "ms");
            return true;
        } else {
            System.out.println("Scenario " + "\033[1;97m" + (scenarioNum) + "\033[0m" + ": " + "\033[1;31m" + "failed"
                    + "\033[0m" + " (Expected Return: " + expectedResult + ", Actual Return: " + result + ") in: "
                    + elapsedTime + "ms");
            return false;
        }
    }
    
    private static boolean defaultMode(Object result, Object expectedResult, int scenarioNum, double elapsedTime) {
        System.out.println("Scenario " + "\033[1;97m" + (scenarioNum) + "\033[0m" + ": "
                + (result.equals(expectedResult) ? "\033[1;32m" + "passed" : "\033[1;31m" + "failed") + "\033[0m");
        return result.equals(expectedResult);
    }
}
