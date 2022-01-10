# PrismaLibraryManagement
Library's book, user and borrower management system
Please create a library application.

This application should provide a REST API that satisfies the following requirements.

1. **returns all users who have actually borrowed at least one book**

1. returns all non-terminated users who have not currently borrowed anything

1. **returns all users who have borrowed a book on a given date**

1. returns all books borrowed by a given user in a given date range

1. returns all available (not borrowed) books


as input you will get three csv files containing all users, books and who borrowed what and when

Please do not use more than 3 working hours for this task. 
We are aware that it is impossible to complete the task within this time frame.

## Enpoints

- http://localhost:8080/getLibrary - Get Libarary
- http://localhost:8080/getUserAtleastBorrowOneBook - Get Users who borrowed at least one book
- http://localhost:8080/getUserBorrowBookOnDate?date=06/23/2020 - Get Users who borrowed book on given date 

