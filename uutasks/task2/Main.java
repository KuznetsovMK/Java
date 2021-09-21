package uutasks.task2;

/**
 * Задание.
 *
 * Создать три объекта класса Human, поместить их в массив и попросить их подпрыгнуть.
 */

import uutasks.task1.Cat;

public class Main {

    public static void main(String[] args) {
        //Создадим несколько котиков, добавим их в массив и попросим каждого подпрыгнуть.

        //1. Создаем несколько котиков
        Cat cat1 = new Cat("Барсик");
        Cat cat2 = new Cat("Мурзик");
        Cat cat3 = new Cat("Грызлик");

        //2. Создаем массив и помещаем в него наших котиков
        Cat[] cats = new Cat[]{cat1, cat2, cat3};

        //3. Просим котиков подпрыгнуть
        for (Cat cat :cats) {
            cat.jump();
        }
    }



}
