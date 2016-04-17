import sys
import numpy as np
from mpl_toolkits.mplot3d import Axes3D
import matplotlib.pyplot as plt


def plt3d(inputFile, xyz_labels):
    fig = plt.figure()
    ax = fig.add_subplot(111, projection='3d')

    markers = ['o','<','8','D','*','<','p','D']
    colors = ['b','g','r','c','m','y','k','b']

    print 'hello'
    with open(inputFile) as f:
        for line in f.readlines():
            content = line.split()
            ax.scatter(float(content[0]),float(content[1]),float(content[2]),c=colors[int(content[3])],
                marker=markers[int(content[3])], s=60)

    ax.set_xlabel(xyz_labels[0])
    ax.set_ylabel(xyz_labels[1])
    ax.set_zlabel(xyz_labels[2])

    plt.show()

if __name__ == "__main__":
    plt3d('kmeans_part1-4_loandata.txt', ('AGE', 'CREDIT SCORE', 'INCOME'))
    plt3d('graph_part2-4_studentdata.txt', ('AGE', 'GPA', 'CREDITS'))
