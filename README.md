📌 Project: Diet Recommendation System
👨‍🎓 Developed by: Azan Mumtaz
                    And
          Syed Ali Akbar Shah Jillani
                            
                        

✅ Requirements:
- Java JDK (v8+)
- MySQL Server installed

📁 Project Structure:
- All source code is inside folders: database, auth, diet, main
- SQL file: diet_tracker.sql (contains all tables and sample data)

🛠️ Setup Instructions:
1. Open MySQL Workbench or phpMyAdmin
2. Import the file `diet_tracker.sql` into a new database called `diet_tracker`

3. Ensure your MySQL user is:
   Username: root
   Password: 1234

⚠️ If your credentials are different, update this line in `DBConnection.java`:
   Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/diet_tracker", "root", "12345");

📦 Compile & Run:
1. Open terminal in project folder
2. Compile:
   javac database\DBConnection.java auth\*.java diet\*.java main\Main.java
3. Run:
   java main.Main

✅ Features Implemented:
- User Registration & Login
- BMI-based Meal Recommendations
- Profile Update (Height, Weight, Goal)
- Admin View: See all registered users

Thank you!

