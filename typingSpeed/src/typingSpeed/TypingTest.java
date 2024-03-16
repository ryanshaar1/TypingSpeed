package typingSpeed;
import java.util.Scanner;
import java.util.concurrent.TimeUnit;

public class TypingTest {
    private String text;
    private int mode;
    private double typingSpeed;
    private int accuracy;

    /**
     * טענת כניסה לבניית אובייקט מסוג TypingTest.
     * הפעולה מקבלת כפרמטרים טקסט להקלדה ומצב התמודדות.
     * תפקיד הפעולה הוא לאתחל את המשתנים הפנימיים text ו-mode.
     */

    public TypingTest(String text, int mode) {
        this.text = text;
        this.mode = mode;
    }
    /**
     * טענת כניסה לפעולה startTest של האובייקט TypingTest.
     * הפעולה מתחילה את המבחן לפי המצב התמודדות שהוגדר עבור האובייקט.
     * תנאי הבדיקה משווה את המצב התמודדות עם 1 או 2, ובהתאם לתוצאה המתקבלת מפעילה את הפונקציה המתאימה.
     */
    /**
     * טענת יציאה מהפעולה startTest של האובייקט TypingTest.
     * הפעולה מופעלת לאחר ביצוע בדיקת התנאי, ומופנה לפונקציה המתאימה לפי המצב התמודדות.
     */

    public void startTest() {
        if (this.mode == 1 || this.mode == 2) {
            typeFullText();
        } 

        else {
            typeInOneMinute();
        }
    }
    /**
     * טענת כניסה לפונקציה typeFullText.
     * פונקציה זו מתחילה את תהליך הקלטת הטקסט מהמשתמש ומשווה אותו לטקסט הנתון.
     * הפונקציה מחשבת את דיוק ההקלדה ואת מהירות ההקלדה של המשתמש.
     */
    /**
     * טענת יציאה מהפונקציה typeFullText.
     * פונקציה זו מסיימת את תהליך הקלטת הטקסט מהמשתמש ומחשבת את הדיוק והמהירות של ההקלדה.
     */

    private void typeFullText() {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Start typing the following text:\n");
        System.out.println(this.text + "\n");

        long start = System.currentTimeMillis();
        String input = scanner.nextLine();
        long end = System.currentTimeMillis();

        this.accuracy = calculateAccuracy(input);
        this.typingSpeed = calculateTypingSpeed(input, start, end);
        this.typingSpeed = this.typingSpeed/4.7*60;
        scanner.close();
        displayResult();
    }
    /**
     * טענת כניסה לפונקציה typeInOneMinute.
     * פונקציה זו מנהלת את תהליך הקלטת הטקסט מהמשתמש במשך דקה אחת.
     * הפונקציה מפעילה תהליך זמן, קוראת קלט מהמשתמש ומחשבת את הדיוק ומהירות ההקלדה.
     */
    /**
     * טענת יציאה מהפונקציה typeInOneMinute.
     * פונקציה זו מסיימת את תהליך הקלטת הטקסט מהמשתמש ומחשבת את הדיוק והמהירות של ההקלדה.
     */
    private void typeInOneMinute() {
        TimerThread timerThread = new TimerThread();
        timerThread.start();//קורה לפעולה run()

        Scanner scanner = new Scanner(System.in);
        
        System.out.println("type the following text in:\n");
        System.out.println(this.text + "\n");

        StringBuilder inputBuilder = new StringBuilder();
        while (timerThread.isAlive()) {//בזמן שהתהליך של הזמן עדיין פעיל:
            if (scanner.hasNextLine()) {
                inputBuilder.append(scanner.nextLine());//תכניס את מה שהמשתמש מקליד לתוך הstring builder
            }
        }
        String input = inputBuilder.toString();//הקלט שנכנס מומר למחרוזת רגילה

        this.accuracy = calculateAccuracy(input);
        this.typingSpeed = input.length()/4.7;//חישוב של כמות האותיות שהוקלדו חלקי ממוצע האותיות של מילה באנגלית
        
        scanner.close();
        displayResult();
        System.exit(0);
    }
    
    /**
     * טענת כניסה לפעולה calculateAccuracy.
     * הפעולה מקבלת כקלט את הטקסט שהוקלד על ידי המשתמש.
     * הפעולה מחשבת את מדד הדיוק בהקלדה על פי השוואת התווים הנכונים לתווים המוקלדים.
     * מחזירה את רמת הדיוק באחוזים.
     */
    /**
     * טענת יציאה מהפעולה calculateAccuracy.
     * הפעולה מחזירה את רמת הדיוק באחוזים שנחשבה על ידי הפעולה.
     */

    private int calculateAccuracy(String input) {
        int correct = 0;
        for (int i = 0; i < this.text.length() && i < input.length(); i++) {
            if (this.text.charAt(i) == input.charAt(i)) {
                correct++;
            }
        }
        return (int) ((double) correct / input.length() * 100);
    }
    /**
     * טענת כניסה לפעולה calculateTypingSpeed.
     * הפעולה מקבלת כקלט את הטקסט שהוקלד על ידי המשתמש, זמן התחלת ההקלדה וזמן סיום ההקלדה.
     * הפעולה מחשבת מהירות ההקלדה של המשתמש על פי כמות התווים שהוקלדו וזמן ההקלדה.
     * מחזירה את מהירות ההקלדה בתווים לשנייה.
     */
    /**
     * טענת יציאה מהפעולה calculateTypingSpeed.
     * הפעולה מחזירה את מהירות ההקלדה בתווים לשנייה שנחשבה על ידי הפעולה.
     */

    private double calculateTypingSpeed(String input, long start, long end) {
        long elapsedTime = (end - start) / 1000; // Convert milliseconds to seconds
        return (double) input.length() / elapsedTime;
    }
    /**
     * טענת כניסה לפעולה displayResult.
     * הפעולה אינה מקבלת קלט מפורש.
     * מציגה את תוצאות מהירות ההקלדה והדיוק שנחשבו על ידי המחלקה TypingTest.
     */
    /**
     * טענת יציאה מהפעולה displayResult.
     * הפעולה אינה מחזירה ערך.
     * מציגה בפלט את תוצאות ההקלדה, כולל מהירות ההקלדה והדיוק.
     */

    private void displayResult() {
        System.out.println("Typing Speed: " + this.typingSpeed + " words per minute");
        System.out.println("Accuracy: " + this.accuracy + "%");
    }

    class TimerThread extends Thread {
        private long start;
        private long end;

        @Override
        /**
         * טענת כניסה לפעולה run.
         * הפעולה אינה מקבלת קלט מפורש.
         * מופעלת בעת הפעלת התהליך כדי לנהל את תהליך הקלטת הטקסט במשך דקה אחת.
         */
        /**
         * טענת יציאה מהפעולה run.
         * הפעולה אינה מחזירה ערך.
         * מסיימת את תהליך הקלטת הטקסט ומסמנת את סיום התהליך.
         */

        public void run() {
            try {
            	TimeUnit.SECONDS.sleep(1);
            	System.out.println("3");
            	TimeUnit.SECONDS.sleep(1);
            	System.out.println("2");
            	TimeUnit.SECONDS.sleep(1);
            	System.out.println("1");
            	TimeUnit.SECONDS.sleep(1);
            	System.out.println("START!");
            	TimeUnit.SECONDS.sleep(1);
                sleep(60000); // Sleep for 60 seconds
                System.out.println("\n\nTime is up!\n");
                end = System.currentTimeMillis();
            } catch (InterruptedException e) {
                // Thread interrupted, do nothing
            }
        }

        public long getStart() {
            return start;
        }

        public long getEnd() {
            return end;
        }
    }
}



