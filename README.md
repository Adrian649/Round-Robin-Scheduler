# CIIC4020/ICOM4035: Data Structures - First Project
# Round-Robin Scheduler

## What is this project about?
This projects uses a round robin algorithm, which is an algorithm used to assign a time to each process in a circular manner, to simulate how this algorithm would work in a real life application. In this project we use a circular linked list to store the simulated tasks and so we can go in the previously mentioned circular manner order. I also explored how multithreading worked as we needed to figure out how the processes worked with multithreading already implemented by our professor.

## Implemented Functions:
- fillRoundRobin: Creates the doubly circular linked list based on desired number of nodes of the program.
- findFilledSlot: Changes the processed flag from false to true.
- findEmptySlot: Changes the processed flag from true to false.

