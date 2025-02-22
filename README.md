## CS545 Project 1: Custom Two-linked Song List

In this project, you will implement a custom linked list class called SongList, which manages a collection of SongNode objects. Each SongNode contains:
-	A Song object, representing a song that has a title, artist, and score (rating).
-	Two pointers: nextByTitle – pointing to the next node in ascending order by title, and nextByScore – pointing to the next node in descending order by score.
 	
Instead of having a single “next” pointer, each SongNode has two pointers, allowing one to iterate through the same set of songs two different ways: by title (alphabetically) or by score (highest to lowest). 
This project demonstrates how multiple “keys” (in this case, title and score) can be supported in a single data structure by giving each node two different “next” pointers, each forming a separate chain within the same list. 
Please see the project description on Canvas (in pdf).
