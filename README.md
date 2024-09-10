David and I have found a way to reduce the number of if statements Rock Paper Scissors needs from 4 to 2!

If rock = 1, paper = 2, scissors = 3; P1 (x axis) - P2 (y axis) = 
    R  P  S P2
    1  2  3
R 1 0 -1 -2
P 2 1  0 -1
S 3 2  1  0
P1

Issue: when P1 wins, the number is either -2 or 1! This will require two if statements to run.

Solution: make rock = 2, paper = 3, scissors = 1 with a quadratic equation! (cannot be factored, unfortunately)
     R  P  S P2
     2  3  1
R 1 -1 -2  0
P 2  0 -1  1
S 3  1  0  2
P1

All P1 wins and only P1 wins are equal to 0! Yay! (p1 = p2)

Result: Program takes two if statements:
1) Check for a tie (p1 = p2)
2) Check for P1 winning (p1 = modified p2)
3) Assume default case (P2 wins)
