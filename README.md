Problem description:

Implementation of a system to find the n most popular hashtags appeared on social media such as Facebook or Twitter. 
Hashtags were given from an input file.

Basic idea for the implementation was to use a max priority structure to find out the most popular hashtags.
Following structures were used for the implementation:

1. Max Fibonacci heap: used to keep track of the frequencies of hashtags.
2. Hash table: Key for the hash table is the hashtag and value is pointer to the corresponding node in the Fibonacci heap.

Large number of hashtags can appears in the stream and increase key operation need to performed many times. Max Fibonacci heap provided better theoretical bounds for increase key operation.

Input Format:

Hashtags appear one per each line in the input file and starts with # sign. After the hashtag an integer will appear and that is the count of the hashtag (There is a space between hashtag and the integer). We need to increment the hashtag by that count. Queries will also be appeared in the input file and once a query appears we should append the output to the output file. Query will be an integer number (n) without # sign in the beginning. We should write the top most n hashtags to the output file. Once it reads the stop (without hashtag) program should end. Following is an example of an input file.
#saturday 5
#sunday 3
#saturday 10
#monday 2
#reading 4
#playing_games 2
#saturday 6
#sunday 8
#friday 2
#tuesday 2
#saturday 12
#sunday 11
#monday 6
3
#saturday 12
#monday 2
#stop 3
#playing 4
#reading 15
#drawing 3
#friday 12
#school 6
#class 5
5
stop
Output Format
Once a query is appeared we need to write down the most popular hashtags of appeared number in to the output file in descending order. Output for a query should be comma separated list without any new lines. Once the output for a query is finished we should put a new line to write the output for another query. We need to produce all the outputs in the output file named “output_file.txt”.
Following is the output file for the above input file.
saturday,sunday,monday
saturday,sunday,reading,friday,monday
