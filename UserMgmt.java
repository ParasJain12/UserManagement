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

public class UserMgmt{
    static Scanner sc = new Scanner(System.in);
    private static final Pattern emailPattern = Pattern.compile("^[\\w.-]+@[\\w.-]+\\.[A-Za-z]{2,}$");
    private static final Pattern phonePattern = Pattern.compile("^[6-9]\\d{9}$");
    static Map<String,List<String>> map = new HashMap<>();

    public static void main(String args[]){
        while(true){
            System.out.println("!!!Admin Panel!!!");
            System.out.println("1. Create User");
            System.out.println("2. Delete User");
            System.out.println("3. Get Single User");
            System.out.println("4. Get All users");
            System.out.println("5. Update User");
            System.out.println("6. Exit");
            int choice = Integer.parseInt(sc.nextLine());
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

    public static void createUser() throws UserNotFoundException{
        List<String> ls = new ArrayList<>();
        System.out.print("Enter a name: ");
        String name = sc.nextLine();
        System.out.print("Enter a email: ");
        String email = sc.nextLine();
        if(!emailPattern.matcher(email).matches()){
            System.out.println("Invalid email format!!!");
            return;
        }
        if(map.containsKey(email)){
            System.out.println("Already exists");
        }
        ls.add(name);
        while(true){
            System.out.print("Enter a mobile number: ");
            String mobileNo = sc.nextLine();
            if(phonePattern.matcher(mobileNo).matches()){
                ls.add(mobileNo);
            }else{
                System.out.println("Invalid phone number");
                return;
            }
            System.out.print("Add another mobile number: (yes/no)?");
             if(!sc.nextLine().equalsIgnoreCase("yes")){
                break;
            }
        }
        map.put(email,ls);

    }

    public static void deleteUser() throws UserNotFoundException{
        System.out.print("Enter a email to delete data: ");
        String email = sc.nextLine();
        if(map.remove(email) != null){
            System.out.println("User is deleted");
        }
        else{
            System.out.println("User not found");
        }
    }

    public static void getSingleUser() throws UserNotFoundException{
        System.out.print("Enter a email to find user: ");
        String email = sc.nextLine();
        List<String> list = map.get(email);
        if(list == null){
            System.out.println("Cannot find the data");
        }
        else{
            System.out.println("Name: "+list.get(0));
            System.out.println("Email: "+email);
            System.out.print("Mobile Number: ");
            for(int i=1;i<list.size();i++){
                System.out.print(list.get(i) + " ");
            }
        }
    }

    public static void getAllUser() throws UserNotFoundException{
        if(map.isEmpty()){
            System.out.println("Not found any data");
            return;
        }
        for(Map.Entry<String,List<String>>entry:map.entrySet()){
            String email = entry.getKey();
            List<String> list = entry.getValue();
            System.out.println("Email: "+email);
            System.out.println("Name: "+list.get(0));
            System.out.print("Mobile Number: ");
            for(int i=1;i<list.size();i++){
                System.out.print(list.get(i) + " ");
            }
        }
    }

    public static void updateUser() throws UserNotFoundException{
        System.out.print("Enter a email to update the data: ");
        String email = sc.nextLine();
        if(!map.containsKey(email)){
            System.out.println("Not found");
            return;
        }
        List<String> list = new ArrayList<>();
        System.out.print("Enter a new name: ");
        String name = sc.nextLine();
        list.add(name);
        while(true){
            System.out.print("Enter a new mobile number: ");
            String mobileNo = sc.nextLine();
            if(phonePattern.matcher(mobileNo).matches()){
                list.add(mobileNo);
            }else{
                System.out.println("Invalid phone number");
                return;
            }
            System.out.print("Add another mobile number: (yes/no)?");
            if(!sc.nextLine().equalsIgnoreCase("yes")){
                break;
            }
        }
        map.put(email,list);
        System.out.println("Information is updated");
    }
}