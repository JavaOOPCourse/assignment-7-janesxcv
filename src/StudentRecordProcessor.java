

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class StudentRecordProcessor {
    // Поля для хранения данных
    private final List<Student> students = new ArrayList<>();

    // _____реализуйте класс Student ниже в этом же файле______

    private double averageScore;
    private Student highestStudent;


    /**
     * Task 1 + Task 2 + Task 5 + Task 6
     */
    public void readFile() {
        // TODO: реализуйте чтение файла здесь
        System.out.println("--- reading file ----");

        try {
            BufferedReader br = new BufferedReader(new FileReader("students.txt"));
            String line;
            while ((line = br.readLine()) != null) {
                line = line.trim();

                if (line.isEmpty()) continue;

                try {
                    String[] parts = line.split(",", 2);
                    if (parts.length != 2) {
                        throw new NumberFormatException("wrong format");

                    }
                    String name = parts[0].trim();
                    int score = Integer.parseInt(parts[1].trim());

                    if (score < 0 || score > 100) {
                        throw new InvalidScoreException("invalid score");
                    }

                    Student student = new Student(name, score);
                    students.add(student);
                    System.out.println("new student : " + student);
                }catch (NumberFormatException e) {
                    System.out.println("Invalid data type");
                } catch (InvalidScoreException e) {
                    System.out.println("Invalid data : "+line);
                }
            }
        } catch (FileNotFoundException e) {
            System.out.println("Error with reading a file");;
        } catch (IOException e) {
            System.out.println("Error with file");;
        }
        System.out.println("Valid number of students : "+students.size());
    }


    /**
     * Task 3 + Task 8
     */
    public void processData() {
        // TODO: обработка данных и сортировка здесь
        if (students.isEmpty()) {
            System.out.println("file is empty");
            return;
        }
        students.sort((a,b) -> b.getScore() - a.getScore());
        int total = 0;
        for (Student s: students) {
            total += s.getScore();
        }
        averageScore = (double) total / students.size();
        highestStudent = students.get(0);

        System.out.println("---Process the data----");
        System.out.println("average score : "+ averageScore);
        System.out.println("Student with highest gpa : "+highestStudent);

    }

    /**
     * Task 4 + Task 5 + Task 8
     */
    public void writeFile() {
        // TODO: запись результата в файл здесь
        if (students.isEmpty()) {
            System.out.println("invalid data");
            return;
        }

        // Create output/ directory if it doesn't exist (defensive programming)
        File outDir = new File("report.txt").getParentFile();
        if (outDir != null && !outDir.exists()) {
            outDir.mkdirs();
        }

        try {
            BufferedWriter writer = new BufferedWriter(new FileWriter("report.txt"));

            String line;
            writer.write(String.format("Average score: %.2f", averageScore));
            writer.newLine();
            writer.write(String.format("student with highest score : "+highestStudent));
            writer.newLine();
            writer.newLine();

            writer.write("---Students ranking----");
            writer.newLine();
            int rank = 1;
            for (Student a : students) {
                writer.write(String.format("%d. %s", rank++, a));
                writer.newLine();

            }



        } catch (IOException e) {
            System.out.println("some problems with file");;
        }
    }

    public static void main(String[] args) {
        StudentRecordProcessor processor = new StudentRecordProcessor();

        try {
            processor.readFile();
            processor.processData();
            processor.writeFile();
            System.out.println("Processing completed. Check output/report.txt");
        } catch (Exception e) {
            System.err.println("Unexpected error: " + e.getMessage());
        }
    }
}

// class InvalidScoreException реализуйте меня
// class Student (name, score)
class InvalidScoreException extends Exception {
    public InvalidScoreException(String msg) {
        super(msg);
    }
}

class Student {
    private String name;
    private int score;

    public Student(String name, int score) {
        this.name = name;
        this.score = score;
    }

    public String getName() {
        return name;
    }

    public int getScore() {
        return score;
    }

    @Override
    public String toString() {
        return "Student{" +
                "name='" + name + '\'' +
                ", score=" + score +
                '}';
    }
}
