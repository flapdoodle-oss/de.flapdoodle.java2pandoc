#!/bin/sh

pandoc $1 -s --highlight-style tango -o $1.pdf
