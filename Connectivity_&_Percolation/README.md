# Percolation Phase Transitions in Graphs

This repository contains scripts and code in **R** for studying phase transitions in graphs subjected to node or edge percolation processes. The scripts and results are stored in the `/Fonts` directory, where experimental data and analysis are performed.

## 1. Introduction
All the documentation is originally written in catalan language and can be found in `ConnectivitatIPercolacio_g22.pdf`.

### 1.1 Objectives

The main goal of this project is to carry out an experimental study of the existence of phase transitions in graphs when subjected to percolation processes on nodes or edges. Specifically, we aim to study the phase transition concerning the number of connected components in graphs after undergoing a percolation process.

To achieve this, the project has been divided into several tasks:

- **Understanding Percolation Concepts:**  
  The first step involves mastering the concepts of the project, including node and edge percolation and their impact on graph properties such as connectivity.

- **Planning and Developing Algorithms:**  
  For the experimental part, algorithms need to be developed to generate the different graph samples required to analyze the data. This includes percolation algorithms and data extraction methods. Additionally, an efficient algorithm for calculating the number of connected components for each graph type is essential.

- **Conducting Experiments with Different Graphs:**  
  Using the developed algorithms, experiments will be conducted on different graph models by varying the probability \( q \) (the probability that a node does not fail) and the number of nodes in the graphs. Data will be extracted for each graph after the percolation process.

- **Studying Phase Transitions:**  
  With the extracted data, the graph properties will be analyzed to compare different models and determine whether phase transitions occur concerning the number of connected components. The aim is to identify whether there exists a critical value of \( q \) beyond which a drastic change occurs in the graph structure.

## 2. Directory Structure

- `/Fonts`: Contains the R scripts and experimental data used to perform the percolation studies and generate results.
- `README.md`: Project overview and instructions.

## 3. Group Members

- Daniel Ermoshin Danichkin  
- Otman Ezzayat Maid  
- Joan Gómez Català  
- Guillermo O’Tuama Pascual  

- ([daniel.ermoshin@estudiantat.upc.edu]())  
- ([otman.ezzayat@estudiantat.upc.edu]())  
- ([joan.gomez.catala@estudiantat.upc.edu]())  
- ([guillermo.o.tuama@estudiantat.upc.edu]())
