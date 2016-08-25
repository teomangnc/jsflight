package player

import com.focusit.player.scenario.UserScenario
import com.focusit.player.webdriver.SeleniumDriver
import com.focusit.player.webdriver.WebDriverWrapper
import com.focusit.script.ScriptEngine
import org.json.JSONObject
import spock.lang.Shared
import spock.lang.Specification

class SeleniumDriverSpec extends Specification {

    @Shared
            formXp = "//*[@id='errorPageContainer']";


    def sd;

    def setup() {
        ScriptEngine.init(ClassLoader.getSystemClassLoader())
        sd = new SeleniumDriver(new UserScenario())
        sd.setFormDialogXpath(formXp)
        sd.setGetFirefoxPidScript('"echo 1".execute().text');
        sd.setProcessSignalScript("println()");
    }

    def "browser containing form is not closed"() {

        JSONObject testEvent = new JSONObject();
        testEvent.put("tabuuid", "1")

        WebDriverWrapper wd = getWd(testEvent)

        wd.get('http://localhost')

        when:
        sd.releaseBrowser(wd.getWrappedDriver(), testEvent)

        then:
        !sd.drivers.isEmpty()
    }


    def "browser without form quits"() {
        JSONObject testEvent = new JSONObject();
        testEvent.put("tabuuid", "2")

        WebDriverWrapper wd = getWd(testEvent)

        when:
        sd.releaseBrowser(wd.getWrappedDriver(), testEvent)

        then:
        sd.drivers.isEmpty()
    }


    def cleanup() {
        sd.drivers.values().each { it ->
            it.quit()
        }
        sd.drivers.clear()
    }

    WebDriverWrapper getWd(def event) {
        return sd.getDriverForEvent(event, true, '', '', '', '')
    }
}
