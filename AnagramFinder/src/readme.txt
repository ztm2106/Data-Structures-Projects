Q: How long does your program take to produce the answer when using the bst, avl, and hash flags?

A: From my AnagramFinder program time analysis, the hash map results in the fasted time (average real time = 1.236s) to produce the answer.

Q: What data structure do you expect to have the best (fastest) performance? Which one do you expect
to be the slowest? Do the results of timing your program’s execution match your expectations? If so,
briefly explain the correlation.

A: I expected the hash map data structure to perform the best (fastest) because the time complexity for hash map retrieval takes O(1).
Comparing this time complexity of retrieval to bst O(treeHeight) and avl O(smallestTreeHeight),
hash map has the better time complexity overall. From this analysis, I expected bst to perform the worst (slowest).
The results of timing my program’s execution does match your expectations where bst (average real time = 4.084s) > avl (average real time = 2.494s) > hash (average real time = 1.236s).
Hence, hash has the fastest execution time and bst has the slowest execution time.

bst  3.77s user 0.21s system 111% cpu 3.576 total
bst  4.00s user 0.32s system 107% cpu 4.034 total
bst  4.06s user 0.24s system 108% cpu 3.962 total
bst  4.54s user 0.39s system 102% cpu 4.816 total
bst  4.05s user 0.28s system 102% cpu 4.236 total
average real time = 4.084s

avl  1.88s user 0.18s system 148% cpu 1.391 total
avl  1.82s user 0.17s system 150% cpu 1.318 total
avl  3.48s user 0.41s system 77% cpu 5.040 total
avl  2.73s user 0.39s system 137% cpu 2.272 total
avl  2.56s user 0.26s system 145% cpu 1.936 total
average real time = 2.494s

hash  1.00s user 0.15s system 167% cpu 0.690 total
hash  1.41s user 0.21s system 152% cpu 1.059 total
hash  1.67s user 0.35s system 145% cpu 1.388 total
hash  1.18s user 0.17s system 173% cpu 0.776 total
hash  0.92s user 0.13s system 158% cpu 0.664 total
average real time = 1.236s



