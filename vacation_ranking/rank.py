#! /usr/bin/env python3

import sys
import pandas as pd
import time
from functools import cmp_to_key
from blessed import Terminal

term = Terminal()

count = 0

def get_preference(a, b):
    global count
    count += 1

    print("")
    print(term.home + term.clear + term.move_y(term.height // 2 - 2))
    print(term.black_on_darkkhaki(term.center(f"{a.upper()}    vs    {b.upper()}")) + "\n")
    print(term.center(f"[A]            [L]") + "\n")
    print(term.center(f"[SPACE] if indifferent"))

    inp = None
    while inp not in ['a', 'l', ' ']:

        with term.cbreak(), term.hidden_cursor():
            inp = term.inkey().lower()

    print("\n" + term.black_on_darkkhaki(term.center(term.bold(repr(inp)))))

    if inp == 'a':
        selection = a.upper()
    elif inp == 'l':
        selection = b.upper()
    else:
        selection = "WHO CARES"

    print(term.home + term.clear + term.move_y(term.height // 2 - 2))
    print(term.black_on_darkkhaki(term.center(selection)) + "\n")

    time.sleep(0.2)

    return {'a': -1, 'l': 1, ' ': 0}[inp]


def handle_results(ranked_list, outfile):
    print(term.home + term.clear)
    print(term.move_y(2) + term.center("Your top 10 destinations are") + "\n")
    for i, el in enumerate(ranked_list[:10]):
        print(term.center(f"{i+1}. {el}"))
    print("\n" + term.black_on_darkkhaki(term.center(
        f"Should this ranking be saved to '{outfile}'? [Y] or [N]")))
    inp = None
    while inp not in ['y', 'n']:
        with term.cbreak(), term.hidden_cursor():
            inp = term.inkey().lower()
    print("\n\n")
    if inp == 'y':
        print(term.center(f"Cool. Saving to '{outfile}'"))
        try:
            with open(outfile, 'w') as f:
                for line in ranked_list:
                    f.write(f"{line}\n")
        except:
            print("\nOh no! Failed to write to file.\n")
            for line in ranked_list:
                print(line)
    else:
        print(term.center("Bummer."))
    print("\n\n")
    time.sleep(2)
    print(term.home + term.clear + "Thanks for playing.")



def main():
    df = pd.read_csv(sys.argv[1], keep_default_na=False)
    outfile = sys.argv[2]
    locs = list(df.Locations)
    slocs = sorted(locs, key=cmp_to_key(get_preference))
    handle_results(slocs, outfile)


if __name__ == "__main__":
    main()

