## What is this repository for :question:
#### Simple MVI Application with view models, live data, presenter and data binding. Same functionality coded in java/kotlin to explain how MVI pattern can be implemented in different scenarios:

## How MVI pattern works :question:
MVI pattern means Model-View-Intent. This is not very popular among android development and is very closly related to MVP(Model view presenter) and MVVM(Model view view-Model) patterns of android.


**Model** : Acts as the model for data. It can be network models, local database models or any other type of data classes.<br>
**View** : Deals with the view to the user which can be the UI. As usual the view layer is always dumb and is unware of bussiness logics of the application.<br>
**Intent** : Now this is something new. Intents basically represents the states of an acitivity, frament, dialog etc. Intents can be called as "STATE MODELS".

Note: Intent in MVI is not the same as android's traditional Intent(android.content.Intent). MVI intents are just like data clasess but the only different between them is that MVI intents represents the state of UI.

## What is the meaning of representing states of UI :question:

Let us understand this one with an example. Suppose we have a login screen.
<br><br><br>
<p align="center"><img src="https://github.com/saksham24/Android-simple-MVI-pattern-with-MVP-MVVM-collaboration/blob/main/%20MVI%20with%20ViewModel%20(Java)%20/image1.jpg" width="35%"><br>
<br><br><br>
 
#### Login screen will have four states:
  1. Not - started -> When login screen is just launced and user saw it for the first time.
  2. Login - progressing -> When user press the login button to initiate the login process. User can see progress bar that time and login button or other input        fields are disabled.
  3. Login - error -> When the login process is finished. It failed due to some reason and user can see error message on the screen.
  4. Login - successful -> When the login process is finished. It was successful and user received the user token.
  
 
 
 Let's see some code now for these states:
<p align="center"><img src="https://github.com/saksham24/Android-simple-MVI-pattern-with-MVP-MVVM-collaboration/blob/main/%20MVI%20with%20ViewModel%20(Java)%20/image2.png" width="90%"><br>  
 

 
 
 LoginStateInten class maintains all the sufficient data to represent the state of login screen. We handle the state data in renverView() function:
 Since renderView() function is the only place where we recieve the UI-state model to update the login UI so this is also called single source of truth.
 
 <p align="left"><img src="https://github.com/saksham24/Android-simple-MVI-pattern-with-MVP-MVVM-collaboration/blob/main/%20MVI%20with%20ViewModel%20(Java)%20/image3.png" width="65%"><br>
  
  Simple flow of application in MVI is flow of intents between bussiness layer and UI update. This flow is unidirectional and single souce of truth.
   <p align="center"><img src="https://github.com/saksham24/Android-simple-MVI-pattern-with-MVP-MVVM-collaboration/blob/main/%20MVI%20with%20ViewModel%20(Java)%20/image4.png" width="75%"><br>
    
**This repository explains the following:**
    
    
1. MVI integration with presenter(from MVP) concept- **JAVA**.
2. MVI integration with presenter(from MVP) concept - **KOTLIN**.
3. MVI integration with Databinding and view-Model(from MVVM) concept- **JAVA**.
4. MVI integration with Databinding and view-Model(from MVVM) concept- **KOTLIN**.
5. MVI integration with Livedata and view-Model(from MVVM) concept- **JAVA**.
6. MVI integration with Livedata and view-Model(from MVVM) concept- **KOTLIN**.
    
    
## What are the advantages of using MVI :question:
    
1. No state problem anymore, because there is only one state for our view, which is a single source of truth. We can know the state of view at any moment just by knowing the value of it's UI- Intent.
2. Unidirectional data flow, which makes the logic of our view more predictable and easier to understand.
3. Unidirectional data flow ensures that our app is easy to debug. Every time we pass our data from one component to another, we can log the current value of the outflow. Thus, when we get a bug report, we can see the state our app was in when the error was made, and we can even see the user’s intent under which this state was created. 
4. Easy scalability if we want add more UI-states to an activity. We just have to add code of new UI-states in sending and receiving logic. This makes testing cycle small and easy.
5. Better decoupled logic than MVC, MVP, MVVM.
6. Easier to understand code as we know there is a single point where we update UI and single point where we handle user inputs in bussiness layer.
    

## Disadvantages of using MVI:
1. A lot of boilerplate. Each small UI change has to start with a user’s intent and then must pass the whole circle. Even with the easiest implementation, you have to create at least an intent and a state object for every action made in our app.
2. Object creation, which is expensive. If too many objects are created, your heap memory can easily reach full capacity and then your garbage collector will be too busy. You should strike a balance between the structure and size of your app.<br><br><br>
    
    
# END     :warning:
