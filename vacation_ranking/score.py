#! /usr/bin/env python3

import sys
import pandas as pd
import math


def read_lines(filename):
    with open(filename, 'r') as f:
        return f.read().splitlines()


def score_option(opt, rankings):
    indices = []
    for person in rankings:
        row = list(rankings[person])
        i = math.sqrt(row.index(opt))
        indices.append(i)
    return sum(indices)


def main():

    infiles = sys.argv[1:]

    d = {}

    un = set()

    for infile in infiles:
        lines = read_lines(infile)
        d[infile] = lines
        un = un | set(lines)

    df = pd.DataFrame(d)

    rankings = []

    for u in un:
        score = score_option(u, df)
        rankings.append((u, score))
    
    rankings = sorted(rankings, key=lambda x: x[1])

    for ranking in rankings:
        print(ranking)



if __name__ == "__main__":
    main()
