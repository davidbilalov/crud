package org.example;

import java.util.List;
import java.util.Optional;
import java.util.Scanner;

public class Main {
    private static final UserService userService = new UserService();
    private static final Scanner scanner = new Scanner(System.in);

    public static void main(String[] args) {
        while (true) {
            printMenu();
            String input = scanner.nextLine();
            switch (input) {
                case "1" -> createUser();
                case "2" -> listUsers();
                case "3" -> updateUser();
                case "4" -> deleteUser();
                case "5" -> {
                    System.out.println("Выход...");
                    return;
                }
                default -> System.out.println("Неверный ввод");
            }
        }
    }

    private static void printMenu() {
        System.out.println("""
                Выберите действие:
                1. Создать пользователя
                2. Показать всех пользователей
                3. Обновить пользователя
                4. Удалить пользователя
                5. Выход
                """);
    }

    private static void createUser() {
        try {
            System.out.print("Имя: ");
            String name = scanner.nextLine();
            System.out.print("Email: ");
            String email = scanner.nextLine();
            System.out.print("Возраст: ");
            Integer age = Integer.parseInt(scanner.nextLine());

            User user = new User();
            user.setName(name);
            user.setEmail(email);
            user.setAge(age);

            userService.addUser(user);
            System.out.println("Пользователь создан с id: " + user.getId());
        } catch (Exception e) {
            System.err.println("Ошибка при создании пользователя: " + e.getMessage());
        }
    }

    private static void listUsers() {
        List<User> users = userService.getAllUsers();
        if (users.isEmpty()) {
            System.out.println("Пользователь не найден");
        } else {
            users.forEach(u -> System.out.printf("ID: %d, Имя: %s, Email: %s, Возраст: %d, Создан: %s%n",
                    u.getId(), u.getName(), u.getEmail(), u.getAge(), u.getCreatedAt()));
        }
    }

    private static void updateUser() {
        try {
            System.out.print("ID пользователя: ");
            Long id = Long.parseLong(scanner.nextLine());

            Optional<User> userOpt = userService.getUserById(id);
            if (userOpt.isEmpty()) {
                System.out.println("Пользователь не найден.");
                return;
            }
            User user = userOpt.get();
            System.out.print("Новое имя (пусто - не менять): ");
            String name = scanner.nextLine();
            if (!name.isBlank()) {
                user.setName(name);
            }
            System.out.print("Новый email (пусто - не менять): ");
            String email = scanner.nextLine();
            if (!email.isBlank()) {
                user.setEmail(email);
            }
            System.out.print("Новый возраст (пусто - не менять): ");
            String age = scanner.nextLine();
            if (!age.isBlank()) {
                user.setAge(Integer.parseInt(age));
            }
            userService.updateUser(user);
            System.out.println("Пользователь обновлен.");
        } catch (Exception e) {
            System.err.println("Ошибка при обновлении пользователя: " + e.getMessage());
        }
    }

    private static void deleteUser() {
        try {
            System.out.print("ID пользователя для удаления: ");
            Long id = Long.parseLong(scanner.nextLine());

            userService.deleteUser(id);
            System.out.println("Пользователь удалён, если существовал.");
        } catch (Exception e) {
            System.err.println("Ошибка при удалении пользователя: " + e.getMessage());
        }
    }
}