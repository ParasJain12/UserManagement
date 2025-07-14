import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;
import java.util.regex.Pattern;
class User{
    private String name;
    private String email;
    private List<String> mobileNumber;

    public User(String name, String email, List<String> mobileNumber){
        this.name = name;
        this.email = email;
        this.mobileNumber = mobileNumber;
    }

    public String getName(){
        return name;
    }

    public String getEmail(){
        return email;
    }

    public List<String> getMobileNumber(){
        return mobileNumber;
    }

    public void setName(String name){   
        this.name = name;
    }
    
    public void setMobileNumber(List<String> mobileNumber){
        this.mobileNumber = mobileNumber;
    }

    @Override
    public String toString(){
        return "Name: "+ name + "\nEmail: " + email + "\nMobileNumber: " + mobileNumber; 
    }
}

class UserNotFoundException extends Exception {
    public UserNotFoundException(String message) {
        super(message);
    }
}

public class UserManagement{
    private static final Scanner sc = new Scanner(System.in);
    private static final Map<String,User> user = new HashMap<>();
    private static final Pattern emailPattern = Pattern.compile("^[\\w.-]+@[\\w.-]+\\.[A-Za-z]{2,}$");
    private static final Pattern phonePattern = Pattern.compile("^[6-9]\\d{9}$");
    public static void main(String args[]){
        while(true){
            System.out.println("!!!Admin Panel!!!");
            System.out.println("1. Create User");
            System.out.println("2. Delete User");
            System.out.println("3. Get Single User");
            System.out.println("4. Get All users");
            System.out.println("5. Update User");
            System.out.println("6. Exit");
            int choice = sc.nextInt();
            try{
                switch(choice){
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
            }
            catch(UserNotFoundException e){
                e.printStackTrace();
            }
        }
    }

    //Create a user
    public static void createUser() throws UserNotFoundException{
        System.out.print("Enter name: ");
        String name = sc.next();
        System.out.print("Enter a email: ");
        String email = sc.next();
        if(!emailPattern.matcher(email).matches()){
            System.out.println("Invalid email format!!!");
            return;
        }
        if(user.containsKey(email)){
            System.out.println("User already exits with this email");
        }
        List<String> mobileNumber = new ArrayList<>();
        while(true){
            System.out.print("Enter a mobile number: ");
            String number = sc.next();
            if(!phonePattern.matcher(number).matches()){
                System.out.println("Invalid mobile number!!!");
                return;
            }
            else{
                mobileNumber.add(number);
            }
            System.out.print("Add another number:(yes/no) ");
            if(!sc.next().equalsIgnoreCase("yes")){
                break;
            }
        }
        User u = new User(name,email,mobileNumber);
        user.put(email, u);
        System.out.println("User created successfully");
    }

    //Delete a user
    public static void deleteUser() throws UserNotFoundException{
        System.out.println("Enter email for delete operation: ");
        String email = sc.next();
        if(!user.containsKey(email)){
            throw new UserNotFoundException("User not found!!!");
        }
        user.remove(email);
        System.out.println("User is deleted");
    }

    //Get a single user
    public static void getSingleUser() throws UserNotFoundException{
        System.out.print("Enter a email to fetch single user: ");
        String email = sc.next();
        if(!user.containsKey(email)){
            throw new UserNotFoundException("User not found!!!");
        }
        System.out.println(user.get(email));
    }

    //Get All users
    public static void getAllUser() throws UserNotFoundException{
        if(user.isEmpty()){
            System.out.println("No users found!!!");
            return;
        }
        for(User u : user.values()){
            System.out.println(u);
        }
    }

    //Update a user
    public static void updateUser() throws UserNotFoundException{
        System.out.print("Enter a email to update: ");
        String email = sc.next();
        if(!user.containsKey(email)) throw new UserNotFoundException("User not found!!!");
        User u = user.get(email);
        System.out.print("Enter a new name: ");
        String newName = sc.next();
        if(!newName.isEmpty()){
            u.setName(newName);
        }
        List<String> newNumber = new ArrayList<>();
        System.out.println("Enter a new number: ");
        while(true){
            System.out.print("Enter a mobile number: ");
            String number = sc.next();
             if(!phonePattern.matcher(number).matches()){
                System.out.println("Invalid mobile number!!!");
                return;
            }
            else{
                newNumber.add(number);
            }
            System.out.print("Add another number: (yes/no): ");
            if (!sc.next().equalsIgnoreCase("yes")){
                break;
            }
        }
        u.setMobileNumber(newNumber);
        System.out.println("User information updated successfully");
    }

}
