import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;
import java.util.regex.Pattern;

class UserNotFoundException extends Exception {
    public UserNotFoundException(String message) {
        super(message);
    }
}

public class UserMgmt {
    static Scanner sc = new Scanner(System.in);
    private static final Pattern emailPattern = Pattern.compile("^[\\w.-]+@[\\w.-]+\\.[A-Za-z]{2,}$");
    private static final Pattern phonePattern = Pattern.compile("^[6-9]\\d{9}$");
    static Map<String, List<String>> map = new HashMap<>();

    public static void main(String args[]) {
        while (true) {
            System.out.println("!!!Admin Panel!!!");
            System.out.println("1. Create User");
            System.out.println("2. Delete User");
            System.out.println("3. Get Single User");
            System.out.println("4. Get All users");
            System.out.println("5. Update User");
            System.out.println("6. Exit");
            int choice;
            while (true) {
                System.out.print("Enter your choice: ");
                String input = sc.nextLine().trim();
                try {
                    choice = Integer.parseInt(input);
                    if (choice >= 1 && choice <= 6) {
                        break;
                    } else {
                        System.out.println("Please enter a number between 1 and 6.");
                    }
                } catch (NumberFormatException e) {
                    System.out.println("Invalid input! Please enter a valid number.");
                }
            }
            try {
                switch (choice) {
                    case 1 -> createUser();
                    case 2 -> deleteUser();
                    case 3 -> getSingleUser();
                    case 4 -> getAllUser();
                    case 5 -> updateUser();
                    case 6 -> {
                        System.out.println("Exit from application...");
                        return;
                    }
                    default -> System.out.println("Incorrect choice");
                }
            } catch (UserNotFoundException e) {
                e.printStackTrace();
            }
        }
    }

    public static void createUser() throws UserNotFoundException {
        System.out.println("---You chose create a user---");
        System.out.print("Are you sure you want to create user data? (yes to continue / no to cancel): ");
        String confirm = sc.nextLine().trim();
        if (!confirm.equalsIgnoreCase("yes")) {
            System.out.println("Returning to main menu...");
            return;
        }
        List<String> ls = new ArrayList<>();
        String name;
        while (true) {
            System.out.print("Enter a name: ");
            name = sc.nextLine().trim();
            if (name.isEmpty()) {
                System.out.println("Name cannot be empty!");
                continue;
            }
            if (!name.matches("[a-zA-Z ]+")) {
                System.out.println(
                        "Name must contain only letters and spaces \nNo digits or special characters allowed!");
                continue;
            }
            break;
        }

        String email;
        while (true) {
            System.out.print("Enter a email: ");
            email = sc.nextLine();
            if (!emailPattern.matcher(email).matches()) {
                System.out.println("Invalid email format!!!");
                continue;
            }
            if (map.containsKey(email)) {
                System.out.println("Already exists");
            }
            break;
        }
        ls.add(name);
        while (true) {
            System.out.print("Enter a mobile number(enter only 10 digit & starts with only 6,7,8,9): ");
            String mobileNo = sc.nextLine();
            if (phonePattern.matcher(mobileNo).matches()) {
                ls.add(mobileNo);
            } else {
                System.out.println("Invalid phone number");
                continue;
            }
            System.out.print("Add another mobile number: (yes/no)?");
            if (!sc.nextLine().equalsIgnoreCase("yes")) {
                break;
            }
        }
        map.put(email, ls);
    }

    public static void deleteUser() throws UserNotFoundException {
        System.out.println("---You chose to delete the user data---");
        System.out.print("Are you sure you want to delete user data? (yes to continue / no to cancel): ");
        String confirm = sc.nextLine().trim();
        if (!confirm.equalsIgnoreCase("yes")) {
            System.out.println("Returning to main menu...");
            return;
        }
        String email;
        while (true) {
            System.out.print("Enter a email to delete the data: ");
            email = sc.nextLine();
            if (!emailPattern.matcher(email).matches()) {
                System.out.println("Invalid email format!!!");
                continue;
            }
            break;
        }
        if (map.remove(email) != null) {
            System.out.println("User is deleted");
        } else {
            System.out.println("User not found");
        }
    }

    public static void getSingleUser() throws UserNotFoundException {
        System.out.println("---You chose to get the single user---");
        System.out.print("Are you sure you want to fetch the single user data? (yes to continue / no to cancel): ");
        String confirm = sc.nextLine().trim();
        if (!confirm.equalsIgnoreCase("yes")) {
            System.out.println("Returning to main menu...");
            return;
        }
        String email;
        while (true) {
            System.out.print("Enter a email to fetch single user data: ");
            email = sc.nextLine();
            if (!emailPattern.matcher(email).matches()) {
                System.out.println("Invalid email format!!!");
                continue;
            }
            break;
        }
        List<String> list = map.get(email);
        if (list == null) {
            System.out.println("Cannot find the data");
        } else {
            System.out.println("Name: " + list.get(0));
            System.out.println("Email: " + email);
            System.out.print("Mobile Number: ");
            for (int i = 1; i < list.size(); i++) {
                System.out.print(list.get(i) + " ");
            }
        }
    }

    public static void getAllUser() throws UserNotFoundException {
        System.out.println("---You chose to get all users---");
        System.out.print("Are you sure you want to fetch all users data? (yes to continue / no to cancel): ");
        String confirm = sc.nextLine().trim();
        if (!confirm.equalsIgnoreCase("yes")) {
            System.out.println("Returning to main menu...");
            return;
        }
        if (map.isEmpty()) {
            System.out.println("Not found any data");
            return;
        }
        for (Map.Entry<String, List<String>> entry : map.entrySet()) {
            String email = entry.getKey();
            List<String> list = entry.getValue();
            System.out.println("Email: " + email);
            System.out.println("Name: " + list.get(0));
            System.out.print("Mobile Number: ");
            for (int i = 1; i < list.size(); i++) {
                System.out.print(list.get(i) + " ");
            }
        }
    }

    public static void updateUser() throws UserNotFoundException {
        System.out.println("---You chose to update the user---");
        System.out.print("Are you sure you want to update user data? (yes to continue / no to cancel): ");
        String confirm = sc.nextLine().trim();
        if (!confirm.equalsIgnoreCase("yes")) {
            System.out.println("Returning to main menu...");
            return;
        }

        String email;
        while (true) {
            System.out.print("Enter an email to update user data: ");
            email = sc.nextLine().trim();
            if (!emailPattern.matcher(email).matches()) {
                System.out.println("Invalid email format!!!");
                continue;
            }
            if (!map.containsKey(email)) {
                System.out.println("User not found.");
                return;
            }
            break;
        }

        List<String> userData = map.get(email);

        while (true) {
            System.out.println("What do you want to update?");
            System.out.println("1. Only Name");
            System.out.println("2. Only Email");
            System.out.println("3. Only Mobile Number");
            System.out.println("4. Exit");

            int choice;
            while (true) {
                System.out.print("Enter your choice: ");
                String input = sc.nextLine().trim();
                try {
                    choice = Integer.parseInt(input);
                    if (choice >= 1 && choice <= 4) {
                        break;
                    } else {
                        System.out.println("Please enter a number between 1 and 4");
                    }
                } catch (NumberFormatException e) {
                    System.out.println("Invalid input! Please enter a valid number");
                }
            }

            switch (choice) {
                case 1:
                    String newName;
                    while (true) {
                        System.out.print("Enter new name: ");
                        newName = sc.nextLine().trim();
                        if (newName.isEmpty()) {
                            System.out.println("Name cannot be empty");
                            continue;
                        }
                        if (!newName.matches("[a-zA-Z ]+")) {
                            System.out.println("Name must contain only letters and spaces. No digits or special characters allowed!");
                            continue;
                        }
                        break;
                    }
                    userData.set(0, newName);
                    System.out.println("Name updated successfully.");
                    break;

                case 2:
                    String newEmail;
                    while (true) {
                        System.out.print("Enter new email: ");
                        newEmail = sc.nextLine();
                        if (newEmail.isEmpty()) {
                            System.out.println("Email cannot be empty.");
                            continue;
                        }
                        if (!emailPattern.matcher(newEmail).matches()) {
                            System.out.println("Invalid email format.");
                            continue;
                        }
                        if (map.containsKey(newEmail)) {
                            System.out.println("Email already exists.");
                            continue;
                        }
                        break;
                    }
                    map.remove(email);
                    map.put(newEmail, userData);
                    email = newEmail;
                    System.out.println("Email updated successfully.");
                    break;

                case 3: 
                    List<String> updatedNumbers = new ArrayList<>();
                    updatedNumbers.add(userData.get(0)); 

                    while (true) {
                        String mobile;
                        while (true) {
                            System.out.print("Enter new mobile number: ");
                            mobile = sc.nextLine().trim();
                            if (mobile.isEmpty()) {
                                System.out.println("Mobile number cannot be empty.");
                                continue;
                            }
                            if (!phonePattern.matcher(mobile).matches()) {
                                System.out.println("Invalid phone number.");
                                continue;
                            }
                            break;
                        }
                        updatedNumbers.add(mobile);

                        System.out.print("Add another mobile number? (yes/no): ");
                        String more = sc.nextLine().trim();
                        if (!more.equalsIgnoreCase("yes"))
                            break;
                    }
                    map.put(email, updatedNumbers);
                    System.out.println("Mobile number(s) updated successfully.");
                    break;

                case 4:
                    System.out.println("Exiting update section...");
                    return;
            }

            System.out.print("\nDo you want to update anything else for this user? (yes/no): ");
            String again = sc.nextLine();
            if (!again.equalsIgnoreCase("yes")) {
                System.out.println("Finished updating user. Returning to main menu...");
                break;
            }
        }
    }
}
