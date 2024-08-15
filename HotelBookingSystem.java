import java.util.ArrayList;
import java.util.HashMap;
import java.util.Scanner;

class Booking {
    private static int idCounter = 1;
    private int bookingId;
    private String customerName;
    private HotelRoom room;
    private int stayDuration;

    public Booking(String customerName, HotelRoom room, int stayDuration) {
        this.bookingId = idCounter++;
        this.customerName = customerName;
        this.room = room;
        this.stayDuration = stayDuration;
    }

    public int getBookingId() {
        return bookingId;
    }

    public String getCustomerName() {
        return customerName;
    }

    public HotelRoom getRoom() {
        return room;
    }

    public int getStayDuration() {
        return stayDuration;
    }

    @Override
    public String toString() {
        return "Booking{" +
                "Booking ID=" + bookingId +
                ", Customer Name='" + customerName + '\'' +
                ", Room Details=" + room +
                ", Duration (nights)=" + stayDuration +
                '}';
    }
}

class HotelManagement {
    private ArrayList<HotelRoom> rooms;
    private HashMap<Integer, Booking> bookings;

    public HotelManagement() {
        rooms = new ArrayList<>();
        bookings = new HashMap<>();
        rooms.add(new HotelRoom(101, "Single", 100.0));
        rooms.add(new HotelRoom(102, "Double", 150.0));
        rooms.add(new HotelRoom(103, "Suite", 250.0));
    }

    public void showBookingDetails(int bookingId) {
        Booking booking = bookings.get(bookingId);
        if (booking != null) {
            System.out.println("Booking Details:");
            System.out.println(booking);
        } else {
            System.out.println("No booking found with ID " + bookingId);
        }
    }

    public void createBooking(String customerName, int roomNumber, int stayDuration) {
        HotelRoom room = findAvailableRoom(roomNumber);
        if (room == null) {
            System.out.println("Sorry, Room " + roomNumber + " is not available.");
            return;
        }

        double totalAmount = room.getCost() * stayDuration;
        if (PaymentService.processPayment(customerName, totalAmount)) {
            room.setAvailability(false);
            Booking booking = new Booking(customerName, room, stayDuration);
            bookings.put(booking.getBookingId(), booking);
            System.out.println("Booking confirmed: " + booking);
        } else {
            System.out.println("Payment failed. Unable to complete the booking.");
        }
    }

    public void displayAvailableRooms() {
        System.out.println("Available Rooms:");
        for (HotelRoom room : rooms) {
            if (room.isAvailable()) {
                System.out.println(room);
            }
        }
    }

    public HotelRoom findAvailableRoom(int number) {
        for (HotelRoom room : rooms) {
            if (room.getNumber() == number && room.isAvailable()) {
                return room;
            }
        }
        return null;
    }
}

class HotelRoom {
    private int number;
    private String type;
    private double cost;
    private boolean isAvailable;

    public HotelRoom(int number, String type, double cost) {
        this.number = number;
        this.type = type;
        this.cost = cost;
        this.isAvailable = true;
    }

    public int getNumber() {
        return number;
    }

    public String getType() {
        return type;
    }

    public double getCost() {
        return cost;
    }

    public boolean isAvailable() {
        return isAvailable;
    }

    public void setAvailability(boolean available) {
        this.isAvailable = available;
    }

    @Override
    public String toString() {
        return "HotelRoom{" +
                "Room Number=" + number +
                ", Type='" + type + '\'' +
                ", Cost=" + cost +
                ", Available=" + isAvailable +
                '}';
    }
}

class PaymentService {
    public static boolean processPayment(String customerName, double totalAmount) {
        System.out.println("Processing payment for " + customerName + " of $" + totalAmount);
        return true;
    }
}

public class HotelBookingSystem {
    public static void main(String[] args) {
        HotelManagement hotelManagement = new HotelManagement();
        Scanner scanner = new Scanner(System.in);

        while (true) {
            System.out.println("\n1. View Available Rooms");
            System.out.println("2. Create a Booking");
            System.out.println("3. View Booking Details");
            System.out.println("4. Exit");
            System.out.print("Select an option: ");
            
            int choice = scanner.nextInt();
            switch (choice) {
                case 1:
                    hotelManagement.displayAvailableRooms();
                    break;
                case 2:
                    System.out.print("Please provide the customer name: ");
                    scanner.nextLine();
                    String customerName = scanner.nextLine();

                    System.out.print("Please enter the room number: ");
                    int roomNumber = scanner.nextInt();

                    System.out.print("Please specify the number of nights: ");
                    int stayDuration = scanner.nextInt();

                    hotelManagement.createBooking(customerName, roomNumber, stayDuration);
                    break;
                case 3:
                    System.out.print("Please provide the booking ID: ");
                    int bookingId = scanner.nextInt();
                    hotelManagement.showBookingDetails(bookingId);
                    break;
                case 4:
                    scanner.close();
                    System.out.println("Thank you for using our Hotel Booking System. Goodbye!");
                    return;
                default:
                    System.out.println("Invalid option. Please try again.");
            }
        }
    }
}
