import java.lang.reflect.Method;

public class UnitTest {
    public static enum Config {
        Default,
        Advanced,
        No_Console
    }

    private static Config _config = Config.Default;

    public static boolean test(Object obj, Method function, Object[][] parametersList, Object[] resultList) throws Exception {
        boolean allTestsPassed = true;
        try {
            int i = 0;
            while(i != parametersList.length){
                long startTime = System.nanoTime();
                Object result = function.invoke(obj, parametersList[i]);
                long elapsedTime = System.nanoTime() - startTime;
                switch (_config){
                    case Default : 
                        if(!defaultMode(result, resultList[i], i+1, elapsedTime)) allTestsPassed = false;
                        break;
                    case Advanced :
                        if(!advancedMode(result, resultList[i], i+1, elapsedTime)) allTestsPassed = false;
                        break;
                    case No_Console :
                        if(result.equals(resultList[i])) allTestsPassed = false;
                        break;
                }

                i++;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return allTestsPassed;
    }

    private static boolean advancedMode(Object result, Object expectedResult, int scenarioNum, long elapsedTime) {
        if(result.equals(expectedResult)) {
            System.out.println("Scenario " + "\033[1;97m" + (scenarioNum) + "\033[0m" + ": " + "\033[1;32m" + "passed" + "\033[0m" + " in: " + elapsedTime + "ms");
            return true;
        }
        else {
            System.out.println("Scenario " + "\033[1;97m" + (scenarioNum) + "\033[0m" + ": " + "\033[1;31m" + "failed" + "\033[0m" + " (Expected Return: " + expectedResult + ", Actual Return: " + result + ") in: " + elapsedTime + "ms");
            return false;
        }
    }

    private static boolean defaultMode(Object result, Object expectedResult, int scenarioNum, long elapsedTime) {
        System.out.println("Scenario " + "\033[1;97m" + (scenarioNum) + "\033[0m" + ": " + (result.equals(expectedResult) ? "\033[1;32m" + "passed" : "\033[1;31m" + "failed")  + "\033[0m");
        return result.equals(expectedResult);
    }

    public static Method setupMethod(Class objClass, String methodName, Class... paramsClass) {
        try {
            return objClass.getMethod(methodName, paramsClass);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public static void setConfig(Config config) {
        _config = config;
    }

    public static Config getConfig() {
        return _config;
    }
}
