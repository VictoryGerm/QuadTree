package ru.vsu.cs.oop.german_v_v.quad_tree;

import java.util.ArrayList;
import java.util.List;
public class QuadTree {
    private final Rectangle boundary; //границы этого участка(коробка)
    private final int capacity; //сколько точек влезет до деления
    private final List<Point> points = new ArrayList<>(); //сами точки
    private boolean divided = false; //поделен ли этот участок на 4 части

    //4 квадранта, дочерние узлы
    private QuadTree northeast;
    private QuadTree northwest;
    private QuadTree southeast;
    private QuadTree southwest;

    //КОНСТРУКТОР, СОЗДАЕМ НОВУЮ КОРОБКУ
    public QuadTree(Rectangle boundary, int capacity) {
        if (boundary == null) throw new IllegalArgumentException("boundary == null");
        if (capacity < 1) throw new IllegalArgumentException("capacity must be >= 1");
        this.boundary = boundary;
        this.capacity = capacity;
    }

    //ВСТАВЛЯЕМ ТОЧКИ
    public boolean insert(Point p) {
        if (!boundary.contains(p)) { //если точка не влезает в границы этой коробки
            return false;
        }
        if (points.size() < capacity) { //если в коробке есть еще место, то кладем точку туда
            points.add(p);
            return true;
        }
        if (!divided) { //если места нет,а коробка не поделена - делим ее
            subdivide();
        }
        return northeast.insert(p)
                || northwest.insert(p)
                || southeast.insert(p)
                || southwest.insert(p);
    }

    //ДЕЛЕНИЕ КОРОБКИ НА 4 ЧАСТИ
    private void subdivide() {
        double x = boundary.getX();
        double y = boundary.getY();
        double w = boundary.getW() / 2; //т.к. новые половинки в 2 раза меньше
        double h = boundary.hetH() / 2;

        //создаем 4 новые коробки
        northeast = new QuadTree(new Rectangle(x+w, y-h, w, h), capacity);
        northwest = new QuadTree(new Rectangle(x-w, y-h, w, h), capacity);
        southeast = new QuadTree(new Rectangle(x+w, y+h, w, h), capacity);
        southwest = new QuadTree(new Rectangle(x-w, y+h, w, h), capacity);

        divided = true;
    }

    //ПОИСК ТОЧЕК В ОБЛАСТИ
    public List<Point> query(Rectangle range) {
        List<Point> found = new ArrayList<>();
        query(range, found);
        return found;
    }

    private void query(Rectangle range, List<Point> found) {
        if (!boundary.intersects(range)) { // если рамка поиска не пересекает коробку - выходим
            return;
        }

        //проверяем точки, лежащие непосредственно в коробке
        for (Point p : points) {
            if (range.contains(p)) {
                found.add(p);
            }
        }
        //если коробка поделена, проверяем каждого из "детей"
        if (divided) {
            northeast.query(range, found);
            northwest.query(range, found);
            southeast.query(range, found);
            southwest.query(range, found);
        }
    }

    //ВСПОМОГАТЕЛЬНЫЙ МЕТОД ДЛЯ ВЫВОДА ДЕРЕВА В КОНСОЛЬ
    public void printTree() {
        printTree(0);
    }

    private void printTree(int depth) {
        String indent = "   ".repeat(depth);
        System.out.println(indent + "|-" + boundary + " points=" + points.size() + (divided ? " [divided]" : ""));
        for (Point p : points) System.out.println(indent + "|    * " + p);
        if (divided) {
            northeast.printTree(depth + 1);
            northwest.printTree(depth + 1);
            southeast.printTree(depth + 1);
            southwest.printTree(depth + 1);
        }
    }
}
