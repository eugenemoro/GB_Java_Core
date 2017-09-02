package com.company;

public class Main {
    public static void main(String[] args) {
        Course c = new Course(new Cross(500),
                new Wall(3),
                new Water(5),
                new Cross(800)); // Создаем полосу препятствий
        Team team = new Team(new Cat("Barsik", 1000, 5,0),
                new Dog("Sharik", 2000, 2, 20),
                new Cat("Vaska", 1000, 1, 5),
                new Dog("Barbos", 1500, 2, 25)); // Создаем команду
        c.doIt(team); // Просим команду пройти полосу
        System.out.println();
        team.showResults(); // Показываем результаты
    }
}
