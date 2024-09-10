Flash news: You can implement Rock Paper Scissors in multiple ways! The key here is that deterministic inputs leads to deterministic outputs.

Here are a couple (proposed) ways to implement RPS:
1) the normal way. BORING! (3 comparisons needed)
2) Use a quadratic equation to convert player two's input so that "p1" = "p2" when player one wins. (2 comparisons needed)
3) Convert player input into a two digit number (e.g. 1, 2 -> 12) and have the following EQs: 
  1) (x - 13) (x - 21) (x - 32) = 0 when P1 wins
  2) (x - 31) (x - 12) (x - 23) = 0 when P2 wins
4) take input as a string of bits and use bitwise operators to hack your way to the answer (I'm not going to implement this)
5) use a single polynomial equation and immediately output what is approximately the answer (round it afterwards)

I don't know which way is the fastest, so I gotta test it out! (That's what this program is for.)

Thanks to my friend David's math equation ideas, I've gotten a couple interesting algorithms!
