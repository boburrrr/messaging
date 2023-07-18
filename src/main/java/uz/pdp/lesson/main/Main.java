package uz.pdp.lesson.main;

import org.apache.commons.collections4.queue.PredicatedQueue;
import org.apache.poi.ss.usermodel.CellType;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.apache.poi.xwpf.usermodel.XWPFDocument;
import org.apache.poi.xwpf.usermodel.XWPFParagraph;
import org.apache.poi.xwpf.usermodel.XWPFRun;
import uz.pdp.lesson.model.MyRunnable;
import uz.pdp.lesson.model.User;

import java.io.*;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.stream.Stream;

public class Main {
    public static Queue<Integer> integers = new ArrayDeque<>();
    public static ArrayList<Integer> integers1 = new ArrayList<>();
    public static HashMap<Integer,String> integerHashMap = new HashMap<>();
    public static ConcurrentHashMap<Integer,String> concurrentHashMap = new ConcurrentHashMap<>();
    public static void main(String[] args) {
//        lesson1();
//        lesson2();
//        lesson3();

//        lessonsFile();

        String path = "C:\\Users\\bobur\\Desktop\\javaProject\\messaging\\src\\main\\resources\\darslik\\xlsxFile\\file.xlsx";
        lesson4(path);
        //lesson5(path);

        String path1 = "C:\\Users\\bobur\\Desktop\\javaProject\\messaging\\src\\main\\resources\\darslik\\docxFile\\file.docx";
        //lesson6(path1);
        //lesson7(path1);

        //lesson8();
        //lesson9();

        //lesson10();

    }

    private static void lesson10() {
        Integer number = 1;
        Runnable runnable = new Runnable() {
            @Override
            public void run() {
                for (int j = 0; j < 1000; j++) {
                    integerHashMap.put(j++,""+j);
                }
            }
        };
        for (int i = 0; i < 100; i++) {
            Thread thread = new Thread(runnable);
            thread.start();
        }
        Runnable runnable1 = new Runnable() {
            @Override
            public void run() {
                for (int j = 0; j < 1000; j++) {
                    concurrentHashMap.put(j++,""+j);
                }
            }
        };
        for (int i = 0; i < 100; i++) {
            Thread thread = new Thread(runnable1);
            thread.start();
        }
        try {
            Thread.sleep(20000);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        System.out.println(integerHashMap.size());
        System.out.println(concurrentHashMap.size());
    }

    private static void lesson9() {
        for (int i = 0; i < 100; i++) {
            integers.add(i);
        }
        MyRunnable myRunnable = new MyRunnable();
        Thread fromThread = new Thread(myRunnable);
        Thread fromThread1 = new Thread(myRunnable);
        Thread fromThread2 = new Thread(myRunnable);
        Thread fromThread3 = new Thread(myRunnable);
        Thread fromThread4 = new Thread(myRunnable);
        fromThread.start();
        fromThread1.start();
        fromThread2.start();
        fromThread3.start();
        fromThread4.start();
    }

    private static void lesson8() {
        for (int i = 0; i < 100; i++) {
            integers.add(i);
        }
    }

    private static void lesson7(String path) {
        try {
            XWPFDocument xwpfDocument = new XWPFDocument(new FileInputStream(path));
            List<XWPFParagraph> paragraphs = xwpfDocument.getParagraphs();
            for (XWPFParagraph paragraph : paragraphs) {
                System.out.println(paragraph.getText());
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private static void lesson6(String path) {
        XWPFDocument xwpfDocument =new XWPFDocument();
        XWPFParagraph xwpfParagraph = xwpfDocument.createParagraph();
        XWPFRun xwpfRun = xwpfParagraph.createRun();
        xwpfRun.setText("Bobur");
        try {
            xwpfDocument.write(new FileOutputStream(new File(path)));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private static void lesson5(String path) {
        XSSFWorkbook workbook = new XSSFWorkbook();
        XSSFSheet xssfSheet = workbook.createSheet("Bobur");

        XSSFRow xssfRow = xssfSheet.createRow(0);

        XSSFCell xssfCell = xssfRow.createCell(0,CellType.STRING);
        xssfCell.setCellValue("name");

        XSSFCell xssfCell1 = xssfRow.createCell(1,CellType.STRING);
        xssfCell1.setCellValue("age");

        ArrayList<User> users = new ArrayList<>();
        users.add(new User("Bobur",2003));
        users.add(new User("Bobur",2003));
        users.add(new User("Bobur",2003));
        users.add(new User("Bobur",2003));
        users.add(new User("Bobur",2003));

        int rowNumbers = 1;
        for (User user : users) {
            XSSFRow xssfRow1 = xssfSheet.createRow(rowNumbers++);

            XSSFCell xssfCell11 = xssfRow1.createCell(0,CellType.STRING);
            xssfCell11.setCellValue(user.getName());

            XSSFCell xssfCell12 = xssfRow1.createCell(1,CellType.NUMERIC);
            xssfCell12.setCellValue(user.getYear());

        }
        try {
            workbook.write(new FileOutputStream(path));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private static void lesson4(String path) {
        XSSFWorkbook workbook = new XSSFWorkbook();
        XSSFSheet xssfSheet = workbook.createSheet("Bobur");
        XSSFRow xssfRow = xssfSheet.createRow(0);
        XSSFCell xssfCell = xssfRow.createCell(0);
        xssfCell.setCellType(CellType.STRING);
        xssfCell.setCellValue("salom bobur");
        try {
            workbook.write(new FileOutputStream(path));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private static void lessonsFile() {
        int i = 1;
        while (i<10){
            File file = new File("D:\\JavaLesson\\tester"+"\\module"+i);
            System.out.println(file.mkdirs());
            int i1 = 1;
            while (i1<13){
                File file1 = new File("D:\\JavaLesson\\tester"+"\\module"+i+"\\lesson"+i1);
                System.out.println(file1.mkdirs());
                i1++;
            }
            i++;
        }
    }

    private static void lesson3() {
        ArrayList<Integer> nums = new ArrayList<>();
        nums.add(31);
        nums.add(38);
        nums.add(32);
        Stream<Integer> sorted1 =  nums.stream().sorted((n, n1) -> {
            if(n < n1){
                return 1;
            }else if(n > n1){
                return -1;
            }else{
                return 0;
            }
        });
        sorted1.forEach(System.out::println);
    }

    private static void lesson2() {
        Function<User,Integer> birthYear = new Function<User, Integer>() {
            @Override
            public Integer apply(User user) {
                Calendar calendar = Calendar.getInstance();
                return calendar.getWeekYear()-user.getYear();
            }
        };
        Function<User,String> newBirth = new Function<User, String>() {
            @Override
            public String apply(User user) {
                Calendar calendar = Calendar.getInstance();
                return calendar.getWeekYear()-user.getYear()+"";
            }
        };

        User user = new User("bobur",2003);
        System.out.println(birthYear.apply(user));
        System.out.println(newBirth.apply(user));

    }

    private static void lesson1() {
        Predicate<Integer> juftToq = new Predicate<Integer>() {
            @Override
            public boolean test(Integer integer) {
                if (integer % 2 == 0) {
                    return true;
                } else {
                    return false;
                }
            }

        };
        Predicate<String> start = new Predicate<String>() {
            @Override
            public boolean test(String s) {
                if(s.equals("start")){
                    return true;
                }
                return false;
            }
        };

        System.out.println(juftToq.test(8));
        System.out.println(start.test("start"));
    }
}
