# TWAM-Project

This is another college project for the "Tecnologias Web e Aplicações Móveis" (In English: "Web Technologies and Mobile Applications") class. Made in the Spring semester of 2016.

The project requested that students built an application that:
* Included a Web application
* An Android application
* A database that shared information between both versions

What our project intended to do was to create a simple modernized version of Introversion's 2001 game Uplink.

We tried to do this in the following way:
The user would be able to play from either his computer or his phone.
Both would simulate a home screen from their respective operating systems (in the case of the browser version, we used Anders Evenrud's OS.js to simulate a desktop) and have a few built-in applications like a browser that would allow the user to go and get jobs, a map to let him pick targets, a password breaker to get into the servers... etc. The pseudo-account the user created would allow him/her to connect from both the computer and the phone. Even at the same time, meaning that, potentially, while the user was attacking a server on the computer, he/she could, during that, use the phone to do something else inside the game.

What ended up happening in the end, due to an over-ambitious scope on my end, was that the phone ended up taking just the task of interacting with the database to give the player the location of the IPs they got from taking jobs.
