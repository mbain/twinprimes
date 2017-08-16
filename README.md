# twinprimes
This is a simple Twin Prime calculator in Java.

The bulk of the runtime complexity is in the process of finding all prime numbers up to the maximum specified by the user.  This implementation of finding primes gives the time complexity an upper bound less than O(N^1.5)/2.  More efficient algorithms will be tested for a future version.

The program displays a progress bar while searching for prime numbers when the user-specified maximum number exceeds 1 Million.  On a relatively modern computer, runtime for smaller maximum numbers should be almost instantaneous. The progress bar is 80 characters wide, to fit in a standard 24x80 character console window.  Therefore, a new "•" character is added for every 1.25% of progress.

Since there are far fewer prime numbers than there are total numbers, the subsequent search for twin primes has a much smaller data set to work with, and runs in linear time.  The search for twin primes in the list of primes takes an almost imperceptible amount of time in comparison to the search for all primes, so a progress bar is not displayed for this portion of the program.

The program saves the twin prime pairs to a CSV output file.  The full path of the file is displayed when it attempts to save.

There is additional code in the source that is commented-out.  This code allows you to display the twin prime pairs to STDOUT (console/display), and also does some math to show a unique property of twin primes - that when you multiply the two primes together, that the product mod 9 equals 8, with the exception of 3 and 5.  Un-commenting this code will cause this to display.

Enjoy, and please feel free to submit pull requests for any desired improvements.
