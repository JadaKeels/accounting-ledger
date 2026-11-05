# LedgeCheck Financial Tracker

A application you can track all financial transactions for business or for personal use.

## Description of the Project

LedgeCheck is a Java command-line financial ledger application that allows users to track deposits and payments, view transaction history, and generate financial reports.
Transactions are stored in a CSV file and automatically loaded when the application starts. Users can add deposits, record payments, review their ledger, search transaction history, and create reports based on dates, vendors, and custom criteria.

## Features

-  Object-Oriented Programming (OOP)
-  Collections using ArrayList
-  File input/output (CSV storage)
-  Date and time handling
-  Menu-driven application design
-  Searching, filtering, and sorting data

## User Stories

<!-- The stories you worked from, one per bullet, in the format you learned in Workbook 3b:
     As a [type of user], I want [some goal] so that [some reason].
     Same text as the cards on your GitHub project board. -->

- As a user I want to enter deposit details and save them so that I can maintain an accurate record of deposits and account activity.
- As a user I want the system to prompt me to enter my debit information, so I can securely provide my details and have them saved on file for future use or processing.
- As a user I want the user to display a ledger screen so I can clearly view my recorded transactions and understand my financial activity.
- As a user I want the system to provide a clear option to exit the application, so I can close the program safely and stop using when I'm finished.
- As a user I want the system to show only the ledger entries that are deposits into my account, so I can easily review the money I've added without sorting through unrelated transactions.
- As a user I want the system to show only the negative ledger entries that represent payments, so I can easily review the money leaving my account without having to ort through all transactions.
- As a user I want the system to provide a reports screen where I can run predefined date range or perform a custom vendor search, so I can analyze my financial activity for specific periods or partivular vendor.
- As a user I want the system to provide an option to return to the home screen, so I can easily navigate back to the main starting point and access other features without confusion.
- As a user, I want to perform a custom search on my ledger transactions so that I can find specific transactions based on one or more search criteria.
- Customize your app, unique app name and starter screen.
- An informative README that describes project and how to run it.

## Setup

### Prerequisites

- Java 17 (Amazon Corretto), the version used in class.
- IntelliJ IDEA Community Edition: <https://www.jetbrains.com/idea/download/>

### Running the application

1. Clone this repository, or download it as a zip and unzip it.
2. In IntelliJ: File > Open, pick the project folder (the one that holds `pom.xml`), press Open.
3. Wait for IntelliJ to finish indexing (progress bar at the bottom).
4. Open `src/main/java/com/pluralsight/YourMainClassName.java`. <!-- replace YourMainClassName with the class that has main -->
5. Right-click inside the file and choose Run 'YourMainClassName.main()'.

### Data and configuration

transaction.csv` sits at the project root, next to `pom.xml`. The program reads it at start.

## Technologies Used

- Java 17
- IntelliJ IDEA


## Demo

<!-- Pictures of the app running: the console for a console app, Insomnia or Swagger for an API.
     Screenshots are enough; a GIF is a bonus.
     1. Create a folder named `images` at the project root, next to `pom.xml`.
     2. Take screenshots of the app in the IntelliJ console (the menu, one feature, one error message):
          Windows: Win + Shift + S, drag a rectangle, then paste into Paint and save as PNG, or open the
                   notification and save. Save into the `images` folder.
          Mac:     Shift + Cmd + 4, drag a rectangle. The file lands on the Desktop; move it into `images`.
     3. Name the files by what they show: `images/menu.png`, `images/search.png`.
     4. Reference each one below with a relative path. No spaces in file names.
     5. Commit the images with the rest of your work (Git > Commit shows them as unversioned files: tick them).
     Optional GIF: LICEcap (free, Windows and Mac, <https://www.cockos.com/licecap/>) records a region of the
     screen straight to a .gif. Keep it under 10 seconds and under 10 MB, save it into `images`. -->

![Main menu](images/menu.png)

![One feature in action](images/feature.png)

## A piece of code I am proud of

System.out.println("\n================================");

System.out.println(" Welcome to LedgeCheck ");

System.out.println(" Account Ledger System ");

System.out.println("================================");

System.out.println("D) Add Deposit");

System.out.println("P) Make Payment");

System.out.println("L) Ledger");

System.out.println("X) Exit");
```java
I am proud of this code because it improves the user experience by making the application 
feel more personal & user friendly. The menu serves as the main entry point for all 
features in the program, and this is clear on the application use. Reflects the importance 
of creating applications that are  functional and purposeful.
```

## Future Work

- Add user authentication.
- Add transaction editing/deletion.
- Add monthly spending summaries.


## Resources

- Java YearUp Fall 2026 hub: <https://raymaroun.github.io/yearup-java-fall2026/>
- https://raymaroun.github.io/yearup-java-fall2026/week-04/reference/build-guide.html#s12
- https://raymaroun.github.io/yearup-java-fall2026/week-04/reference/requirements-checklist.html 
- https://yearup.brightspace.com/d2l/le/enhancedSequenceViewer/12949?url=https%3A%2F%2Fa8aef0e2-4090-467b-bc7e-c872d64ba733.sequences.api.brightspace.com%2F12949%2Factivity%2F662699%3FfilterOnDatesAndDepth%3D1

## Author

- **Jada Keels**

  Software Application Development Student
## Thanks

- Thank you Raymond for your knowledge, guidance and provided learning resources throughout the project.
- 
  Thank you to classmates who provided feedback or assistance.