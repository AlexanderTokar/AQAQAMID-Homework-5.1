package ru.netology.delivery;

import com.codeborne.selenide.Condition;
import com.codeborne.selenide.Configuration;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import ru.netology.delivery.DataGenerator;

import java.time.Duration;

import static com.codeborne.selenide.Condition.text;
import static com.codeborne.selenide.Condition.visible;
import static com.codeborne.selenide.Selenide.*;

public class DeliveryTest {
    @BeforeEach
    void setup() {
        open("http://localhost:9999");
    }

    @Test
    @DisplayName("Should successful plan and replan meeting")
    void shouldSuccessfulPlanAndReplanMeeting() throws InterruptedException {
        Configuration.holdBrowserOpen = true;
        var validUser = DataGenerator.Registration.generateUser("ru");
        var daysToAddForFirstMeeting = 4;
        var firstMeetingDate = DataGenerator.generateDate(daysToAddForFirstMeeting);
        var daysToAddForSecondMeeting = 7;
        var secondMeetingDate = DataGenerator.generateDate(daysToAddForSecondMeeting);
        $("[data-test-id='city'] input").setValue(validUser.getCity());
        $("[data-test-id='date'] input").doubleClick().sendKeys(Keys.DELETE, firstMeetingDate);
        $("[data-test-id='name'] input").setValue(validUser.getName());
        $("[data-test-id='phone'] input").setValue(validUser.getPhone());
        $("[data-test-id='agreement']").click();
        $x("//*[contains(text(),'Запланировать')]").click();
        $(".notification__content").shouldBe(visible).shouldHave(text("Встреча успешно запланирована нааа " + firstMeetingDate));
        Thread.sleep(2*1000);
        $("[data-test-id=date] input").doubleClick().sendKeys(Keys.DELETE, firstMeetingDate);
        $x("//*[contains(text(),'Запланировать')]").click();
        Thread.sleep(2*1000);
        $x("//span[contains(text(),'Перепланировать')]").click();
        $(".notification__content").shouldHave(text("Встреча успешно запланирована на " + firstMeetingDate));
        Thread.sleep(2*1000);
        $("[data-test-id=date] input").doubleClick().sendKeys(Keys.DELETE, secondMeetingDate);
        $x("//*[contains(text(),'Запланировать')]").click();
        Thread.sleep(2*1000);
        $x("//span[contains(text(),'Перепланировать')]").click();
        $(".notification__content").shouldHave(text("Встреча успешно запланирована на " + secondMeetingDate));
    }
}
