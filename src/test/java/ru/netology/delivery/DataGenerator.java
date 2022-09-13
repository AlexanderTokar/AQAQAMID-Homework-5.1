package ru.netology.delivery;

import com.github.javafaker.Faker;
import lombok.Value;
import lombok.val;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Locale;
import java.util.Random;

public class DataGenerator {
    private DataGenerator() {
    }

    static Faker faker = new Faker ((new Locale("ru")));

    public static String generateDate(int days) {
        return LocalDate.now().plusDays(days).format(DateTimeFormatter.ofPattern("dd.MM.yyyy"));
    }

    public static String generateCity(String locale) {
        String[] Cities = {"Москва", "Рязань", "Брянск", "Санкт-Петербург", "Красноярск", "Калининград"}; //не стал перечислять полный список городов из jar. выбрал несколько

        Random random = new Random();
        int i = random.nextInt(Cities.length);
        return Cities[i];
    }

    public static String generateName(String locale) {
        return faker.name().fullName(); //здесь иногда возникает проблема, когда faker "генерит" букву ё в ФИО, можно заменить массивом
    }

    public static String generatePhone(String locale) {
        return faker.phoneNumber().cellPhone();
    }

    public static class Registration {
        private Registration() {
        }

        public static UserInfo generateUser(String locale) {
            return new UserInfo(
                    generateCity("ru"),
                    generateName("ru"),
                    generatePhone("ru")
            );
        }
    }

    @Value
    public static class UserInfo {
        String city;
        String name;
        String phone;
    }
}