import java.lang.reflect.Method;

/**
 * This class aids with debugging by providing an easy way to test scenarios.
 */
public class UnitTest {
    /**
     * Contains the different precisions of unit test.
     */
    public static enum Verbose {
        /**
         * Will only display if the Scenario passed.
         */
        DEFAULT,
        /**
         * Will display if the Scenario passed along with the time it took and if it didn't it will display the return value it gave.
         */
        DETAILED,
        /**
         * Will display absolutely nothing but will still return true/false if all the Scenario passed.
         */
        NONE
    }

    private static Verbose _config = Verbose.DEFAULT;

    /**
     * Will test the given method with the given parameters and will then compare the result with the given expected result.
     *
     * @param obj            - Object of the same class as the method
     * @param method         (Method) - result of {@code setupMethod()}
     * @param parametersList (Object[][]) - array of parameters
     * @param resultList     (Object[]) - array of expected results
     * @return <b>true/false</b> depending on whether all tests passed
     * @throws Exception not specific
     */
    public static boolean test(Object obj, Method method, Object[][] parametersList, Object[] resultList) throws Exception {
        // declare vars
        boolean allTestsPassed = true;
        int i = 0;

        while (i != parametersList.length) {
            // calculate elapsed time + get method result
            double startTime = System.nanoTime();
            Object result = method.invoke(obj, parametersList[i]);
            double elapsedTime = (double) Math.round(((System.nanoTime() - startTime) * 0.000001) * 1000d) / 1000d;

            // output to console depending on config
            switch (_config) {
                case DEFAULT:
                    if (defaultMode(result, resultList[i], i + 1, elapsedTime))
                        allTestsPassed = false;
                    break;
                case DETAILED:
                    if (advancedMode(result, resultList[i], i + 1, elapsedTime))
                        allTestsPassed = false;
                    break;
                case NONE:
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
     * @param method         (Method) - result of {@code setupMethod()}
     * @param parametersList (Object[][]) - array of parameters
     * @param resultList     (Object[]) - array of expected results
     * @return <b>true/false</b> depending on whether all tests passed
     * @throws Exception not specific
     */
    public static boolean test(Method method, Object[][] parametersList, Object[] resultList) throws Exception {
        // declare vars
        boolean allTestsPassed = true;
        int i = 0;

        while (i != parametersList.length) {
            // calculate elapsed time + get method result
            double startTime = System.nanoTime();
            Object result = method.invoke(null, parametersList[i]);
            double elapsedTime = (double) Math.round(((System.nanoTime() - startTime) * 0.000001) * 1000d) / 1000d;

            // output to console depending on config
            switch (_config) {
                case DEFAULT:
                    if (defaultMode(result, resultList[i], i + 1, elapsedTime))
                        allTestsPassed = false;
                    break;
                case DETAILED:
                    if (advancedMode(result, resultList[i], i + 1, elapsedTime))
                        allTestsPassed = false;
                    break;
                case NONE:
                    if (result.equals(resultList[i]))
                        allTestsPassed = false;
                    break;
            }
            i++;
        }
        return allTestsPassed;
    }

    public static boolean test(Object obj, Method method, Object[][] parametersList, Object superObj, Method superMethod) throws Exception {
        // declare vars
        boolean allTestsPassed = true;
        int i = 0;

        while (i != parametersList.length) {
            // calculate elapsed time + get method result
            double startTime = System.nanoTime();
            Object result = method.invoke(obj, parametersList[i]);
            double elapsedTime = (double) Math.round(((System.nanoTime() - startTime) * 0.000001) * 1000d) / 1000d;
            Object resultSuper = method.invoke(superObj, parametersList[i]);

            // output to console depending on config
            switch (_config) {
                case DEFAULT:
                    if (defaultMode(result, resultSuper, i + 1, elapsedTime))
                        allTestsPassed = false;
                    break;
                case DETAILED:
                    if (advancedMode(result, resultSuper, i + 1, elapsedTime))
                        allTestsPassed = false;
                    break;
                case NONE:
                    if (result.equals(resultSuper))
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
     * @param objClass    (Class) - parameter format: {@code ClassName.class}
     * @param methodName  - name of the method
     * @param paramsClass (Class) - parameter format: {@code primitive.class, Object.class}
     * @return <b>method</b> (Method) - method for {@code test()}
     */
    public static Method setupMethod(Class objClass, String methodName, Class... paramsClass) {
        try {
            // setup method
            return objClass.getMethod(methodName, paramsClass);
            //! Unchecked call to 'getMethod(String, Class<?>...)' as a member of raw type 'java.lang.Class'
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * Will set the {@code UnitTest} class console output to a certain degree of precision.
     *
     * @param config (Verbose) - config to set
     */
    public static void setConfig(Verbose config) {
        _config = config;
    }

    /**
     * Will return the degree of precision of UnitTest.
     *
     * @return <b>state</b> (Verbose) - {@code UnitTest} precision
     */
    public static Verbose getConfig() {
        return _config;
    }


    private UnitTest() {} // set constructor to private to stop object creation

    private static boolean advancedMode(Object result, Object expectedResult, int scenarioNum, double elapsedTime) {
        if (result.equals(expectedResult)) {
            System.out.println("Scenario " + "\033[1;97m" + (scenarioNum) + "\033[0m" + ": " + "\033[1;32m" + "passed"
                + "\033[0m" + " in: " + elapsedTime + "ms");
            return false;
        } else {
            System.out.println("Scenario " + "\033[1;97m" + (scenarioNum) + "\033[0m" + ": " + "\033[1;31m" + "failed"
                + "\033[0m" + " (Expected Return: " + expectedResult + ", Actual Return: " + result + ") in: "
                + elapsedTime + "ms");
            return true;
        }
    }

    private static boolean defaultMode(Object result, Object expectedResult, int scenarioNum, double elapsedTime) {
        System.out.println("Scenario " + "\033[1;97m" + (scenarioNum) + "\033[0m" + ": "
            + (result.equals(expectedResult) ? "\033[1;32m" + "passed" : "\033[1;31m" + "failed") + "\033[0m");
        return !result.equals(expectedResult);
    }
}
