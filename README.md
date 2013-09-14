Raiden
======

This project was our first attempt at making a video game for mobile devices. 
We start learning java in our 2nd year of college and on our 2nd java project we were given the chance to do an android game.
Our first step was to learn a bit more about programming in the android platform, so
with the help of the tutorials at Kilobolt (http://www.kilobolt.com/game-development-tutorial.html) and the simple beginners' framework they recommend, we started this project.

You can download a compiled .apk from http://bit.ly/18fhhFO

The game is a simple vertical shoot em up with ships, it features 3 levels with bosses, different enemy types with many flight patterns, lots of power ups and animations.

The project features some really cool stuff on the programming side because we really emphasized the use of design patterns on our "engine".
For example, we used the visitor's pattern to easily implement consequences of collisions between different objects. If we wanted a bullet to damage a ship, we simply defined
the bullet.visit(ship) function, to make the ship lose health. The rest of the game would detect the collision of these objects and would call the right function for the consequence.

Another really cool pattern we used was the Observers Pattern. Every hero, enemy and bullet has observers that serve various purposes and are notified through Events.
An Event is our polymorfic java enum that carries information for an animation, sound, music or special effect.
To understand this pattern, take this little example: a bullet hits an enemy, which triggers the explosion Event. This Event has a boom (sound) and a fiery blast (special effect).
The bullet's SoundObserver is notified of the sound, plays it, and the bullet's EffectsObserver is notified of the blast special effect and creates it.
With this simple pattern, we can manage sounds, music, special effects and animation switching (without having to check for these events constantly in the game loop)
and we used the versatility of the Event enum to add more features. One of our last features was game scores. To do this, we simply added ScoreObservers to our objects and created a ScoreUp Event for them to listen to.

For the levels and the enemy flight patterns, we learned to used JSON. Each level is a simple JSON file containing information of enemy type and spawn.
There is also a JSON flight pattern file that contains information of the direction and timing of a series of enemy movements. Due to the ease of parsing JSON files, we could easily add new attributes to enemies as development progressed.
We could also later develop a level construtor software that visually aids a user to creates levels for our game.

In the end of the project, the game turned out really great and helped us learn a lot to use helpful design patterns, as well as android programming.
