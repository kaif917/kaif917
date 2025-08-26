import java.util.Scanner;

public class StudentResult {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);

        // Step 1: Input number of subjects
        System.out.print("Enter the number of subjects: ");
        int n = sc.nextInt();

        int[] marks = new int[n];
        int total = 0;

        // Step 2: Input marks for each subject
        for (int i = 0; i < n; i++) {
            System.out.print("Enter marks for subject " + (i + 1) + " (out of 100): ");
            marks[i] = sc.nextInt();
            total += marks[i];
        }

        // Step 3: Calculate total and average
        double average = (double) total / n;

        // Step 4: Determine grade
        char grade;
        if (average >= 90) {
            grade = 'A';
        } else if (average >= 75) {
            grade = 'B';
        } else if (average >= 50) {
            grade = 'C';
        } else if (average >= 35) {
            grade = 'D';
        } else {
            grade = 'F'; // Fail
        }

        // Step 5: Display results
        System.out.println("\n--- Student Result ---");
        System.out.println("Total Marks: " + total + "/" + (n * 100));
        System.out.println("Average Percentage: " + average + "%");
        System.out.println("Grade: " + grade);

        sc.close();
    }
}
