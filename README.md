<div align="center">
  <h1>ACCOUNT MANAGER</h1>
</div>

<p>
The Account Manager project is being developed as a summer assignment. With this application, users can create a new account, open a bank account, and perform deposit or withdrawal transactions. Additionally, users can update their personal information, delete their account, and view their existing details.
</p>

<p>
In this project, Java Spring Boot and Gradle were used on the backend side. Node.js was chosen for the frontend, while MySQL was used for database management. Additionally, technologies like Docker and GitHub were utilized to develop the project as professionally as possible.
</p>
<p>
  This project consists of two main files: <a href="https://github.com/serhatbsts/account-manager">frontend</a> and <a href="https://github.com/serhatbsts/accountManager">backend.</a>
</p>

<p>
I would also like to thank Yusuf Beştaş (<a href="https://github.com/karaelf33/">Github</a> , <a href="https://www.linkedin.com/in/yusufbestas/">LinkedIn</a>) for providing me with all kinds of support and for following me step by step throughout this project.
</p>
<p>
If you would like to develop a project like this, feel free to contact me (<a href="https://www.linkedin.com/in/serhatbestas/">LinkedIn</a>) or Yusuf Beştaş (<a href="https://github.com/karaelf33/">Github</a> , <a href="https://www.linkedin.com/in/yusufbestas/">LinkedIn</a>).
</p>
<div align="center">
  <h2>
PROJECT DETAILS
  </h2>
  </div>
   <h3>
HOME PAGE
  </h3>
<p>
  You can access the login or user creation pages from here.
</p>
<img src="https://github.com/user-attachments/assets/ee8e03ad-3fa1-4fe8-97ef-b62eca83fe27" alt="Home Page Screenshot" />


<h3>
  CREATE ACCOUNT PAGE
</h3>
<p>
  On this page, you can create a user. Please pay attention to the following points: You cannot create multiple accounts with the same email address, and you must confirm your password. If these conditions are not met, the user cannot be created. If all information is correct, the user will be created, and you will be redirected to the login page.
</p>
<p>
<img src="https://github.com/user-attachments/assets/497cc640-c8e2-470d-a3cc-57bff73f650d"/>
</p>
<p>
<img src="https://github.com/user-attachments/assets/d1a3f459-56c9-4b0a-a8e1-a3069fcaafcb"/>
</p>
<p>
  <img src="https://github.com/user-attachments/assets/f4998409-bab6-4601-a754-0009e34affba"/>
</p>
<h3>
LOGIN PAGE
</h3>
<p>
If you enter all the information correctly on this page, you can log in.
</p>
<p>
  <img src="https://github.com/user-attachments/assets/94a9e6bb-bacb-419a-8c6e-c10fedbc15a0"/>
</p>
<h3>
  USER PAGE
</h3>
<p>
  On this page, you can view user-related information, create a bank account for the user, update the user's information, delete the user, and log out.
</p>
<p>
  <img src="https://github.com/user-attachments/assets/222c05ea-e851-47f7-9ff6-1dfdc033e1fc"/>
</p>
<h3>
  CREATE ACCOUNT PAGE
</h3>
<p>
  Here, you can create an account by entering the balance available in your account. When you create a bank account, a unique ten-digit account number will be automatically generated. Additionally, deposit, withdraw, and delete account buttons will be added to the user page.
<p>
  <img src="https://github.com/user-attachments/assets/fa520c99-71c0-4f1a-8a44-cecba2e01141"/>
</p>
<p>
  <img src="https://github.com/user-attachments/assets/392a80ff-7748-460b-9919-32f1f21e96b5"/>
</p>
<h3>
DEPOSIT PAGE
</h3>
<p>
On this page, you can perform a deposit transaction. The amount you enter will be added to your account, and you will be redirected to the user page.
</p>
<p>
  <img src="https://github.com/user-attachments/assets/8dda909c-35f2-4042-a228-917148486918"/>
</p>
<p>
  <img src="https://github.com/user-attachments/assets/6486581d-29a0-4ae7-b0dc-1c9e4c70b4cb"/>
</p>
<h3>
WITHDRAWAL PAGE
</h3>
<p>
  On this page, you can perform a withdrawal transaction. However, if you request to withdraw an amount greater than your account balance, you will receive an error. The amount you enter will be deducted from your account, and you will be redirected to the user page.
</p>
<p>
  <img src="https://github.com/user-attachments/assets/dc90c558-2c16-4709-985f-c2c9152775a1"/>
</p>
<p>
  <img src="https://github.com/user-attachments/assets/31a8debe-c27c-457a-ab33-1baabcbd7e4e"/>
<p>
  <img src="https://github.com/user-attachments/assets/97122ddc-673f-45dd-9844-ee676e0ebcd3"/>
</p>
<h3>
  EDIT YOUR DETAILS PAGE
</h3>
<p>
  On this page, you can update your user information, and the updated information will be visible on the user page.
</p>
<p>
  <img src="https://github.com/user-attachments/assets/5300967a-64ba-4291-8b37-e0d5430b8f5c"/>
</p>
<div align="center">
  <h2>DATABASE DETAILS</h2>
</div>
<p>
  User information and bank account details are stored in separate tables, and there is a one-to-one relationship between these tables. When a user is created, the user ID is generated uniquely and automatically. When a bank account is created, the account number and account ID are also generated uniquely and automatically. Additionally, when a bank account is deleted, the user information is not deleted; however, when a user is deleted, their associated bank account is also deleted.
<p>
  <img src="https://github.com/user-attachments/assets/fabfd5fe-23d4-4770-b6e4-8ac34777700a"/>
</p>
<p>
  <img src="https://github.com/user-attachments/assets/d6249382-7917-4dd4-a0f7-01fbe8f2b396"/>
</p>
