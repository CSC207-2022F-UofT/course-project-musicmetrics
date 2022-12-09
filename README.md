<br />
<p align="center">
  <img src="https://github.com/CSC207-2022F-UofT/course-project-musicmetrics/blob/main/images/musicmetriclogo.png" alt="Logo">
 
</div>

  <h2 align="center">MusicMetric</h2>

  <p align="center">

<!-- TABLE OF CONTENTS -->
<details open="open">
  <summary>Table of Contents</summary>
  <ol>
    <li><a href="#about-the-project">About The Project</a></li>
    <li>
      <a href="#project-structure">Project Structure</a>
       <ul>
        <li><a href="#drivers">Drivers</a></li>
        <li><a href="#interface-adapters">Interface adapters</a></li>
        <li><a href="#use-cases">Use Cases</a></li>
        <li><a href="#entities">Entities</a></li>
       </ul>
    </li>
    <li>
      <a href="#how-to-use">How to Use</a>
      <ul>
        <li><a href="#installation">Installation</a></li>
        <li><a href="#running-the-program">Running the Program</a></li>
      </ul>
    </li>
    <li><a href="#course-milestones-documents">Course Milestones / Documents</a></li>
    <li><a href="#contributors">Contributors</a></li>
  </ol>
</details>
  
  
  <!-- ABOUT THE PROJECT -->

## About The Project

MusicMetric is the newest data analysis software that allows users to access music stats from major streaming platforms. Using the program, the user can discover new artists, based on similar artists they listen to. Users can also choose to sign up for an account using the email. After creating an account, the user has the ability to be kept up to date with their favourite artists and their favourite genres of music, giving them the options to follow their favourite musicians and receive alerts on music chart-related updates of their favourite artists. Another of the many features a user can do, is they can take two of their favourite artists, and compare their music stats.

MusicMetric makes the most use of Java, utilising both its frontend and backend capabilities. It has a text-based user interface that receives pop-ups when users receive alerts of music-chart-related updates. It currently reads a mock database in the form of text files for artist-related data and user data.

  
  <!-- PROJECT STRUCTURE -->

## Project Structure
 
```
├── src
│    ├── main 
|    │    └── java
|    |         │── drivers
|    |         │── entities
|    |         │── interface_adapters
|    |         └── use_cases                      
│    └── test
└── README.md
```

The following will briefly go in depth of what's in each layer, explaining the functionality of some of the classes and how the flow of control works throughout the program
  
  <!-- DRIVERS -->
  
## Drivers

`src/main/java/drivers`
   
  #### TextBasedFrontend
  
   We have one class in the drivers folder and it is our `TextBaseFrontend.java` class which is our user interface class.
  
  
  <!-- INTERFACE ADAPTERS -->
  
## Interface Adapters

`src/main/java/interface_adapter`

The entities of MusicMetric are comprised of the classes AlertsController, RecommendController, Searcher, UserController, UserDataBuilder and UserPresenter.
  
  #### AlertsController
  
  The class `AlertsController.java` calls the Alert class’ trigger method and returns each top artisst names and their stream growth rate in front of their names.
  
  #### RecommendController
  
  The class `RecommendController` class calls the RecommendArtist class’ recommend method and returns an artist within the genre the user is searching for
  
  #### Searcher
  
  Attributes of the `Searcher.java` class:
  * `actions`
  * `artists`
  * `genres`
  
The `Searcher.java` class was designed to accept the user's input keyword, and it filters relevant actions that the user can take, artists, and genres, and then provides the user suggestions in List<String>. The Searcher class reads data from the database file and stores them in private ArrayList attributes in the Searcher class. The constructor calls the setUp method from MusicDataBuilder to initialize the data.
  
  
  #### UserController
  
  The `UserController.java` class is used by a logged-in user in UserData to perform various actions, such as letting them follow a given artist or giving them the ability to change their passwords.
  
  #### UserDataBuilder
  
  `UserDataBuilder.java` is a class that creates a single UserData instance. It also keeps track of this UserData instance so that it can be accessed by the frontend. UserDataBuilder is only called once, when the program starts running initially.
  
  #### UserPresenter
  
The `UserPresenter.java` class checks if the user is logged in to their account.
  
  
  <!-- USE CASES -->

## Use Cases

`src/main/java/use_cases`
  
The uses cases of MusicMetric are comprised of the classes Alert, ArtistBuilder, ArtistComparer, MusicData, MusicDataBuilder, RecommendArtist and UserPresenter.
  
  #### Alert
  
  The `Alert.java` is used to inform the user if one of their following artists have a significant growth in streams.
  
  * Trigger()

    * The Trigger() method takes in a value and the method compares artists' number of streams in their latest week of streams with the number of streams in their previous week of streams.

    * If the growth percentage in streaming numbers is equal or more than the value the method takes in, the artist will be put in tops hashmap as a key and its growth rate as a value. 
  
  
  #### ArtistBuilder
  
The `ArtistBuilder.java` reads from relevant data for an artist, such as the number of streams, the number of follows etc. from the files in our database and stores in a new instance of an Artist. 

  #### ArtistComparer
  
  
The `ArtistComparer.java` class compares different artists. It could be comparing them by the number of streams, or by how much they are trending compare to one another. 
  
  
  #### MusicData
  
`MusicData.java` makes use of MusicDataBuilder to be built, and this is done by reading the MusicDatabase text file, and appending onto a HashMap in MusicData by the builder. This portion makes use of the Builder design pattern.

  
  #### MusicDataBuilder
  
  The `MusicDataBuilder.java` is the builder to construct MusicData instances. Its method setData(), reads from relevant data files in our databases, and in turn, stores the info into a HashMap .
  
  #### RecommendArtist
  
`RecommendArtist.java` is a class that recommends an artist based on a particular genre. 
  
  #### UserData
  
  `UserData.jav` keeps track of all RegisteredUser objects, and whether they are logged in or out with its HashMap. It includes methods to register a new User in saveUser, login, logout, amongst other getter and setters
  
  
  <!-- ENTITIES -->

## Entities

`src/main/java/entities`

The entities of MusicMetric are comprised of the classes Artist, FollowsBuilder, GuestUser, RegisteredUser and User.
  
#### Artist

The class `Artist.java` creates a new Artist object with the attributes:
* `email`
* `genre`
* `likes`

#### FollowsBuilder

The `FollowBuilder.java` class has one attribute in:
 * followsMap
  
The `FollowsBuilder.java` class initializes a follows Hashmap for each user in AllRegisteredUsers. This hashmap is then used to create Users' follows.

#### GuestUser
  
`GuestUser.java` a class that creates GuestUser objects which all users start off with when running the program initially. From there, one can choose to continue using MetricMetrics as a guest user, which lacks the same features a registered user would have, such as following their favorite artists.

  
#### RegisteredUser
  
`RegisteredUser.java` is a class used to store the data of a user who has signed up for a class. `RegisteredUser` extends the `User` interface.

The `RegisteredUser` has the following attributes:

* `email`
* `password`
* `follows`
  
#### User
 
 `User.java` is an abstract class that defines common functionality between GuestUser and RegisteredUser, such as the checkPermissions method which determines which features the user can access.
      
  <!-- HOW TO USE -->

## How to Use
     
  <!-- INSTALLATION -->

### Installation

To create a local copy, download the software or create a clone.
    

  <!-- RUNNING THE PROGRAM -->

### Running the Program
  

After installing the software, to run the program, follow these simple steps.
  
1. Run `src/main/java/drivers/TextBasedFrontend.java`. You will see a prompt like this.

<p align="center">
<img src="https://github.com/CSC207-2022F-UofT/course-project-musicmetrics/blob/main/images/figure1.png" alt="Figure1">

2. Type a command (type `help` for command list)

<p align="center">
<img src="https://github.com/CSC207-2022F-UofT/course-project-musicmetrics/blob/main/images/HelpCommand.png" alt="HelpCommand">

3. You can run the program as a guest user or log into your account. You can also choose to register for a new account.
4. Start finding more about your music tastes!
  
  
  <!-- Course Milestones / Documents -->
  
## Course Milestones / Documents
- Milestone 1: [Project Blueprint](https://docs.google.com/document/d/1cg8Mi3mvCOwUAyx21s97vBu5YUonFOOYeqZ_R6AqqbI/edit?usp=drivesdk)
- Milestone 2: [Initial Design](https://docs.google.com/document/d/1ePjlhD8TAqJ5lNda2yaUm7gFvTcbiBnm8wFmLlZaXco/edit?usp=sharing)
- Milestone 4: [Implementation](https://docs.google.com/document/d/1PTRhN5tdep9UDpbdcG0cqoATJgAwxim3PXq59sh-dD4/edit?usp=sharing)
  
  
    <!-- Contributers -->
  
## Contributors
- [Amirhossein Davoodi](https://github.com/aamirhosii)
  - Contact: amirhossein.davoodi@mail.utoronto.ca
- [Sahir Dhalla](https://github.com/SahirD)
  - Contact: sahir.dhalla@mail.utoronto.ca
- [Jaemin Lee](https://github.com/jmlee3210)
  - Contact: jackmin.lee@mail.utoronto.ca
- [Joonki Lee](https://github.com/chr-lee)
  - Contact: joonki.lee@mail.utoronto.ca
- [Dallas Schnitzius](https://github.com/dallasschn)
  - Contact: dallas.schnitzius@mail.utoronto.ca
- [Yu Yang Tan](https://github.com/yuyangggggg)
  - Contact: yuyang.tan@mail.utoronto.ca
- [Max Xu](https://github.com/maxXu0713)
  - Contact: max.xu@mail.utoronto.ca
- [Michael Zhou](https://github.com/michaelzixizhou)
  - Contact: michaelmichaelmichael.zhou@mail.utoronto.ca
