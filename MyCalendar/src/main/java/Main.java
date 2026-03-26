import calendar.domain.*;
import calendar.domain.value.*;

import java.time.LocalDateTime;
import java.time.temporal.WeekFields;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.Scanner;

public class Main {

    public static void main(String[] args) {
        CalendarManager calendar = new CalendarManager();
        Scanner scanner = new Scanner(System.in);
        String user = null;

        String[] usernames = new String[99];
        String[] passwords = new String[99];
        int userCount = 0;

        while (true) {
            if (user == null) {
                printBanner();
                System.out.println("1 - Login");
                System.out.println("2 - Create account");
                System.out.print("Choice: ");

                switch (scanner.nextLine()) {
                    case "1":
                        System.out.print("Username: ");
                        String inputUser = scanner.nextLine();
                        System.out.print("Password: ");
                        String inputPass = scanner.nextLine();
                        for (int i = 0; i < userCount; i++) {
                            if (usernames[i].equals(inputUser) && passwords[i].equals(inputPass)) {
                                user = usernames[i];
                            }
                        }
                        break;
                    case "2":
                        System.out.print("Username: ");
                        String newUser = scanner.nextLine();
                        System.out.print("Password: ");
                        String newPass = scanner.nextLine();
                        System.out.print("Confirm password: ");
                        if (scanner.nextLine().equals(newPass)) {
                            usernames[userCount] = newUser;
                            passwords[userCount] = newPass;
                            userCount++;
                            user = newUser;
                        } else {
                            System.out.println("Passwords do not match.");
                        }
                        break;
                }
            }

            while (user != null) {
                System.out.println("\nHello, " + user);
                System.out.println("=== Calendar Manager ===");
                System.out.println("1 - View events");
                System.out.println("2 - Add appointment");
                System.out.println("3 - Add meeting");
                System.out.println("4 - Add periodic event");
                System.out.println("5 - Delete event");
                System.out.println("6 - Logout");
                System.out.print("Choice: ");

                switch (scanner.nextLine()) {
                    case "1":
                        handleView(calendar, scanner);
                        break;
                    case "2":
                        handleAddAppointment(calendar, scanner, user);
                        break;
                    case "3":
                        handleAddMeeting(calendar, scanner, user);
                        break;
                    case "4":
                        handleAddPeriodic(calendar, scanner);
                        break;
                    case "5":
                        handleDelete(calendar, scanner);
                        break;
                    default:
                        user = null;
                }
            }
        }
    }

    private static void handleView(CalendarManager calendar, Scanner scanner) {
        System.out.println("1 - All events");
        System.out.println("2 - By month");
        System.out.println("3 - By week");
        System.out.println("4 - By day");
        System.out.print("Choice: ");

        switch (scanner.nextLine()) {
            case "1":
                List<Event> conflicts = calendar.findConflicts();
                calendar.eventsInPeriod(
                        EventDateTime.of(LocalDateTime.MIN),
                        EventDateTime.of(LocalDateTime.MAX)
                ).forEach(e -> {
                    String prefix = conflicts.contains(e) ? "[CONFLICT] " : "- ";
                    System.out.println(prefix + e.description());
                });
                break;
            case "2":
                System.out.print("Year: ");
                int year = Integer.parseInt(scanner.nextLine());
                System.out.print("Month (1-12): ");
                int month = Integer.parseInt(scanner.nextLine());
                EventDateTime startMonth = EventDateTime.of(LocalDateTime.of(year, month, 1, 0, 0));
                EventDateTime endMonth = EventDateTime.of(LocalDateTime.of(year, month, 1, 0, 0).plusMonths(1).minusSeconds(1));
                printEvents(calendar.eventsInPeriod(startMonth, endMonth));
                break;
            case "3":
                System.out.print("Year: ");
                int y = Integer.parseInt(scanner.nextLine());
                System.out.print("Week number (1-52): ");
                int week = Integer.parseInt(scanner.nextLine());
                LocalDateTime weekBase = LocalDateTime.now().withYear(y)
                        .with(WeekFields.of(Locale.FRANCE).weekOfYear(), week)
                        .with(WeekFields.of(Locale.FRANCE).dayOfWeek(), 1)
                        .withHour(0).withMinute(0).withSecond(0);
                printEvents(calendar.eventsInPeriod(EventDateTime.of(weekBase), EventDateTime.of(weekBase.plusDays(7).minusSeconds(1))));
                break;
            case "4":
                System.out.print("Year: ");
                int dy = Integer.parseInt(scanner.nextLine());
                System.out.print("Month: ");
                int dm = Integer.parseInt(scanner.nextLine());
                System.out.print("Day: ");
                int dd = Integer.parseInt(scanner.nextLine());
                printEvents(calendar.eventsInPeriod(
                        EventDateTime.of(LocalDateTime.of(dy, dm, dd, 0, 0)),
                        EventDateTime.of(LocalDateTime.of(dy, dm, dd, 23, 59, 59))
                ));
                break;
        }
    }

    private static void handleAddAppointment(CalendarManager calendar, Scanner scanner, String user) {
        System.out.print("Title: ");
        EventTitle title = EventTitle.of(scanner.nextLine());
        LocalDateTime start = readDateTime(scanner);
        System.out.print("Duration (minutes): ");
        EventDuration duration = EventDuration.ofMinutes(Long.parseLong(scanner.nextLine()));
        calendar.add(new PersonalAppointment(EventId.generate(), title, EventDateTime.of(start), duration));
        System.out.println("Event added.");
    }

    private static void handleAddMeeting(CalendarManager calendar, Scanner scanner, String user) {
        System.out.print("Title: ");
        EventTitle title = EventTitle.of(scanner.nextLine());
        LocalDateTime start = readDateTime(scanner);
        System.out.print("Duration (minutes): ");
        EventDuration duration = EventDuration.ofMinutes(Long.parseLong(scanner.nextLine()));
        System.out.print("Location: ");
        EventLocation location = EventLocation.of(scanner.nextLine());

        List<EventTitle> names = new ArrayList<>();
        names.add(EventTitle.of(user));
        System.out.println("Add participant? (yes/no)");
        while (scanner.nextLine().equals("yes")) {
            System.out.print("Name: ");
            names.add(EventTitle.of(scanner.nextLine()));
            System.out.println("Add another? (yes/no)");
        }
        calendar.add(new Meeting(EventId.generate(), title, EventDateTime.of(start), duration, location, Participants.of(names)));
        System.out.println("Event added.");
    }

    private static void handleAddPeriodic(CalendarManager calendar, Scanner scanner) {
        System.out.print("Title: ");
        EventTitle title = EventTitle.of(scanner.nextLine());
        LocalDateTime start = readDateTime(scanner);
        System.out.print("Duration (minutes): ");
        EventDuration duration = EventDuration.ofMinutes(Long.parseLong(scanner.nextLine()));
        System.out.print("Frequency (days): ");
        Frequency frequency = Frequency.everyDays(Long.parseLong(scanner.nextLine()));
        calendar.add(new PeriodicEvent(EventId.generate(), title, EventDateTime.of(start), duration, frequency));
        System.out.println("Event added.");
    }

    private static void handleDelete(CalendarManager calendar, Scanner scanner) {
        System.out.print("Event ID: ");
        calendar.remove(EventId.of(scanner.nextLine()));
        System.out.println("Event deleted.");
    }

    private static LocalDateTime readDateTime(Scanner scanner) {
        System.out.print("Year: ");
        int y = Integer.parseInt(scanner.nextLine());
        System.out.print("Month (1-12): ");
        int m = Integer.parseInt(scanner.nextLine());
        System.out.print("Day: ");
        int d = Integer.parseInt(scanner.nextLine());
        System.out.print("Hour (0-23): ");
        int h = Integer.parseInt(scanner.nextLine());
        System.out.print("Minute (0-59): ");
        int min = Integer.parseInt(scanner.nextLine());
        return LocalDateTime.of(y, m, d, h, min);
    }

    private static void printEvents(List<Event> events) {
        if (events.isEmpty()) {
            System.out.println("No events found.");
            return;
        }
        events.forEach(e -> System.out.println("- " + e.description()));
    }

    private static void printBanner() {
        System.out.println(" _____      _                 _            __  __");
        System.out.println("|  __ \\    | |               | |          |  \\/  |");
        System.out.println("| |  | | __| | __ _ _ __   __| | __ _ _ __| \\  / | __ _ _ __   __ _  __ _  ___ _ __");
        System.out.println("| |  | |/ _` |/ _` | '_ \\ / _` |/ _` | '__| |\\/| |/ _` | '_ \\ / _` |/ _` |/ _ \\ '__|");
        System.out.println("| |__| | (_| | (_| | | | | (_| | (_| | |  | |  | | (_| | | | | (_| | (_| |  __/ |");
        System.out.println("|_____/ \\__,_|\\__,_|_| |_|\\__,_|\\__,_|_|  |_|  |_|\\__,_|_| |_|\\__,_|\\__, |\\___|_|");
        System.out.println("                                                                        __/ |");
        System.out.println("                                                                       |___/");
    }
}
