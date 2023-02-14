#!/bin/sh

opennlp DoccatTrainer -model models/en-doccat.bin -lang en -data data/en-doccat.train -encoding UTF-8
