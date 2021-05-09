CNT4504 - Computer Networks
  - Sources Used:
      - http://www.codejava.net/java-se/networking/java-socket-server-examples-tcp-ip
      - https://alvinalexander.com/java/edu/pj/pj010016
      - https://beginnersbook.com/2013/03/multithreading-in-java/

# Project 1 - Iterative Socket Server:
This project requires the implementation of an iterative (single-threaded) server for use in a client-server configuration to examine, analyze, and study the effects an iterative server has on the efficiency (average turn-around time) of processing client requests.

Features Two (2) Programs:
1) An iterative (single-threaded) server:
    - Handles one client request at a time (serially)
    - Supports the following client request:
        - Date and Time - the date and time on the server
        - Uptime - how long the server has been running since last boot-up
        - Memory Use - the current memory usage on the server
        - Netstat - lists network connections on the server
        - Current Users - list of users currently connected to the server
        - Running Processes - list of programs currently running on the server
       
2) A multi-threaded client:
    - Transmits requests to the server on a specified network address and port
    - Must be able to spawn multiple client sessions
    - Request which operation to preform and output the data from the server
    - Collect the following data:
        - Turn-around Time - (elapsed time) for each client request
        - Total Turn-around Time - sum of the turn-around times for all the client requests
        - Average Turn-around Time - (Total Turn-around Time) / (number of client requests)
