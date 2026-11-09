package ru.vsu.cs.oop.german_v_v.quad_tree;

import java.util.List;
import java.util.Random;

public class Main {
    public static void main(String[] args) {
        Rectangle world = new Rectangle(0,0,200,200);
        QuadTree qt = new QuadTree(world,4);
        Random rand = new Random(42);

        for (int i = 0; i < 100; i++) {
            qt.insert(new Point(rand.nextDouble() * 400 - 200, rand.nextDouble() * 400 - 200));
        }

        Rectangle range = new Rectangle(50,50, 40, 40);
        List<Point> found = qt.query(range);
        System.out.println("Найдено точек: " + found.size());
        for (Point p : found) System.out.println(" " + p);
    }
}
