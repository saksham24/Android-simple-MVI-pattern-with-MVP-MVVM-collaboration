## What is this repository for :question:
A Simple Application to explain how **MVI Architecture works**. MVI functionality with collaboration of **View model / Live data / Presenter / Data binding** has been added. 
<br>The same functionality is coded in java/kotlin. Choose whatever language you are comfortable with.

## How MVI pattern works :question:
MVI pattern means **Model-View-Intent**. This is not a very popular pattern among the android developers. MVI is very closely related to MVP(Model view presenter) and MVVM(Model view view-Model) patterns of android. The main concept of MVI **revolves arround state preservation**. **Compose UI** for native android and **Flutter UI kit** is majorly inspired by this pattern.  

**Model** : Acts as the model for data. It can be network models, local database models or any other type of data classes.<br>
**View** : Deals with the view to the user which can be the UI. As usual the view layer is always dumb and is unaware of business logic of the application.<br>
**Intent** : Now this is something new. Intents basically represent the states of an activity, fragment, dialog or any other UI. Intents can be called as "STATE MODELS".

**Note**: Intent in a MVI pattern is not the same as android's traditional Intent(android.content.Intent). MVI intents are just **like data classes** but they majorly represent the state of UI.

## What is the meaning of representing states of UI :question:

Let us understand this one with an example. Suppose we have a login screen.
<br><br><br>
<p align="center"><img src="https://github.com/saksham24/Android-simple-MVI-pattern-with-MVP-MVVM-collaboration/blob/main/%20MVI%20with%20ViewModel%20(Java)%20/image1.jpg" width="35%"><br>
<br><br><br>
 
#### A Login screen can have four states:
  1. **Not-started** -> When login screen is just launched and the user saw it for the first time.
  2. **Login-progressing** -> When user presses the login button to initiate the login process. User can see progress bar and login button or other input fields are disabled.
  3. **Login-error** -> When the login request is finished. It failed due to some reason and user can see error message on the screen.
  4. **Login-successful** -> When the login process is finished. It was successful and the user received the user token.
  
 
 
 Let's see some code now for these states:
<p align="center"><img src="https://github.com/saksham24/Android-simple-MVI-pattern-with-MVP-MVVM-collaboration/blob/main/%20MVI%20with%20ViewModel%20(Java)%20/image2.png" width="90%"><br>  
 

 
 
 **LoginStateIntent** class maintains all the sufficient data to represent the state of the login screen. We handle the state data in **renderView()** function:
 Since the **renderView() function** is the only place where we can receive the UI-state model to update the login UI. This is also called **single source of truth**.
 
 <p align="left"><img src="https://github.com/saksham24/Android-simple-MVI-pattern-with-MVP-MVVM-collaboration/blob/main/%20MVI%20with%20ViewModel%20(Java)%20/image3.png" width="65%"><br>
  
  Simple flow of an application in MVI pattern is flow of intents between business layer and UI layer. This flow is **unidirectional and single source** of truth.
   <p align="center"><img src="https://github.com/saksham24/Android-simple-MVI-pattern-with-MVP-MVVM-collaboration/blob/main/%20MVI%20with%20ViewModel%20(Java)%20/image4.png" width="75%"><br>
    
**This repository explains the following:**
    
    
1. MVI integration with presenter(from MVP) concept- **JAVA**.
2. MVI integration with presenter(from MVP) concept - **KOTLIN**.
3. MVI integration with Databinding and view-Model(from MVVM) concept- **JAVA**.
4. MVI integration with Databinding and view-Model(from MVVM) concept- **KOTLIN**.
5. MVI integration with Livedata and view-Model(from MVVM) concept- **JAVA**.
6. MVI integration with Livedata and view-Model(from MVVM) concept- **KOTLIN**.
    
    
## What are the advantages of using MVI :question:
    
1. No state problem anymore, because there is only one state for our view now, which is a single source of truth. We can know the state of the view at any moment just by knowing the value of it's UI- Intent.
2. Unidirectional data flow, which makes the logic of our view more predictable and easier to understand.
3. Unidirectional data flow ensures that our app is easy to debug. Every time we pass our data from one component to another, we can log the current value of the outgoing Intent. Thus, when we get a bug report, we can see the state of our app in which it was when the error was made. 
4. Easy scalability if we want to add more UI-states to an activity. We just have to add code of new UI-states in sending and receiving logic. This makes testing cycle small and easy.
5. Better decoupled logic than traditional MVC, MVP, MVVM.
6. Easier to understand the code as we know there is a single point where we update the UI and single point where we handle user inputs in business layer.
    

## Disadvantages of using MVI:
1. A lot of boilerplate. Each small UI change has to start with a user’s intent and then must pass the whole circle. Even with the simplest implementation, you have to create at least an intent and a state object for every action made in our app.
2. Object creation, which is expensive. If too many objects are created, your heap memory can easily reach full capacity, causing the garbage collector to become overloaded. You should strike a balance between the structure and size of your app.<br><br><br>
    
    
# END     :warning:
