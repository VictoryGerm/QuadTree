package ru.vsu.cs.oop.german_v_v.quad_tree;

public class Rectangle {
    private final double x;
    private final double y;
    private final double w;
    private final double h;

    public Rectangle(double x, double y, double w, double h) {
        this.x = x;
        this.y = y;
        this.w = w;
        this.h = h;
    }

    // Проверим, лежит ли точка внутри прямоугольника
    public boolean contains(Point p) {
        return p.getX() >= this.x - this.w && p.getX() < this.x + this.w &&
                p.getY() >= this.y - this.h && p.getY() < this.y + this.h;
    }

    // Проверим, пересекаются ли два прямоугольника
    public boolean intersects(Rectangle range) {
        return !(range.x - range.w > this.x + this.w ||
                range.x + range.w < this.x - this.w ||
                range.y - range.h > this.y + this.h ||
                range.y + range.h < this.y - this.h);
    }

    // Геттеры
    public double getX() {return x;}
    public double getY() {return y;}
    public double getW() {return w;}
    public double hetH() {return h;}
}
