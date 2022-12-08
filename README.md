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
    <li><a href="#main-features">Main Features</a></li>
    <li><a href="#project-structure">Project Structure</a></li>
    <li><a href="#drivers">Drivers</a></li>
    <li><a href="#interface-adapters">Interface adapters</a></li>
    <li><a href="#use-cases">Use Cases</a></li>
    <li><a href="#entities">Entities</a></li>
    <li>
      <a href="#solid-principles-and-design-patterns">SOLID Principles and Design Patterns</a>
      <ul>
        <li><a href="#solid-principles">SOLID Principles</a></li>
        <li><a href="#design-patterns">Design Patterns</a></li>
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

MusicMetric is the newest data analysis software that helps the user to keep track of their music. Using the program, the user can discover new artists, based on smiliar artists they listen to. Users can also choose to sign up for an account using the email. After creating an account, the user has the ablity to be kept up to date with their favorite artists and their favorite genres of music, giving them the options to follow their favorite musicans and receive alerts on music chart-related updates of their favorite artists.
  

  <!-- MAIN FEATURES -->

## Main Features

MusicMetric makes the most use of Java, utlizing both it's frontend and backend capabilites. It has a text-based user interface that receives pop-ups when users receive alerts of music-chart-related updates. It currently reads a mock database in the form of text files for artist-related data and user data.

  
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

  
  <!-- DRIVERS -->
  
## Drivers

`src/main/java/drivers`
  
  We have one class in the drivers folder and it is our `TextBaseFrontend` class which is our user interface class.
  
  ### TextBasedFrontend
  
  
  <!-- INTERFACE ADAPTERS -->
  
## Interface Adapters

`src/main/java/interface_adapter`
  
  ### AlertsController
  ### RecommendController
  ### Searcher
  ### UserController
  ### UserDataBuilder
  ### UserPresenter
  
  
  <!-- USE CASES -->

## Use Cases

`src/main/java/use_cases`
  
  ### Alert
  ### ArtistBuilder
  ### ArtistComparer
  ### MusicData
  ### MusicDataBuilder
  ### RecommendArtist
  ### UserData
  
  
  <!-- ENTITIES -->

## Entities

`src/main/java/entities`

The entities of MusicMetric are comprised of the clases Artist, FollowsBuiler, GuestUser, RegisteredUser and User.
  
### Artist
### FollowsBuilder
### GuestUser
### RegisteredUser
  
`RegisteredUser.java` is a class used to store the data of a user who has signed up for a class. `RegisteredUser` implements the `User` interface.

The `RegisteredUser` has the following attributes:

* `email`
* `password`
* `follows`
  
  ### User
 
 `User.java` ...
    

  <!-- SOLID PRINCIPLES AND DESIGN PATTERNS -->

## SOLID Principles and Design Patterns
    

  <!-- SOLID PRINCIPLES -->

### SOLID Principles
  
  #### Single responsibility principle
  
  #### Open/closed principle
  
  #### Liskov substitution principle
  
  #### Interface segregation principle
  
  #### Dependency inversion principle
  
    

  <!-- DESIGN PATTERNS -->

### Design Patterns
      
  
  <!-- HOW TO USE -->

## How to Use
     
  
  <!-- INSTALLATION -->

### Installation

To create a local copy, download the software or create a clone.
    

  <!-- RUNNING THE PROGRAM -->

### Running the Program
  

After installing the software, to run the program, follow these simple steps.
  
1. Run `src/main/java/drivers/TextBasedFrontend.java`
2. Type a command (type `help` for command list)
3. You can run the program as a guest user or log into your account. You can also choose to register for a new account.
4. Start finding more about your music tastes!
  
 
  
  <!-- Course Milestones / Documents -->
  
## Course Milestones / Documents
- Milestone 1: [Project Blueprint](https://docs.google.com/document/d/1cg8Mi3mvCOwUAyx21s97vBu5YUonFOOYeqZ_R6AqqbI/edit?usp=drivesdk)
- Milestone 2: [Inital Design](https://docs.google.com/document/d/1ePjlhD8TAqJ5lNda2yaUm7gFvTcbiBnm8wFmLlZaXco/edit?usp=sharing)
- Milestone 4: [Implementation](https://docs.google.com/document/d/1PTRhN5tdep9UDpbdcG0cqoATJgAwxim3PXq59sh-dD4/edit?usp=sharing)
  
  
    <!-- Contributers -->
  
  ## Contributors
- [Amirhossein Davoodi](https://github.com/aamirhosii)
  - Contact: amirhossein.davoodi@mail.utoronto.ca
  - *Task:*
- [Sahir Dhalla](https://github.com/SahirD)
  - Contact: sahir.dhalla@mail.utoronto.ca
  - *Task:*
- [Jaemin Lee](https://github.com/jmlee3210)
  - Contact: jackmin.lee@mail.utoronto.ca
  - *Task:*
- [Joonki Lee](https://github.com/chr-lee)
  - Contact: joonki.lee@mail.utoronto.ca
  - *Task:*
- [Dallas Schnitzius](https://github.com/dallasschn)
  - Contact: dallas.schnitzius@mail.utoronto.ca
  - *Task:*
- [Yu Yang Tan](https://github.com/yuyangggggg)
  - Contact: yuyang.tan@mail.utoronto.ca
  - *Task:*
- [Max Xu](https://github.com/maxXu0713)
  - Contact: max.xu@mail.utoronto.ca
  - *Task:*
- [Michael Zhou](https://github.com/michaelzixizhou)
  - Contact: michaelmichaelmichael.zhou@mail.utoronto.ca
  - *Task:*
